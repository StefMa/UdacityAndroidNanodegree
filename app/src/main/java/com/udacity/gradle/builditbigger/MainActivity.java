package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.udacity.gradle.builditbigger.GetJokeAsyncTask.JokeReceivedListener;
import guru.stefma.jokeindicator.JokeIndicatorActivity;


public class MainActivity extends AppCompatActivity implements JokeReceivedListener {

    public static final String BUNDLE_KEY_IS_TASK_RUNNING = "IS_TASK_RUNNING";

    @Nullable
    private AsyncTask<Void, Void, String> mTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null && savedInstanceState.containsKey(BUNDLE_KEY_IS_TASK_RUNNING)) {
            final boolean isTaskRunning = savedInstanceState.getBoolean(BUNDLE_KEY_IS_TASK_RUNNING);
            // If the task was running but not finished - due an orientation change - start it again
            if (isTaskRunning) {
                getJoke();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTask != null) {
            mTask.cancel(true);
        }
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        final boolean isTaskRunning = mTask != null && mTask.getStatus() != Status.FINISHED;
        outState.putBoolean(BUNDLE_KEY_IS_TASK_RUNNING, isTaskRunning);
    }

    /**
     * Listener which got called if a button in the {@link MainActivityFragment} get called.
     */
    public void tellJoke(View view) {
        getJoke();
    }

    private void getJoke() {
        mTask = new GetJokeAsyncTask(this).execute();
    }

    @Override
    public void onJokeReceived(@NonNull final String joke) {
        if (joke.equals(GetJokeAsyncTask.ERROR)) {
            Toast.makeText(this, "There was an error while downloading the Joke :/", Toast.LENGTH_LONG)
                    .show();
            return;
        }
        startActivity(JokeIndicatorActivity.newInstance(this, joke));
    }

}
