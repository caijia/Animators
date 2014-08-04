package com.cs.animators.dao.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 播放记录
 * @Author android_nihao (caijia)
 * @Time 2014-8-2 下午5:09:07
 */
@DatabaseTable(tableName="VIDEO_PLAY_RECORD")
public class VideoPlayRecord {

	//哪一集视频的id 视频的Videoid
	@DatabaseField(id = true)
	private long idAndVideoId ;
	
	//播放记录
	@DatabaseField
	private long playRecord;

	//播放的哪一集
	@DatabaseField
	private int series ;
	
	//是否播放完成
	@DatabaseField
	private boolean playComplete;
	
	//记录播放 的时间戳
	@DatabaseField
	private long recordTime ;

	public long getIdAndVideoId() {
		return idAndVideoId;
	}

	public void setIdAndVideoId(long idAndVideoId) {
		this.idAndVideoId = idAndVideoId;
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

	public boolean isPlayComplete() {
		return playComplete;
	}

	public void setPlayComplete(boolean playComplete) {
		this.playComplete = playComplete;
	}

	public long getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(long recordTime) {
		this.recordTime = recordTime;
	}

	public VideoPlayRecord(long idAndVideoId, long playRecord, int series,
			boolean playComplete, long recordTime) {
		this.idAndVideoId = idAndVideoId;
		this.playRecord = playRecord;
		this.series = series;
		this.playComplete = playComplete;
		this.recordTime = recordTime;
	}

	public VideoPlayRecord() {
		
	}
	
}
