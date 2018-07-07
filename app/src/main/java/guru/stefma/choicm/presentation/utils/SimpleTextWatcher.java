package guru.stefma.choicm.presentation.utils;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * An interface which extends the {@link TextWatcher} but provides
 * for all methods <b>default implementations</b> which are basically do nothing.
 *
 * Use this and override only the methods you want.
 */
public interface SimpleTextWatcher extends TextWatcher {

    @Override
    default void beforeTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {
    }

    @Override
    default void onTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {

    }

    @Override
    default void afterTextChanged(final Editable editable) {

    }
}
