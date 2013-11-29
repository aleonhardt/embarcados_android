package com.example.gyromusic;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

public class MusicTone implements Runnable {

	   private final int sampleRate = 44100;
	    private final int numSamples =AudioTrack.getMinBufferSize(sampleRate, AudioFormat.CHANNEL_OUT_STEREO, 
	    		AudioFormat.ENCODING_PCM_16BIT);
	    private   short sample[] = new short [numSamples];

	    private int frequency =440;
	    private boolean finished = false;
	   
	    
	    final AudioTrack audioTrack;

	    public MusicTone() {
	        audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
	                sampleRate, AudioFormat.CHANNEL_OUT_STEREO,
	                AudioFormat.ENCODING_PCM_16BIT, numSamples,
	                AudioTrack.MODE_STREAM);
	        audioTrack.play();
	        		
	           }

	    public void addPureSine(final int freq) {
	    	
	    	double twopi = 8.*Math.atan(1.);
	        double phase = 0.0;
	        int amp = 10000;
	        // fill out the array
	        for (int i = 0; i < numSamples; ++i) {
	        	sample[i] = (short) (amp*Math.sin(phase));
	            phase += twopi*freq/sampleRate;
	        }
	        	     	        
	        audioTrack.write(sample, 0, numSamples);
	        
	    }
	    
	    public void addSquareWave(final int freq) {
	    	
	    	double twopi = 8.*Math.atan(1.);
	        double phase = 0.0;
	        int amp = 10000;
	        // fill out the array
	        for (int i = 0; i < numSamples; ++i) {
	        	sample[i] = (short) ((amp*Math.sin(phase))+(amp*Math.sin(3*phase)/3)+(amp*Math.sin(5*phase)/5)+(amp*Math.sin(7*phase)/7)+(amp*Math.sin(9*phase)/9)+(amp*Math.sin(11*phase)/11));
	            phase += twopi*freq/sampleRate;
	        }
	        	     	        
	        audioTrack.write(sample, 0, numSamples);
	        
	    }

	    public synchronized void frequency(int fr) {

	    	frequency = fr;

	    	}
	    public synchronized void frequencyUp() {

	    	frequency = frequency+48;

	    	}
	  
	    public synchronized void frequencyDown() {

	    	frequency = frequency-48;

	    	}
	   
	    public synchronized void stop() {
	    	
	    	finished=true;
	        audioTrack.stop();
	    }
	    
	    @Override
	    public void run(){
	    	while(!finished)
	    	{
	    		addPureSine(frequency);
	    	}
	    }
}
