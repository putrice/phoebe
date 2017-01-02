package com.putri.phoebe.presentation.components;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;

import com.google.android.gms.common.images.Size;
import com.google.android.gms.vision.CameraSource;

import java.io.IOException;

/**
 * Created by putri on 12/13/16.
 */

public class CameraComponentPreview extends ViewGroup {
    private static final String TAG = "CameraSourcePreview";

    private Context context;

    private SurfaceView surfaceView;

    private boolean isStartRequested;

    private boolean isSurfaceAvailable;

    private CameraSource cameraSource;

    private GraphicOverlay graphicOverlay;

    public CameraComponentPreview(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        isStartRequested = false;
        isSurfaceAvailable = false;

        surfaceView = new SurfaceView(context);
        surfaceView.getHolder().addCallback(new SurfaceCallback());
        addView(surfaceView);
    }

    public void start(CameraSource cameraSource) throws IOException {
        if (cameraSource == null) {
            stop();
        }

        this.cameraSource = cameraSource;

        if (this.cameraSource != null) {
            isStartRequested = true;
            startIfReady();
        }
    }

    public void start(CameraSource cameraSource, GraphicOverlay graphicOverlay) throws IOException {
        this.graphicOverlay = graphicOverlay;
        start(cameraSource);
    }

    public void stop() {
        if (cameraSource != null) {
            cameraSource.stop();
        }
    }

    public void release() {
        if (cameraSource != null) {
            cameraSource.release();
            cameraSource = null;
        }
    }

    private void startIfReady() throws IOException {

        if (isStartRequested && isSurfaceAvailable) {
            cameraSource.start(surfaceView.getHolder());
            if (graphicOverlay != null) {
                Size size = cameraSource.getPreviewSize();
                int min = Math.min(size.getWidth(), size.getHeight());
                int max = Math.max(size.getWidth(), size.getHeight());
                if (isPortraitMode()) {
                    graphicOverlay.setCameraInfo(min, max, cameraSource.getCameraFacing());
                } else {
                    graphicOverlay.setCameraInfo(max, min, cameraSource.getCameraFacing());
                }
                graphicOverlay.clear();
            }
            isStartRequested = false;
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int width = 320;
        int height = 240;

        if (cameraSource != null) {
            Size size = cameraSource.getPreviewSize();
            if (size != null) {
                width = size.getWidth();
                height = size.getHeight();
            }
        }

        if (isPortraitMode()) {
            int tmp = width;
            width = height;
            height = tmp;
        }

        final int layoutWidth = r - l;
        final int layoutHeight = b - t;

        int childWidth;
        int childHeight;
        int childXOffset = 0;
        int childYOffset = 0;
        float widthRatio = (float) layoutWidth / (float) width;
        float heightRatio = (float) layoutHeight / (float) height;

        if (widthRatio > heightRatio) {
            childWidth = layoutWidth;
            childHeight = (int) ((float) height * widthRatio);
            childYOffset = (childHeight - layoutHeight) / 2;
        } else {
            childWidth = (int) ((float) width * heightRatio);
            childHeight = layoutHeight;
            childXOffset = (childWidth - layoutWidth) / 2;
        }

        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).layout(-1 * childXOffset, -1 * childYOffset, childWidth - childXOffset, childHeight - childYOffset);
        }

        try {
            startIfReady();
        } catch (IOException e) {
            Log.e(TAG, "Could not start camera", e);
        }
    }

    private boolean isPortraitMode() {
        int orientation = context.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return false;
        }

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            return true;
        }

        Log.d(TAG, "isPortraitMode returning false");
        return false;

    }

    private class SurfaceCallback implements SurfaceHolder.Callback {

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            isSurfaceAvailable = true;
            try {
                startIfReady();
            } catch (IOException e) {
                Log.e(TAG, "Could not start camera", e);
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            isSurfaceAvailable = false;
        }
    }
}
