package guru.stefma.baking.presentation.overview;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.*;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import guru.stefma.baking.R;
import guru.stefma.baking.presentation.detail.RecipeDetailActivity;
import guru.stefma.baking.presentation.model.IngredientViewModel;
import guru.stefma.baking.presentation.model.RecipeViewModel;
import guru.stefma.baking.presentation.model.StepViewModel;
import java.util.ArrayList;
import java.util.List;
import org.junit.*;
import org.junit.runner.*;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public IntentsTestRule<MainActivity> mRule = new IntentsTestRule<>(MainActivity.class);

    @Test
    public void testSetDate_ShouldDisplayDataInList() {
        final List<RecipeViewModel> viewModels = createFakeData(null, null);

        final MainActivity activity = mRule.getActivity();
        activity.runOnUiThread(() -> activity.setData(viewModels));

        onView(withText("TestRecipe")).check(matches(isDisplayed()));
        onView(withText("TestRecipe2")).check(matches(isDisplayed()));
        onView(withText("TestRecipe3")).check(matches(isDisplayed()));
    }

    @Test
    public void testClickOnItem_ShouldOpenDetailActivityWithIngredientAndSteps() throws InterruptedException {
        final List<RecipeViewModel> viewModels = createFakeData(new ArrayList<>(), new ArrayList<>());

        final MainActivity activity = mRule.getActivity();
        activity.runOnUiThread(() -> activity.setData(viewModels));

        onView(withId(R.id.activity_main_recipes_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        intended(allOf(
                hasComponent(RecipeDetailActivity.class.getName()),
                hasExtra("BUNDLE_KEY_RECIPE", viewModels.get(0))
        ));
    }

    @NonNull
    private List<RecipeViewModel> createFakeData(
            @Nullable List<IngredientViewModel> ingredientViewModels,
            @Nullable List<StepViewModel> stepViewModels) {
        final ArrayList<RecipeViewModel> viewModels = new ArrayList<>();
        viewModels.add(new RecipeViewModel("TestRecipe", ingredientViewModels, stepViewModels, null));
        viewModels.add(new RecipeViewModel("TestRecipe2", ingredientViewModels, stepViewModels, null));
        viewModels.add(new RecipeViewModel("TestRecipe3", ingredientViewModels, stepViewModels, null));
        return viewModels;
    }
}