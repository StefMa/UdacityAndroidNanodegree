package guru.stefma.choicm.presentation.account;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.widget.TextView;
import guru.stefma.choicm.R;
import guru.stefma.choicm.domain.di.DependencyGraph;
import guru.stefma.choicm.domain.usecase.account.GetAccountUseCase;
import guru.stefma.choicm.domain.usecase.account.SignOutUseCase;
import net.grandcentrix.thirtyinch.TiActivity;
import toothpick.Toothpick;

public class AccountActivity extends TiActivity<AccountPresenter, AccountView> implements AccountView {

    public static Intent newInstance(@NonNull final Context context) {
        return new Intent(context, AccountActivity.class);
    }

    private TextView mUsernameView;

    @NonNull
    @Override
    public AccountPresenter providePresenter() {
        final GetAccountUseCase accountUseCase = new GetAccountUseCase(null, null);
        final SignOutUseCase signOutUseCase = new SignOutUseCase(null, null);
        Toothpick.inject(accountUseCase, DependencyGraph.getApplicationScope());
        Toothpick.inject(signOutUseCase, DependencyGraph.getApplicationScope());
        return new AccountPresenter(accountUseCase, signOutUseCase);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        mUsernameView = findViewById(R.id.activity_account_username);
        findViewById(R.id.activity_account_logout).setOnClickListener((view) -> {
            getPresenter().onLogoutClicked();
        });
    }

    @Override
    public void showUsername(@NonNull final String username) {
        mUsernameView.setText(getString(R.string.activity_account_welcome_user, username));
    }

    @Override
    public void showGetAccountError() {
        mUsernameView.setText(null);
        Snackbar.make(mUsernameView, getString(R.string.activity_account_get_error), Snackbar.LENGTH_INDEFINITE)
                .setAction(android.R.string.ok, null)
                .show();
    }

    @Override
    public void finishViewSuccessfully() {
        setResult(Activity.RESULT_OK);
        finish();
    }

    @Override
    public void showLogoutError() {
        Snackbar.make(mUsernameView, getString(R.string.activity_account_signout_error), Snackbar.LENGTH_INDEFINITE)
                .show();
    }
}
