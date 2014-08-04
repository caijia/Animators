package com.cs.animators.dao.service;

import java.sql.SQLException;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.cs.animators.dao.bean.VideoPlayRecord;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class AnimSQLiteHelper extends OrmLiteSqliteOpenHelper {
	
	public AnimSQLiteHelper(Context context) {
		super(context, "anim.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
		try {
			TableUtils.createTableIfNotExists(connectionSource, VideoPlayRecord.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, ConnectionSource connectionSource, int arg2,
			int arg3) {
		try {
			TableUtils.dropTable(connectionSource, VideoPlayRecord.class, true);
			onCreate(arg0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
