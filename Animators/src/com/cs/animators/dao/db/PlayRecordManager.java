package com.cs.animators.dao.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cs.animators.dao.bean.VideoPlayRecord;
import com.cs.animators.dao.common.TableUtil;


/**
 * 
 * @author Administrator
 * @param <T> 表示对象
 * @param <K> 表示主键
 */
public class PlayRecordManager implements PlayRecordDao {

	private AnimationOpenHelper helper ;
	
	public static PlayRecordManager getInstance(Context context)
	{
		return new PlayRecordManager(context); 
	}
	
	private PlayRecordManager(Context context){
		helper = new AnimationOpenHelper(context);
	}
	
	@Override
	public void save(VideoPlayRecord playRecord) {
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(TableUtil.TablePlayRecord.ID, playRecord.getId());
		values.put(TableUtil.TablePlayRecord.VIDEO_ID, playRecord.getVideoId());
		values.put(TableUtil.TablePlayRecord.PLAY_RECORD, playRecord.getPlayRecord());
		values.put(TableUtil.TablePlayRecord.SERIES, playRecord.getSeries());
		values.put(TableUtil.TablePlayRecord.RECORD_TIME, playRecord.getRecordTime());
		values.put(TableUtil.TablePlayRecord.DURATION, playRecord.getDuration());
		values.put(TableUtil.TablePlayRecord.VIDEO_NAME, playRecord.getVideoName());
		db.insert(TableUtil.TablePlayRecord.TABLE_NAME, null, values);
		db.close();
	}

	@Override
	public void delete(Integer id) {
		SQLiteDatabase db = helper.getWritableDatabase();
		String whereClause = TableUtil.TablePlayRecord.ID + " = ?";
		db.delete(TableUtil.TablePlayRecord.TABLE_NAME, whereClause, new String[]{id.toString()});
		db.close();
	}

	@Override
	public void deleteAll() {
		SQLiteDatabase db = helper.getWritableDatabase();
		db.delete(TableUtil.TablePlayRecord.TABLE_NAME, null, null);
		db.close();
	}

	@Override
	public void update(VideoPlayRecord record) {
		SQLiteDatabase db = helper.getWritableDatabase();
		String sql = "update " + TableUtil.TablePlayRecord.TABLE_NAME +" set "
				+ TableUtil.TablePlayRecord.DURATION + " = ? , "
				+ TableUtil.TablePlayRecord.PLAY_RECORD + " = ? , "
				+ TableUtil.TablePlayRecord.RECORD_TIME + " = ? , "
				+ TableUtil.TablePlayRecord.SERIES + " = ? , "
				+ TableUtil.TablePlayRecord.VIDEO_NAME + " = ? where " 
				+ TableUtil.TablePlayRecord.VIDEO_ID + " = ? and "
				+ TableUtil.TablePlayRecord.ID + " = ?";
		db.execSQL(sql, new Object[]{record.getDuration(),record.getPlayRecord(),record.getRecordTime(),
				record.getSeries(),record.getVideoName() ,record.getVideoId(),record.getId()});
		db.close();
	}

	@Override
	public VideoPlayRecord query(Integer k) {
//		SQLiteDatabase db = helper.getWritableDatabase();
//		String sql = "select * from " + TableUtil.TABLE_PLAYVIDEO_NAME + " where " 
//				+ TableUtil.TablePlayRecord.TablePlayRecord.ID + " = ?";
//		db.rawQuery(sql, selectionArgs);
		return null;
	}

	@Override
	public List<VideoPlayRecord> queryAll() {
		List<VideoPlayRecord> lists = new ArrayList<VideoPlayRecord>();
		SQLiteDatabase db = helper.getWritableDatabase();
		String sql = "select * from " + TableUtil.TablePlayRecord.TABLE_NAME ;
		Cursor cursor = db.rawQuery(sql, null);
		while(cursor != null && cursor.moveToNext())
		{
			int id = cursor.getInt(cursor.getColumnIndex(TableUtil.TablePlayRecord.ID));
			String videoId = cursor.getString(cursor.getColumnIndex(TableUtil.TablePlayRecord.VIDEO_ID));
			long playRecord = cursor.getLong(cursor.getColumnIndex(TableUtil.TablePlayRecord.PLAY_RECORD));
			int series = cursor.getInt(cursor.getColumnIndex(TableUtil.TablePlayRecord.SERIES));
			long recordTime = cursor.getLong(cursor.getColumnIndex(TableUtil.TablePlayRecord.RECORD_TIME));
			long duration = cursor.getLong(cursor.getColumnIndex(TableUtil.TablePlayRecord.DURATION));
			String videoName = cursor.getString(cursor.getColumnIndex(TableUtil.TablePlayRecord.VIDEO_NAME));
			VideoPlayRecord record = new VideoPlayRecord(id, videoId, playRecord, series, recordTime, duration,videoName);
			lists.add(record);
		}
		if(cursor != null)
			cursor.close();
		db.close();
		return lists;
	}

