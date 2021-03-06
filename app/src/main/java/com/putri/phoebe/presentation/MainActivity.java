package com.putri.phoebe.presentation;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.putri.phoebe.R;
import com.putri.phoebe.presentation.components.CameraComponentPreview;
import com.putri.phoebe.presentation.components.FaceDecoration;
import com.putri.phoebe.presentation.components.GraphicFaceTracker;
import com.putri.phoebe.presentation.components.GraphicOverlay;
import com.putri.phoebe.presentation.components.Sticker;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import rx.Subscription;
import rx.functions.Action1;
import rx.subjects.PublishSubject;

public class MainActivity extends AppCompatActivity {

    private static final int RESULT_LOAD_IMG = 1;

    private static final int RC_HANDLE_GMS = 9001;

    private static final int RC_HANDLE_CAMERA_PERM = 2;

    private CameraSource cameraSource;

    private Subscription subscription;

    private PublishSubject<Boolean> publishSubject;

    private boolean isOpenCamera;

    private PermissionListener cameraPermissionListener;

    private FaceDecoration faceDecoration;

    @BindView(R.id.btn_pick_an_image)
    ImageView btnPickAnImage;

    @BindView(R.id.imageView)
    ImageView imgView;

    @BindView(R.id.btn_open_camera)
    ImageView btnOpenCamera;

    @BindView(R.id.preview)
    CameraComponentPreview cameraComponentPreview;

    @BindView(R.id.faceOverlay)
    GraphicOverlay graphicOverlay;

    @BindView(R.id.activity_main)
    CoordinatorLayout rootView;

    @BindView(R.id.tvTakePicture)
    TextView tvTakePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initPermissionListeners();

        Sticker sticker = new Sticker(this);
        faceDecoration = new FaceDecoration(sticker);

        openCamera();

