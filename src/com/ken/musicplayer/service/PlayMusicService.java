package com.ken.musicplayer.service;


import java.io.IOException;

import com.example.musicplayer.activity.R;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class PlayMusicService extends Service {
	private MediaPlayer mp;
//	private SeekBar seekBar;
	
    @Override  
    public IBinder onBind(Intent intent) {  
    	Log.d("PlayMusicService", "###PlayMusicService onBind");
        return new LocalBinder();          
    }     
       
    
    @Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}


	@Override
	public boolean onUnbind(Intent intent) {
		// TODO Auto-generated method stub
		Log.d("PlayMusicService", "###PlayMusicService onUnbind");
		stopPlay();
		mp.release();
		return super.onUnbind(intent);
	}


	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.d("PlayMusicService", "###PlayMusicService on Create");
		mp=new MediaPlayer();
	
		
		
		
        //seekBar=(SeekBar)LayoutInflater.from(R.layout.activity_main).inflate(R.layout.music_item, null).findViewById(R.id.seekBar1);
        //seekBar.setOnSeekBarChangeListener(new SeekBarListener());
        
        //View view=LayoutInflater.from(this).inflate(R.layout.activity_selecting_address_item, null);
        //Button btn=(Button) view.findViewByid(R.id.other);
        //R.id.other是别的XML中的ID
		
//		View view=LayoutInflater.from(this).inflate(R.layout.activity_main, null);
//		seekBar=(SeekBar)view.findViewById(R.id.seekBar1);
//		seekBar.setOnSeekBarChangeListener(new SeekBarListener());
	}


	public void startPlay(String url){  
		stopPlay();		
		try {
		    mp.reset();
			mp.setDataSource(url);
			mp.prepare();
			mp.start();
		} catch (Exception e) {
			e.printStackTrace();
		}	
    }     
    
    public void stopPlay(){  
    	if(mp.isPlaying()){
    		mp.stop();
		}	    	
    }
          
    public class LocalBinder extends Binder {  
    	public PlayMusicService getService() {  
            // Return this instance of LocalService so clients can call public methods  
            return PlayMusicService.this;  
        }     
    } 

    
    public MediaPlayer getMediaPlayer(){
    	return mp;
    }

    
}



