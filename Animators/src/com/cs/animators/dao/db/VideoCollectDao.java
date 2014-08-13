package com.cs.animators.dao.db;

import com.cs.animators.entity.HotItem;

public interface VideoCollectDao extends Dao<String,HotItem> {
	
	void saveOrUpdate(HotItem collect);
}
