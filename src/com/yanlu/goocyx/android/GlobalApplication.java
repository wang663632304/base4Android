package com.yanlu.goocyx.android;

import android.app.Application;
import android.content.Context;
import com.yanlu.goocyx.android.common.util.Log2;
import com.yanlu.goocyx.android.db.DBHelper;

/**
 * User: captain_miao
 * Date: 13-4-6
 */
public class GlobalApplication extends Application {

	private static final String TAG = "GlobalApplication";

	private static Context context;
	
	private static DBHelper mDbHelper;

	public static Context getContext() {
		return context;
	}


	@Override
	public void onCreate() {
		super.onCreate();
		context = this.getApplicationContext();// 必须先初始化

		Log2.i(TAG, "app ------------------->  onCreate");



		Log2.i(TAG, "app ------------------->  onCreate  over");
	}

    //读取values.string的值
    public static String getStringFromValues(int i) {
        return context.getString(i);
    }
    
    //获取数据库访问实例
    public static DBHelper getDBHelper() {
		if (mDbHelper == null) {
			mDbHelper = new DBHelper(context);
		}
		return mDbHelper;
	}
}
