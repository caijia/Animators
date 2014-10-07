package com.cs.cj.http.utils;

import java.io.File;

import android.content.Context;

import com.cs.cj.http.cacheservice.CacheDatabaseHelper;
import com.cs.cj.http.cacheservice.CacheManager;
import com.cs.cj.util.FileUtils;
import com.nostra13.universalimageloader.utils.StorageUtils;

public class CacheUtil {

	
	public static final long DB_SIZE = 24576 ;
    /**
    * 得到文件夹的大小 单位是以K,M,G表示
    * @param path 文件的路径
    * @return 文件的大小
    */
    public static String getCacheSize(Context context) {
        File httpCache = new File(context.getCacheDir().getParentFile().getAbsolutePath() + "/databases/" + CacheDatabaseHelper.DB_NAME);
        File imageCache = StorageUtils.getCacheDirectory(context);
        double httpCacheSize = FileUtils.getFileOrFilesSize(httpCache.getAbsolutePath(), FileUtils.SIZETYPE_B);
        double imageCacheSize = FileUtils.getFileOrFilesSize(imageCache.getAbsolutePath(), FileUtils.SIZETYPE_B);
        return FileUtils.FormetFileSize(httpCacheSize + imageCacheSize - DB_SIZE);
    }

    public static void deleteCache(Context context) {
        CacheManager.getInstance(context).deleteCache();
        File cacheDirectory = StorageUtils.getCacheDirectory(context);
        FileUtil.delete(cacheDirectory.getAbsolutePath());
    }
}
