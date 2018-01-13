package com.mcml.space.VUAPIGUI;

import com.mcml.space.Utils.TCPUtils;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.*;

public class UserWindow {

    public static JFrame UserFrame;
    private static JLabel MessageLJ;
    public static Thread WaitingLineThread;
    public static String ServerIP;

    public static void Start() {
        UserFrame = new JFrame("VUpdateAPI ——客户端 |正在进行工作");
        UserFrame.setLayout(null);
        UserFrame.setSize(800, 600);
        UserFrame.setLocation(300, 200);
        UserFrame.setLocationRelativeTo(null);
        UserFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel projectnameJL = new JLabel("项目名字");
        projectnameJL.setSize(100, 30);
        projectnameJL.setLocation(100, 100);
        projectnameJL.setFont(new Font("宋体", Font.BOLD | Font.ITALIC, 12));
        UserFrame.add(projectnameJL);

        final JTextField projectnameField = new JTextField(20);
        projectnameField.setSize(300, 30);
        projectnameField.setLocation(180, 100);
        projectnameField.setText("填入你的插件名字");
        UserFrame.add(projectnameField);

        JLabel versionJL = new JLabel("记录版本号");
        versionJL.setSize(100, 30);
        versionJL.setLocation(100, 150);
        versionJL.setFont(new Font("宋体", Font.BOLD | Font.ITALIC, 12));
        UserFrame.add(versionJL);

        final JTextField versionField = new JTextField(20);
        versionField.setSize(300, 30);
        versionField.setLocation(180, 150);
        versionField.setText("填入一个纯数字");
        UserFrame.add(versionField);

        JLabel downloadJL = new JLabel("下载地址");
        downloadJL.setSize(100, 30);
        downloadJL.setLocation(100, 200);
        downloadJL.setFont(new Font("宋体", Font.BOLD | Font.ITALIC, 12));
        UserFrame.add(downloadJL);

        final JTextField downloadField = new JTextField(20);
        downloadField.setSize(300, 30);
        downloadField.setLocation(180, 200);
        downloadField.setText("填入http文件地址");
        UserFrame.add(downloadField);

        JLabel PWJL = new JLabel("项目密码");
        PWJL.setSize(100, 30);
        PWJL.setLocation(100, 250);
        PWJL.setFont(new Font("宋体", Font.BOLD | Font.ITALIC, 12));
        UserFrame.add(PWJL);

        final JTextField PWField = new JTextField(20);
        PWField.setSize(300, 30);
        PWField.setLocation(180, 250);
        PWField.setText("请记住密码来更新你的服务器数据");
        UserFrame.add(PWField);

        JLabel QQJL = new JLabel("作者QQ");
        QQJL.setSize(100, 30);
        QQJL.setLocation(100, 300);
        QQJL.setFont(new Font("宋体", Font.BOLD | Font.ITALIC, 12));
        UserFrame.add(QQJL);

        final JTextField QQField = new JTextField(20);
        QQField.setSize(300, 30);
        QQField.setLocation(180, 300);
        QQField.setText("确保我们在出现瓜皮问题的时候联系你");
        UserFrame.add(QQField);

        final JButton connectbt = new JButton("提交到服务器");
        connectbt.setSize(300, 50);
        connectbt.setLocation(200, 500);
        UserFrame.add(connectbt);

        connectbt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                String projectname = projectnameField.getText();
                String version = versionField.getText();
                String download = downloadField.getText();
                String PW = PWField.getText();
                String QQ = QQField.getText();
                if (version.matches("[0-9]*") == false) {
                    UserWindow.SendMessage("错误，你的版本号填写的不是一个纯数字！");
                    return;
                }
                if (download.startsWith("http") == false) {
                    UserWindow.SendMessage("错误！你的下载地址写入的不是一个http地址！");
                    return;
                } else if (download.startsWith("https")) {
                    UserWindow.SendMessage("抱歉！我们暂不支持https服务器下载文件！");
                    return;
                }
                if (QQ.matches("[0-9]*") == false) {
                    UserWindow.SendMessage("错误，你的QQ号填写的不是一个QQ号！");
                    return;
                }
                if(projectname.contains("-")){
                    projectname = projectname.replaceAll("-", "§");
                }
                if(download.contains("-")){
                    download = download.replaceAll("-", "§");
                }
                if(PW.contains("-")){
                     PW = PW.replaceAll("-", "§");
                }
                try {
                    TCPUtils.sendOut("uploadData-" + projectname + "-" + version + "-" + download + "-" + PW + "-" + QQ);
                    UserWindow.SendMessage("已将数据提交给服务器，等待服务器答复！");
                } catch (IOException ex) {
                    UserWindow.SendMessage("无法将数据提交给服务器！服务器断线！");
                }
            }
        });

        UserFrame.setVisible(true);
    }

    public static void Close() {
        UserFrame.setVisible(false);
    }

    public static void SendMessage(String message) {
        if (MessageLJ != null) {
            MessageLJ.setText(message);
        } else {
            MessageLJ = new JLabel(message);
            MessageLJ.setSize(500, 100);
            MessageLJ.setLocation(150, 400);
            MessageLJ.setFont(new Font("宋体", Font.BOLD | Font.ITALIC, 12));
            UserFrame.add(MessageLJ);
        }
        UserFrame.repaint();
    }
}