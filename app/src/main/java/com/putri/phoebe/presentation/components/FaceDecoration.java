package com.putri.phoebe.presentation.components;

import android.content.Context;
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

    public FaceDecoration(Sticker sticker) {
        this.sticker = sticker;
    }

    public void updateSticker(int stickerId) {
        sticker.setActiveSticker(stickerId);
    }

    public void startDecorate(Face face, final Canvas canvas, GraphicOverlay.Graphic graphic) {

        switch (sticker.getActiveSticker()) {
            case Sticker.HEADBAND1:
                drawHeadbandStickerOnCamera(Sticker.HEADBAND1, face, canvas, graphic);
                break;

            case Sticker.HEADBAND2:
                drawHeadbandStickerOnCamera(Sticker.HEADBAND2, face, canvas, graphic);
                break;

            case Sticker.RYUK:
                drawCharacterStickerOnCamera(Sticker.RYUK, face, canvas, graphic);
                break;

            case Sticker.CAT:
                drawCharacterStickerOnCamera(Sticker.CAT, face, canvas, graphic);
                break;
        }
    }

    private void drawHeadbandStickerOnCamera(int stickerId, Face face, Canvas canvas, GraphicOverlay.Graphic graphic) {
        int drawableId = 0;
        int width = 0;
        int height = 0;

        width = (int) Math.ceil(face.getWidth() * 2);
        height = (int) Math.ceil(face.getHeight());

        if(stickerId == Sticker.HEADBAND1) {
            drawableId = R.drawable.headband;
        } else if(stickerId == Sticker.HEADBAND2) {
            drawableId = R.drawable.flower_snapchat;
        } else if(stickerId == Sticker.RYUK) {
            drawableId = R.drawable.hair;
        } else if(stickerId == Sticker.CAT) {
            drawableId = R.drawable.ears;
            width = width / 2;
            height = height / 4;
        }

        Bitmap headband = Bitmap.createScaledBitmap(sticker.getBitmap(drawableId), width, height, false);
        canvas.drawBitmap(headband, graphic.translateX(face.getPosition().x + face.getWidth()), graphic.translateY(face.getPosition().y), null);
    }

    private void drawCharacterStickerOnCamera(int stickerId, Face face, Canvas canvas, GraphicOverlay.Graphic graphic) {
        drawHeadbandStickerOnCamera(stickerId, face, canvas, graphic);

        float xPositionRightMouth = 0;
        float yPositionRightMouth = 0;
        float xPositionLeftMouth = 0;
        float yPositionLeftMouth = 0;

        for (Landmark landmark : face.getLandmarks()) {
            if(stickerId == Sticker.RYUK) {
                if (landmark.getType() == Landmark.LEFT_EYE || landmark.getType() == Landmark.RIGHT_EYE) {
                    final double eyeWidth = (double) (face.getWidth() / 4);
                    final double eyeHeight = face.getHeight() / 7.5;

                    final float newX = landmark.getPosition().x;
                    final float newY = landmark.getPosition().y;

                    final Bitmap newEyes = Bitmap.createScaledBitmap(sticker.getBitmap(R.drawable.eyes), (int) Math.ceil(eyeWidth), (int) Math.ceil(eyeHeight), false);
                    if (graphic.getFacing() == CameraSource.CAMERA_FACING_FRONT) {
                        canvas.drawBitmap(newEyes, graphic.translateX(newX + (float) (eyeWidth / 4)), graphic.translateY(newY - (float) (eyeHeight / 2)), null);
                    } else {
                        canvas.drawBitmap(newEyes, graphic.translateX(newX - (float) (eyeWidth / 4)), graphic.translateY(newY - (float) (eyeHeight / 4)), null);
                    }
                }

                if (landmark.getType() == Landmark.RIGHT_MOUTH) {
                    xPositionRightMouth = landmark.getPosition().x;
                    yPositionRightMouth = landmark.getPosition().y;

                    if (xPositionLeftMouth != 0 && xPositionRightMouth != 0) {
                        double mouthWidth = (double) (face.getWidth() / 5);
                        double mouthHeight = (double) (face.getHeight() / 15);

                        float mouthCenterTop = yPositionLeftMouth - ((float) mouthHeight / 2);
                        float mouthCenterBottom = yPositionLeftMouth + ((float) mouthHeight / 2);

                        Bitmap newMouth = Bitmap.createScaledBitmap(sticker.getBitmap(R.drawable.mouth), (int) Math.ceil(mouthWidth), (int) Math.ceil(mouthHeight), false);
                        if (graphic.getFacing() == CameraSource.CAMERA_FACING_FRONT) {
                            canvas.drawBitmap(newMouth, null, new RectF(graphic.translateX(xPositionLeftMouth), graphic.translateY(mouthCenterTop), graphic.translateX(xPositionRightMouth), graphic.translateY(mouthCenterBottom)), null);
                        } else {
                            mouthCenterTop = yPositionLeftMouth - ((float) mouthHeight / 4);
                            canvas.drawBitmap(newMouth, null, new RectF(graphic.translateX(xPositionRightMouth), graphic.translateY(mouthCenterTop), graphic.translateX(xPositionLeftMouth), graphic.translateY(mouthCenterBottom)), null);
                        }
                    }
                }

                if (landmark.getType() == Landmark.LEFT_MOUTH) {
                    xPositionLeftMouth = landmark.getPosition().x;
                    yPositionLeftMouth = landmark.getPosition().y;
                }
            }

            if(stickerId == Sticker.CAT) {
                if(landmark.getType() == Landmark.NOSE_BASE) {
                    double noseWidth = (double) (face.getWidth() / 2);
                    double noseHeight = (double) (face.getHeight() / 4);

                    Bitmap nose = Bitmap.createScaledBitmap(sticker.getBitmap(R.drawable.nose), (int) Math.ceil(noseWidth), (int) Math.ceil(noseHeight), false);
                    canvas.drawBitmap(nose, graphic.translateX((float) (landmark.getPosition().x + (noseWidth / 4))), graphic.translateY((float) (landmark.getPosition().y - (noseHeight / 2))), null);
                }
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
        switch (sticker.getActiveSticker()) {
            case Sticker.HEADBAND1:
                drawHeadbandStickerOnImage(Sticker.HEADBAND1, faces, canvas);
                break;

            case Sticker.HEADBAND2:
                drawHeadbandStickerOnImage(Sticker.HEADBAND2, faces, canvas);
                break;

            case Sticker.RYUK:
                drawCharacterStickerOnImage(Sticker.RYUK, faces, canvas);
                break;
        }
    }

    private void drawHeadbandStickerOnImage(int stickerId, SparseArray<Face> faces, Canvas canvas) {
        for (int i = 0; i < faces.size(); i++) {
            Face thisFace = faces.valueAt(i);
            int drawableId = 0;
            if (stickerId == Sticker.HEADBAND1) {
                drawableId = R.drawable.headband;
            } else if (stickerId == Sticker.HEADBAND2) {
                drawableId = R.drawable.flower_snapchat;
            }

            Bitmap headband = Bitmap.createScaledBitmap(sticker.getBitmap(drawableId), (int) Math.ceil(thisFace.getWidth()), (int) Math.ceil(thisFace.getHeight() / 3), false);
            canvas.drawBitmap(headband, thisFace.getPosition().x, thisFace.getPosition().y, null);
        }
    }

    private void drawCharacterStickerOnImage(int stickerId, SparseArray<Face> faces, Canvas canvas) {
        for (int i = 0; i < faces.size(); i++) {
            Face thisFace = faces.valueAt(i);
            float xPositionRightMouth = 0;
            float yPositionRightMouth = 0;
            float xPositionLeftMouth = 0;
            float yPositionLeftMouth = 0;

            for (Landmark landmark : thisFace.getLandmarks()) {
                if (landmark.getType() == Landmark.LEFT_EYE || landmark.getType() == Landmark.RIGHT_EYE) {
                    double eyeWidth = (double) (thisFace.getWidth() / 4);
                    double eyeHeight = thisFace.getHeight() / 7.5;

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
