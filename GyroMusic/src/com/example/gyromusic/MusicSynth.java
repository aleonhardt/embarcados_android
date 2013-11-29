package com.example.gyromusic;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.widget.Button;


public class MusicSynth extends Activity implements SensorEventListener {

	final MusicTone music= new MusicTone();
	final Gyroscoping gyroMath = new Gyroscoping();
	private SensorManager mSensorManager;
	private Sensor mAccelerometer, mGyroscope;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_music_synth);

		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mGyroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

		mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
		mSensorManager.registerListener(this, mGyroscope, SensorManager.SENSOR_DELAY_NORMAL);

		final Button sing = (Button)findViewById(R.id.buttonSing);
	

		sing.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				music.addPureSine(Gyroscoping.DO);
				music.addPureSine(Gyroscoping.RE);
				music.addPureSine(Gyroscoping.MI);
				music.addPureSine(Gyroscoping.FA);
				music.addPureSine(Gyroscoping.SOL);
				music.addPureSine(Gyroscoping.LA);
				music.addPureSine(Gyroscoping.SI);

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
		gyroMath.accuracy(sensor,accuracy);
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub

		int whatToPlay = gyroMath.doTheMath(event);

		if(whatToPlay ==Gyroscoping.DO)
			music.addSquareWave(Gyroscoping.DO);
		else if(whatToPlay ==Gyroscoping.RE)
			music.addSquareWave(Gyroscoping.RE);
		else if(whatToPlay ==Gyroscoping.MI)
			music.addSquareWave(Gyroscoping.MI);		
		else if(whatToPlay ==Gyroscoping.FA)
			music.addSquareWave(Gyroscoping.FA);
		else if(whatToPlay ==Gyroscoping.SOL)
			music.addSquareWave(Gyroscoping.SOL);
		else if(whatToPlay ==Gyroscoping.LA)
			music.addSquareWave(Gyroscoping.LA);
		else if(whatToPlay ==Gyroscoping.SI)
			music.addSquareWave(Gyroscoping.SI);
		
		//music.addSquareWave(whatToPlay);
		
		//if(whatToPlay ==Gyroscoping.PLAY_SQUARE)
			//music.addSquareWave(220);

	}
	
	@Override
	protected void onResume() {
		super.onResume();
		mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
		mSensorManager.registerListener(this, mGyroscope, SensorManager.SENSOR_DELAY_NORMAL);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		mSensorManager.unregisterListener(this);
	}

}
