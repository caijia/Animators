package com.cs.animators.entity;

import java.util.List;

public class PlayerSeries {

	private String groupTitle;
	private List<VideoDetailSeries> child;

	public String getGroupTitle() {
		return groupTitle;
	}

	public void setGroupTitle(String groupTitle) {
		this.groupTitle = groupTitle;
	}

	public List<VideoDetailSeries> getChild() {
		return child;
	}

	public void setChild(List<VideoDetailSeries> child) {
		this.child = child;
	}

}
