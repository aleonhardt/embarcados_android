package com.example.gyroclient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import com.example.gyroclient.R;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MusicSynth extends Activity implements SensorEventListener {


	final Gyroscoping gyroMath = new Gyroscoping();
	public static final int PORT = 8888;
	Socket socket;
	private String serverIpAddress = "";
	private DataInputStream dataInputStream;
	private DataOutputStream dataOutputStream;

	private SensorManager mSensorManager;

	private Sensor mAccelerometer, mGyroscope;
	Thread musicThread = null;
	boolean threadStarted = false;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_music_synth);

		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mGyroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

		mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
		mSensorManager.registerListener(this, mGyroscope, SensorManager.SENSOR_DELAY_NORMAL);

		// Register our receiver for the ACTION_SCREEN_OFF action. This will make our receiver
		// code be called whenever the phone enters standby mode.
		IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
		registerReceiver(mReceiver, filter);

		dialogGetIp();

		final Button connect = (Button)findViewById(R.id.buttonConnect);
		connect.setOnClickListener(new View.OnClickListener() {


			@Override
			public void onClick(View v) {



				if(serverIpAddress.length()>2)
				{
					
					new Thread(new Runnable() {

						@Override
						public void run() {
							try {
								socket = new Socket(serverIpAddress, PORT);
								System.out.println("connected. IP: "+serverIpAddress);
								dataInputStream = new DataInputStream(socket.getInputStream());
								dataOutputStream = new DataOutputStream(socket.getOutputStream());
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

						}
					}).start();

				}


				//connect.setEnabled(false);
			}
		});



	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.music_synth, menu);		
		return true;
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub

		int whatToPlay = gyroMath.doTheMath(event);

		if(whatToPlay ==Gyroscoping.DO || whatToPlay ==Gyroscoping.RE || whatToPlay ==Gyroscoping.MI ||
				whatToPlay ==Gyroscoping.FA || whatToPlay ==Gyroscoping.SOL || whatToPlay ==Gyroscoping.LA || 
				whatToPlay ==Gyroscoping.SI)
		{
			try {

				if(dataOutputStream!=null)				
				{
					dataOutputStream.writeUTF(Integer.toString(whatToPlay));
					System.out.println("sent: "+Integer.toString(whatToPlay));
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}







	}

	private void dialogGetIp()
	{

		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("Server IP address");

		// Set an EditText view to get user input 
		final EditText input = new EditText(this);
		input.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_CLASS_TEXT);
		alert.setView(input);

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				serverIpAddress = input.getText().toString();
				System.out.println("IP: " +serverIpAddress);
			}
		});
		alert.show();
	}
	// BroadcastReceiver for handling ACTION_SCREEN_OFF.
	public BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			// Check action just to be on the safe side.
			if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
				// Unregisters the listener and registers it again.
				mSensorManager.unregisterListener(MusicSynth.this);
				mSensorManager.registerListener(MusicSynth.this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
				mSensorManager.registerListener(MusicSynth.this, mGyroscope, SensorManager.SENSOR_DELAY_NORMAL);
			}
		}
	};

	@Override
	protected void onResume() {
		super.onResume();
		mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
		mSensorManager.registerListener(this, mGyroscope, SensorManager.SENSOR_DELAY_NORMAL);
		IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
		registerReceiver(mReceiver, filter);
	}

	@Override
	protected void onPause() {
		super.onPause();
		if(socket!=null)
		{
			try {
				dataOutputStream.close();
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		mSensorManager.unregisterListener(this);
		unregisterReceiver(mReceiver);
	}

}
