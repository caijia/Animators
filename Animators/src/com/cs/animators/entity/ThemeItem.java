package com.cs.animators.entity;

import com.alibaba.fastjson.annotation.JSONField;

public class ThemeItem {
	
	private String name ;
	
	private String id ;
	
	private String pic ;
	
	private String lastup ;
	
	@JSONField(name="tips_time")
	private String playTime ;
	
	@JSONField(name="is_new")
	private String isNew ;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getLastup() {
		return lastup;
	}

	public void setLastup(String lastup) {
		this.lastup = lastup;
	}

	public String getPlayTime() {
		return playTime;
	}

	public void setPlayTime(String playTime) {
		this.playTime = playTime;
	}

	public String getIsNew() {
		return isNew;
	}

	public void setIsNew(String isNew) {
		this.isNew = isNew;
	}
	
}
