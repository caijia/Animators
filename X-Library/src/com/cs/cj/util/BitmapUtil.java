package com.cs.cj.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Base64;

public class BitmapUtil {
	
  //计算图片的缩放值
    private static int calculateInSampleSize(BitmapFactory.Options options,int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
                 final int heightRatio = Math.round((float) height/ (float) reqHeight);
                 final int widthRatio = Math.round((float) width / (float) reqWidth);
                 inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
            return inSampleSize;
    }
    
  // 根据路径获得图片并压缩，返回bitmap用于显示
    private  static Bitmap getSmallBitmap(Context context ,int resId, int width , int height) {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(context.getResources(), resId, options);

            // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, width, height);

            // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeResource(context.getResources(), resId, options);
        }
    
 // 根据路径获得图片并压缩，返回bitmap用于显示
    public  static Bitmap getSmallBitmap(String filePath , int width , int height) {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(filePath, options);

            // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, width, height);

            // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(filePath, options);
        }
    
  //把bitmap转换成String
    public static String bitmapToString(String filePath , int width , int height) {
            ByteArrayOutputStream baos = bitmapToStream(filePath, width, height);
            byte[] b = baos.toByteArray();
            return Base64.encodeToString(b, 0, b.length, Base64.DEFAULT);
        }

    public static ByteArrayOutputStream bitmapToStream(String filePath, int width,int height) {
		Bitmap bm = getSmallBitmap(filePath,width,height);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 40, baos);
		return baos;
	}
	
	 public static String bitmapToString(Context context , int resId , int width , int height) {
         ByteArrayOutputStream baos = bitmapToStream(context , resId, width, height);
         byte[] b = baos.toByteArray();
         return Base64.encodeToString(b, 0, b.length, Base64.DEFAULT);
     }

	private static ByteArrayOutputStream bitmapToStream(Context context , int resId , int width , int height) {
		Bitmap bm = getSmallBitmap(context,resId,width,height);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 40, baos);
		return baos;
	}
	
    public static File bitmapToFile(Context context , Bitmap bitmap, String path){
    	File saveFile = new File(path);
    	if(saveFile.exists()){
    		return saveFile ;
    	}
    	OutputStream out = null ;
    	if(saveFile != null)
    	{
    		try {
				out = new FileOutputStream(saveFile);
			} catch (FileNotFoundException e) {}
    	}
    	if(out != null){
    		bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
    	}
    	return saveFile ;
    }
    
    public static Bitmap decodeBitmap(Context context ,int resId,int width,int height)  {
            Bitmap bm = getSmallBitmap(context ,resId , width , height);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 80, baos);
            Bitmap bitmap = BitmapFactory.decodeByteArray(baos.toByteArray(), 0, baos.size());
            return bitmap ;
        }
    
    public static Bitmap decodeBitmap(String pathName,int width,int height)  {
        Bitmap bm = getSmallBitmap(pathName , width , height);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 80, baos);
        Bitmap bitmap = BitmapFactory.decodeByteArray(baos.toByteArray(), 0, baos.size());
        return bitmap ;
    }
    
    public static Bitmap scaleImageView(Bitmap src , float scaleX , float scaleY){
		Matrix m = new Matrix();
		m.postScale(scaleX, scaleY);
		Bitmap createBitmap = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), m, true);
		return createBitmap ;
	}
    
}
