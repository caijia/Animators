package com.cs.cj.http.cacheservice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CacheManager {
    private CacheDatabaseHelper mCacheDatabaseHelper;

    private CacheManager(Context context) {
        this.mCacheDatabaseHelper = new CacheDatabaseHelper(context);
    }

    public static CacheManager getInstance(Context context) {
        return new CacheManager(context);
    }

    public void insertCache(ServerDataCache serverDataCache) {
        SQLiteDatabase db = mCacheDatabaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CacheTable.URL, serverDataCache.getUrl());
        values.put(CacheTable.DATA, serverDataCache.getServerData());
        values.put(CacheTable.TIME, serverDataCache.getTime());
        values.put(CacheTable.COOKIE, serverDataCache.getCookie());
        db.insert(CacheTable.TABLE_NAME, null, values);
        db.close();
    }

    public void deleteCache(String url) {
        SQLiteDatabase db = mCacheDatabaseHelper.getWritableDatabase();
        db.execSQL("delete from "+CacheTable.TABLE_NAME+" where "+CacheTable.URL+" = ? ", new Object[] { url });
        db.close();
    }

    public void deleteCache() {
        SQLiteDatabase db = mCacheDatabaseHelper.getWritableDatabase();
        db.execSQL("delete from "+CacheTable.TABLE_NAME);
        db.close();
    }

    public ServerDataCache findCacheByUrl(String url) {
        SQLiteDatabase db = mCacheDatabaseHelper.getWritableDatabase();
        ServerDataCache cacheData = null;
        Cursor r = db.rawQuery("select * from "+CacheTable.TABLE_NAME+" where "+CacheTable.URL+" = ? ", new String[] { url });
        while (r.moveToNext()) {
            String cache_url = r.getString(r.getColumnIndex(CacheTable.URL));
            String cache_data = r.getString(r.getColumnIndex(CacheTable.DATA));
            long cache_time = r.getLong(r.getColumnIndex(CacheTable.TIME));
            String cache_cookie = r.getString(r.getColumnIndex(CacheTable.COOKIE));
            cacheData = new ServerDataCache(cache_url, cache_data, cache_time,cache_cookie);
        }
        r.close();
        db.close();
        return cacheData;
    }

    public void updateCache(ServerDataCache serverDataCache) {
        SQLiteDatabase db = mCacheDatabaseHelper.getWritableDatabase();
        db.execSQL("update "+CacheTable.TABLE_NAME+" set  "+CacheTable.DATA+" = ? , "+CacheTable.TIME+" = ? ,"+ CacheTable.COOKIE + " = ? where "+CacheTable.URL+" = ?", new Object[] { serverDataCache.getServerData(), serverDataCache.getTime(), serverDataCache.getCookie() , serverDataCache.getUrl() });
        db.close();
    }

    public void insertOrUpdateCache(ServerDataCache serverDataCache) {
        ServerDataCache cacheData = findCacheByUrl(serverDataCache.getUrl());
        if (cacheData == null) {
            insertCache(serverDataCache);
        } else {
            updateCache(serverDataCache);
        }
    }
}
