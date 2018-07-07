package guru.stefma.choicm.presentation.account.signin;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import guru.stefma.choicm.R;

public class SignInErrorDialog extends AppCompatDialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        return new AlertDialog.Builder(requireContext())
                .setTitle(R.string.singin_dialog_error_title)
                .setMessage(R.string.singin_dialog_error_message)
                .setPositiveButton(android.R.string.ok, null)
                .create();
    }
}
