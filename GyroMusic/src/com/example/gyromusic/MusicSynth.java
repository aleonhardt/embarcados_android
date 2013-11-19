package com.example.gyromusic;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;


public class MusicSynth extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_music_synth);


		final MusicTone music = new MusicTone();

		final Button sing = (Button)findViewById(R.id.buttonSing);

		sing.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				for(int i=1; i<=6; i++)
				{
					music.addTone(440*i);
				}


			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.music_synth, menu);		
		return true;
	}

}
