package com.example.easyclusterapp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Control {

    public static void LengthControl(Context myContext, EditText editText, CharSequence charSequence, String s, int l, int xOff, int yOff) {
        if (charSequence.length() > l) {
            Toast mes = Toast.makeText(myContext, "Too much symbols for " + s, Toast.LENGTH_SHORT);
            mes.setGravity(Gravity.TOP | Gravity.LEFT, xOff, yOff);
            mes.show();
            editText.setText(charSequence.toString().substring(0, l));
            editText.setSelection(l);
        }
    }

    public static boolean SwitchEye(EditText editText, ImageView imageView, Drawable visibleIcon, Drawable invisibleIcon, MotionEvent motionEvent) {
        int idx = editText.getSelectionEnd();
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                editText.setInputType(131073);
                imageView.setImageDrawable(visibleIcon);
                break;
            case MotionEvent.ACTION_UP:
                editText.setInputType(129);
                imageView.setImageDrawable(invisibleIcon);
                break;
        }
        editText.setSelection(idx);
        return true;
    }
}
