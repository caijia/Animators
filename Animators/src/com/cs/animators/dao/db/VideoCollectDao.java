package com.cs.animators.dao.db;

import java.util.List;

import com.cs.animators.entity.HotItem;

public interface VideoCollectDao extends Dao<String,HotItem> {
	
	void saveOrUpdate(HotItem collect);
	
	List<HotItem> query(int limit , int page);
}
