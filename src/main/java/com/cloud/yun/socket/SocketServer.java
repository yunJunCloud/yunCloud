package com.cloud.yun.socket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @ClassName SocketServer
 * @Description SocketServer is to handle xxxx
 * @Author jack
 * @Date 7/21/2022 3:07 PM
 * @Version 1.0
 **/
public class SocketServer {
	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(8088);
			Socket socket = new Socket();
			while (true){
				socket = serverSocket.accept();
				ServerThread serverThread = new ServerThread(socket);
				serverThread.start();
				InetAddress inetAddress = socket.getInetAddress();
				System.out.println("当前客户端的ip:"+ inetAddress.getHostAddress());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
