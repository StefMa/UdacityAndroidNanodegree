package com.udacity.gradle.builditbigger;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.*;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import guru.stefma.jokeindicator.JokeIndicatorActivity;
import org.junit.*;
import org.junit.runner.*;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public IntentsTestRule<MainActivity> mRule = new IntentsTestRule<>(MainActivity.class);

    @Test
    public void testTellJoke_AfterReceived_StartJokeIndicatorActivityWithKeyAndString() {
        final View fakeView = new View(InstrumentationRegistry.getTargetContext());

        mRule.getActivity().tellJoke(fakeView);

        intended(allOf(
                hasComponent(JokeIndicatorActivity.class.getName()),
                hasExtraWithKey("JOKE")
        ));
    }

    @Test
    public void testTellJoke_AfterReceived_DisplayNotEmptyJoke() {
        final View fakeView = new View(InstrumentationRegistry.getTargetContext());

        mRule.getActivity().tellJoke(fakeView);

        onView(withId(R.id.activity_joke_indicator_joke)).check(matches(isDisplayed()));
        onView(withId(R.id.activity_joke_indicator_joke)).check(matches(not(withText(""))));
    }
}