package com.cs.animators.dao.db;

import com.cs.animators.dao.bean.VideoCollect;

public interface VideoCollectDao extends Dao<String,VideoCollect> {
	
	void saveOrUpdate(VideoCollect collect);
}
