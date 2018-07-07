package guru.stefma.choicm.presentation.account.signup;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import guru.stefma.choicm.R;

public class SignUpErrorDialog extends AppCompatDialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        return new AlertDialog.Builder(requireContext())
                .setTitle(R.string.singup_dialog_error_title)
                .setMessage(R.string.signup_dialog_error_message)
                .setPositiveButton(android.R.string.ok, null)
                .create();
    }
}
