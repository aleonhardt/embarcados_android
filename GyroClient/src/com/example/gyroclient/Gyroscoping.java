package com.example.gyroclient;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;


public class Gyroscoping {

	public static final int PLAY = 1;
	public static final int PLAY_SINE = 2;
	public static final int FREQ_UP = 3;
	public static final int FREQ_DOWN = 4;
	public static final int ERROR = -1;
	
	public static final int DO = 220;
	public static final int RE = 247;
	public static final int MI = 262;
	public static final int FA = 294;
	public static final int SOL = 330;
	public static final int LA = 349;
	public static final int SI = 392;

	public Gyroscoping(){
		
	}
	
	private int mathAccelerometer(SensorEvent event)
	{
		float alpha = (float) 0.8;
		 float gravity[] = {0,0,0};
		 float linear_acceleration[] = {0,0,0};
		  // Isolate the force of gravity with the low-pass filter.
		  gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
		  gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
		  gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

		  // Remove the gravity contribution with the high-pass filter.
		  linear_acceleration[0] = event.values[0] - gravity[0];
		  linear_acceleration[1] = event.values[1] - gravity[1];
		  linear_acceleration[2] = event.values[2] - gravity[2];
		  
		  if(linear_acceleration[0]>5)
			  return DO;
		  else if(linear_acceleration[0]<-5)
			  return RE;
		  else if(linear_acceleration[1]>5)
			  return MI;
		  else if(linear_acceleration[1]<-5)
			  return FA;
		  else if(linear_acceleration[2]>15)
			  return SOL;
		  else if(linear_acceleration[2]<-15)
			  return LA;

		  else
			  return ERROR;
	}
	
	public static final float EPSILON = 0.000000001f;
	
	private int mathGyroscope(SensorEvent event)
	{
		
            // Axis of the rotation sample, not normalized yet.
            float axisX = event.values[0];
            float axisY = event.values[1];
            float axisZ = event.values[2];

                        
            System.out.println("X: "+axisX+" Y: "+axisY+" Z: "+axisX);
            if(axisX > 3)
            	return PLAY;
            if(axisY>3)
            	return FREQ_UP;
            if(axisZ>3)
            	return FREQ_DOWN;
            
            return ERROR;
            
	}

	public int doTheMath(SensorEvent event)
	{
		if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER)
		{
			return mathAccelerometer(event);
		}
		else if(event.sensor.getType() == Sensor.TYPE_GYROSCOPE)
		{
			return mathGyroscope(event);
		}
		return ERROR;
	}

	
	
}
