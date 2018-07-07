package guru.stefma.choicm.presentation.account.signin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import guru.stefma.choicm.R;
import guru.stefma.choicm.domain.di.DependencyGraph;
import guru.stefma.choicm.domain.usecase.account.SignInUseCase;
import guru.stefma.choicm.presentation.account.signup.SignUpActivity;
import guru.stefma.choicm.presentation.base.loading.Loading;
import net.grandcentrix.thirtyinch.TiActivity;
import toothpick.Toothpick;

// TODO: Document the result codes... or handle it better somehow :shrug:
public class SignInActivity extends TiActivity<SignInPresenter, SignInView> implements SignInView {

    private static final String TAG_SIGNIN_ERROR_DIALOG = "TAG_SIGNIN_ERROR_DIALOG";

    public static Intent newInstance(@NonNull final Context context) {
        return new Intent(context, SignInActivity.class);
    }

    private final Loading mLoading = new Loading();

    @NonNull
    @Override
    public SignInPresenter providePresenter() {
        final SignInUseCase signInUseCase = new SignInUseCase(null, null);
        Toothpick.inject(signInUseCase, DependencyGraph.getApplicationScope());
        return new SignInPresenter(signInUseCase);
    }

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        final Toolbar toolbar = findViewById(R.id.activity_signin_toolbar);
        final EditText emailView = findViewById(R.id.activity_signin_email);
        final EditText passwordView = findViewById(R.id.activity_signin_password);

        setupToolbar(toolbar);

        findViewById(R.id.activity_signin_dont_have_account).setOnClickListener((view) -> {
            // TODO: Start Activity with slide from right to left
            startActivity(SignUpActivity.newInstance(this));
        });

        findViewById(R.id.activity_signin_signin).setOnClickListener((view) -> {
            getPresenter().onSignInClicked(
                    emailView.getText().toString().trim(),
                    passwordView.getText().toString().trim());
        });
    }

    private void setupToolbar(final Toolbar toolbar) {
        // TODO: Finish with slide from top to bottom
        toolbar.setNavigationOnClickListener((view) -> {
            setResult(Activity.RESULT_CANCELED);
            finish();
        });
    }

    @Override
    public void finishViewSuccessfully() {
        setResult(Activity.RESULT_OK);
        finish();
    }

    @Override
    public void showLoginError() {
        mLoading.hide();
        new SignInErrorDialog().show(getSupportFragmentManager(), TAG_SIGNIN_ERROR_DIALOG);
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
    protected void onDestroy() {
        super.onDestroy();
        mLoading.destroy();
    }
}
