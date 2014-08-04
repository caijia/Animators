package com.cs.cj.http.utils;

import com.cs.cj.http.cacheservice.CacheDatabaseHelper;
import com.cs.cj.http.cacheservice.CacheManager;

import android.content.Context;

import java.io.File;

public class CacheUtil {

    /**
    * 得到文件夹的大小 单位是以K,M,G表示
    *
    * @param path
    *            文件的路径
    * @return 文件的大小
    */
    public static String getCacheSize(Context context) {
        File file = new File(context.getCacheDir().getParentFile().getAbsolutePath() + "/databases/" + CacheDatabaseHelper.DB_NAME);
        return FileUtil.getFileSize(file);
    }

    public static void deleteCache(Context context) {
        CacheManager.getInstance(context).deleteCache();
    }
}
