package com.cs.cj.http.work;

import com.cs.cj.http.progress.HttpProgress;

public abstract class ProgressDataCallback<T> implements DataCallback<T> {
    private HttpProgress mProgress;

    public ProgressDataCallback(HttpProgress progress) {
        mProgress = progress;
    }

    @Override
    public void onStart() {
        if (mProgress != null) {
            mProgress.show();
        }
    }
    @Override
    public void onSuccess( Response<T> data) {

    }

    @Override
    public void onFailure(String responseString) {
        if (mProgress != null) {
            mProgress.dismiss();
        }
    }

    @Override
    public void onFinish() {
        if (mProgress != null) {
            mProgress.dismiss();
        }
    }

}
