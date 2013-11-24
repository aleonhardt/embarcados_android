package com.example.gyromusic;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

public class MusicTone {

	   private final int sampleRate = 44100;
	    private final int numSamples =AudioTrack.getMinBufferSize(sampleRate, AudioFormat.CHANNEL_OUT_MONO, 
	    		AudioFormat.ENCODING_PCM_16BIT);
	    private   short sample[] = new short [numSamples];

	    final AudioTrack audioTrack;

	    public MusicTone() {
	        audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
	                sampleRate, AudioFormat.CHANNEL_OUT_MONO,
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
	        	sample[i] = (short) ((amp*Math.sin(phase))+(amp*Math.sin(3*phase)/3)+(amp*Math.sin(5*phase)/5)+(amp*Math.sin(7*phase)/7)+(amp*Math.sin(9*phase)/9));
	            phase += twopi*freq/sampleRate;
	        }
	        	     	        
	        audioTrack.write(sample, 0, numSamples);
	        
	    }

	  
	    public void stop() {
	        audioTrack.stop();
	    }
}
