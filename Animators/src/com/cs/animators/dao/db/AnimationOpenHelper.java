package com.cs.animators.dao.db;

import com.cs.animators.dao.common.TableUtil;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class AnimationOpenHelper extends SQLiteOpenHelper {

	public AnimationOpenHelper(Context context) {
		super(context, TableUtil.DATABSE_NAME, null, TableUtil.VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//创建PlayVideo表
		String playvideo_sql = "create table " + TableUtil.TablePlayRecord.TABLE_NAME +"( " 
		+ TableUtil.TablePlayRecord.PLAYRECORD_ID + " integer primary key autoincrement,"
		+ TableUtil.TablePlayRecord.ID + " long ,"
		+ TableUtil.TablePlayRecord.VIDEO_ID +" long ,"
		+ TableUtil.TablePlayRecord.PLAY_RECORD + " long ,"
		+ TableUtil.TablePlayRecord.SERIES + " integer ,"
		+ TableUtil.TablePlayRecord.RECORD_TIME +" long , "
		+ TableUtil.TablePlayRecord.DURATION + " long )";
		Log.i("playvideo_table_sql", playvideo_sql);
		db.execSQL(playvideo_sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {

		String playvideo_sql = "drop table if exists " + TableUtil.TablePlayRecord.TABLE_NAME ;
		db.execSQL(playvideo_sql);
		onCreate(db);
	}

}
