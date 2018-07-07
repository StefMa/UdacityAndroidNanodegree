package guru.stefma.choicm.presentation.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;
import guru.stefma.choicm.R;
import guru.stefma.choicm.domain.di.DependencyGraph;
import guru.stefma.choicm.domain.model.Question;
import guru.stefma.choicm.domain.usecase.GetQuestionsUseCase;
import guru.stefma.choicm.domain.usecase.account.IsLoggedInUseCase;
import guru.stefma.choicm.presentation.account.AccountActivity;
import guru.stefma.choicm.presentation.account.signin.SignInActivity;
import guru.stefma.choicm.presentation.base.loading.Loading;
import guru.stefma.choicm.presentation.common.RecyclerViewFabListener;
import guru.stefma.choicm.presentation.main.MainQuestionAdapter.OnQuestionClickedListener;
import guru.stefma.choicm.presentation.question.add.AddQuestionActivity;
import guru.stefma.choicm.presentation.question.show.ShowQuestionActivity;
import java.util.List;
import net.grandcentrix.thirtyinch.TiActivity;
import toothpick.Toothpick;

public class MainActivity
        extends TiActivity<MainPresenter, MainView>
        implements MainView, OnQuestionClickedListener {

    private final Loading mLoading = new Loading();

    private final MainQuestionAdapter mMainQuestionAdapter = new MainQuestionAdapter(this);

    private SwipeRefreshLayout mRefreshLayout;

    @NonNull
    @Override
    public MainPresenter providePresenter() {
        final GetQuestionsUseCase getQuestionsUseCase = new GetQuestionsUseCase(null, null);
        final IsLoggedInUseCase loggedInUseCase = new IsLoggedInUseCase(null, null);
        Toothpick.inject(getQuestionsUseCase, DependencyGraph.getApplicationScope());
        Toothpick.inject(loggedInUseCase, DependencyGraph.getApplicationScope());
        return new MainPresenter(getQuestionsUseCase, loggedInUseCase);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Toolbar toolbar = findViewById(R.id.activity_main_toolbar);
        mRefreshLayout = findViewById(R.id.activity_main_refresh);
        final RecyclerView recyclerView = findViewById(R.id.activity_main_list);
        final FloatingActionButton fab = findViewById(R.id.activity_main_add_question);

        setupToolbar(toolbar);
        setupRefreshLayout(mRefreshLayout);
        setupRecyclerView(recyclerView, fab);
        setupFab(fab);
    }

    private void setupToolbar(@NonNull final Toolbar toolbar) {
        toolbar.inflateMenu(R.menu.activtiy_main);
        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.activity_main_menu_account:
                    getPresenter().onAccountClicked();
                    return true;
                default:
                    return false;
            }
        });
    }

    private void setupRefreshLayout(@NonNull final SwipeRefreshLayout refreshLayout) {
        refreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent);
        refreshLayout.setOnRefreshListener(() -> getPresenter().onRefreshRequested());
    }

    private void setupRecyclerView(
            @NonNull final RecyclerView recyclerView,
            @NonNull final FloatingActionButton fab) {
        recyclerView.setAdapter(mMainQuestionAdapter);
        recyclerView.addItemDecoration(new MainQuestionItemDecoration());
        recyclerView.addOnScrollListener(new RecyclerViewFabListener(fab));
    }

    private void setupFab(@NonNull final FloatingActionButton fab) {
        fab.setOnClickListener(view -> getPresenter().onAddQuestionClicked());
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
    public void showAccount() {
        startActivity(AccountActivity.newInstance(this));
    }

    @Override
    public void showAddQuestion() {
        startActivity(AddQuestionActivity.newInstance(this));
    }

    @Override
    public void showQuestion(@NonNull final String questionKey) {
        startActivity(ShowQuestionActivity.newInstance(this, questionKey));
    }

    @Override
    public void showQuestions(@NonNull final List<Question> questions) {
        mRefreshLayout.setRefreshing(false);
        mMainQuestionAdapter.setQuestions(questions);
    }

    @Override
    public void showSignIn() {
        startActivity(SignInActivity.newInstance(this));
    }

    @Override
    public void onQuestionClicked(final int position) {
        getPresenter().onQuestionClicked(position);
    }
}
