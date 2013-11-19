package com.example.gyromusic;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;


public class MusicSynth extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_music_synth);
		
		
		MusicTone music = new MusicTone();
		
		for(int i=1; i<=6; i++)
		{
			music.addTone(440*i);
		}
		
		music.stop();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.music_synth, menu);		
		return true;
	}

}
