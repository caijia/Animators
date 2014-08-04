package com.cs.cj.http.cacheservice;

public class ServerDataCache {
	
	private String url;
	
	private String serverData;
	
	private long time;
	
	private String cookie;

	public ServerDataCache() {
	}
	
	public ServerDataCache(String url, String serverData, long time) {
		this.url = url;
		this.serverData = serverData;
		this.time = time;
		
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getServerData() {
		return serverData;
	}

	public void setServerData(String serverData) {
		this.serverData = serverData;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getCookie() {
		return cookie;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}

	public ServerDataCache(String url, String serverData, long time,String cookie) {
		this.url = url;
		this.serverData = serverData;
		this.time = time;
		this.cookie = cookie;
	}

}
