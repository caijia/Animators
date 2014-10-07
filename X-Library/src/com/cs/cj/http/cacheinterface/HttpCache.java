package com.cs.cj.http.cacheinterface;

import com.cs.cj.http.cacheservice.ServerDataCache;

public interface HttpCache {

	public boolean getHttpCache(String url);

	public void putHttpCache(ServerDataCache cache);

	public boolean isNotExpried();

	public long wifiCacheExpriedTime();

	public long sgCacheExpriedTime();

}