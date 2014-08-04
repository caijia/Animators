package com.cs.cj.http.httplibrary;

import org.apache.http.Header;
import android.util.Log;
import com.alibaba.fastjson.JSON;
import com.cs.cj.http.cacheinterface.HttpCache;
import com.cs.cj.http.cacheservice.ServerDataCache;
import com.cs.cj.http.parserinterface.BaseParser;
import com.cs.cj.http.work.DataCallback;
import com.cs.cj.http.work.JHttpClient;
import com.cs.cj.http.work.Response;

public class JAsyncHttpResponseHandler<T> extends AsyncHttpResponseHandler {
    private BaseParser<T> mBaseParser;
    private String mCacheUrl;
    private HttpCache mHttpCache;
    private DataCallback<T> mDataCallback ;

    public JAsyncHttpResponseHandler(BaseParser<T> baseParse,HttpCache httpCache, String cacheUrl, DataCallback<T> callback) {
        this.mBaseParser = baseParse;
        this.mCacheUrl = cacheUrl;
        this.mHttpCache = httpCache;
        this.mDataCallback = callback ;
    }

    @Override
    public void onStart() {
        if(mDataCallback != null){
            mDataCallback.onStart();
        }
    }

    @Override
    public void onFinish() {
        if(mDataCallback != null){
            mDataCallback.onFinish();
        }
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        if (statusCode == 200) {
            T parse = null;
            String parseString = null;
            try {
                parseString = new String(responseBody,"UTF-8");
                parse = mBaseParser.parse(parseString);
            } catch(Exception e)  {
                Log.d(JAsyncHttpResponseHandler.class.getSimpleName(), "statusCode="+statusCode+"---"+e.getLocalizedMessage());
                onFailure(200, headers, responseBody,e);
                return;
            }
            
            if (parse != null) 
			{
				Response<T> response = new Response<T>();
				response.setResult(parse);
				String cookie = getCookie(headers);
				response.setCookie(cookie);
				mDataCallback.onSuccess(response);
				saveCache(response);
				Log.i(JHttpClient.class.getSimpleName(),"Data taken from the server");
			} 
        }
    }
    
    private void saveCache(Response<T> response) {
		//Successfully returned to save the server data
		ServerDataCache cache = new ServerDataCache(mCacheUrl, JSON.toJSONString(response.getResult()), System.currentTimeMillis(),response.getCookie());
		if(mHttpCache != null)
		{
			if(mHttpCache.isNotExpried())
			{
				cache.setTime(JHttpClient.NOT_EXPIRED);
			}
			//insert httpcache
			mHttpCache.putHttpCache(cache);
		}
	}
    
    private String getCookie(Header[] headers) {
		StringBuilder sb = new StringBuilder();
		for (Header header : headers) {
			if(header.getName().equals("Set-Cookie"))
			{
				sb.append(header.getValue()).append(",");
			}
		}
		if(sb.length()>0)
			sb.deleteCharAt(sb.length() - 1); //删除最后一个多余的分号;
		return sb.toString();
	}

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        Log.d(JAsyncHttpResponseHandler.class.getSimpleName(), "jhttpclient error");
        if(mDataCallback != null){
            mDataCallback.onFailure(error.getMessage());
        }
    }
}
