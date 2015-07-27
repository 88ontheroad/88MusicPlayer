package com.ken.musicplayer.activity;


import com.example.musicplayer.activity.R;
import com.ken.musicplayer.service.PlayMusicService;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Resources;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabWidget;
import android.widget.TextView;


public class MainActivity extends TabActivity {
	private SeekBar seekBar;
	private MediaPlayer mp1; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
				
		TabHost tabHost = getTabHost();
		Resources res = getResources();
		Intent intent;
		
		intent = new Intent().setClass(this, MusicListActivity.class);
		tabHost.addTab(tabHost
				  .newTabSpec("全部歌曲")
				  .setIndicator("全部歌曲", res.getDrawable(R.drawable.item))// setIndicator()此方法用来设置标签和图表
				  .setContent(intent));
		
		intent = new Intent().setClass(this, ArtistsActivity.class);
		tabHost.addTab(tabHost
				  .newTabSpec("歌手")
				  .setIndicator("歌手", res.getDrawable(R.drawable.artist))// setIndicator()此方法用来设置标签和图表
				  .setContent(intent));
		
	//	intent = new Intent().setClass(this, AlbumsActivity.class);
		tabHost.addTab(tabHost
				  .newTabSpec("专辑")
				  .setIndicator("专辑", res.getDrawable(R.drawable.album))// setIndicator()此方法用来设置标签和图表
				  .setContent(intent));
		
	//	intent = new Intent().setClass(this, SongsActivity.class);
		tabHost.addTab(tabHost
				  .newTabSpec("最近播放")
				  .setIndicator("最近播放", res.getDrawable(R.drawable.history))// setIndicator()此方法用来设置标签和图表
				  .setContent(intent));
		
		tabHost.setCurrentTab(0);	
		
		//设置tabhost字体和颜色
		TabWidget tabWidget=this.getTabWidget();
        for (int i = 0; i < tabWidget.getChildCount(); i++) {
            TextView tv=(TextView)tabWidget.getChildAt(i).findViewById(android.R.id.title);
            //tv.setGravity(BIND_AUTO_CREATE);
            //tv.setPadding(10, 10,10, 10);
            tv.setTextSize(14);//设置字体的大小；
            tv.setTextColor(Color.WHITE);//设置字体的颜色；
        }

        
		
		seekBar=(SeekBar)findViewById(R.id.seekBar1);
		seekBar.setOnSeekBarChangeListener(new SeekBarListener());    
		
		
	}
	
	public void setMediaPlayer(MediaPlayer mp){
		mp1=mp; 
	}
	
	public MediaPlayer getMediaPlayer(){
		return mp1; 
	}
	
	
    //播放进度条
    class SeekBarListener implements OnSeekBarChangeListener{
    	//mp1=PlayMusicService.getMediaPlayer();
        
      @Override
    	public void onProgressChanged(SeekBar seekBar, int progress,
    			boolean fromUser) {
    		// TODO Auto-generated method stub
    	// fromUser判断是否用户改变的滑块的值
    	  Log.d("SeekBarListener", "###duration="+duration+", progress="+progress);
    	  if(progress>=duration){
    		  Log.d("SeekBarListener", "#####################################################");
    		  handler.removeCallbacks(updateThread);
    		  
    	  }
    		if(fromUser==true){
    			Log.d("SeekBarListener", "###mp1="+mp1);
    			mp1.seekTo(progress);   
    		}
    		
    		
    	}
    	@Override
      public void onStartTrackingTouch(SeekBar seekBar) {
          // TODO Auto-generated method stub        
      }

      @Override
      public void onStopTrackingTouch(SeekBar seekBar) {
          // TODO Auto-generated method stub        
      }    
    }
    
    
	//关闭线程
	//handler.removeCallbacks(updateThread);
//	((MainActivity)getParent()).handler.removeCallbacks(((MainActivity)getParent()).updateThread);
  
    private int duration;
    
    Handler handler=new Handler();
    
    public Runnable updateThread = new Runnable() {
		
		@Override
		public void run() {
			 Log.d("Runnable", "###Runnable Runnable");
            //获取歌曲播放最大长度
            seekBar.setMax(mp1.getDuration());
            duration=mp1.getDuration();
            //获取歌曲播放位置
            seekBar.setProgress(mp1.getCurrentPosition());
            //每次延迟100毫秒启动线程，如果播放完成停止handler
            if(mp1.getCurrentPosition() < duration)
            	handler.postDelayed(updateThread, 100);
		}
	};

//    class UpdateThread implements Runnable
//    {        
// 
//        private volatile boolean isRunning = true;
//    	@Override
//        public void run() {
//           
//            Log.d("Runnable", "###Runnable Runnable");
//            
//            //获取歌曲播放最大长度
//            seekBar.setMax(mp1.getDuration());
//            duration=mp1.getDuration();
//            //获取歌曲播放位置
//            //seekBar.setProgress(mp1.getCurrentPosition());
//            //每次延迟100毫秒启动线程
//            //handler.postDelayed(updateThread, 100);
//        	
//            while(isRunning) {
//            	seekBar.setProgress(mp1.getCurrentPosition());
//            	try {
//					Thread.sleep(100);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//            }
//        }
//        
//        public void stopUpdate() {
//        	this.isRunning = false;
//        }
//        
//        public boolean isRunning() {
//        	return isRunning;
//        }
//    }
    



}






