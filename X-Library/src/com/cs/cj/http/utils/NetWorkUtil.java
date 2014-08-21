
package com.cs.cj.http.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.widget.TextView;

/**
 *  描述: 网络工具类
 * @ClassName: NetWorkUtil
 * @author xiaoming.yuan
 * @date 2013-12-23 上午11:09:30
 */
public class NetWorkUtil {

    /**
     * 描述:网络类型
     * @ClassName: NetworkType
     * @author xiaoming.yuan
     * @date 2013-12-23 上午11:09:53
     */
    public static class NetworkType {

        public static final String UNKNOWN = "unknown";

        public static final String NET_2G = "2G";

        public static final String NET_3G = "3G";

        public static final String WIFI = "wifi";

        public static final String NET_CMNET = "cmnet";

        public static final String NET_CMWAP = "cmwap";
    }

//    /**
//     *
//     * @Title: isNetworkAvailable(当前是否有可用网络)
//     * @author xiaoming.yuan
//     * @data 2013-12-23 上午11:10:22
//     * @param context
//     * @return  boolean 返回类型
//     */
//    public static boolean isNetworkAvailable(Context context) {
//        return !(NetworkType.UNKNOWN.endsWith(getNetworkType(context)));
//    }



    /**
     *
     * @Title: isConnected(网络是否连接)
     * @author xiaoming.yuan
     * @data 2013-12-23 上午11:10:22
     * @param context
     * @return
     * boolean 返回类型
     */
    public static boolean isConnected(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context. getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }


    /**
     *
     * @Title: getNetworkType( 获取当前的网络类型)
     * @author xiaoming.yuan
     * @data 2013-12-23 上午11:10:45
     * @param context
     * @return
     * String 返回类型
     */
    public static String getNetworkType(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo == null) {
            return NetworkType.UNKNOWN;
        }
        if (netInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return NetworkType.WIFI;
        }
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        int netType = tm.getNetworkType();

        // 已知3G类型
        // NETWORK_TYPE_UMTS 3
        // NETWORK_TYPE_EVDO_0 5
        // NETWORK_TYPE_EVDO_A 6
        // NETWORK_TYPE_HSDPA 8
        // NETWORK_TYPE_HSUPA 9
        // NETWORK_TYPE_HSPA 10
        // NETWORK_TYPE_EVDO_B 12
        // NETWORK_TYPE_LTE 13
        // NETWORK_TYPE_EHRPD 14
        // NETWORK_TYPE_HSPAP 15

        // 已知2G类型
        // NETWORK_TYPE_GPRS 1
        // NETWORK_TYPE_EDGE 2
        // NETWORK_TYPE_CDMA 4
        // NETWORK_TYPE_1xRTT 7
        // NETWORK_TYPE_IDEN 11

        if (netType == TelephonyManager.NETWORK_TYPE_GPRS
                || netType == TelephonyManager.NETWORK_TYPE_EDGE
                || netType == TelephonyManager.NETWORK_TYPE_CDMA
                || netType == TelephonyManager.NETWORK_TYPE_1xRTT || netType == 11) {
            return NetworkType.NET_2G;
        }
        return NetworkType.NET_3G;
    }
    
    public static final int NO_NETWORK_TYPE = -1;
    
	public static int getNetType(Context context) {
		int netStatus = NO_NETWORK_TYPE;
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mobileNetInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		NetworkInfo wifiNetInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		NetworkInfo allNetInfo = cm.getActiveNetworkInfo();

		if (allNetInfo == null) {
			if (mobileNetInfo != null&& (mobileNetInfo.isConnected() || mobileNetInfo.isConnectedOrConnecting())) {
				netStatus = ConnectivityManager.TYPE_MOBILE;
			} else if (wifiNetInfo != null && wifiNetInfo.isConnected()|| wifiNetInfo.isConnectedOrConnecting()) {
				netStatus = ConnectivityManager.TYPE_WIFI;
			} else {
				netStatus = NO_NETWORK_TYPE;
			}
		} else {
			if (allNetInfo.isConnected()|| allNetInfo.isConnectedOrConnecting()) {
				if (mobileNetInfo.isConnected()|| mobileNetInfo.isConnectedOrConnecting()) {
					netStatus = ConnectivityManager.TYPE_MOBILE;
				} else {
					netStatus = ConnectivityManager.TYPE_WIFI;
				}
			} else {
				netStatus = NO_NETWORK_TYPE;
			}
		}
		return netStatus ;
	}

    /**
     *
     * @Title: isCmwap(是否cmwap)
     * @author xiaoming.yuan
     * @data 2013-12-23 上午11:11:27
     * @param context
     * @return
     * boolean 返回类型
     */
    public static boolean isCmwap(Context context) {
        String currentNetworkType = getNetworkType(context);
        if (NetworkType.NET_2G.equals(currentNetworkType)) {
            try {
                ConnectivityManager connectMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connectMgr != null) {
                    NetworkInfo mobNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                    if (mobNetInfo != null && mobNetInfo.isConnected()) {
                        if (NetworkType.NET_CMWAP.equalsIgnoreCase(mobNetInfo.getExtraInfo())) {
                            return true;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }



    public static void checkNetwork(final Activity activity) {
        if (!isConnected(activity)) {
            TextView msg = new TextView(activity);
            msg.setText("当前没有可以使用的网络，请设置网络!");
            new AlertDialog.Builder(activity).setIcon(android.R.drawable.ic_dialog_info).setTitle("网络状态提示").setView(msg).setPositiveButton("确定", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int whichButton) {
                    // 跳转到设置界面
                    activity.startActivityForResult(new Intent(Settings.ACTION_WIRELESS_SETTINGS), 0);
                }
            }).create().show();
        }
        return;
    }
}
