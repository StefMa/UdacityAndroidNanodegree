package com.udacity.sandwichclub;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

public class VerticalTitleDescriptionView extends LinearLayout {

    private final TextView desc;

    private final TextView title;

    public VerticalTitleDescriptionView(final Context context, @Nullable final AttributeSet attrs) {
        super(context, attrs);

        setOrientation(VERTICAL);

        inflate(context, R.layout.view_vertical_title_description, this);
        title = findViewById(R.id.view_vertical_title_desc_title);
        desc = findViewById(R.id.view_vertical_title_desc_desc);

        TypedArray typedArray = context.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.VerticalTitleDescriptionView, 0, 0);
        try {
            setTitle(typedArray.getString(R.styleable.VerticalTitleDescriptionView_verticalTitle));
        } finally {
            typedArray.recycle();
        }
    }

    public void setTitle(@Nullable CharSequence title) {
        this.title.setText(title);
    }

    public void setDescription(@Nullable CharSequence desc) {
        this.desc.setText(desc);
    }
}
