package com.cs.animators.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.Toast;

public class CommonUtil {
	
	private static DisplayMetrics getScreenMetrics(Context context){
		DisplayMetrics  dm = new DisplayMetrics(); 
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		windowManager.getDefaultDisplay().getMetrics(dm);
		return dm ;
	}
	
	public static int getWidthMetrics(Context context){
		return getScreenMetrics(context).widthPixels;
	}
	
	public static int getHeightMetrics(Context context){
		return getScreenMetrics(context).heightPixels;
	}
	
	public static int px2dip(Context context, float pxValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (pxValue / scale + 0.5f);  
    }  
	
	public static float getDensity(Context context){
		return  context.getResources().getDisplayMetrics().density;  
	}
	
	public static int dip2px(Context context, float dipValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (dipValue * scale + 0.5f);  
    } 
	
	public static int getScreenWidth(Context context){
		return px2dip(context, getWidthMetrics(context));
	}
	
	public static int getScreenHeight(Context context){
		return px2dip(context, getHeightMetrics(context));
	}

	public static String getMobileIp() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()&& inetAddress instanceof Inet4Address) {
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (Exception e) {}
		return null;
	}
	
	public static String getMobileIMIE(Context context){
		TelephonyManager telephonyManager=(TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		return telephonyManager.getDeviceId();
	}
	
	
  // 验证密码是否格式良好
	 public static boolean isPasswordCorrect(String password) {
      if (!TextUtils.isEmpty(password)) {
          Pattern pattern = Pattern.compile("^[0-9a-zA-Z_]{6,20}$");
          Matcher matcher = pattern.matcher(password);
          return matcher.find();
      }
      return false;
  }

  // 用户是否格式良好
  public static boolean isUserNameCorrect(String userName) {
      if (!TextUtils.isEmpty(userName)) {
          Pattern pattern = Pattern.compile("^[0-9a-zA-Z_]{6,20}$");
          Matcher matcher = pattern.matcher(userName);
          return matcher.find();
      }
      return false;
  }
  
  public static boolean isEmailAddress(String emailAddress){
	  if (!TextUtils.isEmpty(emailAddress)) {
          Pattern pattern = Pattern.compile("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+$");
          Matcher matcher = pattern.matcher(emailAddress);
          return matcher.find();
      }
      return false;
  }

  ///(^(\d{6})(18|19|20)?(\d{2})((0[1-9])|(1[0-2]))(([0|1|2][1-9])|(3[0-1]))(\d{3})(\d|X){1}$)/
  public static boolean isIDNumber(String id){
	  if(!TextUtils.isEmpty(id)){
		  Pattern pattern = Pattern.compile("^(\\d{6})(18|19|20)?(\\d{2})((0[1-9])|(1[0-2]))(([0|1|2][1-9])|(3[0-1]))(\\d{3})(\\d|X){1}$");
		  Matcher matcher = pattern.matcher(id);
		  return matcher.find();
	  }
	  return false ;
  }
	
  public static boolean isPhoneNumber(String phoneNumber){
	  if(!TextUtils.isEmpty(phoneNumber)){
		  Pattern pattern = Pattern.compile("^1[3458]\\d{9}$");
		  Matcher matcher = pattern.matcher(phoneNumber);
		  return matcher.find();
	  }
	  return false ;
  }
  

	public static boolean isNumber(String status) {
		Pattern pattern = Pattern.compile("\\d+");
		Matcher matcher = pattern.matcher(status);
		return matcher.matches();
	}
  
  public static boolean isQQNumber(String qqNumber){
	  if(!TextUtils.isEmpty(qqNumber)){
		  Pattern pattern = Pattern.compile("\\d{4,12}$");
		  Matcher matcher = pattern.matcher(qqNumber);
		  return matcher.find();
	  }
	  return false ;
  }
  
	public static boolean isAppOnForeground(Context context) {
		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
		if (appProcesses == null) {
			return false;
		}
		final String packageName = context.getPackageName();
		for (RunningAppProcessInfo appProcess : appProcesses) {
			if (appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND&& appProcess.processName.equals(packageName)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isMountSdCard(){
		String mountState = Environment.getExternalStorageState();
		if(mountState.equals(Environment.MEDIA_MOUNTED))
		{
			return true ;
		}
		return false ;
	}
	
	public static void saveHeadIcon(Context context , String headIconPath ){
		SharedPreferences preferences = context.getSharedPreferences("head_icon_file", Context.MODE_PRIVATE);
		Editor edit = preferences.edit();
		edit.putString("head_icon", headIconPath);
		edit.commit();
	}
	
	public static String getHeadIcon(Context context ){
		SharedPreferences preferences = context.getSharedPreferences("head_icon_file", Context.MODE_PRIVATE);
		return preferences.getString("head_icon", null);
	}
	
	public static void saveAutoLogin(Context context , boolean autologin ){
		SharedPreferences preferences = context.getSharedPreferences("head_icon_file", Context.MODE_PRIVATE);
		Editor edit = preferences.edit();
		edit.putBoolean("auto_login", autologin);
		edit.commit();
	}
	
	public static boolean getAutoLogin(Context context ){
		SharedPreferences preferences = context.getSharedPreferences("head_icon_file", Context.MODE_PRIVATE);
		return preferences.getBoolean("auto_login", false);
	}

	public static File createDir(String dir) {
		File saveDir = null ;
		if(isMountSdCard())
		{
			saveDir = new File(Environment.getExternalStorageDirectory(), dir);
			if(!saveDir.exists())
			{
				saveDir.mkdirs();
			}
		}
		return saveDir;
	}

	public static void showMessage(Context context, String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}
	
	
	public static String getApkPackageName(Context context , String path) {
        PackageManager pm = context.getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(path,PackageManager.GET_ACTIVITIES);
        ApplicationInfo appInfo = null;
        if (info != null) {
            appInfo = info.applicationInfo;
            String packageName = appInfo.packageName;
            return packageName;
        }
        return null ;
    }
	
	public static boolean isInstallApk(Context context, String packageName) {
		if (TextUtils.isEmpty(packageName)) {
			return false;
		}

		try {
			PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
			if (packageInfo != null) {
				return true;
			}
		} catch (NameNotFoundException e) {
			return false;
		}
		return false;
	}
	
	public static void launchIntentFromPackageName(Context context , String packageName){
		if(isInstallApk(context, packageName))
		{
			PackageManager packageManager = context.getPackageManager(); 
			Intent launchIntent = packageManager.getLaunchIntentForPackage(packageName);
			launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(launchIntent);
		}
	}
	
	public static void installApk(Context context , String path)
	{
		Intent intent = new Intent(Intent.ACTION_VIEW); 
		 intent.setDataAndType(Uri.fromFile(new File(path)), "application/vnd.android.package-archive"); 
		 intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		 context.startActivity(intent);
	}
	
	public static Double getVersion(Context context)//获取版本号
	{
		try {
			PackageInfo pi=context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			return Double.parseDouble(pi.versionName);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return 0.0 ;
	}
	
	 public static String getNotCompletePhoneNumber(String phoneNumber) {
			if(CommonUtil.isPhoneNumber(phoneNumber))
			{
				String phoneHead = phoneNumber.substring(0 , 3);
				String phoneEnd = phoneNumber.substring(7);
				return phoneHead.concat("****").concat(phoneEnd);
			}
			return "" ;
		}
	 
	 public static String streamToString(InputStream in)
	 {
		 ByteArrayOutputStream out = null ;
		 try {
			out = new ByteArrayOutputStream();
			int len = -1 ;
			byte[] buffer = new byte[1024];
			while((len = in.read(buffer)) != -1)
			{
				out.write(buffer, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(out != null)
					out.close();
				if(in != null)
					in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		 return out.toString();
	 }
	 
	/**
	 * 获取状态栏的高度 
	 * @param context
	 * @return
	 */
	public static int getStatusBarHeight(Context context) {
		int statusBarHeight = 0 ;
		
		if (statusBarHeight == 0) {
			try {
				Class<?> c = Class.forName("com.android.internal.R$dimen");
				Object o = c.newInstance();
				Field field = c.getField("status_bar_height");
				int x = (Integer) field.get(o);
				statusBarHeight = context.getResources().getDimensionPixelSize(x);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return statusBarHeight;
	}
	 
}
