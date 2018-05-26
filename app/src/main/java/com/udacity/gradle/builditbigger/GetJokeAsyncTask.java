package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;
import java.io.IOException;

public class GetJokeAsyncTask extends AsyncTask<Void, Void, String> {

    /**
     * If an issue raised we will put this value to the {@link JokeReceivedListener#mJokeListener}
     * instead of the "real" joke...
     */
    public static final String ERROR = "ERROR!";

    public interface JokeReceivedListener {

        void onJokeReceived(@NonNull final String joke);

    }

    private static MyApi myApiService = null;

    @NonNull
    private final JokeReceivedListener mJokeListener;

    GetJokeAsyncTask(@NonNull final JokeReceivedListener jokeListener) {
        mJokeListener = jokeListener;
    }

    @Override
    protected String doInBackground(Void... params) {
        if (myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            myApiService = builder.build();
        }

        try {
            return myApiService.getJoke().execute().getData();
        } catch (IOException e) {
            Log.e(GetJokeAsyncTask.class.getName(), "Error while download jokes", e);
            return ERROR;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        mJokeListener.onJokeReceived(result);
    }
}

