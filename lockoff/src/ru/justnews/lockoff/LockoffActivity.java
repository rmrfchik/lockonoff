package ru.justnews.lockoff;

import android.app.Activity;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;

public class LockoffActivity extends Activity {

	protected final int RESULT_ENABLE = 1;
	ComponentName adminComponentName;
	AppAdminReceiver appAdminReceiver;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		adminComponentName=new ComponentName(this, AppAdminReceiver.class);
		appAdminReceiver=new AppAdminReceiver();
		
		setContentView(R.layout.main);
		ToggleButton onOff = (ToggleButton) findViewById(R.id.onOff);
		updateButtonStatus();
		onOff.setOnCheckedChangeListener(new OnCheckedChangeListener() {			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean state) {
				if (state) {
					Intent intent = new Intent(
							DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
					intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,
							adminComponentName);
					Log.i("LOCKONOFF","Component '"+adminComponentName.toString()+"'");
					intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
							"To be able to clear and set password when Metawatch connection changed");
					startActivityForResult(intent, RESULT_ENABLE);
				} else {
					DevicePolicyManager mDPM = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
					mDPM.removeActiveAdmin(adminComponentName);
				}
				updateButtonStatus();
			}
		});
		Button setPassword = (Button) findViewById(R.id.setPassword);
		setPassword.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(LockoffActivity.this,
						ChangePasswordActivity.class);
				startActivity(intent);
			}
		});
	}

	private void updateButtonStatus() {
		ToggleButton onOff = (ToggleButton) findViewById(R.id.onOff);
		DevicePolicyManager mDPM = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
		onOff.setChecked(mDPM.isAdminActive(adminComponentName));
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		switch (requestCode) {
		case RESULT_ENABLE:
			if (resultCode == Activity.RESULT_OK) {
				Log.i("LockOnOff", "Admin enabled!");
			} else {
				Log.i("LockOnOff", "Admin enable FAILED!");
			}
			updateButtonStatus();
			return;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	
}