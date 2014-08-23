package com.cs.animators.eventbus;


public class SelectSeriesEvent {

	private String videoName ;
	private String series ;
	private String url ;
	private long record ;
	
	public String getVideoName() {
		return videoName;
	}

	public String getSeries() {
		return series;
	}

	public String getUrl() {
		return url;
	}
	
	public long getPlayRecord(){
		return record ;
	}

	public SelectSeriesEvent(String name, String series,long playRecord, String loadVideoAddressUrl) {
		this.videoName = name ;
		this.series = series ;
		this.url = loadVideoAddressUrl ;
		this.record = playRecord ;
	}

}
