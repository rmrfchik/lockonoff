package ru.justnews.lockoff;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AppAdminReceiver extends DeviceAdminReceiver{
	
	public AppAdminReceiver()
	{
		super();
	}

	void showToast(Context context, CharSequence msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }	
    
    @Override
    public void onEnabled(Context context, Intent intent) {
        showToast(context, "MetawatchLocker enabled");
    }
    
    @Override
    public void onDisabled(Context context, Intent intent) {
        showToast(context, "MetawatchLocker disabled");
    }
}
