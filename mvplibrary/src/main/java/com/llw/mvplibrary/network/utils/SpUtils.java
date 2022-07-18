package com.llw.mvplibrary.network.utils;
 
import android.content.Context;
import android.content.SharedPreferences;
 
public class SpUtils {
	private static String SPFILENAME = "SmartBeiJingSp";
 
	/**
	 * @param mc
	 *            上下文
	 * @param key
	 *            根据此节点找值
	 * 
	 * @param def
	 *            默认值
	 * @return 返回储存的值
	 */
	public static int getSpInt(Context mc, String key, int def) {
		SharedPreferences sp = mc.getSharedPreferences(SPFILENAME,
				mc.MODE_PRIVATE);
		return sp.getInt(key, def);
	}
 
	/**
	 * @param mc
	 *            上下文
	 * @param key
	 *            数据所在节点名
	 * @param value
	 *            保存的值
	 */
	public static void putSpInt(Context mc, String key, int value) {
		SharedPreferences sp = mc.getSharedPreferences(SPFILENAME,
				mc.MODE_PRIVATE);
		sp.edit().putInt(key, value).commit();
	}
 
	/**
	 * @param mc
	 *            上下文
	 * @param key
	 *            根据此节点找值
	 * @param def
	 *            默认值
	 * @return 返回储存的值
	 */
	public static String getSpString(Context mc, String key, String def) {
		SharedPreferences sp = mc.getSharedPreferences(SPFILENAME,
				mc.MODE_PRIVATE);
		return sp.getString(key, def);
	}
 
	/**
	 * @param mc
	 *            上下文
	 * @param key
	 *            数据所在节点名
	 * @param value
	 *            保存的值
	 */
	public static void putSpString(Context mc, String key, String value) {
		SharedPreferences sp = mc.getSharedPreferences(SPFILENAME,
				mc.MODE_PRIVATE);
		sp.edit().putString(key, value).commit();
	}
 
	/**
	 * @param mc
	 *            上下文
	 * @param key
	 *            根据此节点找值
	 * @param def
	 *            默认值
	 * @return 返回储存的值
	 */
	public static boolean getSpBoolean(Context mc, String key, boolean def) {
		SharedPreferences sp = mc.getSharedPreferences(SPFILENAME,
				mc.MODE_PRIVATE);
		return sp.getBoolean(key, def);
	}
 
	/**
	 * @param mc
	 *            上下文
	 * @param key
	 *            数据所在节点名
	 * @param value
	 *            保存的值
	 */
	public static void putSpBoolean(Context mc, String key, boolean value) {
		SharedPreferences sp = mc.getSharedPreferences(SPFILENAME,
				mc.MODE_PRIVATE);
		sp.edit().putBoolean(key, value).commit();
	}
 
	/**
	 * 移除一个节点
	 * 
	 * @param mc
	 *            上下文
	 * @param key
	 *            要移除的节点
	 */
	public static void remove(Context mc, String key) {
		SharedPreferences sp = mc.getSharedPreferences(SPFILENAME,
				mc.MODE_PRIVATE);
		sp.edit().remove(key).commit();
	}
 
}