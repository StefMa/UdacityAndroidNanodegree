package guru.stefma.choicm.presentation.account.signup;

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
import guru.stefma.choicm.domain.usecase.account.SignUpUseCase;
import guru.stefma.choicm.presentation.base.loading.Loading;
import net.grandcentrix.thirtyinch.TiActivity;
import toothpick.Toothpick;

// TODO: Document the result codes... or handle it better somehow :shrug:
public class SignUpActivity extends TiActivity<SignUpPresenter, SignUpView> implements SignUpView {

    private static final String TAG_SIGNUP_ERROR_DIALOG = "TAG_SIGNUP_ERROR_DIALOG";

    public static Intent newInstance(@NonNull final Context context) {
        return new Intent(context, SignUpActivity.class);
    }

    private final Loading mLoading = new Loading();

    @NonNull
    @Override
    public SignUpPresenter providePresenter() {
        final SignUpUseCase signUpUseCase = new SignUpUseCase(null, null);
        Toothpick.inject(signUpUseCase, DependencyGraph.getApplicationScope());
        return new SignUpPresenter(signUpUseCase);
    }

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        final Toolbar toolbar = findViewById(R.id.activity_signup_toolbar);
        final EditText userNameView = findViewById(R.id.activity_signup_username);
        final EditText emailView = findViewById(R.id.activity_signup_email);
        final EditText passwordView = findViewById(R.id.activity_signup_password);

        setupToolbar(toolbar);

        findViewById(R.id.activity_signup_signup).setOnClickListener(view -> {
            getPresenter().onSignUpClicked(
                    userNameView.getText().toString().trim(),
                    emailView.getText().toString().trim(),
                    passwordView.getText().toString().trim());
        });
    }

    private void setupToolbar(final Toolbar toolbar) {
        // TODO: Finish with slide from left to right
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
    public void showLoading() {
        mLoading.show(getSupportFragmentManager());
    }

    @Override
    public void hideLoading() {
        mLoading.hide();
    }

    @Override
    public void showRegisterError() {
        mLoading.hide();
        new SignUpErrorDialog().show(getSupportFragmentManager(), TAG_SIGNUP_ERROR_DIALOG);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLoading.destroy();
    }
}
