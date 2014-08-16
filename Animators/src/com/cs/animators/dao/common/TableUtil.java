package com.cs.animators.dao.common;

public class TableUtil {

	public static final String DATABSE_NAME = "animation.db";
	public static final int VERSION = 4;

	// VideoPlayRecord数据表的定义
	public static class TablePlayRecord {
		public static final String TABLE_NAME = "play_record";
		public static final String PLAYRECORD_ID = "playrecord_id";
		public static final String ID = "id";
		public static final String VIDEO_ID = "video_id";
		public static final String PLAY_RECORD = "play_record";
		public static final String SERIES = "series";
		public static final String RECORD_TIME = "record_time";
		public static final String DURATION = "duration";
		public static final String VIDEO_NAME = "video_name";
	}
	
	//VideoCollect 的数据表的定义
	public static class TableVideoCollect{
		public static final String TABLE_NAME = "video_collect";
		public static final String VIDEO_ID = "video_id";
		public static final String NAME = "name";
		public static final String COVER = "cover";
		public static final String CUR_SERIES = "cur_series";
		public static final String TOTAL_SERIES = "total_series";
		public static final String CATEGORY = "category";
		public static final String SCORE = "score";
		public static final String UPDATE_YEAR = "update_year";
	}
	
}
