/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcml.space.Utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class TCPUtils {

    public static DataOutputStream out;
    public static DataInputStream input;
    public static Socket socket;
    public static boolean hadpong;
    public static String SelfSocketString;

    public static void Connect(String IP, int port) throws UnknownHostException, IOException {
        if (input != null && out != null) {
            return;
        }
//        try {
        System.out.println("网络客户端开始尝试连接...");
        socket = new Socket(IP, port);
        input = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
        System.out.println("成功建立连接！");
        return;
//        } catch (UnknownHostException ex) {
//            System.out.println("无法解析到Host，域名不存在?");
//        } catch (ConnectException ex) {
//            System.out.println("无法连接到服务器!服务器不存在或离线?");
//        } catch (IOException ex) {
//            Logger.getLogger(TCPUtils.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    public static void sendOut(String message) throws IOException {
        System.out.println("发送: " + message);
        out.writeUTF(message);
    }

    public static void DisConnect() throws IOException {
        socket.close();
        input = null;
        out = null;
        System.out.println("已经断开对已有服务器的连接.");
    }

    public static String RecIn() throws IOException {
        return input.readUTF();
    }
}
