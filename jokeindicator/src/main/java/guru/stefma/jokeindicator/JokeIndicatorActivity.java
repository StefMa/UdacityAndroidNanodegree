package guru.stefma.jokeindicator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class JokeIndicatorActivity extends AppCompatActivity {

    private static final String BUNDLE_KEY_JOKE = "JOKE";

    public static Intent newInstance(@NonNull final Context context, @Nullable final String joke) {
        final Intent intent = new Intent(context, JokeIndicatorActivity.class);
        intent.putExtra(BUNDLE_KEY_JOKE, joke);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_indicator);

        final TextView jokeView = findViewById(R.id.activity_joke_indicator_joke);
        jokeView.setText(getIntent().getStringExtra(BUNDLE_KEY_JOKE));
    }
}
