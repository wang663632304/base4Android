package com.yanlu.goocyx.android.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * 
 * @author goocyx
 *
 * 2013-4-10
 */
public class DBHelper extends SQLiteOpenHelper {
	private static final String DB_NAME = "yourdb";
	private static final int DB_VERSION = 1;
	
	public DBHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//各个表的创建
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//数据库的升级
	}

}
