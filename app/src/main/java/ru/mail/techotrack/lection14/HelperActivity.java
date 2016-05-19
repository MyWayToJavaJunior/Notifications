package ru.mail.techotrack.lection14;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

/**
 * Created by vlad on 19/05/16.
 */
public class HelperActivity extends Activity {
	private static final String TAG = "HelperActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = getIntent().getExtras();
		if (bundle.getBoolean("notification")) {
			MainActivity.messages.clear();
			Toast toast = Toast.makeText(getApplicationContext(),
					"Нажали на пуш!", Toast.LENGTH_SHORT);
			toast.show();
			Log.d(TAG, "onStart() called with: " + "notification");
		}
		if (bundle.getBoolean("todo")) {
			Toast toast = Toast.makeText(getApplicationContext(),
					"Нажали на действие в пуше!", Toast.LENGTH_SHORT);
			toast.show();
			Log.d(TAG, "onStart() called with: " + "notification");

		}
		finish();
	}
}
