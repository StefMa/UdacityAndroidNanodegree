package guru.stefma.baking.presentation.detail;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.*;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import guru.stefma.baking.R;
import guru.stefma.baking.presentation.detail.step.RecipeDetailStepActivity;
import guru.stefma.baking.presentation.model.IngredientViewModel;
import guru.stefma.baking.presentation.model.RecipeViewModel;
import guru.stefma.baking.presentation.model.StepViewModel;
import java.util.ArrayList;
import java.util.List;
import org.junit.*;
import org.junit.runner.*;

@RunWith(AndroidJUnit4.class)
public class RecipeDetailActivityTest {

    @Rule
    public IntentsTestRule<RecipeDetailActivity> mRule =
            new IntentsTestRule<>(RecipeDetailActivity.class, true, false);

    @Test
    public void testStartActivity_ShouldDisplayIngredientsAndSteps() {
        startActivity(createTestIngredients(), createTestSteps());

        // Ingredients
        onView(withId(R.id.fragment_recipe_detail_overview_ingredients)).check(matches(isDisplayed()));
        onView(withText("Egg")).check(matches(allOf(isDisplayed(), not(isClickable()))));
        onView(withText("5.0 TSP")).check(matches(allOf(isDisplayed(), not(isClickable()))));
        onView(withText("Flour")).check(matches(allOf(isDisplayed(), not(isClickable()))));
        onView(withText("1.0 G")).check(matches(allOf(isDisplayed(), not(isClickable()))));

        // Steps
        onView(withId(R.id.fragment_recipe_detail_overview_steps)).check(matches(isDisplayed()));
        onView(withText("0ShortDesc")).check(matches(allOf(isDisplayed(), isClickable())));
        onView(withText("1. 1ShortDesc")).check(matches(allOf(isDisplayed(), isClickable())));
        onView(withText("2. 2ShortDesc")).check(matches(allOf(isDisplayed(), isClickable())));
    }

    @Test
    public void testClickOnItem_OnPhones_ShouldStartActivity() {
        final List<StepViewModel> testSteps = createTestSteps();
        startActivity(createTestIngredients(), testSteps);

        onView(withId(R.id.fragment_recipe_detail_overview_steps))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        final boolean isPhone =
                InstrumentationRegistry.getTargetContext().getResources().getBoolean(R.bool.is_phone);
        if (isPhone) {
            intended(allOf(
                    hasComponent(RecipeDetailStepActivity.class.getName()),
                    hasExtraWithKey("bundle::key::step")
            ));
        }
    }

    @Test
    public void testClickOnItem_OnTablets_ShouldDisplayStepContent() {
        final boolean isTablet =
                InstrumentationRegistry.getTargetContext().getResources().getBoolean(R.bool.is_tablet);
        if (isTablet) {
            final List<StepViewModel> testSteps = createTestSteps();
            startActivity(createTestIngredients(), testSteps);
            onView(withId(R.id.fragment_recipe_step_title)).check(matches(withText("")));
            onView(withId(R.id.fragment_recipe_step_description)).check(matches(withText("")));
            onView(withId(R.id.fragment_recipe_step_video)).check(matches(isDisplayed()));

            onView(withId(R.id.fragment_recipe_detail_overview_steps))
                    .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

            onView(withId(R.id.fragment_recipe_step_title)).check(matches(withText(testSteps.get(0).getStep())));
            onView(withId(R.id.fragment_recipe_step_description))
                    .check(matches(withText(testSteps.get(0).getDescription())));
            onView(withId(R.id.fragment_recipe_step_video)).check(matches(isDisplayed()));
        }
    }

    private void startActivity(
            final List<IngredientViewModel> testIngredients,
            final List<StepViewModel> testSteps
    ) {
        final RecipeViewModel testRecipe =
                new RecipeViewModel("TestRecipe", testIngredients, testSteps, "");
        mRule.launchActivity(
                RecipeDetailActivity.newInstance(InstrumentationRegistry.getTargetContext(), testRecipe)
        );
    }

    private List<IngredientViewModel> createTestIngredients() {
        final List<IngredientViewModel> viewModels = new ArrayList<>();
        viewModels.add(new IngredientViewModel(5.0f, "TSP", "Egg"));
        viewModels.add(new IngredientViewModel(1f, "G", "Flour"));
        return viewModels;
    }

    private List<StepViewModel> createTestSteps() {
        final List<StepViewModel> viewModels = new ArrayList<>();
        viewModels.add(new StepViewModel("0", "0ShortDesc", "0Desc", "https://google.com", ""));
        viewModels.add(new StepViewModel("1", "1ShortDesc", "1Desc", "https://google.com", ""));
        viewModels.add(new StepViewModel("2", "2ShortDesc", "2Desc", "https://google.com", "https://google.com"));
        return viewModels;
    }
}