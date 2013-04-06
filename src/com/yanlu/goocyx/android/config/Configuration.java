package com.yanlu.goocyx.android.config;

import android.content.res.AssetManager;
import com.yanlu.goocyx.android.GlobalApplication;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * User: captain_miao
 * Date: 13-4-6
 */
public class Configuration {

	public static final String CFG_PRINT_LOG_KEY = "isprintlog";

	private static Properties defaultProperty;

	static {
		init();
	}

	static void init() {
		defaultProperty = new Properties();

		defaultProperty.setProperty(CFG_PRINT_LOG_KEY, "true");
		InputStream is = null;
		String t4jProps = "config.dat";

		try {
			AssetManager am = GlobalApplication.getContext().getAssets();
			is = am.open(t4jProps);
			defaultProperty.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();

			}
		}
	}

	public static boolean getBoolean(String name) {
		String value = getProperty(name);
		return Boolean.valueOf(value);
	}

	public static int getIntProperty(String name) {
		String value = getProperty(name);
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException nfe) {
			return -1;
		}
	}

	public static long getLongProperty(String name) {
		String value = getProperty(name);
		try {
			return Long.parseLong(value);
		} catch (NumberFormatException nfe) {
			return -1;
		}
	}

	public static String getProperty(String name) {
		return defaultProperty.getProperty(name);
	}
}
