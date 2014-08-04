package com.cs.cj.http.cacheservice;

import android.provider.BaseColumns;

/**
 *
 * @ClassName: LatestSearchTable
 * @author xiaoming.yuan
 * @date 2014-5-22 上午10:41:05
 */
public class CacheTable implements BaseColumns {

    public static final String TABLE_NAME = "server_data_cache";

    public static final String URL = "url";
    public static final String DATA = "data";
    public static final String TIME = "time";
    public static final String COOKIE = "cookie";


    public static final String CREATE_TABLE_LATESTSEARCHTABLE = " CREATE TABLE IF NOT EXISTS " +
            TABLE_NAME + " (" +
            _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            URL + " VARCHAR UNIQUE , " +
            DATA + "  VARCHAR NOT NULL , " +
            TIME + " LONG NOT NULL  ," +
            COOKIE + " VARCHAR NOT NULL " +
            ")";
}
