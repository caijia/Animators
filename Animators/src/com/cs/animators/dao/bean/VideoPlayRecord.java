package com.cs.animators.dao.bean;

/**
 * 播放记录
 * @Author android_nihao (caijia)
 * @Time 2014-8-2 下午5:09:07
 */
public class VideoPlayRecord {
	
	
	//哪一集视频的id
	private long id ;

	//视频的Videoid  (根据videoId 查找 播放记录大于 0 的记录 表示有播放记录  )
	private String videoId ;
	
	//播放记录 时间
	private long playRecord;

	//播放的哪一集
	private int series ;
	
	//记录播放 的时间戳
	private long recordTime ;

	//视频播放的总时间
	private long duration ;
	
	//视频的名字
	private String videoName ;

	public VideoPlayRecord() {
	}
	
	public VideoPlayRecord(long id, String videoId, long playRecord,
			int series, long recordTime, long duration, String videoName) {
		this.id = id;
		this.videoId = videoId;
		this.playRecord = playRecord;
		this.series = series;
		this.recordTime = recordTime;
		this.duration = duration;
		this.videoName = videoName;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}

	public long getPlayRecord() {
		return playRecord;
	}

	public void setPlayRecord(long playRecord) {
		this.playRecord = playRecord;
	}

	public int getSeries() {
		return series;
	}

	public void setSeries(int series) {
		this.series = series;
	}

	public long getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(long recordTime) {
		this.recordTime = recordTime;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public String getVideoName() {
		return videoName;
	}

	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}
	
}
