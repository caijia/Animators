package com.cs.animators.eventbus;

public class PlayerSizeEvent {

	private int videoSize ;
	
	public int getVideoSize(){
		return videoSize ;
	}
	public PlayerSizeEvent(int videoSize) {
		this.videoSize = videoSize ;
	}
	
}
