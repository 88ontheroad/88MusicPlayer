package com.ken.musicplayer.activity;


import java.security.PublicKey;
import java.util.List;















import com.example.musicplayer.activity.R;
import com.ken.musicplayer.dao.SelectedMusicList;
import com.ken.musicplayer.model.Music;
import com.ken.musicplayer.service.PlayMusicService;
import com.ken.musicplayer.service.PlayMusicService.LocalBinder;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SelectedMusicListActivity extends Activity {
	private ListView listView;
	
	ServiceConnection sc; 	 
	PlayMusicService localService=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_selectedmusiclist);	//��listView��layout
		
		Intent intent  = getIntent();	//���intent
		String selectedName    = intent.getStringExtra("name");
		//Toast.makeText(SelectedMusicListActivity.this, name + "", 1).show();
		

		listView = (ListView)this.findViewById(R.id.listAllMusic);
		
		List<Music> getMusicList = SelectedMusicList.getMusicData(getApplicationContext(),selectedName);	
		//����Music�� MusicList��
		//getApplicationContext()��Activity��һ�����������ص�ǰ���ж����������

		//�Զ���MusicAdapter������������ȡ����listmusic����ͨ������������item������
		final SelectedMusicAdapter adapter = new SelectedMusicAdapter(this,getMusicList);	
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub			
				localService.startPlay(((Music)adapter.getItem(position)).getUrl());
			}			
			
		});
		
        sc = new ServiceConnection() {
			@Override
			public void onServiceConnected(ComponentName name, IBinder service) {
				// TODO Auto-generated method stub
				localService = ((LocalBinder)service).getService(); 
			}
			@Override
			public void onServiceDisconnected(ComponentName name) {
				// TODO Auto-generated method stub				
			}              
        }; 
        
		Intent intent1 = new Intent(SelectedMusicListActivity.this,PlayMusicService.class);  
        this.getApplicationContext().bindService(intent1, sc, Context.BIND_AUTO_CREATE);          
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
		super.onDestroy();
		this.getApplicationContext().unbindService(sc);
	}	
	
}


class SelectedMusicAdapter extends BaseAdapter {
	
    private List<Music> listMusic;
    private Context context;
    
    public SelectedMusicAdapter(Context context,List<Music> listMusic){
    	this.context=context;	//this.context���ڵ�33�е�this
    	this.listMusic=listMusic;	//listMusic���ڵ�33�е�getMusicList
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
			convertView=LayoutInflater.from(context).inflate(R.layout.music_item, null);	//����������ʾ��layout
		}
		Music m=listMusic.get(position);	//get���ָ������λ�õ�Ԫ��
		//������
		TextView textMusicName=(TextView) convertView.findViewById(R.id.music_item_name);
		textMusicName.setText(m.getName());
		//����
		TextView textMusicSinger=(TextView) convertView.findViewById(R.id.music_item_singer);
		textMusicSinger.setText(m.getSinger());
	   //����ʱ��
		TextView textMusicTime=(TextView) convertView.findViewById(R.id.music_item_time);
		textMusicTime.setText(toTime((int)m.getTime()));
		return convertView;
	}
	  /**
			 * ʱ���ʽת��
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
