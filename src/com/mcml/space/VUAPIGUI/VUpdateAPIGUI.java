package com.mcml.space.VUAPIGUI;

import com.mcml.space.Utils.TCPUtils;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VUpdateAPIGUI {

    public static void main(String[] args) {
        for (String arg : args) {
            if (arg.startsWith("-upload:")) {
                try {
                    TCPUtils.Connect("vuapi.relatev.com", 51410);
                } catch (UnknownHostException ex) {
                    Logger.getLogger(VUpdateAPIGUI.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(VUpdateAPIGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                String doUpload = arg.substring(8, arg.length());
                try {
                    TCPUtils.sendOut(doUpload);
                    System.out.println("成功发送请求包！");
                } catch (IOException ex) {
                    UserWindow.SendMessage("无法将数据提交给服务器！服务器断线！");
                }
                return;
            }
        }
        LoginWindow.Start();
    }
}
