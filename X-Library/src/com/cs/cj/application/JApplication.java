package com.cs.cj.application;

import android.app.Application;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.SparseArray;

import com.cs.cj.ui.AppContext;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LRULimitedMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class JApplication extends Application {
	
	private SparseArray<Fragment> mCurrent_fragment_array;
	
	public SparseArray<Fragment> getCurFragmentArray(){
		return mCurrent_fragment_array ;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		mCurrent_fragment_array = new SparseArray<Fragment>();
		AppContext.init(getApplicationContext());
		initImageLoader(getApplicationContext());
	}
	
	private void initImageLoader(Context context) {
        int width = context.getResources().getDisplayMetrics().widthPixels;
        int height = context.getResources().getDisplayMetrics().heightPixels;
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .memoryCacheExtraOptions(width, height).threadPriority(Thread.NORM_PRIORITY)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .memoryCache(new LRULimitedMemoryCache(12 * 1024 * 1024))
                .memoryCacheSize(12 * 1024 * 1024).memoryCacheSizePercentage(50) // default
                .build();
        ImageLoader.getInstance().init(config);
    }
	
}
