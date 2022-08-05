package com.cloud.yun.socket;

import java.io.*;
import java.net.Socket;

/**
 * @ClassName SocketClient
 * @Description SocketClient is to handle xxxx
 * @Author jack
 * @Date 7/21/2022 2:59 PM
 * @Version 1.0
 **/
public class SocketClient {
	public static void main(String[] args) {
		try {
			Socket socket = new Socket("localhost",8088);
			OutputStream outputStream = socket.getOutputStream();
			PrintWriter printWriter = new PrintWriter(outputStream);
			printWriter.write("客户端发送消息");
			printWriter.flush();
			socket.shutdownOutput();


			InputStream inputStream = socket.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			String info = null;
			while ((info=bufferedReader.readLine())!=null){
				System.out.println("接收来自服务端的返回信息：" + info);
			}
			bufferedReader.close();
			inputStream.close();
			printWriter.close();
			outputStream.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
