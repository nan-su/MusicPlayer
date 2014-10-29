package com.example.testmusic;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends Activity implements View.OnClickListener{
	
	DetailSong[] Songlist=new DetailSong[4];

	ImageButton btnNext,btnPrevious;
	ToggleButton btnPlay;
	ImageView sicon,artists;
	TextView stitle,showtime;
	SeekBar seek_bar;
	MediaPlayer player;
	Handler seekHandler=new Handler();
	double finaltime,timescape;
	int i;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initi();
		
		finaltime=player.getDuration();
		
		seekUpdation();
		seek_bar.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
				seekChange(arg0);
				return false;
			}
		});
		
		btnNext.setOnClickListener(this);
		btnPrevious.setOnClickListener(this);
		btnPlay.setOnClickListener(this);
	}
	public void initi()
	{
		btnNext=(ImageButton)findViewById(R.id.imgN);
		btnPrevious=(ImageButton)findViewById(R.id.imgP);
		btnPlay=(ToggleButton)findViewById(R.id.imgS);
		sicon=(ImageView)findViewById(R.id.imgIcon);
		artists=(ImageView)findViewById(R.id.imgArtists);
		stitle=(TextView)findViewById(R.id.txtTitile);
		seek_bar=(SeekBar)findViewById(R.id.seekBar1);
		showtime=(TextView)findViewById(R.id.songCurrentDurationLabel);
		
		//initialize array
		Songlist[0]=new DetailSong("Track1", R.drawable.track0, R.raw.track0);
		Songlist[1]=new DetailSong("Track2", R.drawable.track1, R.raw.track1);
		Songlist[2]=new DetailSong("Track3", R.drawable.track2, R.raw.track2);
		Songlist[3]=new DetailSong("Track4", R.drawable.track3, R.raw.track3);
		
		player=MediaPlayer.create(this, Songlist[0].getSongID());
		seek_bar.setMax(player.getDuration());
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		case R.id.imgN:
			i++;
			ChangeSong();
			if(i>4)
			{
				ImageButton btnN=(ImageButton)findViewById(R.id.imgN);
			btnN.setVisibility(View.INVISIBLE);
			}break;
		case R.id.imgP:
			i--;
			ChangeSong();break;
		case R.id.imgS:
			if (btnPlay.isChecked()) { // Checked - Pause icon visible
				player.start();
	        } else { // Unchecked - Play icon visible
	            player.pause();
	        }break;
			
		}
	}
	Runnable run= new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			seekUpdation();
			timescape=player.getCurrentPosition();
			double timeRemaining = finaltime - timescape;
			showtime.setText(String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining), TimeUnit.MILLISECONDS.toSeconds((long) timeRemaining) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining))));
			
			//durationHandler.postDelayed(this, 100);
		}
	};
	public void seekChange(View v)
	{
		if(player.isPlaying()){
			SeekBar sb=(SeekBar)v;
			player.seekTo(sb.getProgress());
		}
	}
	public void seekUpdation(){
		seek_bar.setProgress(player.getCurrentPosition());
		seekHandler.postDelayed(run, 1000);
	}
	public void ChangeSong()
	{
		player.reset();
		if(i>=0 && i<4)
		{
		
		 sicon.setImageResource(Songlist[i].getSongImageID());
		 artists.setImageResource(Songlist[i].getSongImageID());
		 stitle.setText(Songlist[i].getSongtitle());
		 player=MediaPlayer.create(this, Songlist[i].getSongID());
		 player.start();
		 if(i<0)
				btnPrevious.setEnabled(false);
		 
		
		
		}
		
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		
		super.onDestroy();
		player.stop();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/*public class SongsManager 
    {
        // SDCard Path
        //final String MEDIA_PATH = new String(MediaStore.Audio.Media.getContentUri("external").toString());
        private ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();

        public SongsManager()
        {

        }

        public ArrayList<HashMap<String, String>> getPlayList()
        {
         //   File home = new File(MEDIA_PATH);
            File home = Environment.getExternalStorageDirectory();

            if (home.listFiles(new FileExtensionFilter()).length > 0) 
            {

                for (File file : home.listFiles(new FileExtensionFilter())) 
                {
                    HashMap<String, String> song = new HashMap<String, String>();
                    song.put("songTitle", file.getName().substring(0, (file.getName().length() - 4)));
                    song.put("songPath", file.getPath());

                    // Adding each song to SongList
                    songsList.add(song);

                }
            }
            // return songs list array
            return songsList;

        }


        class FileExtensionFilter implements FilenameFilter 
        {
            public boolean accept(File dir, String name) 
            {
                return (name.endsWith(".mp3") || name.endsWith(".MP3"));
            }
        }

    }
*/
	
	
}
