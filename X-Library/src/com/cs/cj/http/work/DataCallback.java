
package com.cs.cj.http.work;


public interface DataCallback<T> {

    /**
     * 请求开始
     */
    public void onStart();

    /**
     * 请求成功回调
     *
     * @param data
     */
    public void onSuccess(Response<T> data);

    /**
     * 请求失败
     *
     * @param responseString
     */
    public void onFailure(String responseString);

    /**
     * 请求结束
     */
    public void onFinish();
}
