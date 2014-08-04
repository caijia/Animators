package com.cs.animators.dao.service;


import com.cs.animators.dao.db.PlayRecordManager;

import android.content.Context;

public class DaoFactory {
	
	public static PlayRecordManager getVideoRecordInstance(Context context)
	{
		return PlayRecordManager.getInstance(context);
	}
}
