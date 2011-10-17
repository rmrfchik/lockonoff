package ru.justnews.lockoff;

import android.content.Context;
import android.content.SharedPreferences;

public class PasswordMgmt {
	public static String getPassword(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(
				Const.PREFERENCES, Context.MODE_PRIVATE);
		return prefs.getString(Const.PREFERENCES_PASSWORD, "");

	}

	public static void setPassword(Context context, String password) {
		SharedPreferences prefs = context.getSharedPreferences(
				Const.PREFERENCES, Context.MODE_PRIVATE);
		prefs.edit().putString(Const.PREFERENCES_PASSWORD, password).commit();
	}
}
