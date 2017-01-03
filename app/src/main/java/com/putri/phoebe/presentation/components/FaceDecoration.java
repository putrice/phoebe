package com.putri.phoebe.presentation.components;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.SparseArray;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.Landmark;
import com.putri.phoebe.R;

/**
 * Created by putri on 1/2/17.
 */

public class FaceDecoration {

    private Sticker sticker;

    private GraphicOverlay.Graphic graphic;

    public FaceDecoration(Sticker sticker) {
        this.sticker = sticker;
    }

    public FaceDecoration(Sticker sticker, GraphicOverlay.Graphic graphic) {
        this.sticker = sticker;
        this.graphic = graphic;
    }

    public void startDecorate(Face face, final Canvas canvas) {
        float xPositionRightMouth = 0;
        float yPositionRightMouth = 0;
        float xPositionLeftMouth = 0;
        float yPositionLeftMouth = 0;

        Bitmap headband = Bitmap.createScaledBitmap(sticker.getBitmap(R.drawable.flower_snapchat), (int) Math.ceil(face.getWidth() * 2), (int) Math.ceil(face.getHeight()), false);
        canvas.drawBitmap(headband, graphic.translateX(face.getPosition().x + face.getWidth()), graphic.translateY(face.getPosition().y), null);

        for (Landmark landmark : face.getLandmarks()) {
            if (landmark.getType() == Landmark.LEFT_EYE || landmark.getType() == Landmark.RIGHT_EYE) {
                final double eyeWidth = (double) (face.getWidth() / 4);
                final double eyeHeight = face.getHeight() / 7.5;

                final float newX = landmark.getPosition().x;
                final float newY = landmark.getPosition().y;

//                if(face.getEulerZ() < 1 && face.getEulerZ() >= 0) {
                    final Bitmap newEyes = Bitmap.createScaledBitmap(sticker.getBitmap(R.drawable.eyes), (int) Math.ceil(eyeWidth), (int) Math.ceil(eyeHeight), false);
                    if (graphic.getFacing() == CameraSource.CAMERA_FACING_FRONT) {
                        canvas.drawBitmap(newEyes, graphic.translateX(newX + (float) (eyeWidth / 4)), graphic.translateY(newY - (float) (eyeHeight / 2)), null);
                    } else {
                        canvas.drawBitmap(newEyes, graphic.translateX(newX - (float) (eyeWidth / 4)), graphic.translateY(newY - (float) (eyeHeight / 4)), null);
                    }
//                }
            }

            if (landmark.getType() == Landmark.RIGHT_MOUTH) {
                xPositionRightMouth = landmark.getPosition().x;
                yPositionRightMouth = landmark.getPosition().y;

                if (xPositionLeftMouth != 0 && xPositionRightMouth != 0) {
                    double mouthWidth = (double) (face.getWidth() / 5);
                    double mouthHeight = (double) (face.getHeight() / 15);

                    float mouthCenterTop = yPositionLeftMouth - ((float) mouthHeight / 2);
                    float mouthCenterBottom = yPositionLeftMouth + ((float) mouthHeight / 2);

//                    if(face.getEulerZ() < 1 && face.getEulerZ() >= 0) {
                        Bitmap newMouth = Bitmap.createScaledBitmap(sticker.getBitmap(R.drawable.mouth), (int) Math.ceil(mouthWidth), (int) Math.ceil(mouthHeight), false);
                        if (graphic.getFacing() == CameraSource.CAMERA_FACING_FRONT) {
                            canvas.drawBitmap(newMouth, null, new RectF(graphic.translateX(xPositionLeftMouth), graphic.translateY(mouthCenterTop), graphic.translateX(xPositionRightMouth), graphic.translateY(mouthCenterBottom)), null);
                        } else {
                            mouthCenterTop = yPositionLeftMouth - ((float) mouthHeight / 4);
                            canvas.drawBitmap(newMouth, null, new RectF(graphic.translateX(xPositionRightMouth), graphic.translateY(mouthCenterTop), graphic.translateX(xPositionLeftMouth), graphic.translateY(mouthCenterBottom)), null);
                        }
//                    }
                }
            }

            if (landmark.getType() == Landmark.LEFT_MOUTH) {
                xPositionLeftMouth = landmark.getPosition().x;
                yPositionLeftMouth = landmark.getPosition().y;
            }
        }
    }

    private float getX(float x, float y, float eulerY) {
        float newX = (float) (x * Math.cos(eulerY) + y * Math.sin(eulerY));
        return newX;
    }

    private float getY(float x, float y, float eulerY) {
        float newY = (float) (-(x * Math.sin(eulerY)) + y * Math.cos(eulerY));
        return newY;
    }

    public void startDecorate(SparseArray<Face> faces, Canvas canvas) {
        for (int i = 0; i < faces.size(); i++) {
            Face thisFace = faces.valueAt(i);
            float xPositionRightMouth = 0;
            float yPositionRightMouth = 0;
            float xPositionLeftMouth = 0;
            float yPositionLeftMouth = 0;

            Bitmap headband = Bitmap.createScaledBitmap(sticker.getBitmap(R.drawable.headband), (int) Math.ceil(thisFace.getWidth()), (int) Math.ceil(thisFace.getHeight() / 3), false);
            canvas.drawBitmap(headband, thisFace.getPosition().x, thisFace.getPosition().y, null);

            for (Landmark landmark : thisFace.getLandmarks()) {
                if (landmark.getType() == Landmark.LEFT_EYE || landmark.getType() == Landmark.RIGHT_EYE) {
                    double eyeWidth = (double) (thisFace.getWidth() / 4);
                    double eyeHeight = (double) (thisFace.getHeight() / 7.5);

                    Bitmap newEyes = Bitmap.createScaledBitmap(sticker.getBitmap(R.drawable.eyes), (int) Math.ceil(eyeWidth), (int) Math.ceil(eyeHeight), false);
                    canvas.drawBitmap(newEyes, landmark.getPosition().x - (float) (eyeWidth / 2), landmark.getPosition().y - (float) (eyeHeight / 1.5), null);
                }

                if (landmark.getType() == Landmark.RIGHT_MOUTH) {
                    xPositionRightMouth = landmark.getPosition().x;
                    yPositionRightMouth = landmark.getPosition().y;

                    if (xPositionLeftMouth != 0 && xPositionRightMouth != 0) {
                        double mouthWidth = (double) (thisFace.getWidth() / 5);
                        double mouthHeight = (double) (thisFace.getHeight() / 15);

                        float mouthCenterTop = yPositionLeftMouth - ((float) mouthHeight / 4);
                        float mouthCenterBottom = yPositionLeftMouth + ((float) mouthHeight);

                        Bitmap newMouth = Bitmap.createScaledBitmap(sticker.getBitmap(R.drawable.mouth), (int) Math.ceil(mouthWidth), (int) Math.ceil(mouthHeight), false);
                        canvas.drawBitmap(newMouth, null, new RectF(xPositionRightMouth, mouthCenterTop, xPositionLeftMouth, mouthCenterBottom), null);
                    }
                }

                if (landmark.getType() == Landmark.LEFT_MOUTH) {
                    xPositionLeftMouth = landmark.getPosition().x;
                    yPositionLeftMouth = landmark.getPosition().y;
                }
            }
        }
    }
}
