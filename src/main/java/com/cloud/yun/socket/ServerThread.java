package com.cloud.yun.socket;

import java.io.*;
import java.net.Socket;

/**
 * @ClassName ServerThread
 * @Description ServerThread is to handle xxxx
 * @Author jack
 * @Date 7/21/2022 3:09 PM
 * @Version 1.0
 **/
public class ServerThread extends Thread{

	private Socket socket = null;

	public ServerThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		InputStream is=null;
		InputStreamReader isr=null;
		BufferedReader br=null;
		OutputStream os=null;
		PrintWriter pw=null;
		try {
			is = socket.getInputStream();
			isr = new InputStreamReader(is);
			br = new BufferedReader(isr);

			String info = null;

			while((info=br.readLine())!=null){
				System.out.println("我是服务器，客户端说："+info);
			}
			socket.shutdownInput();

			os = socket.getOutputStream();
			pw = new PrintWriter(os);
			pw.write("服务器欢迎你");

			pw.flush();
		} catch (Exception e) {
			// TODO: handle exception
		} finally{
			//关闭资源
			try {
				if(pw!=null)
					pw.close();
				if(os!=null)
					os.close();
				if(br!=null)
					br.close();
				if(isr!=null)
					isr.close();
				if(is!=null)
					is.close();
				if(socket!=null)
					socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}