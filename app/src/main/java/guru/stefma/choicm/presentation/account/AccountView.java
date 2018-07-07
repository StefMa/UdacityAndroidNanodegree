package guru.stefma.choicm.presentation.account;

import android.support.annotation.NonNull;
import net.grandcentrix.thirtyinch.TiView;

interface AccountView extends TiView {

    /**
     * Shows the given username in the view
     */
    void showUsername(@NonNull final String username);

    /**
     * Shows a error that the user can't be fetched at the moment...
     */
    void showGetAccountError();

    /**
     * Finish the view successfully means that the user
     * is logged out.
     */
    void finishViewSuccessfully();

    /**
     * Shows a error that the user can't logged out...
     */
    void showLogoutError();
}
