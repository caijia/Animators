package com.cs.animators.dao.service;


import com.cs.animators.dao.db.PlayRecordManager;
import com.cs.animators.dao.db.VideoCollectManager;

import android.content.Context;

public class DaoFactory {
	
	public static PlayRecordManager getVideoRecordInstance(Context context)
	{
		return PlayRecordManager.getInstance(context);
	}
	
	public static VideoCollectManager getVideoCollectInstance(Context context)
	{
		return VideoCollectManager.getInstance(context);
	}
}
