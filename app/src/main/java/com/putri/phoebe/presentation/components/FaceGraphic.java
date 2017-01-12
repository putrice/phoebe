package com.putri.phoebe.presentation.components;

import android.graphics.Canvas;

import com.google.android.gms.vision.face.Face;

/**
 * Created by putri on 12/14/16.
 */

public class FaceGraphic extends GraphicOverlay.Graphic {

    private volatile Face face;

    FaceGraphic(GraphicOverlay graphicOverlay) {
        super(graphicOverlay);
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

        getFaceDecoration().startDecorate(face, canvas, this);
    }
}
