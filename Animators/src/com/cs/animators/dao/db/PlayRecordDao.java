package com.cs.animators.dao.db;

import com.cs.animators.dao.bean.VideoPlayRecord;

public interface PlayRecordDao extends Dao<Integer,VideoPlayRecord> {
	
	//根据videoid 查找有播放记录的 记录
	VideoPlayRecord queryLastPlayRecord(String videoId);
	
	//根据videoid 和 id 查找该视频的播放记录
	VideoPlayRecord queryVideoPlayRecord(String videoId , long id);
	
	//根据videoid 和 id 查找是否有记录
	void saveOrUpdate(VideoPlayRecord record);
	
	void delete(String videoId , long id );

}
