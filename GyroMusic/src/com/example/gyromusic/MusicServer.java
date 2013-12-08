package com.example.gyromusic;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MusicServer implements Runnable{

	public static final int PORT = 8888;
	ServerSocket serverSocket;
	Socket socket;
	
	
	private DataInputStream dataInputStream;
	private DataOutputStream dataOutputStream;

	private int remoteFrequency;
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			try {
				serverSocket = new ServerSocket(PORT);
				socket = serverSocket.accept();
				dataInputStream = new DataInputStream(socket.getInputStream());
				dataOutputStream = new DataOutputStream(socket.getOutputStream());
				
				setRemoteFrequency(Integer.parseInt(dataInputStream.readUTF()));
				

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public int getRemoteFrequency() {
		return remoteFrequency;
	}
	public void setRemoteFrequency(int remoteFrequency) {
		this.remoteFrequency = remoteFrequency;
	}
	public boolean isConnected() {
		return socket.isConnected();
	}


}
