package com.cs.animators.dao.common;

public class TableUtil {

	public static final String DATABSE_NAME = "animation.db";
	public static final int VERSION = 1;

	// VideoPlayRecord数据表的定义
	public static class TablePlayRecord {
		public static final String TABLE_NAME = "PlayRecord";
		public static final String PLAYRECORD_ID = "playrecord_id";
		public static final String ID = "id";
		public static final String VIDEO_ID = "video_id";
		public static final String PLAY_RECORD = "play_record";
		public static final String SERIES = "series";
		public static final String RECORD_TIME = "record_time";
		public static final String DURATION = "duration";
	}
	
	
}
