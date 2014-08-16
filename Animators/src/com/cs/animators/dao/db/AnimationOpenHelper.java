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
		String playvideo_sql = "create table if not exists " + TableUtil.TablePlayRecord.TABLE_NAME +"( " 
				+ TableUtil.TablePlayRecord.PLAYRECORD_ID + " integer primary key autoincrement,"
				+ TableUtil.TablePlayRecord.ID + " long ,"
				+ TableUtil.TablePlayRecord.VIDEO_ID +" long ,"
				+ TableUtil.TablePlayRecord.PLAY_RECORD + " long ,"
				+ TableUtil.TablePlayRecord.SERIES + " integer ,"
				+ TableUtil.TablePlayRecord.RECORD_TIME +" long , "
				+ TableUtil.TablePlayRecord.DURATION + " long , "
				+ TableUtil.TablePlayRecord.VIDEO_NAME + " text )";
		Log.i("playvideo_table_sql", playvideo_sql);
		db.execSQL(playvideo_sql);
		
		//创建VideoCollect表
		String videoCollect_sql = "create table if not exists " + TableUtil.TableVideoCollect.TABLE_NAME +"( "
				+ TableUtil.TableVideoCollect.VIDEO_ID + " text primary key , "
				+ TableUtil.TableVideoCollect.NAME + " text , "
				+ TableUtil.TableVideoCollect.COVER + " text ,"
				+ TableUtil.TableVideoCollect.CUR_SERIES + " text , "
				+ TableUtil.TableVideoCollect.TOTAL_SERIES + " text , "
				+ TableUtil.TableVideoCollect.CATEGORY + " text , "
				+ TableUtil.TableVideoCollect.SCORE + " text , "
				+ TableUtil.TableVideoCollect.UPDATE_YEAR + " text )";
		Log.i("videoCollect_table_sql", videoCollect_sql);
		db.execSQL(videoCollect_sql);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {

		//删除PlayVideo表
		String playvideo_sql = "drop table if exists " + TableUtil.TablePlayRecord.TABLE_NAME ;
		db.execSQL(playvideo_sql);
		
		//删除VideoCollect表
		String videoCollect_sql = "drop table if exists " + TableUtil.TableVideoCollect.TABLE_NAME ;
		db.execSQL(videoCollect_sql);
		
		//重建
		onCreate(db);
	}

}
