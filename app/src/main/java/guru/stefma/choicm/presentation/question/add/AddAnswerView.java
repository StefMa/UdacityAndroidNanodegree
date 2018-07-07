package guru.stefma.choicm.presentation.question.add;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.transition.TransitionManager;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import guru.stefma.choicm.R;
import guru.stefma.choicm.presentation.utils.KeyboardUtils;
import guru.stefma.choicm.presentation.utils.SimpleTextWatcher;

/**
 * An custom view which is used to show a "Add another answer to the question".
 *
 * This View has two different states - while the first one is the default:
 * <p>
 * The first - default or disabled - state will display a "add" icon
 * together with an text which says something like "Add a answer".
 * The views are grayed out by default.
 *
 * If you click on that View it will go to the second - or enabled - state.
 * </p>
 * <p>
 * The second - or enabled - state will display a "remove" icon
 * together with an {@link android.widget.EditText}.
 *
 * If the user press on the "remove" icon the view will be go to the first state again.
 * </p>
 */
public class AddAnswerView extends LinearLayout {

    interface OnViewChangedListener {

        void onTextChanged(@NonNull final String text);

        void onStateChanged(@NonNull final State state);

    }

    enum State {
        DISABLED, ENABLED
    }

    private State mState = State.DISABLED;

    private ImageView mIcon;

    private TextView mDisabledText;

    private EditText mEnabledEditText;

    /**
     * The "user" onClickListener will be set if the "client" or "user" call
     * {@link #setOnClickListener(OnClickListener)}.
     *
     * We do this because call this method by ourself to
     * behave differently on clicks on that view.
     */
    @Nullable
    private OnClickListener mUserOnClickListener = null;

    @Nullable
    private OnViewChangedListener mViewChangedListener;

    public AddAnswerView(final Context context) {
        this(context, null);
    }

    public AddAnswerView(final Context context, @Nullable final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AddAnswerView(final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_add_answer, this);
        setGravity(Gravity.CENTER_VERTICAL);

        mIcon = findViewById(R.id.view_add_answer_icon);
        mDisabledText = findViewById(R.id.view_add_answer_add_text);
        mEnabledEditText = findViewById(R.id.view_add_answer_add_answer);

        switchState(State.DISABLED);
        mIcon.setOnClickListener(view -> switchState());
        super.setOnClickListener(view -> {
            switchState();
            if (mUserOnClickListener != null) {
                mUserOnClickListener.onClick(view);
            }
        });
        mEnabledEditText.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(final Editable editable) {
                if (mViewChangedListener != null) {
                    mViewChangedListener.onTextChanged(editable.toString());
                }
            }
        });
    }

    private void switchState() {
        switchState(mState == State.DISABLED ? State.ENABLED : State.DISABLED);
    }

    private void switchState(final State newState) {
        if (newState != mState) {
            mState = newState;
            switch (newState) {
                case ENABLED:
                    switchToEnabledState();
                    break;
                case DISABLED:
                    switchToDisabledState();
                    break;
            }
            if (mViewChangedListener != null) {
                mViewChangedListener.onStateChanged(newState);
            }
        }
    }

    private void switchToEnabledState() {
        TransitionManager.beginDelayedTransition(this);
        mIcon.setImageResource(R.drawable.ic_remove_circle);
        mIcon.setImageTintList(
                ColorStateList.valueOf(ContextCompat.getColor(getContext(), android.R.color.black)));
        mDisabledText.setVisibility(GONE);
        mEnabledEditText.setVisibility(VISIBLE);
        mEnabledEditText.requestFocus();
        KeyboardUtils.showKeyboard(mEnabledEditText);
    }

    private void switchToDisabledState() {
        TransitionManager.beginDelayedTransition(this);
        mIcon.setImageResource(R.drawable.ic_add_circle);
        mIcon.setImageTintList(
                ColorStateList.valueOf(ContextCompat.getColor(getContext(), android.R.color.darker_gray)));
        mDisabledText.setVisibility(VISIBLE);
        mEnabledEditText.setVisibility(GONE);
        mEnabledEditText.setText(null);
        KeyboardUtils.hideKeyboard(mEnabledEditText);
    }

    @Override
    public void setOnClickListener(@Nullable final OnClickListener onClickListener) {
        mUserOnClickListener = onClickListener;
    }

    public void setViewChangedListener(@Nullable final OnViewChangedListener viewChangedListener) {
        mViewChangedListener = viewChangedListener;
    }

    public void setState(final State state) {
        switchState(state);
    }

    public void setText(final String text) {
        mEnabledEditText.setText(text);
    }
}
