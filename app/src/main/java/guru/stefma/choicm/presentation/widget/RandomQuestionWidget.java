package guru.stefma.choicm.presentation.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.widget.RemoteViews;
import guru.stefma.choicm.R;
import guru.stefma.choicm.presentation.main.MainActivity;

import java.util.AbstractMap.SimpleEntry;
import java.util.List;

/**
 * An App Widget which will be refreshed each 24 to show a different question.
 */
public class RandomQuestionWidget extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        ContextCompat.startForegroundService(context, LoadRandomQuestionsForWidgetIntentService.getInstance(context, appWidgetIds));
    }

    public static void updateAllAppWidgets(
            @NonNull final Context context,
            final int[] widgetIds,
            @NonNull final List<SimpleEntry<String, List<String>>> questionWithAnswers) {
        final AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        for (int i = 0; i < widgetIds.length; i++) {
            final String question = questionWithAnswers.get(i).getKey();
            final List<String> answers = questionWithAnswers.get(i).getValue();
            updateAppWidget(context, appWidgetManager, widgetIds[i], question, answers);
        }
    }

    private static void updateAppWidget(
            @NonNull final Context context,
            @NonNull final AppWidgetManager widgetManager,
            int appWidgetId,
            @NonNull final String question,
            @NonNull final List<String> answers) {
        final CharSequence answerOne = answers.get(0);
        CharSequence answerTwo = "";
        if (answers.size() >= 2) {
            answerTwo = answers.get(1);
        }

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.random_question_widget);
        views.setTextViewText(R.id.widget_random_question_question, question);
        views.setTextViewText(R.id.widget_random_question_answer_one, answerOne);
        views.setTextViewText(R.id.widget_random_question_answer_two, answerTwo);

        final Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        final PendingIntent pendingIntent = PendingIntent
                .getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.widget_random_question_view, pendingIntent);

        // Instruct the widget manager to update the widget
        widgetManager.updateAppWidget(appWidgetId, views);
    }
}

