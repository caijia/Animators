package com.cs.cj.http.work;

import com.cs.cj.http.cacheimpl.DefaultHttpCache;
import com.cs.cj.http.cacheinterface.HttpCache;
import com.cs.cj.http.cacheservice.CacheManager;
import com.cs.cj.http.cacheservice.ServerDataCache;
import com.cs.cj.http.httplibrary.AsyncHttpClient;
import com.cs.cj.http.httplibrary.AsyncHttpResponseHandler;
import com.cs.cj.http.httplibrary.JAsyncHttpResponseHandler;
import com.cs.cj.http.httplibrary.RequestParams;
import com.cs.cj.http.parserinterface.BaseParser;
import com.cs.cj.http.utils.NetWorkUtil;

import org.apache.http.client.CookieStore;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Http请求数据
 *
 * @Time 2014-4-21 上午10:00:21
 */
public class JHttpClient {
    public static final Long NOT_EXPIRED = Long.MAX_VALUE;

    public static final String GET = "get";

    public static final String POST = "post";

    private static AsyncHttpClient mAsyncHttpClient = new AsyncHttpClient();

    private static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        mAsyncHttpClient.post(url, params, responseHandler);
    }

    private static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        mAsyncHttpClient.get(url, params, responseHandler);
    }

    public static void setCookieStore(CookieStore cookieStore) {
        mAsyncHttpClient.setCookieStore(cookieStore);
    }

    public static String getUrlWithQueryString(String url, RequestParams params) {
        return AsyncHttpClient.getUrlWithQueryString(true, url, params);
    }

    private static <T> void getServerData(Context context, String url, RequestParams params, BaseParser<T> baseParser, String requestMethod, HttpCache httpCache, final DataCallback<T> dataCallback) {
        String cacheUrl = JHttpClient.getUrlWithQueryString(url, params).concat(requestMethod);
        // The  onlys URL
        if (baseParser == null) {
            throw new RuntimeException("BaseParse equals to null ,Please send a data parser");
        }
        if(!NetWorkUtil.isConnected(context)){
            Toast.makeText(context, "网络不可用", Toast.LENGTH_SHORT).show();
            getCache(context, baseParser, dataCallback, cacheUrl);
            return;
        }
        if (httpCache != null&&httpCache.getHttpCache(cacheUrl)) {
            getCache(context, baseParser, dataCallback, cacheUrl);
        }else {
            if (requestMethod != null && requestMethod.equals(GET)) {
                JHttpClient.get(url, params, new JAsyncHttpResponseHandler<T>( baseParser, httpCache, cacheUrl,dataCallback));
            } else if (requestMethod != null && requestMethod.equals(POST)) {
                JHttpClient.post(url, params, new JAsyncHttpResponseHandler<T>(baseParser, httpCache, cacheUrl,dataCallback));
            }
        }

    }

    private static <T> void getCache(Context context, BaseParser<T> baseParser, final DataCallback<T> dataCallback, String cacheUrl) {
        dataCallback.onStart();
        ServerDataCache cacheData = CacheManager.getInstance(context).findCacheByUrl(cacheUrl);
        if(cacheData != null)
		{
			try {
				T result = baseParser.parse(cacheData.getServerData());
				Response<T> response = new Response<T>();
				response.setResult(result);
				response.setCookie(cacheData.getCookie());
				dataCallback.onSuccess(response);
				Log.i(JHttpClient.class.getSimpleName(), "Data taken from the cache");
			} catch (Exception e) {
				Log.i(JHttpClient.class.getSimpleName(), "find cache error");
				dataCallback.onFailure("find cache error" );
			}
		}
        else
		{
			Log.i(JHttpClient.class.getSimpleName(), "cache equals null");
			dataCallback.onFailure("cache equals null");
		}
    }


    /****************************************************************************************************************************/

    /**
     * 自定义缓存请求（post）
     *
     * @param context
     * @param url
     * @param params
     * @param parser
     * @param httpCache
     * @param dataCallback
     */
    public static <T> void post(Context context, String url, RequestParams params, BaseParser<T> parser, HttpCache httpCache, final DataCallback<T> dataCallback) {
        getServerData(context, url, params, parser, JHttpClient.POST, httpCache, dataCallback);
    }

    /**
     * 自定义缓存请求（get）
     *
     * @param context
     * @param url
     * @param params
     * @param parser
     * @param httpCache
     * @param callback
     */
    public static <T> void get(Context context, String url, RequestParams params, BaseParser<T> parser, HttpCache httpCache, final DataCallback<T> dataCallback) {
        getServerData(context, url, params, parser, JHttpClient.GET, httpCache, dataCallback);
    }

    /**
     * post 请求有缓存
     *
     * @param context
     * @param url
     * @param params
     * @param parser
     * @param callback
     */
    public static <T> void post(Context context, String url, RequestParams params, BaseParser<T> parser, final DataCallback<T> dataCallback) {
        getServerData(context, url, params, parser, JHttpClient.POST, new DefaultHttpCache(context), dataCallback);
    }

    /**
     * get 請求有缓存
     *
     * @param context
     * @param url
     * @param params
     * @param parser
     * @param callback
     */
    public static <T> void get(Context context, String url, RequestParams params, BaseParser<T> parser, final DataCallback<T> dataCallback) {
        getServerData(context, url, params, parser, JHttpClient.GET, new DefaultHttpCache(context), dataCallback);
    }

    /**
     * get 请求 没有缓存
     *
     * @param context
     * @param url
     * @param params
     * @param parser
     * @param callback
     */
    public static <T> void getFromServer(Context context, String url, RequestParams params, BaseParser<T> parser, final DataCallback<T> dataCallback) {
        getServerData(context, url, params, parser, JHttpClient.GET, null, dataCallback);
    }

    /**
     * post 请求 没有缓存
     *
     * @param context
     * @param url
     * @param params
     * @param parser
     * @param callback
     */
    public static <T> void postFromServer(Context context, String url, RequestParams params, BaseParser<T> parser, final DataCallback<T> dataCallback) {
        getServerData(context, url, params, parser, JHttpClient.POST, null, dataCallback);
    }

    /****************************************************************************************************************************/

    /**
     * 自定义缓存请求（post）
     *
     * @param context
     * @param url
     * @param params
     * @param parser
     * @param httpCache
     * @param callback
     */
    public static <T> void post(Context context, String url, RequestParams params, Class<T> clazz, HttpCache httpCache, final DataCallback<T> dataCallback) {
        getServerData(context, url, params, new FastJsonParser<T>(clazz), JHttpClient.POST, httpCache, dataCallback);
    }

    /**
     * 自定义缓存请求（get）
     *
     * @param context
     * @param url
     * @param params
     * @param parser
     * @param httpCache
     * @param callback
     */
    public static <T> void get(Context context, String url, RequestParams params, Class<T> clazz, HttpCache httpCache, final DataCallback<T> dataCallback) {
        getServerData(context, url, params, new FastJsonParser<T>(clazz), JHttpClient.GET, httpCache, dataCallback);
    }

    /**
     * post 請求有缓存
     *
     * @param context
     * @param url
     * @param params
     * @param parser
     * @param callback
     */
    public static <T> void post(Context context, String url, RequestParams params, Class<T> clazz, final DataCallback<T> dataCallback) {
        getServerData(context, url, params, new FastJsonParser<T>(clazz), JHttpClient.POST, new DefaultHttpCache(context), dataCallback);
    }

    /**
     * get 請求有缓存
     *
     * @param context
     * @param url
     * @param params
     * @param parser
     * @param callback
     */
    public static <T> void get(Context context, String url, RequestParams params, Class<T> clazz, final DataCallback<T> dataCallback) {
        getServerData(context, url, params, new FastJsonParser<T>(clazz), JHttpClient.GET, new DefaultHttpCache(context), dataCallback);
    }

    /**
     * get 请求 没有缓存
     *
     * @param context
     * @param url
     * @param params
     * @param parser
     * @param callback
     */
    public static <T> void getFromServer(Context context, String url, RequestParams params, Class<T> clazz, final DataCallback<T> dataCallback) {
        getServerData(context, url, params, new FastJsonParser<T>(clazz), JHttpClient.GET, null, dataCallback);
    }

    /**
     * post 请求 没有缓存
     *
     * @param context
     * @param url
     * @param params
     * @param parser
     * @param callback
     */
    public static <T> void postFromServer(Context context, String url, RequestParams params, Class<T> clazz, final DataCallback<T> dataCallback) {
        getServerData(context, url, params, new FastJsonParser<T>(clazz), JHttpClient.POST, null, dataCallback);
    }

}
