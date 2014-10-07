package com.cs.cj.http.work;


public  abstract class JDataCallback<T> implements DataCallback<T> {

	@Override
	public void onStart() {
	}

	@Override
	public void onFinish() {
	}

	@Override
	public void onFailure(String responseString) {
	}

}
