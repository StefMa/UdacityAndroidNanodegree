package guru.stefma.choicm.presentation.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;

import java.util.Objects;

public class NotificationChannels {

    /**
     * If API is equal or higher than {@link Build.VERSION_CODES#O} it will
     * create a new {@link NotificationChannel} for all
     * foreground services ({@link android.support.v4.content.ContextCompat#startForegroundService(Context, Intent)}).
     */
    public static void createChannelForForegroundService(@NonNull final Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // TODO: Make this nicer
            NotificationChannel channel = new NotificationChannel("id", "name", NotificationManager.IMPORTANCE_LOW);
            channel.setDescription("desc");

            final NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            Objects.requireNonNull(notificationManager).createNotificationChannel(channel);
        }
    }
}
