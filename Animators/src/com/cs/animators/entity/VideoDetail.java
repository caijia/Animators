package com.cs.animators.entity;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

public class VideoDetail {
	@JSONField(name = "video_id")
	private String videoId;
	private String name;
	private String intro;
	private String area;
	private String cover;
	private String character ;
	@JSONField(name = "is_over")
	private String isOver;
	@JSONField(name = "cur_num")
	private String curNum;
	@JSONField(name = "total_num")
	private String totalNum;
	private String category;
	private String director;
	private String score;
	@JSONField(name = "update_time")
	private String updateTime;
	@JSONField(name = "publish_time")
	private String publishTime;
	private String error;
	private List<VideoDetailSeries> episode;

	
	public String getCharacter() {
		return character;
	}

	public void setCharacter(String character) {
		this.character = character;
	}

	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public String getIsOver() {
		return isOver;
	}

	public void setIsOver(String isOver) {
		this.isOver = isOver;
	}

	public String getCurNum() {
		return curNum;
	}

	public void setCurNum(String curNum) {
		this.curNum = curNum;
	}

	public String getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(String totalNum) {
		this.totalNum = totalNum;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public List<VideoDetailSeries> getEpisode() {
		return episode;
	}

	public void setEpisode(List<VideoDetailSeries> episode) {
		this.episode = episode;
	}

}