        publishSubject = PublishSubject.create();
        subscription = publishSubject.subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {

            }
        });
    }

    private void showGalleryMode() {
        imgView.setVisibility(View.VISIBLE);
        cameraComponentPreview.setVisibility(View.GONE);
        tvTakePicture.setVisibility(View.GONE);
    }

    private void showLiveCameraMode() {
        imgView.setVisibility(View.GONE);
        cameraComponentPreview.setVisibility(View.VISIBLE);
        tvTakePicture.setVisibility(View.VISIBLE);
    }

    private void initPermissionListeners() {
        cameraPermissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {
                if(cameraSource == null) {
                    createCameraSource(CameraSource.CAMERA_FACING_FRONT);
                }
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, final PermissionToken token) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    new AlertDialog.Builder(MainActivity.this).setTitle("")
                            .setMessage("Camera access is needed to take pictures of you.")
                            .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                @Override public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    token.cancelPermissionRequest();
                                }
                            })
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    token.continuePermissionRequest();
                                }
                            })
                            .setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override public void onDismiss(DialogInterface dialog) {
                                    token.cancelPermissionRequest();
                                }
                            })
                            .show();
                }
            }
        };
    }

    @OnClick(R.id.btn_open_camera)
    public void openCamera() {
        if(!isOpenCamera) {
            showLiveCameraMode();
            isOpenCamera = true;

            if (Dexter.isRequestOngoing()) {
                return;
            }
            Dexter.checkPermission(cameraPermissionListener, Manifest.permission.CAMERA);
        } else {
            int currentFacing = cameraSource.getCameraFacing();
            cameraSource.stop();

            if(currentFacing == CameraSource.CAMERA_FACING_FRONT) {
                createCameraSource(CameraSource.CAMERA_FACING_BACK);
            } else {
                createCameraSource(CameraSource.CAMERA_FACING_FRONT);
            }
        }
    }

    @OnClick(R.id.btn_pick_an_image)
    public void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }

    @OnLongClick(R.id.preview)
    public boolean takePicture() {
        cameraSource.takePicture(new CameraSource.ShutterCallback() {
            @Override
            public void onShutter() {

            }
        }, new CameraSource.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] bytes) {
                cameraSource.stop();
                Toast.makeText(getApplicationContext(), "Picture Taken", Toast.LENGTH_LONG).show();
            }
        });

        return true;
    }

    private void createCameraSource(int facing) {
        Context context = getApplicationContext();
        FaceDetector faceDetector = new FaceDetector.Builder(context)
                .setClassificationType(FaceDetector.ALL_CLASSIFICATIONS)
                .build();

        faceDetector.setProcessor(new MultiProcessor.Builder<>(new GraphicFaceTrackerFactory()).build());

        if(!faceDetector.isOperational()) {
            Log.w("Phoebe", "Face Detector not available");
        }

        cameraSource = new CameraSource.Builder(context, faceDetector)
                .setRequestedPreviewSize(640, 480)
                .setFacing(facing)
                .setRequestedFps(30.0f)
                .build();

        graphicOverlay.setFacing(facing);

        startCameraSource();
    }

    private void startCameraSource() {
        int code = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(getApplicationContext());
        if(code != ConnectionResult.SUCCESS) {
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(this, code, RC_HANDLE_GMS);
            dialog.show();
        }

        if(cameraSource != null) {
            try {
                cameraComponentPreview.start(cameraSource, graphicOverlay);
            } catch (IOException e) {
                cameraSource.release();
                cameraSource = null;
            }
        }
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        isOpenCamera = false;
        showGalleryMode();

        Dexter.checkPermission(new PermissionListener() {
            @Override public void onPermissionGranted(PermissionGrantedResponse response) {
                if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                        && null != data) {
                    Bitmap selectedImage = getSelectedImage(data);
                    startDetectFace(selectedImage);
                    publishSubject.onNext(true);
                }
            }
            @Override public void onPermissionDenied(PermissionDeniedResponse response) {
                if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                        && null != data) {
                    Bitmap selectedImage = getSelectedImageForMarshmallow(data);
                    startDetectFace(selectedImage);
                    publishSubject.onNext(true);
                }
            }
            @Override public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {/* ... */}
        }, Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    private void startDetectFace(Bitmap selectedImage) {
        FaceDetector faceDetector = new FaceDetector.Builder(getApplicationContext())
                .setTrackingEnabled(false)
                .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                .build();

        if(!faceDetector.isOperational()) {
            Toast.makeText(this, getString(R.string.detect_face_error_message), Toast.LENGTH_LONG);
            return;
        }

        Bitmap tempBitmap = Bitmap.createBitmap(selectedImage.getWidth(), selectedImage.getHeight(), Bitmap.Config.RGB_565);
        Canvas tempCanvas = new Canvas(tempBitmap);
        tempCanvas.drawBitmap(selectedImage, 0, 0, null);

        Frame frame = new Frame.Builder().setBitmap(selectedImage).build();
        SparseArray<Face> faces = faceDetector.detect(frame);
        faceDecoration.startDecorate(faces, tempCanvas);
        imgView.setImageDrawable(new BitmapDrawable(getResources(), tempBitmap));
    }

    private String getPicturePath(Intent data) {
        Uri selectedImage = data.getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(selectedImage,
                filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex =  cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();

        return picturePath;
    }

    private Bitmap getSelectedImageForMarshmallow(Intent data) {
        InputStream inputStream = null;
        try {
            inputStream = new URL(getPicturePath(data)).openStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return BitmapFactory.decodeStream(inputStream);
    }

    private Bitmap getSelectedImage(Intent data) {
        return BitmapFactory.decodeFile(getPicturePath(data));
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(isOpenCamera) {
            startCameraSource();
            showLiveCameraMode();
        } else {
            showGalleryMode();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(isOpenCamera) {
            cameraComponentPreview.stop();
        } else {
            showGalleryMode();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        subscription.unsubscribe();
        if(cameraSource != null) {
            cameraSource.release();
        }
    }

    public class GraphicFaceTrackerFactory implements MultiProcessor.Factory<Face> {

        @Override
        public Tracker<Face> create(Face face) {
            return new GraphicFaceTracker(graphicOverlay);
        }
    }
}
