package com.cs.cj.view;

import android.support.v4.app.Fragment;

public class TabSpec {

	private String title;

	private Fragment fragment;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Fragment getFragment() {
		return fragment;
	}

	public void setFragment(Fragment fragment) {
		this.fragment = fragment;
	}

	public TabSpec() {
	}

	public TabSpec(String title, Fragment fragment) {
		this.title = title;
		this.fragment = fragment;
	}

}
