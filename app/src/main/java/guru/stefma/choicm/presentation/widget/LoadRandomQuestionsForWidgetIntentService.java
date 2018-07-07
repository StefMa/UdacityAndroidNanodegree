package guru.stefma.choicm.presentation.widget;

import android.app.IntentService;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import guru.stefma.choicm.database.Database;
import guru.stefma.choicm.database.model.AnswerResponse;
import guru.stefma.choicm.database.model.AnswersResponse;
import guru.stefma.choicm.domain.di.DependencyGraph;
import guru.stefma.choicm.presentation.notification.Notifications;
import io.reactivex.Observable;
import timber.log.Timber;
import toothpick.Toothpick;

import javax.inject.Inject;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;

/**
 * A service which will get so many "random" questions
 * depending on the amount (of widgetIds) you put into the {@link #getInstance(Context, int[])}.
 *
 * After they got the questions (and answers) it will call
 * {@link RandomQuestionWidget#updateAllAppWidgets(Context, int[], List)}
 * to update all the widgets...
 */
public class LoadRandomQuestionsForWidgetIntentService extends IntentService {

    private static final String BUNDLE_KEY_QUESTION_AMOUNT = "QUESTION_AMOUNT";

    /**
     * The {@link Notification} id for the foreground "task".
     *
     * Its not allowed to be null (zero)!
     */
    private static final int NOTIFICATION_ID = 223;

    /**
     * Create a instance for this {@link IntentService}.
     *
     * @param widgetIds the widget ids which should be updated
     */
    public static Intent getInstance(@NonNull final Context context, int[] widgetIds) {
        final Intent intent = new Intent(context, LoadRandomQuestionsForWidgetIntentService.class);
        intent.putExtra(BUNDLE_KEY_QUESTION_AMOUNT, widgetIds);
        return intent;
    }

    @Inject
    Database mDatabase;

    public LoadRandomQuestionsForWidgetIntentService() {
        super(LoadRandomQuestionsForWidgetIntentService.class.getName());
        Toothpick.inject(this, DependencyGraph.getApplicationScope());
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForeground(NOTIFICATION_ID, Notifications.createNotificationForForegroundService(this));
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(@Nullable final Intent intent) {
        if (intent == null) {
            return;
        }
        final int[] widgetIds = intent.getIntArrayExtra(BUNDLE_KEY_QUESTION_AMOUNT);
        if (widgetIds.length == 0) {
            return;
        }

        try {
            final List<SimpleEntry<String, List<String>>> questionWithAnswers = Observable.range(0, widgetIds.length)
                    .concatMapSingle(ignore -> mDatabase.getRandomQuestion()
                            .flatMap(questionResponse -> mDatabase.getAnswersForQuestionKey(questionResponse.getKey())
                                    .map(AnswersResponse::getAnswers)
                                    .map(answerResponses -> {
                                        final List<String> answers = new ArrayList<>(answerResponses.size());
                                        for (final AnswerResponse answerRespons : answerResponses) {
                                            answers.add(answerRespons.getTitle());
                                        }
                                        return new SimpleEntry<>(questionResponse.getTitle(), answers);
                                    })))
                    .toList()
                    // BlockingGet is safe here since it is an IntentService...
                    .blockingGet();

            Timber.d("Got the following questions with answer for the widget: %s",
                    questionWithAnswers.toString());
            RandomQuestionWidget.updateAllAppWidgets(getApplicationContext(), widgetIds, questionWithAnswers);
        } catch (RuntimeException exe) {
            Timber.e(exe, "Error while getting a random question");
            // Do nothing. Just don't update the widget...
        }
    }

}
