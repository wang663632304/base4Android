package com.yanlu.goocyx.android.common.util;

import com.yanlu.goocyx.android.config.Configuration;

/**
 * User: captain_miao
 * Date: 13-4-6
 */
public final class Log2 {
	private static boolean isPrint = Configuration.getBoolean(Configuration.CFG_PRINT_LOG_KEY);

	private static final String appname = "BaseAndroid";

	private Log2() {
	}

	public static boolean isPrintable() {
		return isPrint;
	}

	public static void setPrintable(boolean isPrint) {
		Log2.isPrint = isPrint;
	}

	/**
	 *
	 * @param tag
	 *            Used to identify the source of a log message. It usually
	 *            identifies the class or activity where the log call occurs.
	 * @param msg
	 *            The message you would like logged.
	 */
	public static int v(String tag, String msg) {
		return isPrint ? android.util.Log.v("[" + appname + "]" + tag, msg)
				: -1;
	}

	public static int d(String tag, String msg) {
		return isPrint ? android.util.Log.d("[" + appname + "]" + tag, msg)
				: -1;
	}

	public static int i(String tag, String msg) {
		return isPrint ? android.util.Log.i("[" + appname + "]" + tag, msg)
				: -1;
	}

	public static int w(String tag, String msg) {
		return isPrint ? android.util.Log.w("[" + appname + "]" + tag, msg)
				: -1;
	}

	public static int e(String tag, String msg) {
		return isPrint ? android.util.Log.e("[" + appname + "]" + tag, msg)
				: -1;
	}

	public static int v(String tag, String msg, Throwable tr) {
		return isPrint ? android.util.Log.v("[" + appname + "]" + tag, msg, tr)
				: -1;
	}

	public static int d(String tag, String msg, Throwable tr) {
		return isPrint ? android.util.Log.d("[" + appname + "]" + tag, msg, tr)
				: -1;
	}

	public static int i(String tag, String msg, Throwable tr) {
		return isPrint ? android.util.Log.i("[" + appname + "]" + tag, msg, tr)
				: -1;
	}

	public static int w(String tag, String msg, Throwable tr) {
		return isPrint ? android.util.Log.w("[" + appname + "]" + tag, msg, tr)
				: -1;
	}

	public static int e(String tag, String msg, Throwable tr) {
		return isPrint ? android.util.Log.e("[" + appname + "]" + tag, msg, tr)
				: -1;
	}
}
