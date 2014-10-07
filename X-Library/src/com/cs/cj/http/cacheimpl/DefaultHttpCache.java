package com.cs.cj.http.cacheimpl;

import com.cs.cj.http.cacheinterface.HttpCache;
import com.cs.cj.http.cacheservice.CacheManager;
import com.cs.cj.http.cacheservice.ServerDataCache;
import com.cs.cj.http.utils.NetWorkUtil;

import android.content.Context;

public class DefaultHttpCache implements HttpCache {
    /**
     * 缓存过期时间
     */
    private static final long SG_CACHE_TIME_OUT = 60 * 1000 * 60 * 24;
    private static final long WIFI_CACHE_TIME_OUT = 60 * 1000 * 60 * 2;
    private CacheManager mCacheManager;
    private Context mContext;

    public DefaultHttpCache(Context context) {
        this.mContext = context;
        mCacheManager = CacheManager.getInstance(context);
    }

    @Override
    public boolean getHttpCache(String url) {
        ServerDataCache serverDataCache = mCacheManager.findCacheByUrl(url);
        if (serverDataCache != null) {

            if (isNotExpried()) {
                return true;
            }

            if (!NetWorkUtil.isConnected(mContext)) {
                return true;
            }
            String networkType = NetWorkUtil.getNetworkType(mContext);
            if (NetWorkUtil.NetworkType.WIFI.equals(networkType)) {
                long cacheTimeMillis = serverDataCache.getTime();
                long currentTimeMillis = System.currentTimeMillis();
                if ((currentTimeMillis - cacheTimeMillis) < wifiCacheExpriedTime()) {
                    return true;
                }else{
                    mCacheManager.deleteCache(url);
                    return false;
                }
            }

            if (NetWorkUtil.NetworkType.NET_3G.equals(networkType)||NetWorkUtil.NetworkType.NET_2G.equals(networkType)||NetWorkUtil.NetworkType.NET_CMNET.equals(networkType)||NetWorkUtil.NetworkType.NET_CMWAP.equals(networkType)) {
                long cacheTimeMillis = serverDataCache.getTime();
                long currentTimeMillis = System.currentTimeMillis();
                if ((currentTimeMillis - cacheTimeMillis) < sgCacheExpriedTime()) {
                    return true;
                }else{
                    mCacheManager.deleteCache(url);
                    return false;
                }
            }
        }
        return false;
    }

    @Override
    public void putHttpCache(ServerDataCache cache) {
        mCacheManager.insertOrUpdateCache(cache);
    }

    @Override
    public long wifiCacheExpriedTime() {
        return WIFI_CACHE_TIME_OUT;
    }

    @Override
    public long sgCacheExpriedTime() {
        return SG_CACHE_TIME_OUT;
    }

    @Override
    public boolean isNotExpried() {
        return false;
    }

}
