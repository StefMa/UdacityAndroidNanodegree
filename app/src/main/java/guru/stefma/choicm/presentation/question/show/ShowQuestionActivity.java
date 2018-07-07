package guru.stefma.choicm.presentation.question.show;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import guru.stefma.choicm.R;
import guru.stefma.choicm.domain.di.DependencyGraph;
import guru.stefma.choicm.domain.usecase.ToggleAnswerForAnswerKeyAtPositionUseCase;
import guru.stefma.choicm.domain.usecase.GetAnswersForQuestionKeyUseCase;
import guru.stefma.choicm.domain.usecase.GetQuestionForQuestionKeyUseCase;
import guru.stefma.choicm.domain.usecase.account.GetAccountUseCase;
import guru.stefma.choicm.presentation.base.loading.Loading;
import guru.stefma.choicm.presentation.question.show.ShowAnswerAdapter.OnAnswerCheckedChanged;
import net.grandcentrix.thirtyinch.TiActivity;
import toothpick.Scope;
import toothpick.Toothpick;

public class ShowQuestionActivity
        extends TiActivity<ShowQuestionPresenter, ShowQuestionView>
        implements ShowQuestionView, OnAnswerCheckedChanged {

    private static final String BUNDLE_KEY_QUESTION_KEY = "BUNDLE_KEY_QUESTION_KEY";

    public static Intent newInstance(
            @NonNull final Context context,
            @NonNull final String questionKey) {
        final Intent intent = new Intent(context, ShowQuestionActivity.class);
        intent.putExtra(BUNDLE_KEY_QUESTION_KEY, questionKey);
        return intent;
    }

    @NonNull
    private final Loading mLoading = new Loading();

    @NonNull
    private final ShowAnswerAdapter mShowAnswerAdapter = new ShowAnswerAdapter(this);

    private TextView mQuestionView;

    @NonNull
    @Override
    public ShowQuestionPresenter providePresenter() {
        final String questionKey = getIntent().getStringExtra(BUNDLE_KEY_QUESTION_KEY);
        final GetQuestionForQuestionKeyUseCase getQuestionForQuestionKeyUseCase =
                new GetQuestionForQuestionKeyUseCase(null, null);
        final GetAnswersForQuestionKeyUseCase getAnswersForQuestionKeyUseCase =
                new GetAnswersForQuestionKeyUseCase(null, null);
        final GetAccountUseCase accountUseCase = new GetAccountUseCase(null, null);
        final ToggleAnswerForAnswerKeyAtPositionUseCase toggleAnswerUseCase =
                new ToggleAnswerForAnswerKeyAtPositionUseCase(null, null);
        final Scope scope = DependencyGraph.getApplicationScope();
        Toothpick.inject(getQuestionForQuestionKeyUseCase, scope);
        Toothpick.inject(getAnswersForQuestionKeyUseCase, scope);
        Toothpick.inject(accountUseCase, scope);
        Toothpick.inject(toggleAnswerUseCase, scope);
        return new ShowQuestionPresenter(
                questionKey,
                getQuestionForQuestionKeyUseCase,
                getAnswersForQuestionKeyUseCase,
                accountUseCase,
                toggleAnswerUseCase);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_question);

        final Toolbar toolbar = findViewById(R.id.activity_show_question_toolbar);
        final RecyclerView recyclerView = findViewById(R.id.activity_show_question_answer_list);
        mQuestionView = findViewById(R.id.activity_show_question_question);

        setupToolbar(toolbar);
        setupRecyclerView(recyclerView);
    }

    private void setupToolbar(@NonNull final Toolbar toolbar) {
        toolbar.setNavigationOnClickListener(view -> finish());
    }

    private void setupRecyclerView(@NonNull final RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mShowAnswerAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLoading.destroy();
    }

    @Override
    public void showQuestion(@NonNull final ShowQuestionViewModel questionViewModel) {
        mQuestionView.setText(questionViewModel.getQuestionTitle());
        mShowAnswerAdapter.setViewModels(questionViewModel.getAnswerViewModels());
        mShowAnswerAdapter.setReadOnly(questionViewModel.isReadOnly());
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
    public void onAnswerChanged(final int position, final boolean checked) {
        getPresenter().onAnswerChanged(position, checked);
    }
}
