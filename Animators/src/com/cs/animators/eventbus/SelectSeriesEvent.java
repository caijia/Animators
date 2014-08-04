package com.cs.animators.eventbus;

import com.cs.animators.entity.VideoDetailSeries;

public class SelectSeriesEvent {

	private VideoDetailSeries series ;
	private String videoId ;
	
	public SelectSeriesEvent(VideoDetailSeries series, String mVideoId) {
		this.series = series ;
		this.videoId = mVideoId ;
	}
	
	public VideoDetailSeries getVideoDetailSeries(){
		return series ;
	}

	public String getVideoId(){
		return videoId ;
	}
	
}
