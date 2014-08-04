package com.cs.animators.dao.service;

import java.sql.SQLException;
import com.cs.animators.dao.bean.VideoPlayRecord;
import com.j256.ormlite.dao.Dao;
import android.content.Context;

public class VideoRecordManager {
	
	private VideoRecordManager()
	{
		
	}
	
	private VideoRecordManager manager = null ;
	
	public VideoRecordManager getInstance(){
		if(manager == null)
		{
			synchronized (VideoRecordManager.class) {
				if(manager == null)
				{
					manager = new VideoRecordManager();
				}
			}
		}
		return manager ;
	}
	
	public Dao<VideoPlayRecord, String>  getVRecordDao(Context context)
	{
		try {
			return new AnimSQLiteHelper(context).getDao(VideoPlayRecord.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null ;
	}
	
	
	
}
