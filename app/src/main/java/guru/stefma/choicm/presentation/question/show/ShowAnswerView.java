package guru.stefma.choicm.presentation.question.show;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import guru.stefma.choicm.R;

// TODO: Document
// TODO: Add clicklistener one whoel view to toggle the checkbox
public class ShowAnswerView extends LinearLayout {

    private CheckBox mCheckBox;

    private TextView mAnswerView;

    public ShowAnswerView(final Context context) {
        this(context, null);
    }

    public ShowAnswerView(final Context context, @Nullable final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShowAnswerView(final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_show_answer, this);
        setGravity(Gravity.CENTER_VERTICAL);

        mCheckBox = findViewById(R.id.view_show_answer_check);
        mAnswerView = findViewById(R.id.view_show_answer_answer);
    }

    public void setText(@Nullable final String answer) {
        mAnswerView.setText(answer);
    }

    public void setChecked(final boolean checked) {
        mCheckBox.setChecked(checked);
    }

    public void setOnCheckedChangeListener(@Nullable final OnCheckedChangeListener onCheckedChangeListener) {
        mCheckBox.setOnCheckedChangeListener(onCheckedChangeListener);
    }

    public void setReadOnly(final boolean readOnly) {
        // TODO: Remove click listener and so on
        mCheckBox.setEnabled(!readOnly);
    }
}
