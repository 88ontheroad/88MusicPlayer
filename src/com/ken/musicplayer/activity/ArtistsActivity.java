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
		setContentView(R.layout.activity_artist_list);	//��listView��layout
		
		listView = (ListView)this.findViewById(R.id.listAllArtist);
		List<Music> getMusicList = ArtistList.getMusicData(getApplicationContext());	
		//ArtistList��
		//getApplicationContext()��Activity��һ�����������ص�ǰ���ж����������
		
		
		//�Զ���MusicAdapter������������ȡ����listmusic����ͨ������������item������
		ArtistAdapter adapter = new ArtistAdapter(this,getMusicList);	
		listView.setAdapter(adapter);		
		
		//���õ���¼�
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub			
				
				
				TextView textMusicSinger=(TextView) view.findViewById(R.id.music_item_singer);	//��textviewλ��
				String s = textMusicSinger.getText().toString().trim();	//��ȡtextview��ֵ
				//Toast.makeText(ArtistsActivity.this, position + s, 1).show();
				
				Intent intent = new Intent(ArtistsActivity.this, SelectedMusicListActivity.class);   //ָ�����ݶ���
				intent.putExtra("name", s);	//����ֵ
				startActivity(intent);
			}
			
			
		});
		
		
	}
}


class ArtistAdapter extends BaseAdapter {
	
    private List<Music> listMusic;
    private Context context;
    public ArtistAdapter(Context context,List<Music> listMusic){
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
		
		if(convertView==null){	//��ȡ����������ʾ��layout����һ����ʱ���ǿգ����Ե�һ��ʱ���ȡ����
			convertView=LayoutInflater.from(context).inflate(R.layout.artists_item, null);	
		}
		Music m=listMusic.get(position);	//get���ָ������λ�õ�Ԫ��

		//����
		TextView textMusicSinger=(TextView) convertView.findViewById(R.id.music_item_singer);
		textMusicSinger.setText(m.getSinger());
		
		//������
		TextView textMusicQty=(TextView) convertView.findViewById(R.id.music_item_qty);
		textMusicQty.setText(m.getSameMusicQty()+ "�׸���");

		return convertView;
	}

}