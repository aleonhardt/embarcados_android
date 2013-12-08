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
	
	Thread musicThread = null;
	MusicTone music = new MusicTone();
	private DataInputStream dataInputStream;
	private DataOutputStream dataOutputStream;
	boolean threadStarted = false;
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			try {
				serverSocket = new ServerSocket(PORT);
				socket = serverSocket.accept();
				dataInputStream = new DataInputStream(socket.getInputStream());
				dataOutputStream = new DataOutputStream(socket.getOutputStream());
				
				int frequency = Integer.parseInt(dataInputStream.readUTF());
				//updateSecondChannelFrequency(); //implementar de alguma forma

				/*music.frequency(frequency);
				
				if(musicThread== null || (!musicThread.isAlive() && threadStarted == true))
				{
					musicThread = new Thread(music);
					music.start();
					musicThread.start();
					threadStarted = true;
				}*/

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


}