	@Override
	public VideoPlayRecord queryLastPlayRecord(String videoId) {
		VideoPlayRecord record = null ;
		SQLiteDatabase db = helper.getWritableDatabase();
		String sql = "select * from " + TableUtil.TablePlayRecord.TABLE_NAME + " where "
				+ TableUtil.TablePlayRecord.VIDEO_ID +" = ? and "
				+ TableUtil.TablePlayRecord.RECORD_TIME + " > 0" + " order by "
				+ TableUtil.TablePlayRecord.RECORD_TIME + " desc";
		Cursor cursor = db.rawQuery(sql, new String[]{videoId});
		//这里取第一项 降序排列第一项为 最后播放的记录   不实用循环while
		if(cursor != null && cursor.moveToNext())
		{
			long id = cursor.getLong(cursor.getColumnIndex(TableUtil.TablePlayRecord.ID));
			long playRecord = cursor.getLong(cursor.getColumnIndex(TableUtil.TablePlayRecord.PLAY_RECORD));
			int series = cursor.getInt(cursor.getColumnIndex(TableUtil.TablePlayRecord.SERIES));
			long recordTime = cursor.getLong(cursor.getColumnIndex(TableUtil.TablePlayRecord.RECORD_TIME));
			long duration = cursor.getLong(cursor.getColumnIndex(TableUtil.TablePlayRecord.DURATION));
			String videoName = cursor.getString(cursor.getColumnIndex(TableUtil.TablePlayRecord.VIDEO_NAME));
			record = new VideoPlayRecord(id, videoId, playRecord, series, recordTime, duration,videoName);
		}
		if(cursor != null)
			cursor.close();
		db.close();
		return record;
	}

	@Override
	public VideoPlayRecord queryVideoPlayRecord(String videoId, long id) {
		VideoPlayRecord record = null ;
		SQLiteDatabase db = helper.getWritableDatabase();
		String sql = "select * from " + TableUtil.TablePlayRecord.TABLE_NAME + " where "
				+ TableUtil.TablePlayRecord.VIDEO_ID +" = ? and "
				+ TableUtil.TablePlayRecord.ID + " = ?" ;
		Cursor cursor = db.rawQuery(sql, new String[]{videoId,String.valueOf(id)});
		//这里取第一项 降序排列第一项为 最后播放的记录   不实用循环while
		if(cursor != null && cursor.moveToNext())
		{
			long playRecord = cursor.getLong(cursor.getColumnIndex(TableUtil.TablePlayRecord.PLAY_RECORD));
			int series = cursor.getInt(cursor.getColumnIndex(TableUtil.TablePlayRecord.SERIES));
			long recordTime = cursor.getLong(cursor.getColumnIndex(TableUtil.TablePlayRecord.RECORD_TIME));
			long duration = cursor.getLong(cursor.getColumnIndex(TableUtil.TablePlayRecord.DURATION));
			String videoName = cursor.getString(cursor.getColumnIndex(TableUtil.TablePlayRecord.VIDEO_NAME));
			record = new VideoPlayRecord(id, videoId, playRecord, series, recordTime, duration,videoName);
		}
		if(cursor != null)
			cursor.close();
		db.close();
		return record;
	}

	@Override
	public void saveOrUpdate(VideoPlayRecord record) {
		VideoPlayRecord playRecord = queryVideoPlayRecord(record.getVideoId(), record.getId());
		if(playRecord != null)
		{
			update(record);
		}
		else
		{
			save(record);
		}
	}

	@Override
	public void delete(String videoId, long id) {
		SQLiteDatabase db = helper.getWritableDatabase();
		String whereClause = TableUtil.TablePlayRecord.ID + " = ? and "
				+ TableUtil.TablePlayRecord.VIDEO_ID + " = ? ";
		db.delete(TableUtil.TablePlayRecord.TABLE_NAME, whereClause, new String[]{id + "",videoId});
		db.close();
	}


}
