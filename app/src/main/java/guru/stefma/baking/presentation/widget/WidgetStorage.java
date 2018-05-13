package guru.stefma.baking.presentation.widget;

import android.content.Context;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

public class WidgetStorage {

    private static final String KEY_WIDGET_TEXT_PREFIX = "widget_text_prefix";

    static void saveTextWithWidgetId(@NonNull final Context context, final int widgetId, final String text) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(KEY_WIDGET_TEXT_PREFIX + widgetId, text)
                .apply();
    }

    static String getTextForWidgetId(@NonNull final Context context, final int appWidgetId, final String fallback) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(KEY_WIDGET_TEXT_PREFIX + appWidgetId, fallback);
    }

    static void deleteTextForWidgetId(@NonNull final Context context, final int appWidgetId) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .remove(KEY_WIDGET_TEXT_PREFIX + appWidgetId)
                .apply();

    }
}
