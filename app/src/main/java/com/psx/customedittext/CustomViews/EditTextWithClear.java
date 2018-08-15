package com.psx.customedittext.CustomViews;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.psx.customedittext.R;

public class EditTextWithClear extends AppCompatEditText {

    Drawable clearButtonImage;

    public EditTextWithClear(Context context) {
        super(context);
        init();
    }

    public EditTextWithClear(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditTextWithClear(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        clearButtonImage = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_clear_opaque_24dp, null);
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                showClearButton();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (getCompoundDrawablesRelative()[2] != null) {
                    float clearButtonStart; //LTR
                    float clearbuttonEnd; //RTL
                    boolean isButtonClicked = false;
                    if (getLayoutDirection() == LAYOUT_DIRECTION_RTL) {
                        clearbuttonEnd = clearButtonImage.getIntrinsicWidth() + getPaddingStart();
                        if (event.getX() < clearbuttonEnd)
                            isButtonClicked = true;
                    } else {
                        //LTR
                        clearButtonStart = (getWidth() - getPaddingEnd() - clearButtonImage.getIntrinsicWidth());
                        if (event.getX() > clearButtonStart)
                            isButtonClicked = true;
                    }
                    if (isButtonClicked) {
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            clearButtonImage = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_clear_black_24dp, null);
                            showClearButton();
                        }
                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            clearButtonImage = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_clear_opaque_24dp, null);
                            hideClearButton();
                            getText().clear();
                            hideClearButton();
                            return true;
                        }
                    } else {
                        return false;
                    }
                }
                return false;
            }
        });
    }

    private void showClearButton() {
        setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, clearButtonImage, null);
    }

    private void hideClearButton() {
        setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
    }

}
