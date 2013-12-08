package com.example.gyroclient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import com.example.gyromusic.R;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.Menu;
import android.view.View;
import android.widget.Button;


public class MusicSynth extends Activity implements SensorEventListener {

	final MusicTone music= new MusicTone();
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

		final Button sing = (Button)findViewById(R.id.buttonSing);
		sing.setText("START");

		sing.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				if(sing.getText().toString().equals("START")){

					if(musicThread== null || (!musicThread.isAlive() && threadStarted == true))
					{
						musicThread = new Thread(music);
						music.start();
						musicThread.start();
						threadStarted = true;
						sing.setText("STOP THIS NONSENSE");
					}
				}
				else
				{
					music.stop();
					sing.setText("START");
				}


			}
		});

		final Button connect = (Button)findViewById(R.id.buttonConnect);
		connect.setOnClickListener(new View.OnClickListener() {
			
	
			@Override
			public void onClick(View v) {
			//	musicServer = new MusicServer();
			//	Thread serverThread = new Thread(musicServer);
			//	serverThread.start();
				
				
			}
		});
		
		try {
			InetAddress serverAddr = InetAddress.getByName(serverIpAddress);
			socket = new Socket(serverAddr, PORT);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

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

		if(whatToPlay ==Gyroscoping.DO)
			music.frequency(Gyroscoping.DO);
		else if(whatToPlay ==Gyroscoping.RE)
			music.frequency(Gyroscoping.RE);
		else if(whatToPlay ==Gyroscoping.MI)
			music.frequency(Gyroscoping.MI);		
		else if(whatToPlay ==Gyroscoping.FA)
			music.frequency(Gyroscoping.FA);
		else if(whatToPlay ==Gyroscoping.SOL)
			music.frequency(Gyroscoping.SOL);
		else if(whatToPlay ==Gyroscoping.LA)
			music.frequency(Gyroscoping.LA);
		else if(whatToPlay ==Gyroscoping.SI)
			music.frequency(Gyroscoping.SI);

		if(whatToPlay == Gyroscoping.PLAY)
		{
			try {

				dataInputStream = new DataInputStream(socket.getInputStream());
				dataOutputStream = new DataOutputStream(socket.getOutputStream());
				
				dataOutputStream.writeUTF(Integer.toString(music.getFrequency()));

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}



		if(whatToPlay ==Gyroscoping.FREQ_UP)
			music.frequencyUp();
		if(whatToPlay ==Gyroscoping.FREQ_DOWN)
			music.frequencyDown();
		
		



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
		mSensorManager.unregisterListener(this);
		unregisterReceiver(mReceiver);
	}

}