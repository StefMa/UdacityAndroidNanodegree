package guru.stefma.choicm.presentation.account.signup;

import guru.stefma.choicm.presentation.base.loading.LoadingView;
import net.grandcentrix.thirtyinch.TiView;

interface SignUpView extends TiView, LoadingView {

    /**
     * Finish the SignUpProcess successfully.
     * Means a new User has registered and is logged in.
     */
    void finishViewSuccessfully();

    /**
     * Shows an error after the registration was <b>not</b> successfully.
     */
    void showRegisterError();

}
