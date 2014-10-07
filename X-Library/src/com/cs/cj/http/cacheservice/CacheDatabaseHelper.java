package com.cs.cj.http.cacheservice;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CacheDatabaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "jhttpcache.db";
    private static final int DATABASE_VERSION = 1;
    public CacheDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
    }

    /**
     * According to the time to determine whether the need to refresh the cache
     * data inside
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        onUpgrade(db, 0, DATABASE_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        if (oldV >= newV) {
            return;
        }

        for (int version = oldV + 1; version <= newV; version++) {
            upgradeTo(db, version);
        }

    }

    private void upgradeTo(SQLiteDatabase db, int version) {
        switch (version) {
            case 1:
                db.execSQL(CacheTable.CREATE_TABLE_LATESTSEARCHTABLE);
                break;

            default:
                break;
        }
    }



//    /**
//     * Add a column to a table using ALTER TABLE.
//     * @param dbTable          name of the table
//     * @param columnName       name of the column to add
//     * @param columnDefinition SQL for the column definition
//     */
//    private void addColumn(SQLiteDatabase db, String dbTable, String columnName,String columnDefinition) {
//        db.execSQL("ALTER TABLE " + dbTable + " ADD COLUMN " + columnName + " "+ columnDefinition);
//    }
//
//    private void dropTable(SQLiteDatabase db, String dbTable) {
//        db.execSQL("DROP TABLE IF EXISTS " + dbTable);
//    }

}
