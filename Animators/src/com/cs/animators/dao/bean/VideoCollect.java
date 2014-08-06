package com.cs.animators.dao.bean;

/**
 * 用户收藏的Video
 * 
 * @Author android_nihao (caijia)
 * @Time 2014-8-2 下午5:31:18
 */
public class VideoCollect {

	private String videoId;
	private String name;
	private String cover;
	private String curSeries;
	private String totalSeries;
	private String category;
	private String score;
	private String updateYear;

	public VideoCollect(String videoId, String name, String cover,
			String curSeries, String totalSeries, String category,
			String score, String updateYear) {
		super();
		this.videoId = videoId;
		this.name = name;
		this.cover = cover;
		this.curSeries = curSeries;
		this.totalSeries = totalSeries;
		this.category = category;
		this.score = score;
		this.updateYear = updateYear;
	}

	public VideoCollect() {
		super();
		// TODO Auto-generated constructor stub
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

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public String getCurSeries() {
		return curSeries;
	}

	public void setCurSeries(String curSeries) {
		this.curSeries = curSeries;
	}

	public String getTotalSeries() {
		return totalSeries;
	}

	public void setTotalSeries(String totalSeries) {
		this.totalSeries = totalSeries;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getUpdateYear() {
		return updateYear;
	}

	public void setUpdateYear(String updateYear) {
		this.updateYear = updateYear;
	}

}
