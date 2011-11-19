package ru.justnews.lockoff;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.PopupWindow;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;

public class LockoffActivity extends Activity {

	ComponentName adminComponentName;
	AppAdminReceiver appAdminReceiver;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		adminComponentName = new ComponentName(this, AppAdminReceiver.class);
		appAdminReceiver = new AppAdminReceiver();

		setContentView(R.layout.main);
		ToggleButton onOff = (ToggleButton) findViewById(R.id.onOff);

		onOff.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean state) {
				if (state) {
					enableAdmin();
				} else {
					disableAdmin();
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
				startActivityForResult(intent, Const.REQUEST_CHANGE_PASSWORD);
			}
		});
		((CheckBox) findViewById(R.id.immediateLockSwitch)).setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View arg0) {
				PrefMgmt.setImmediateLock(LockoffActivity.this, ((CheckBox) arg0).isChecked());
			}
		});
		((Button) findViewById(R.id.immediateLockHelp)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				showDialog(Const.DIALOG_IMMEDIATE_HELP);
				
//				PopupWindow pop=new PopupWindow(findViewById(R.layout.immediatelockhelp));
				//pop.showAtLocation(findViewById(R.layout.main), Gravity.CENTER, 0, 0);
			}
		});
		updateButtonStatus();		
	}
	
	protected Dialog onCreateDialog(int id)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(R.string.immediateLockLongHelp);
		return builder.create();
	}
	private void disableAdmin() {
		DevicePolicyManager mDPM = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
		mDPM.removeActiveAdmin(adminComponentName);
	}

	private void enableAdmin() {
		Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
		intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,
				adminComponentName);
		Log.i(Const.TAG, "Component '" + adminComponentName.toString() + "'");
		intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
				"To be able to clear and set password when Metawatch connection changed");
		startActivityForResult(intent, Const.REQUEST_ADMIN_ENABLE);

	}

	private void updateButtonStatus() {
		ToggleButton onOff = (ToggleButton) findViewById(R.id.onOff);
		onOff.setEnabled(isPasswordSet());
		DevicePolicyManager mDPM = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
		onOff.setChecked(mDPM.isAdminActive(adminComponentName));
		CheckBox immediateLock=(CheckBox)findViewById(R.id.immediateLockSwitch);
		immediateLock.setChecked(PrefMgmt.isImmediateLock(this));
	}

	private boolean isPasswordSet() {
		return !PrefMgmt.getPassword(this).equals("");
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		switch (requestCode) {
		case Const.REQUEST_ADMIN_ENABLE: {
			if (resultCode == Activity.RESULT_OK) {
				Log.i(Const.TAG, "Admin enabled!");
			} else {
				Log.i(Const.TAG, "Admin enable FAILED!");
			}
			updateButtonStatus();
			return;
		}
		case Const.REQUEST_CHANGE_PASSWORD: {
			if (!isPasswordSet())
				disableAdmin();
			updateButtonStatus();
			return;
		}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}