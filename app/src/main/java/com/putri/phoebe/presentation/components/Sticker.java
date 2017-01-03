package com.putri.phoebe.presentation.components;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.putri.phoebe.R;

/**
 * Created by putri on 12/28/16.
 */

public class Sticker {

    public static final int HEADBAND1 = 0;

    public static final int HEADBAND2 = 1;

    public static final int RYUK = 2;

    private int activeSticker;

    private Context context;

    public Sticker(Context context) {
        this.context = context;
    }

    public void setActiveSticker(int activeSticker) {
        this.activeSticker = activeSticker;
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
