package guru.stefma.choicm.presentation.notification;

import android.app.Notification;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;

public class Notifications {

    public static Notification createNotificationForForegroundService(@NonNull final Context context) {
        // TODO: Grab channel ID by NotificationChannels somehow
        // Make this nicer. Add a nice icon to it...
        return new NotificationCompat.Builder(context, "id").build();
    }
}
