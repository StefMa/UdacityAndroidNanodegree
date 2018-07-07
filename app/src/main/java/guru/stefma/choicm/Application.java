package guru.stefma.choicm;

import guru.stefma.choicm.domain.di.DependencyGraph;
import guru.stefma.choicm.presentation.notification.NotificationChannels;

public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();

        DependencyGraph.init(this);
        setupNotificationChannels();
    }

    private void setupNotificationChannels() {
        NotificationChannels.createChannelForForegroundService(this);
    }
}
