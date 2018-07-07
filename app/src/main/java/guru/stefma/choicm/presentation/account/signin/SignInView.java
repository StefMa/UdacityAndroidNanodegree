package guru.stefma.choicm.presentation.account.signin;

import guru.stefma.choicm.presentation.base.loading.LoadingView;
import net.grandcentrix.thirtyinch.TiView;

interface SignInView extends TiView, LoadingView {

    /**
     * Finish the SignInProcess successfully.
     * Means the User is logged in.
     */
    void finishViewSuccessfully();

    /**
     * Shows an error after the login was <b>not</b> successfully.
     */
    void showLoginError();
}
