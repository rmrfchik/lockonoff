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
		DevicePolicyManager mDPM = (DevicePolicyManager) context
				.getSystemService(Context.DEVICE_POLICY_SERVICE);
		//TODO: remove magic string with package and classname
		if (mDPM.isAdminActive(new ComponentName("ru.justnews.lockoff","ru.justnews.lockoff.AppAdminReceiver"))) {
			if (!state) {
				mDPM.resetPassword(
						PasswordMgmt.getPassword(context), 0);
				mDPM.lockNow();
			} else {
				mDPM.resetPassword(Const.EMPTY_PASSWORD, 0);
				Log.i(Const.TAG, "Password locking disabled");
			}
		} else
		{
			Log.i(Const.TAG, "Admin not active");
		}
	}
}