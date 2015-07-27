package com.ken.musicplayer.dao;

import java.util.ArrayList;
import java.util.List;

import com.ken.musicplayer.model.Music;

import android.app.Activity;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

public class MusicList {

	public static List<Music> getMusicData(Context context){
		List<Music> lm =new ArrayList<Music>();
		ContentResolver cr=context.getContentResolver();
		if(cr!=null){
			//��ȡ���и���
			
			//query������ȡ��Ƶ�ļ��� cursorָ��ָ�ڵ�һ������
	    	Cursor cursor=cr.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
	    			null, null,null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
	    	if(null==cursor){
	    		return null;
	    	}
	    	if(cursor.moveToFirst()){
	    		do{
	    			Music m=new Music();
	    	     String title=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
	    	     String singer=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
	    	     if("<unknown>".equals(singer)){
						singer="δ֪������";
					}
	    	     String album=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
	    	     long size=cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));
	    	     long time=cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
	    	     String url=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
	    	     String name=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
	    	     m.setTitle(title);
	    	     m.setSinger(singer);
	    	     m.setAlbum(album);
	    	     m.setSize(size);
	    	     m.setTime(time);
	    	     m.setUrl(url);
	    	     m.setName(name);
	    	     lm.add(m);
	    		}while(cursor.moveToNext());	
	    	}	
    	}
		return lm;
		
	}
}


