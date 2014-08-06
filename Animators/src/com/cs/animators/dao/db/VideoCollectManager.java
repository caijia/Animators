package com.cs.animators.dao.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cs.animators.dao.bean.VideoCollect;
import com.cs.animators.dao.common.TableUtil;

public class VideoCollectManager implements VideoCollectDao {

	private AnimationOpenHelper helper ;
	
	public static VideoCollectManager getInstance(Context context)
	{
		return new VideoCollectManager(context); 
	}
	
	private VideoCollectManager(Context context){
		helper = new AnimationOpenHelper(context);
	}
	
	@Override
	public void save(VideoCollect videoCollect) {
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(TableUtil.TableVideoCollect.VIDEO_ID, videoCollect.getVideoId());
		values.put(TableUtil.TableVideoCollect.NAME, videoCollect.getName());
		values.put(TableUtil.TableVideoCollect.COVER, videoCollect.getCover());
		values.put(TableUtil.TableVideoCollect.CUR_SERIES, videoCollect.getCurSeries());
		values.put(TableUtil.TableVideoCollect.TOTAL_SERIES, videoCollect.getTotalSeries());
		values.put(TableUtil.TableVideoCollect.CATEGORY, videoCollect.getCategory());
		values.put(TableUtil.TableVideoCollect.SCORE, videoCollect.getScore());
		values.put(TableUtil.TableVideoCollect.UPDATE_YEAR, videoCollect.getUpdateYear());
		db.insert(TableUtil.TableVideoCollect.TABLE_NAME, null, values);
		if(db != null)
		{
			db.close();
		}
	}

	@Override
	public void delete(String key) {
		SQLiteDatabase db = helper.getWritableDatabase();
		db.delete(TableUtil.TableVideoCollect.TABLE_NAME,
				TableUtil.TableVideoCollect.VIDEO_ID + " = ? ",
				new String[] { key });
		if (db != null) {
			db.close();
		}
	}

	@Override
	public void deleteAll() {
		SQLiteDatabase db = helper.getWritableDatabase();
		db.delete(TableUtil.TableVideoCollect.TABLE_NAME,null,null);
		if (db != null) {
			db.close();
		}
	}

	@Override
	public void update(VideoCollect videoCollect) {
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(TableUtil.TableVideoCollect.VIDEO_ID, videoCollect.getVideoId());
		values.put(TableUtil.TableVideoCollect.NAME, videoCollect.getName());
		values.put(TableUtil.TableVideoCollect.COVER, videoCollect.getCover());
		values.put(TableUtil.TableVideoCollect.CUR_SERIES, videoCollect.getCurSeries());
		values.put(TableUtil.TableVideoCollect.TOTAL_SERIES, videoCollect.getTotalSeries());
		values.put(TableUtil.TableVideoCollect.CATEGORY, videoCollect.getCategory());
		values.put(TableUtil.TableVideoCollect.SCORE, videoCollect.getScore());
		values.put(TableUtil.TableVideoCollect.UPDATE_YEAR, videoCollect.getUpdateYear());
		db.update(TableUtil.TableVideoCollect.TABLE_NAME, values, TableUtil.TableVideoCollect.VIDEO_ID + " = ? ", new String[] { videoCollect.getVideoId() });
		if (db != null) {
			db.close();
		}
	}

	@Override
	public VideoCollect query(String key) {
		VideoCollect videoCollect = null ;
		SQLiteDatabase db = helper.getWritableDatabase();
		String sql = "select * from " + TableUtil.TableVideoCollect.TABLE_NAME + " where " 
				+ TableUtil.TableVideoCollect.VIDEO_ID + " = ? " ;
		Cursor cs = db.rawQuery(sql, new String[]{key});
		if(cs != null && cs.moveToNext())
		{
			String videoId = cs.getString(cs.getColumnIndex(TableUtil.TableVideoCollect.VIDEO_ID));
			String name = cs.getString(cs.getColumnIndex(TableUtil.TableVideoCollect.NAME));
			String cover = cs.getString(cs.getColumnIndex(TableUtil.TableVideoCollect.COVER));
			String curSeries = cs.getString(cs.getColumnIndex(TableUtil.TableVideoCollect.CUR_SERIES));
			String totalSeries = cs.getString(cs.getColumnIndex(TableUtil.TableVideoCollect.TOTAL_SERIES));
			String category = cs.getString(cs.getColumnIndex(TableUtil.TableVideoCollect.CATEGORY));
			String score = cs.getString(cs.getColumnIndex(TableUtil.TableVideoCollect.SCORE));
			String updateYear = cs.getString(cs.getColumnIndex(TableUtil.TableVideoCollect.UPDATE_YEAR));
			videoCollect = new VideoCollect(videoId, name, cover, curSeries, totalSeries, category, score, updateYear);
		}
		if (cs != null) {
			cs.close();
		}
		if (db != null) {
			db.close();
		}
		return videoCollect;
	}

	@Override
	public List<VideoCollect> queryAll() {
		List<VideoCollect> lists = new ArrayList<VideoCollect>();
		SQLiteDatabase db = helper.getWritableDatabase();
		String sql = "select * from " + TableUtil.TableVideoCollect.TABLE_NAME ;
		Cursor cs = db.rawQuery(sql,null);
		while(cs != null && cs.moveToNext())
		{
			String videoId = cs.getString(cs.getColumnIndex(TableUtil.TableVideoCollect.VIDEO_ID));
			String name = cs.getString(cs.getColumnIndex(TableUtil.TableVideoCollect.NAME));
			String cover = cs.getString(cs.getColumnIndex(TableUtil.TableVideoCollect.COVER));
			String curSeries = cs.getString(cs.getColumnIndex(TableUtil.TableVideoCollect.CUR_SERIES));
			String totalSeries = cs.getString(cs.getColumnIndex(TableUtil.TableVideoCollect.TOTAL_SERIES));
			String category = cs.getString(cs.getColumnIndex(TableUtil.TableVideoCollect.CATEGORY));
			String score = cs.getString(cs.getColumnIndex(TableUtil.TableVideoCollect.SCORE));
			String updateYear = cs.getString(cs.getColumnIndex(TableUtil.TableVideoCollect.UPDATE_YEAR));
			VideoCollect videoCollect = new VideoCollect(videoId, name, cover, curSeries, totalSeries, category, score, updateYear);
			lists.add(videoCollect);
		}
		if (cs != null) {
			cs.close();
		}
		if (db != null) {
			db.close();
		}
		return lists;
	}

	@Override
	public void saveOrUpdate(VideoCollect collect) {
		VideoCollect videoCollect = query(collect.getVideoId());
		if(videoCollect != null)
		{
			update(collect);
		}
		else
		{
			save(collect);
		}
	}

}