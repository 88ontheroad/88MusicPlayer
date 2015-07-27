package com.ken.musicplayer.activity;


import java.util.List;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.musicplayer.activity.R;
import com.ken.musicplayer.dao.MusicList;
import com.ken.musicplayer.model.Music;
import com.ken.musicplayer.service.PlayMusicService;
import com.ken.musicplayer.service.PlayMusicService.LocalBinder;


public class MusicListActivity extends Activity {
	private ListView listView;
	
	ServiceConnection sc; 	 
	PlayMusicService localService=null;
	int pos =0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mylist);	//带listView的layout

		listView = (ListView)this.findViewById(R.id.listAllMusic);
		List<Music> getMusicList = MusicList.getMusicData(getApplicationContext());	
		//定义Music， MusicList类
		//getApplicationContext()是Activity的一个方法，返回当前运行对象的上下文
		
		
		//自定义MusicAdapter适配器，将读取出的listmusic内容通过适配器放在item界面上
		final MusicAdapter adapter = new MusicAdapter(this,getMusicList);	
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
	
				Log.d("LocalService", "------localService="+localService);				
				localService.startPlay(((Music)adapter.getItem(position)).getUrl());
				//启动线程
	
				((MainActivity)getParent()).handler.post(((MainActivity)getParent()).updateThread);
				pos =position;
			}			
			
		});
		
        sc = new ServiceConnection() {
			@Override
			public void onServiceConnected(ComponentName name, IBinder service) {
				// TODO Auto-generated method stub
				localService = ((LocalBinder)service).getService(); 
				Log.d("LocalService", "======localService="+localService);
				((MainActivity)getParent()).setMediaPlayer(localService.getMediaPlayer());
				((MainActivity)getParent()).getMediaPlayer().setOnCompletionListener(new OnCompletionListener() {
					@Override
					public void onCompletion(MediaPlayer mp) {
						
						localService.startPlay(((Music)adapter.getItem(++pos)).getUrl());
						//启动线程
						((MainActivity)getParent()).handler.post(((MainActivity)getParent()).updateThread);
					}
				});
			}
			@Override
			public void onServiceDisconnected(ComponentName name) {
				// TODO Auto-generated method stub				
			}              
        }; 
        
		Intent intent = new Intent(MusicListActivity.this,PlayMusicService.class);  
        boolean flag=this.getApplicationContext().bindService(intent, sc, Context.BIND_AUTO_CREATE);  
        Log.d("MusicListActivity", "flag="+flag);
        
 
       
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();		
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		
		Log.d("MusicListActivity", "####onDestroy=");
		
		super.onDestroy();
		this.getApplicationContext().unbindService(sc);
	}
    
}


class MusicAdapter extends BaseAdapter {
	
    private List<Music> listMusic;
    private Context context;
    public MusicAdapter(Context context,List<Music> listMusic){
    	this.context=context;	//this.context等于第33行的this
    	this.listMusic=listMusic;	//listMusic等于第33行的getMusicList
    }
	public void setListItem(List<Music> listMusic){
		this.listMusic=listMusic;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listMusic.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return listMusic.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		if(convertView==null){
			convertView=LayoutInflater.from(context).inflate(R.layout.music_item, null);	//单个歌曲显示的layout
		}
		Music m=listMusic.get(position);	//get获得指定索引位置的元素
		//音乐名
		TextView textMusicName=(TextView) convertView.findViewById(R.id.music_item_name);
		textMusicName.setText(m.getName());
		//歌手
		TextView textMusicSinger=(TextView) convertView.findViewById(R.id.music_item_singer);
		textMusicSinger.setText(m.getSinger());
	   //持续时间
		TextView textMusicTime=(TextView) convertView.findViewById(R.id.music_item_time);
		textMusicTime.setText(toTime((int)m.getTime()));
		return convertView;
	}
	  /**
			 * 时间格式转换
			 * @param time
			 * @return
			 */
			public String toTime(int time) {
		        
				time /= 1000;
				int minute = time / 60;
				int hour = minute / 60;
				int second = time % 60;
				minute %= 60;
				return String.format("%02d:%02d", minute, second);
			}
}
