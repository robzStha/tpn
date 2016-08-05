package com.official.trialpassnepal.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.EditText;

import com.official.trialpassnepal.R;


/**
 * Created by Rabin on 8/10/2015.
 */
public class EditTextTypeFaced extends EditText {
    public EditTextTypeFaced(Context context) {
        super(context);
        init(null);
    }

    public EditTextTypeFaced(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public EditTextTypeFaced(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.TextViewTypeFaced);
            String fontStyle = a.getString(R.styleable.TextViewTypeFaced_fontStyle);
            if (fontStyle != null) {
                if (fontStyle.equals("bold")) {
                    Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/SegoeUI-Bd.ttf");
                    setTypeface(myTypeface);
                } else if (fontStyle.equals("regular")) {
                    Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/SegoeUI-Rg.ttf");
                    setTypeface(myTypeface);
                } else {
                    String fontPath = "fonts/SegoeUI-Rg.ttf";
                    Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), fontPath);
                    setTypeface(myTypeface);
                }
            } else {
                String fontPath = "fonts/SegoeUI-Rg.ttf";
                Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), fontPath);
                setTypeface(myTypeface);
            }
            a.recycle();
        }
    }
}
