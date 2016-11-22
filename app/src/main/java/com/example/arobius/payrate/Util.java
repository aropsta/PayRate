package com.example.arobius.payrate;

import android.content.Context;


public class Util {
    public static final int  DIALOG_ENTER_TEXT = 1, DIALOG_MAX_TIME_REACHED = 2, DIALOG_CLEAR = 3;

    public int dpToPx(Context context, int dp){

        final double scale = context.getResources().getDisplayMetrics().density;

        return (int)(dp * scale + 0.5f);
    }



}
