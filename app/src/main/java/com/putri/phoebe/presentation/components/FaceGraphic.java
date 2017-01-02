package com.putri.phoebe.presentation.components;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.google.android.gms.vision.face.Face;

/**
 * Created by putri on 12/14/16.
 */

public class FaceGraphic extends GraphicOverlay.Graphic {

    private volatile Face face;

    private Sticker sticker;

    private FaceDecoration faceDecoration;

    FaceGraphic(GraphicOverlay graphicOverlay) {
        super(graphicOverlay);
        sticker = new Sticker(graphicOverlay.getContext());
        faceDecoration = new FaceDecoration(sticker, this);
    }

    public void updateFace(Face face) {
        this.face = face;
        postInvalidate();
    }

    @Override
    public void draw(Canvas canvas) {
        Face face = this.face;
        if(face == null) {
            return;
        }

        faceDecoration.startDecorate(face, canvas);
    }
}
