package com.cs.cj.util;

import android.graphics.Bitmap;
import com.caijia.library.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class ImageLoaderUtil {

	public static DisplayImageOptions roundImageLoaderOptions(int radius, int defaultDrawable) {
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showImageOnLoading(defaultDrawable)
				.showImageForEmptyUri(defaultDrawable)
				.showImageOnFail(defaultDrawable).cacheInMemory(true)
				.cacheOnDisc(true).displayer(new RoundedBitmapDisplayer(radius))
				.bitmapConfig(Bitmap.Config.RGB_565).build();
		return options;
	}
	
	public static DisplayImageOptions roundImageLoaderOptions(int radius) {
		DisplayImageOptions options = roundImageLoaderOptions(radius, R.drawable.shape_loading_image);
		return options;
	}

	public static DisplayImageOptions FadeInImageLoaderOptions(int durationMillis , int defaultDrawable) {
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showImageOnLoading(defaultDrawable)
				.showImageForEmptyUri(defaultDrawable)
				.showImageOnFail(defaultDrawable).cacheInMemory(true)
				.cacheOnDisc(true).displayer(new FadeInBitmapDisplayer(durationMillis))
				.bitmapConfig(Bitmap.Config.RGB_565).build();
		return options;
	}
	
	public static DisplayImageOptions FadeInImageLoaderOptions(int durationMillis) {
		DisplayImageOptions options = FadeInImageLoaderOptions(durationMillis, R.drawable.shape_loading_image);
		return options;
	}
	
}
