package guru.stefma.choicm.presentation.question.add;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import guru.stefma.choicm.R;
import guru.stefma.choicm.domain.di.DependencyGraph;
import guru.stefma.choicm.domain.usecase.AddQuestionUseCase;
import guru.stefma.choicm.presentation.base.loading.Loading;
import guru.stefma.choicm.presentation.question.add.AddAnswerView.State;
import guru.stefma.choicm.presentation.question.show.ShowQuestionActivity;
import java.util.ArrayList;
import java.util.List;
import net.grandcentrix.thirtyinch.TiActivity;
import toothpick.Toothpick;

public class AddQuestionActivity
        extends TiActivity<AddQuestionPresenter, AddQuestionView>
        implements AddQuestionView {

    private static final String BUNDLE_KEY_ANSWERS = "answers";

    private static final String BUNDLE_KEY_STATES = "states";

    public static Intent newInstance(@NonNull final Context context) {
        return new Intent(context, AddQuestionActivity.class);
    }

    private final Loading mLoading = new Loading();

    private final AddAnswerAdapter mAddAnswerAdapter = new AddAnswerAdapter();

    @NonNull
    @Override
    public AddQuestionPresenter providePresenter() {
        final AddQuestionUseCase addQuestionUseCase = new AddQuestionUseCase(null, null);
        Toothpick.inject(addQuestionUseCase, DependencyGraph.getApplicationScope());
        return new AddQuestionPresenter(addQuestionUseCase);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);

        final Toolbar toolbar = findViewById(R.id.activity_add_question_toolbar);
        final RecyclerView recyclerView = findViewById(R.id.activity_add_question_answer_list);
        final EditText questionView = findViewById(R.id.activity_add_question_question);
        final FloatingActionButton fab = findViewById(R.id.activity_add_question_add);

        setupRecyclerView(recyclerView);
        setupToolbar(toolbar);
        setupFab(fab, questionView);

        restoreAnswerStates(savedInstanceState);
    }

    private void setupRecyclerView(@NonNull final RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAddAnswerAdapter);
    }

    private void setupToolbar(@NonNull final Toolbar toolbar) {
        toolbar.setNavigationOnClickListener((ignore) -> finish());
    }

    private void setupFab(final FloatingActionButton fab, final EditText questionView) {
        fab.setOnClickListener((view) -> {
            final List<AddQuestionAnswerViewModel> viewModels = mAddAnswerAdapter.getViewModels();
            final List<String> answers = new ArrayList<>();
            for (final AddQuestionAnswerViewModel viewModel : viewModels) {
                answers.add(viewModel.getAnswer());
            }
            getPresenter().onAddQuestionClicked(questionView.getText().toString(), answers);
        });
    }

    /**
     * Restores on orientation change the answers list.
     *
     * @see #onSaveInstanceState(Bundle)
     */
    private void restoreAnswerStates(@Nullable final Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            final List<AddQuestionAnswerViewModel> viewModels = new ArrayList<>();
            final ArrayList<String> answers = savedInstanceState.getStringArrayList(BUNDLE_KEY_ANSWERS);
            final ArrayList<String> stateStrings = savedInstanceState.getStringArrayList(BUNDLE_KEY_STATES);
            for (int i = 0; i < answers.size(); i++) {
                viewModels.add(new AddQuestionAnswerViewModel(answers.get(i), State.valueOf(stateStrings.get(i))));
            }
            mAddAnswerAdapter.setViewModels(viewModels);
        }
    }

    /**
     * Saves the answers list on orientation change
     *
     * @see #restoreAnswerStates(Bundle)
     */
    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        final List<AddQuestionAnswerViewModel> viewModels = mAddAnswerAdapter.getViewModels();
        final ArrayList<String> answers = new ArrayList<>();
        final ArrayList<String> stateStrings = new ArrayList<>();
        for (final AddQuestionAnswerViewModel viewModel : viewModels) {
            answers.add(viewModel.getAnswer());
            stateStrings.add(viewModel.getState().name());
        }
        outState.putStringArrayList(BUNDLE_KEY_ANSWERS, answers);
        outState.putStringArrayList(BUNDLE_KEY_STATES, stateStrings);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLoading.destroy();
    }

    @Override
    public void showLoading() {
        mLoading.show(getSupportFragmentManager());
    }

    @Override
    public void hideLoading() {
        mLoading.hide();
    }

    @Override
    public void showQuestion(@NonNull final String questionKey) {
        startActivity(ShowQuestionActivity.newInstance(this, questionKey));
        finish();
    }

}
