package com.mcml.space.VUAPIGUI;

import com.mcml.space.Utils.TCPUtils;
import com.mcml.space.Utils.Utils;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class LoginWindow {

    public static JFrame LoginFrame;
    private static JLabel MessageLJ;
    public static Thread WaitingLineThread;
    public static String ServerIP;

    public static void Start() {
        LoginFrame = new JFrame("VUpdateAPI ——客户端 |等待连接到更新公共服务器");
        LoginFrame.setLayout(null);
        LoginFrame.setSize(800, 600);
        LoginFrame.setLocation(300, 200);
        LoginFrame.setLocationRelativeTo(null);
        LoginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JLabel HelpInfoJL = new JLabel("作者乐乐|QQ：1207223090|有需要帮助可以联系 ");
        HelpInfoJL.setSize(600, 100);
        HelpInfoJL.setLocation(100, 50);
        HelpInfoJL.setFont(new Font("宋体", Font.BOLD | Font.ITALIC, 24));
        LoginFrame.add(HelpInfoJL);

        JLabel Connectjl = new JLabel("请输入服务器连接地址: ");
        Connectjl.setSize(400, 100);
        Connectjl.setLocation(100, 100);
        Connectjl.setFont(new Font("宋体", Font.BOLD | Font.ITALIC, 24));
        LoginFrame.add(Connectjl);

        final JTextField IPField = new JTextField(20);
        IPField.setSize(400, 50);
        IPField.setLocation(100, 250);
        IPField.setText("vuapi.relatev.com");
        LoginFrame.add(IPField);

        final JTextField PortField = new JTextField(20);
        PortField.setSize(50, 50);
        PortField.setLocation(550, 250);
        PortField.setText("51410");
        LoginFrame.add(PortField);

        final JButton connectbt = new JButton("连接到服务器!");
        connectbt.setSize(300, 50);
        connectbt.setLocation(200, 500);
        LoginFrame.add(connectbt);

        connectbt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                ServerIP = IPField.getText();
                try {
                    TCPUtils.Connect(ServerIP, Integer.parseInt(PortField.getText()));
                } catch (UnknownHostException ex) {
                    LoginWindow.SendMessage("无法解析到HOST域名，域名不存在?");
                    return;
                } catch (IOException ex) {
                    LoginWindow.SendMessage("无法连接到服务器，服务器离线?");
                    return;
                }
                LoginWindow.SendMessage("请等待..正在登入中...");
                try {
                    TCPUtils.sendOut("Login-" + Utils.ClientVersion);
                } catch (IOException ex) {
                    LoginWindow.SendMessage("无法向服务器发送信息！服务器离线？");
                    return;
                }
                LoginFrame.remove(connectbt);
                WaitingLineThread = new Thread() {

                    public void run() {
                        while (true) {
                            String RecIn;
                            try {
                                RecIn = TCPUtils.RecIn();
                            } catch (IOException ex) {
                                LoginWindow.SendMessage("无法尝试沟通到服务器！服务器离线？");
                                return;
                            }
                            if (RecIn.startsWith("sendLoginWindowMessage-")) {
                                String message = RecIn.substring(23, RecIn.length());
                                LoginWindow.SendMessage(message);
                            }
                            if (RecIn.startsWith("sendUserWindowMessage-")) {
                                String message = RecIn.substring(22, RecIn.length());
                                UserWindow.SendMessage(message);
                            }
                            if (RecIn.startsWith("LoginSuc-")) {
                                LoginWindow.Close();
                                UserWindow.Start();
                                UserWindow.SendMessage("已成功登入到服务器，现在可以提交数据了！");
                            }
                        }
                    }
                };
                WaitingLineThread.start();
            }
        });

        LoginFrame.setVisible(true);
    }

    public static void Close() {
        LoginFrame.setVisible(false);
    }

    public static void SendMessage(String message) {
        if (MessageLJ != null) {
            LoginFrame.remove(MessageLJ);
        }
        MessageLJ = new JLabel(message);
        MessageLJ.setSize(500, 100);
        MessageLJ.setLocation(150, 300);
        MessageLJ.setFont(new Font("宋体", Font.BOLD | Font.ITALIC, 12));
        LoginFrame.add(MessageLJ);
        LoginFrame.repaint();
    }
}