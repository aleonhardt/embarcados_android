package com.example.gyromusic;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;

import android.util.Log;

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

				if(socket == null)
				{
					socket = serverSocket.accept();
					dataInputStream = new DataInputStream(socket.getInputStream());
					dataOutputStream = new DataOutputStream(socket.getOutputStream());
				}
				
				try{
					setRemoteFrequency(Integer.parseInt(dataInputStream.readUTF()));

				} catch (EOFException e) {
					System.out.println("closing");
					dataInputStream.close();
					socket.close();
					serverSocket.close();
					return;
				}

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
		return !socket.isClosed();
	}
	public synchronized String getLocalIpAddress() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress() && 
							!inetAddress.isLinkLocalAddress() && 
							inetAddress.isSiteLocalAddress()) {
						return inetAddress.getHostAddress();
					}
				}
			}
		} catch (SocketException ex) {
			Log.e("err", ex.toString());
		}
		return null;
	}


}
