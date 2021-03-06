package ru.mail.techotrack.lection14;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ActionMode;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
	private static final String TAG = "MainActivity";

	public static List<String> messages = new ArrayList<>();
	private static int count = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button button1 = (Button)findViewById(R.id.send_notification_1);
		Button button2 = (Button)findViewById(R.id.send_notification_2);
		Button button3 = (Button)findViewById(R.id.send_notification_3);
		Button button4 = (Button)findViewById(R.id.send_notification_4);
		Button button5 = (Button)findViewById(R.id.send_notification_5);

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
				createActionNotification(MainActivity.this);
			}
		});
		button4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				createProgressNotification(MainActivity.this);
			}
		});
		button5.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				createChannelNotification(MainActivity.this);
			}
		});
	}

	void createSimpleNotification(Context context) {
		Intent notificationIntent = new Intent(context, HelperActivity.class);
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

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			builder.setChannelId(getChannelId("my_channel_id", "My default Channel", "my_group_id", "My default Group"));
		}

		int defaults = 0;
		defaults |= Notification.DEFAULT_VIBRATE;
		defaults |= Notification.DEFAULT_SOUND;

		builder.setDefaults(defaults);

		Notification nc = builder.build();

		NotificationManager nm = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
		if (nm != null) {
			nm.notify(10, nc);
		}
	}

	void createGroupNotification(Context context) {
		Intent notificationIntent = new Intent(context, HelperActivity.class);
		notificationIntent.putExtra("notification", true);
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
				.setUsesChronometer(true)
				.setContentTitle("Напоминание");
				//.setStyle(new Notification.BigTextStyle().bigText(msg));
				//.setContentText(msg);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			builder.setChannelId(getChannelId("my_channel_id", "My default Channel", "my_group_id", "My default Group"));
		}

		Notification.InboxStyle inbox = new Notification.InboxStyle(builder);
		//inbox.addLine(msg);

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
		if (nm != null) {
			nm.notify(13, nc);
		}
	}


	@TargetApi(Build.VERSION_CODES.KITKAT_WATCH)
	void createActionNotification(Context context) {
		Intent notificationIntent = new Intent(context, HelperActivity.class);
		notificationIntent.putExtra("notification", true);
		PendingIntent contentIntent = PendingIntent.getActivity(context,
				3, notificationIntent,
				PendingIntent.FLAG_UPDATE_CURRENT);

		Intent notificationTodo = new Intent(context, HelperActivity.class);
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

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			builder.setChannelId(getChannelId("my_channel_id", "My default Channel", "my_group_id", "My default Group"));
		}

		int defaults = 0;
		defaults |= Notification.DEFAULT_VIBRATE;
		defaults |= Notification.DEFAULT_SOUND;
		builder.setDefaults(defaults);

		Notification nc = builder.build();

		NotificationManager nm = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
		if (nm != null) {
			nm.notify(10, nc);
		}
	}

	void createChannelNotification(Context context) {
		Intent notificationIntent = new Intent(context, HelperActivity.class);
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
				.setContentText(msg);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			builder.setChannelId(getChannelId("my_second_channel_id", "My second Channel", "my_second_group_id", "My second Group"));
		}
		int defaults = 0;
		defaults |= Notification.DEFAULT_VIBRATE;
		defaults |= Notification.DEFAULT_SOUND;

		builder.setDefaults(defaults);

		Notification nc = builder.build();

		NotificationManager nm = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
		if (nm != null) {
			nm.notify(30, nc);
		}
	}

	@TargetApi(Build.VERSION_CODES.O)
	String getChannelId(String channelId, String name, String groupId, String groupName) {
		NotificationManager nm =
				(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		if (nm != null) {
			List<NotificationChannel> channels = nm.getNotificationChannels();
			for(NotificationChannel channel : channels) {
				if (channel.getId().equals(channelId)) {
					return channel.getId();
				}
			}

			String group = getNotificationChannelGroupId(groupId, groupName);
			int importance = NotificationManager.IMPORTANCE_LOW;
			NotificationChannel notificationChannel = new NotificationChannel(channelId, name, importance);
			notificationChannel.enableLights(true);
			notificationChannel.setLightColor(Color.RED);
			notificationChannel.enableVibration(true);

			notificationChannel.setGroup(group); // set custom group

			notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
			nm.createNotificationChannel(notificationChannel);

			return channelId;
		}
		return null;
	}

	@TargetApi(Build.VERSION_CODES.O)
	String getNotificationChannelGroupId(String groupId, String name) {
		NotificationManager nm =
				(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		if (nm != null) {
			List<NotificationChannelGroup> groups = nm.getNotificationChannelGroups();
			for(NotificationChannelGroup group : groups) {
				if (group.getId().equals(groupId)) {
					return group.getId();
				}
			}
			nm.createNotificationChannelGroup(new NotificationChannelGroup(groupId, name));
			return groupId;
		}
		return null;
	}

	void createProgressNotification(Context context) {
		String msg = "" + count + " - Пока уже таки покормить рыбок, они почти сдохли, это специально длинный текст такой чтобы не влезло";
		count++;

		Intent notificationIntent = new Intent(context, HelperActivity.class);
		notificationIntent.putExtra("notification", true);
		PendingIntent contentIntent = PendingIntent.getActivity(context,
				10, notificationIntent,
				PendingIntent.FLAG_CANCEL_CURRENT);
		final NotificationManager nm = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
		final Notification.Builder builder = new Notification.Builder(context);
		builder.setContentIntent(contentIntent)
				.setSmallIcon(R.drawable.technotrack_24)
				.setContentTitle("To do")
				//.setStyle(new Notification.BigTextStyle().bigText(msg));
				.setContentText(msg);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			builder.setChannelId(getChannelId("my_channel_id", "My default Channel", "my_group_id", "My default Group"));
		}

// Start a lengthy operation in a background thread
		new Thread(
				new Runnable() {
					@Override
					public void run() {
						int incr;
						// Do the "lengthy" operation 20 times
						for (incr = 0; incr <= 100; incr+=5) {
							// Sets the progress indicator to a max value, the
							// current completion percentage, and "determinate"
							// state
							builder.setProgress(100, incr, false);
							// Displays the progress bar for the first time.
							nm.notify(0, builder.build());
							// Sleeps the thread, simulating an operation
							// that takes time
							try {
								// Sleep for 5 seconds
								Thread.sleep(5*1000);
							} catch (InterruptedException e) {
								Log.d(TAG, "sleep failure");
							}
						}
						// When the loop is finished, updates the notification
						builder.setContentText("Download complete")
								// Removes the progress bar
								.setProgress(0,0,false);
						nm.notify(123, builder.build());
					}
				}
// Starts the thread by calling the run() method in its Runnable
		).start();

	}



}
