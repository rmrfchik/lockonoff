package ru.justnews.lockoff;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefMgmt {
	
	public static SharedPreferences prefs(Context context)
	{
		return context.getSharedPreferences(
				Const.PREFERENCES, Context.MODE_PRIVATE); 
	}
	public static String getPassword(Context context) {
		return prefs(context).getString(Const.PREFERENCES_PASSWORD, "");

	}

	public static void setPassword(Context context, String password) {
		prefs(context).edit().putString(Const.PREFERENCES_PASSWORD, password).commit();
	}
	
	public static void setImmediateLock(Context context, boolean immediateLock)
	{
		prefs(context).edit().putBoolean(Const.PREFERENCES_IMMEDIATE_LOCK, immediateLock).commit();
	}
	public static boolean isImmediateLock(Context context)
	{
		return prefs(context).getBoolean(Const.PREFERENCES_IMMEDIATE_LOCK, true);
	}
}
