package ru.justnews.lockoff;

import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

public class MWMReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		boolean state = intent.getBooleanExtra("state", false);
		Log.i("LOCKONOFF", "State changed " + intent.getStringExtra("reason"));
		DevicePolicyManager mDPM = (DevicePolicyManager) context
				.getSystemService(Context.DEVICE_POLICY_SERVICE);
		if (mDPM.isAdminActive(new ComponentName("ru.justnews.lockoff","ru.justnews.lockoff.AppAdminReceiver"))) {
			if (!state) {
				SharedPreferences prefs = context.getSharedPreferences(
						Const.PREFERENCES, Context.MODE_PRIVATE);
				mDPM.resetPassword(
						prefs.getString(Const.PREFERENCES_PASSWORD, ""), 0);
				mDPM.lockNow();
			} else {
				mDPM.resetPassword("", 0);
				Log.i("LOCKONOFF", "Password locking disabled");
			}
		} else
		{
			Log.i("LOCKONOF", "Admin not active");
		}
	}
}