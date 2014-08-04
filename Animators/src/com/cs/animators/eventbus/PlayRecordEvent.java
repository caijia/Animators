package com.cs.animators.eventbus;

import com.cs.animators.dao.bean.VideoPlayRecord;

public class PlayRecordEvent {

	private VideoPlayRecord record ;
	
	
	public VideoPlayRecord getPlayRecord()
	{
		return record ;
	}
	
	public PlayRecordEvent(VideoPlayRecord record) {
		this.record = record ;
	}

}
