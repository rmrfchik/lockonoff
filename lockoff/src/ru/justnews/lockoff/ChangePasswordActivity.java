package ru.justnews.lockoff;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangePasswordActivity extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.password);

		Button setPassword = (Button) findViewById(R.id.setPassword);

		setPassword.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				EditText password = (EditText) findViewById(R.id.password);
				EditText confirm = (EditText) findViewById(R.id.repeatPassword);

				if (!password.getText().toString()
						.equals(confirm.getText().toString())) {
					Toast.makeText(ChangePasswordActivity.this,
							"Passwords Mismatch", Toast.LENGTH_SHORT);
				} else {
					SharedPreferences prefs = getSharedPreferences(
							Const.PREFERENCES, MODE_PRIVATE);
					prefs.edit()
							.putString(Const.PREFERENCES_PASSWORD,
									password.getText().toString()).commit();
					Intent intent = new Intent(ChangePasswordActivity.this,
							LockoffActivity.class);
					startActivity(intent);
				}
			}
		});
	}
}
