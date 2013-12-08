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

	MusicServer()
	{
		try {
			serverSocket = new ServerSocket(PORT);

			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			try {

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
	public synchronized int getRemoteFrequency() {
		return remoteFrequency;
	}
	public synchronized void setRemoteFrequency(int remoteFrequency) {
		this.remoteFrequency = remoteFrequency;
	}
	public synchronized boolean isConnected() {
		if(socket == null)
			return false;
		return socket.isConnected();
	}
	public synchronized String getIP() {
		return serverSocket.getInetAddress().toString();
	}


}
