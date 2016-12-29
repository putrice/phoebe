package com.putri.phoebe.presentation.components;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.Landmark;
import com.putri.phoebe.R;

/**
 * Created by putri on 12/14/16.
 */

public class FaceGraphic extends GraphicOverlay.Graphic {

    private static final float FACE_POSITION_RADIUS = 10.0f;

    private static final float ID_TEXT_SIZE = 40.0f;

    private static final float ID_Y_OFFSET = 50.0f;

    private static final float ID_X_OFFSET = 50.0f;

    private static final float BOX_STROKE_WIDTH = 5.0f;

    private static int currentColorIndex = 0;

    private Paint facePositionPaint;

    private Paint idPaint;

    private Paint boxPaint;

    private volatile Face face;

    private int faceId;

    private float faceHappiness;

    private Sticker sticker;

    FaceGraphic(GraphicOverlay graphicOverlay) {
        super(graphicOverlay);

        facePositionPaint = new Paint();

        idPaint = new Paint();
        idPaint.setTextSize(ID_TEXT_SIZE);

        boxPaint = new Paint();
        boxPaint.setStyle(Paint.Style.STROKE);
        boxPaint.setStrokeWidth(BOX_STROKE_WIDTH);

        sticker = new Sticker(graphicOverlay.getContext());
    }

    public void setId(int id) {
        faceId = id;
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

        float xPositionRightMouth = 0;
        float yPositionRightMouth = 0;
        float xPositionLeftMouth = 0;
        float yPositionLeftMouth = 0;

        for(Landmark landmark : face.getLandmarks()) {
            if (landmark.getType() == Landmark.LEFT_EYE || landmark.getType() == Landmark.RIGHT_EYE) {
                double eyeWidth = (double) (face.getWidth() / 4);
                double eyeHeight = (double) (face.getHeight() / 7.5);

                Bitmap newEyes = Bitmap.createScaledBitmap(sticker.getBitmap(R.drawable.eyes), (int) Math.ceil(eyeWidth), (int) Math.ceil(eyeHeight), false);
                canvas.drawBitmap(newEyes, translateX(landmark.getPosition().x + (float)(eyeWidth / 2)), translateY(landmark.getPosition().y - (float)(eyeHeight)), null);
            }

            if(landmark.getType() == Landmark.RIGHT_MOUTH) {
                xPositionRightMouth = landmark.getPosition().x;
                yPositionRightMouth = landmark.getPosition().y;

                if(xPositionLeftMouth != 0 && xPositionRightMouth != 0) {
                    double mouthWidth = (double) (face.getWidth() / 5);
                    double mouthHeight = (double) (face.getHeight() / 15);

                    float mouthCenterTop = yPositionLeftMouth - ((float) mouthHeight);
                    float mouthCenterBottom = yPositionLeftMouth + ((float) mouthHeight / 2);

                    Bitmap newMouth = Bitmap.createScaledBitmap(sticker.getBitmap(R.drawable.mouth), (int) Math.ceil(mouthWidth), (int) Math.ceil(mouthHeight), false);
                    canvas.drawBitmap(newMouth, null, new RectF(translateX(xPositionLeftMouth), translateY(mouthCenterTop), translateX(xPositionRightMouth), translateY(mouthCenterBottom)), null);
                }
            }

            if(landmark.getType() == Landmark.LEFT_MOUTH) {
                xPositionLeftMouth = landmark.getPosition().x;
                yPositionLeftMouth = landmark.getPosition().y;
            }
        }
    }
}
