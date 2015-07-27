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

public class ArtistList {

	public static List<Music> getMusicData(Context context){
		List<Music> lm =new ArrayList<Music>();
		ContentResolver cr=context.getContentResolver();
		if(cr!=null){
			//获取所有歌曲
			
			//query方法获取音频文件， cursor指针指在第一个数据
	    	Cursor cursor=cr.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
	    			null, null,null, MediaStore.Audio.Media.ARTIST + " DESC");	//以ARTIST升序排序
	    	if(null==cursor){
	    		return null;
	    	}
	    	
	    	
	    	if(cursor.moveToFirst()){	//moveToFirst()移动到第一行记录
	    		String singer;
	    		long SameMusicQty = 1;	//初始值置1
	    		singer = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
	    		
	    		while(cursor.moveToNext()){    			
	    			Music m=new Music();
	    			
		    		if(singer.equals(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)))){
		    			++SameMusicQty;
		    		}
		    		else{
		    			m.setSinger(singer);
		    			m.setSameMusicQty(SameMusicQty);
		    			lm.add(m);			    			
		    			SameMusicQty = 1;	//初始值置1
		    			singer = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)); //singer等于下一首
		    		}		    		
	    	     	    	     
	    		}
	    	}	
    	}
		return lm;
		
	}
}


