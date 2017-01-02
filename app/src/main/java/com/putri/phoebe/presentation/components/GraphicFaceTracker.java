package com.putri.phoebe.presentation.components;

import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.face.Face;

/**
 * Created by putri on 12/14/16.
 */

public class GraphicFaceTracker extends Tracker<Face> {

    private GraphicOverlay graphicOverlay;

    private FaceGraphic faceGraphic;

    public GraphicFaceTracker(GraphicOverlay graphicOverlay) {
        this.graphicOverlay = graphicOverlay;
        faceGraphic = new FaceGraphic(graphicOverlay);
    }

    @Override
    public void onNewItem(int id, Face item) {
    }

    @Override
    public void onUpdate(Detector.Detections<Face> detections, Face item) {
        graphicOverlay.add(faceGraphic);
        faceGraphic.updateFace(item);
    }

    @Override
    public void onMissing(Detector.Detections<Face> detections) {
        graphicOverlay.remove(faceGraphic);
    }

    @Override
    public void onDone() {
        graphicOverlay.remove(faceGraphic);
    }
}
