package com.cs.animators.entity;

public class LocalVideo {

	private String videoPath;
	private String videoName;
	private String videoTitle;
	private long videoDuration;

	public LocalVideo() {
		super();
	}

	public LocalVideo(String videoPath, String videoName, String videoTitle,
			long videoDuration) {
		super();
		this.videoPath = videoPath;
		this.videoName = videoName;
		this.videoTitle = videoTitle;
		this.videoDuration = videoDuration;
	}

	public String getVideoPath() {
		return videoPath;
	}

	public void setVideoPath(String videoPath) {
		this.videoPath = videoPath;
	}

	public String getVideoName() {
		return videoName;
	}

	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}

	public String getVideoTitle() {
		return videoTitle;
	}

	public void setVideoTitle(String videoTitle) {
		this.videoTitle = videoTitle;
	}

	public long getVideoDuration() {
		return videoDuration;
	}

	public void setVideoDuration(long videoDuration) {
		this.videoDuration = videoDuration;
	}

}
