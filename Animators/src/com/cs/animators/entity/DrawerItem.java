package com.cs.animators.entity;

import android.graphics.drawable.Drawable;

public class DrawerItem {

	private Drawable drawerImage;
	private String drawerTitle;

	public Drawable getDrawerImage() {
		return drawerImage;
	}

	public void setDrawerImage(Drawable drawerImage) {
		this.drawerImage = drawerImage;
	}

	public String getDrawerTitle() {
		return drawerTitle;
	}

	public void setDrawerTitle(String drawerTitle) {
		this.drawerTitle = drawerTitle;
	}

	public DrawerItem(Drawable drawerImage, String drawerTitle) {
		super();
		this.drawerImage = drawerImage;
		this.drawerTitle = drawerTitle;
	}

	public DrawerItem() {
	}

}
