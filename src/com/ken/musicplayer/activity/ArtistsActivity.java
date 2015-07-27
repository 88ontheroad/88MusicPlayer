package com.ken.musicplayer.activity;

import java.util.List;






import com.example.musicplayer.activity.R;
import com.ken.musicplayer.dao.ArtistList;
import com.ken.musicplayer.dao.MusicList;
import com.ken.musicplayer.model.Music;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class ArtistsActivity extends Activity {
	private ListView listView;
	public String ArtistName;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_artist_list);	//带listView的layout
		
		listView = (ListView)this.findViewById(R.id.listAllArtist);
		List<Music> getMusicList = ArtistList.getMusicData(getApplicationContext());	
		//ArtistList类
		//getApplicationContext()是Activity的一个方法，返回当前运行对象的上下文
		
		
		//自定义MusicAdapter适配器，将读取出的listmusic内容通过适配器放在item界面上
		ArtistAdapter adapter = new ArtistAdapter(this,getMusicList);	
		listView.setAdapter(adapter);		
		
		//设置点击事件
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub			
				
				
				TextView textMusicSinger=(TextView) view.findViewById(R.id.music_item_singer);	//绑定textview位置
				String s = textMusicSinger.getText().toString().trim();	//获取textview的值
				//Toast.makeText(ArtistsActivity.this, position + s, 1).show();
				
				Intent intent = new Intent(ArtistsActivity.this, SelectedMusicListActivity.class);   //指定传递对象
				intent.putExtra("name", s);	//传递值
				startActivity(intent);
			}
			
			
		});
		
		
	}
}


class ArtistAdapter extends BaseAdapter {
	
    private List<Music> listMusic;
    private Context context;
    public ArtistAdapter(Context context,List<Music> listMusic){
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
		
		if(convertView==null){	//获取单个歌曲显示的layout，第一个的时候是空，所以第一个时候获取界面
			convertView=LayoutInflater.from(context).inflate(R.layout.artists_item, null);	
		}
		Music m=listMusic.get(position);	//get获得指定索引位置的元素

		//歌手
		TextView textMusicSinger=(TextView) convertView.findViewById(R.id.music_item_singer);
		textMusicSinger.setText(m.getSinger());
		
		//音乐名
		TextView textMusicQty=(TextView) convertView.findViewById(R.id.music_item_qty);
		textMusicQty.setText(m.getSameMusicQty()+ "首歌曲");

		return convertView;
	}

}