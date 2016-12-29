package com.putri.phoebe.presentation.components;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

/**
 * Created by putri on 12/28/16.
 */

public class Sticker {

    private Context context;

    public Sticker(Context context) {
        this.context = context;
    }

    public Bitmap getBitmap(int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        Bitmap mouth = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mouth);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return mouth;
    }

}
