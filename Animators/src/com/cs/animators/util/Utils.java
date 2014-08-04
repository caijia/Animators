package com.cs.animators.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.provider.Settings;

public class Utils {
	
	public static int getScreenWidthPixels(Context context)
	{
		return context.getResources().getDisplayMetrics().widthPixels;
	}
	
	public static int getScreenHeightPixels(Context context)
	{
		return context.getResources().getDisplayMetrics().heightPixels;
	}
	
	public static int getScreenBrightness(Context context)
	{
		int brightness = 0 ;
		try {
			brightness = android.provider.Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return brightness ;
	}
	
	public static void setScreenBrightness(Context context ,int brightness)
	{
		 android.provider.Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, brightness);
	}
	
	
	public static String getSystemTime(){
		SimpleDateFormat format = new SimpleDateFormat("HH:mm",Locale.CHINA);
		return format.format(new Date());
	}
}
