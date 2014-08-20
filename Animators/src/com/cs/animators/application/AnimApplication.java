package com.cs.animators.application;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.baidu.frontia.FrontiaApplication;
import com.cs.cj.application.JApplication;
import com.cs.cj.http.cacheservice.CacheDatabaseHelper;

public class AnimApplication extends JApplication {
	@Override
	public void onCreate() {
		super.onCreate();
		FrontiaApplication.initFrontiaApplication(this);
		copyDataBase();
	}
	
	   public void copyDataBase() {
	        // 复制数据库
	        String dbPath = getCacheDir().getParent() + "/databases/" + CacheDatabaseHelper.DB_NAME;
	        File file = new File(dbPath);
	        if (!file.exists()) {
	            BufferedInputStream in = null;
	            BufferedOutputStream out = null;
	            try {
	                file.getParentFile().mkdirs();
	                in = new BufferedInputStream(getAssets().open(CacheDatabaseHelper.DB_NAME), 1024);
	                out = new BufferedOutputStream(new FileOutputStream(file));
	                byte buffer[] = new byte[1024];
	                int len = -1;
	                while ((len = in.read(buffer)) != -1) {
	                    out.write(buffer, 0, len);
	                }
	            } catch (IOException e) {
	                e.printStackTrace();
	            } finally {
	                try {
	                    if (out != null)
	                        out.close();
	                    if (in != null)
	                        in.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	    }
}
