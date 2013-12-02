package com.example.gyromusic;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

public class MusicTone implements Runnable {

	public final static int[] NOTES = {Gyroscoping.DO, Gyroscoping.RE, Gyroscoping.MI,
		Gyroscoping.FA, Gyroscoping.SOL, Gyroscoping.LA, Gyroscoping.SI};
	
	public final static int TIME = 3;
		
	   private final int sampleRate = 44100;
	    private final int numSamples =AudioTrack.getMinBufferSize(sampleRate, AudioFormat.CHANNEL_OUT_STEREO, 
	    		AudioFormat.ENCODING_PCM_16BIT)*TIME;
	    private short sample[] = new short [numSamples];

	    private int frequency =5;
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
	        int smooth = numSamples / 15;
   
	        // fill out the array
	        for (int i = 0; i < numSamples; ++i) {
	        	
	        	amp = smoothAmp(i, numSamples, smooth);
	        	sample[i] = (short) (amp*Math.sin(phase));
	            phase += twopi*freq/sampleRate;
	        }
	        	     	        
	        audioTrack.write(sample, 0, numSamples);
	        
	    }
	    
	    public void addSquareWave(final int freq) {
	    	
	    	double twopi = 8.*Math.atan(1.);
	        double phase = 0.0;
	        int amp = 10000;
	        int smooth = numSamples / 15;

	        // fill out the array
	        
	        for (int i = 0; i < numSamples; ++i) {
	        	
	        	amp = smoothAmp(i, numSamples, smooth);
	        	sample[i] = (short) ((amp*Math.sin(phase))+(amp*Math.sin(3*phase)/3)+(amp*Math.sin(5*phase)/5)+(amp*Math.sin(7*phase)/7)+(amp*Math.sin(9*phase)/9)+(amp*Math.sin(11*phase)/11));
	            phase += twopi*freq/sampleRate;
	        }
	        
        
	        	        	     	        
	        audioTrack.write(sample, 0, numSamples);
	        
	    
	    }    	
	    
	    // smooth beginning/end of sound wave
	    public int smoothAmp(int index, int numSamples, int smooth)
	    {
	    	if(index < smooth)
	    		return 10000*index/smooth;
	    	else if(index > numSamples - smooth)
	    	{
	    		return 10000*(numSamples - index)/smooth;
	    	}
	    	else
	    	{
	    		return 10000;
	    	}
	    }

	    public synchronized void frequency(int fr) {

	    	frequency = fr;
	    	for(int i=0; i<NOTES.length; i++)
	    		if(fr==NOTES[i])
	    			frequency =i;
	    	
	    	

	    	}
	    public synchronized void frequencyUp() {

	    	if(frequency<6)
	    		frequency++;

	    	}
	  
	    public synchronized void frequencyDown() {

	    	if(frequency>0)
	    		frequency--;

	    	}
	   
	    public synchronized void stop() {
	    	
	    	finished=true;
	        audioTrack.stop();
	    }
	    
	    public synchronized boolean isPlaying() {
	    	
	    	return !finished;
	    }
	    
	    
	    public synchronized void start() {
	    	
	    	finished=false;
	    	audioTrack.play();
	    }
	    
	    @Override
	    public void run(){
	    	while(!finished)
	    	{
	    		addPureSine(NOTES[frequency]);
	    	}
	    }
}
