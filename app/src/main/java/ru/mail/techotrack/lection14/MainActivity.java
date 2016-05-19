package ru.mail.techotrack.lection14;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ActionMode;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

	private static List<String> messages = new ArrayList<>();
	private static int count = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button button1 = (Button)findViewById(R.id.send_notification_1);
		assert button1 != null;
		Button button2 = (Button)findViewById(R.id.send_notification_2);
		assert button2 != null;
		Button button3 = (Button)findViewById(R.id.send_notification_3);
		assert button3 != null;
		Button button4 = (Button)findViewById(R.id.send_notification_4);
		assert button4 != null;
		button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				createSimpleNotification(MainActivity.this);
			}
		});

		button2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				createGroupNotification(MainActivity.this);
			}
		});
		button3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				createSimpleNotification(MainActivity.this);
			}
		});
		button4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				createSimpleNotification(MainActivity.this);
			}
		});
	}


	@Override
	protected void onStart() {
		super.onStart();
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			if (bundle.getBoolean("notification")) {
				messages.clear();
				Toast toast = Toast.makeText(getApplicationContext(),
						"Нажали на пуш!", Toast.LENGTH_SHORT);
				toast.show();
			}
			if (bundle.getBoolean("todo")) {
				Toast toast = Toast.makeText(getApplicationContext(),
						"Нажали на действие в пуше!", Toast.LENGTH_SHORT);
				toast.show();

			}
		}
	}

	void createSimpleNotification(Context context) {
		Intent notificationIntent = new Intent(context, MainActivity.class);
		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		notificationIntent.putExtra("notification", true);
		PendingIntent contentIntent = PendingIntent.getActivity(context,
				0, notificationIntent,
				PendingIntent.FLAG_CANCEL_CURRENT);

		String msg = "" + count + " - Пока уже таки покормить рыбок, они почти сдохли, это специально длинный текст такой чтобы не влезло";
		count++;


		Notification.Builder builder = new Notification.Builder(context);
		builder.setContentIntent(contentIntent)
				.setSmallIcon(R.drawable.technotrack_24)
				.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.technotrack_128))
				.setTicker("Last china warning")
				.setWhen(System.currentTimeMillis())
				.setShowWhen(true)
				.setAutoCancel(true)
				.setContentTitle("Напоминание")
				//.setStyle(new Notification.BigTextStyle().bigText(msg));
				.setContentText(msg);


		int defaults = 0;
		defaults |= Notification.DEFAULT_VIBRATE;

		String sound = null;
		if (sound != null) {
			if (sound.equals("default")) {
				defaults |= Notification.DEFAULT_SOUND;
			} else {
				Uri soundUri = Uri.parse("android.resource://" + context.getPackageName() + "/raw/" + sound);
				builder.setSound(soundUri);
			}
		} else {
			defaults |= Notification.DEFAULT_SOUND;
		}
		builder.setDefaults(defaults);

		Notification nc = builder.build();

		NotificationManager nm = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
		nm.notify(10, nc);
	}

	void createGroupNotification(Context context) {
		Intent notificationIntent = new Intent(context, MainActivity.class);
		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent contentIntent = PendingIntent.getActivity(context,
				2, notificationIntent,
				PendingIntent.FLAG_CANCEL_CURRENT);

		String msg = "" + count + " - Пока уже таки покормить рыбок, они почти сдохли, это специально длинный текст такой чтобы не влезло";
		count++;
		messages.add(msg);

		Notification.Builder builder = new Notification.Builder(context);
		builder.setContentIntent(contentIntent)
				.setSmallIcon(R.drawable.technotrack_24)
				.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.technotrack_128))
				.setTicker("Last china warning")
				.setWhen(System.currentTimeMillis())
				.setShowWhen(true)
				.setAutoCancel(true)
				.setContentTitle("Напоминание");
				//.setStyle(new Notification.BigTextStyle().bigText(msg));
				//.setContentText(msg);


		Notification.InboxStyle inbox = new Notification.InboxStyle(builder);
		inbox.addLine(msg);

		int count = 0;
		int more = 0;
		final int groupId = 12;
		for (String m : messages) {
			if (count <= 5) {
				inbox.addLine(m);
			} else {
				++more;
			}
			++count;
		}

		if (more > 0)
			inbox.setSummaryText("+" + more + " more");
		Notification nc = inbox.build();

		NotificationManager nm = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
		nm.notify(13, nc);
	}


	@TargetApi(Build.VERSION_CODES.KITKAT_WATCH)
	void createActionNotification(Context context) {
		Intent notificationIntent = new Intent(context, MainActivity.class);
		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		notificationIntent.putExtra("notification", true);
		PendingIntent contentIntent = PendingIntent.getActivity(context,
				3, notificationIntent,
				PendingIntent.FLAG_UPDATE_CURRENT);

		Intent notificationTodo = new Intent(context, MainActivity.class);
		notificationTodo.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		notificationIntent.putExtra("todo", true);
		PendingIntent piTodo = PendingIntent.getActivity(context,
				1, notificationIntent,
				PendingIntent.FLAG_UPDATE_CURRENT);

		String msg = "" + count + " - Надо что-то сделать";
		count++;

		Notification.Builder builder = new Notification.Builder(context);
		builder.setContentIntent(contentIntent)
				.setSmallIcon(R.drawable.technotrack_24)
				.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.technotrack_128))
				.setTicker("Last china warning")
				.setWhen(System.currentTimeMillis())
				.addAction(new Notification.Action.Builder(R.drawable.social_add_person, "Previous", piTodo).build()) // #0)
				.setShowWhen(true)
				.setAutoCancel(true)
				.setContentTitle("To do")
				//.setStyle(new Notification.BigTextStyle().bigText(msg));
				.setContentText(msg);


		int defaults = 0;
		defaults |= Notification.DEFAULT_VIBRATE;

		String sound = null;
		if (sound != null) {
			if (sound.equals("default")) {
				defaults |= Notification.DEFAULT_SOUND;
			} else {
				Uri soundUri = Uri.parse("android.resource://" + context.getPackageName() + "/raw/" + sound);
				builder.setSound(soundUri);
			}
		} else {
			defaults |= Notification.DEFAULT_SOUND;
		}
		builder.setDefaults(defaults);

		Notification nc = builder.build();

		NotificationManager nm = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
		nm.notify(10, nc);
	}



}
