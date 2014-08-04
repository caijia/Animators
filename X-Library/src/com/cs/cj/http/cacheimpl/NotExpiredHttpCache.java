package com.cs.cj.http.cacheimpl;

import android.content.Context;

public class NotExpiredHttpCache extends DefaultHttpCache {

	public NotExpiredHttpCache(Context context) {
		super(context);
	}

	@Override
	public boolean isNotExpried() {
		return true;
	}

}
