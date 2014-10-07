package com.cs.animators.eventbus;

public class PlayRecordEvent {

	private long playRecord ;
	
	private long duration ;
	
	private String extra ;
	
	public long getPlayRecord()
	{
		return playRecord ;
	}
	
	public String getExtra(){
		return extra ;
	}
	
	public long getDuration(){
		return duration ;
	}
	
	public PlayRecordEvent(long playRecord, long duration, String extra) {
		this.playRecord = playRecord ;
		this.duration = duration ;
		this.extra = extra ;
	}

}
