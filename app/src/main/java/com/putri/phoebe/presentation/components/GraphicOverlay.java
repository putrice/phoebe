package com.putri.phoebe.presentation.components;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import com.google.android.gms.vision.CameraSource;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by putri on 12/13/16.
 */

public class GraphicOverlay extends View {

    private final Object lock = new Object();

    private int previewWidth;

    private float widthScaleFactor = 1.0f;

    private int previewHeight;

    private float heightScaleFactor = 1.0f;

    private int facing = CameraSource.CAMERA_FACING_BACK;

    private Set<Graphic> graphics = new HashSet<>();

    public GraphicOverlay(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void clear() {
        synchronized (lock) {
            graphics.clear();
        }
        postInvalidate();
    }

    public void add(Graphic graphic) {
        synchronized (lock) {
            graphics.add(graphic);
        }
        postInvalidate();
    }

    public void remove(Graphic graphic) {
        synchronized (lock) {
            graphics.remove(graphic);
        }
        postInvalidate();
    }

    public void setCameraInfo(int previewWidth, int previewHeight, int facing) {
        synchronized (lock) {
            this.previewWidth = previewWidth;
            this.previewHeight = previewHeight;
            this.facing = facing;
        }
        postInvalidate();
    }

    public void setFacing(int facing) {
        synchronized (lock) {
            this.facing = facing;
        }
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        synchronized (lock) {
            if((previewWidth != 0) && (previewHeight != 0)) {
                widthScaleFactor = (float) canvas.getWidth() / (float) previewWidth;
                heightScaleFactor = (float) canvas.getHeight() / (float) previewHeight;
            }

            for(Graphic graphic : graphics) {
                graphic.draw(canvas);
            }
        }
    }

    public static abstract class Graphic {
        private GraphicOverlay graphicOverlay;

        public Graphic(GraphicOverlay graphicOverlay) {
            this.graphicOverlay = graphicOverlay;
        }

        public abstract void draw(Canvas canvas);

        public float scaleX(float horizontal) {
            return horizontal * graphicOverlay.widthScaleFactor;
        }

        public float scaleY(float vertical) {
            return vertical * graphicOverlay.heightScaleFactor;
        }

        public float translateX(float x) {
            if(graphicOverlay.facing == CameraSource.CAMERA_FACING_FRONT) {
                return graphicOverlay.getWidth() - scaleX(x);
            } else {
                return scaleX(x);
            }
        }

        public float translateY(float y) {
            return scaleY(y);
        }

        public void postInvalidate() {
            graphicOverlay.postInvalidate();
        }

        public int getFacing() {
            return graphicOverlay.facing;
        }
    }
}
