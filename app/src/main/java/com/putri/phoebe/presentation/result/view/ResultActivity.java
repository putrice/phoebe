package com.putri.phoebe.presentation.result.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Button;
import android.widget.ImageView;
import com.putri.phoebe.R;
import com.putri.phoebe.presentation.base.BaseActivity;
import com.putri.phoebe.presentation.internal.components.DaggerPictureComponent;
import com.putri.phoebe.presentation.internal.components.PictureComponent;
import com.putri.phoebe.presentation.internal.modules.PictureModule;
import com.putri.phoebe.presentation.result.ResultContract;
import com.putri.phoebe.presentation.result.presenter.ResultPresenter;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by putri on 1/11/17.
 */

public class ResultActivity extends BaseActivity implements ResultContract.View {

    @BindView(R.id.iv_result)
    ImageView ivResult;

    @BindView(R.id.btn_share)
    Button btnShare;

    @Inject
    ResultPresenter resultPresenter;

    private PictureComponent pictureComponent;

    @Override
    public void setup() {
        initInjector();
        resultPresenter.setView(this);
        resultPresenter.getPicture();
    }

    private void initInjector() {
        if(pictureComponent == null) {
            pictureComponent = DaggerPictureComponent.builder()
                    .applicationComponent(getApplicationComponent())
                    .activityModule(getActivityModule())
                    .pictureModule(new PictureModule())
                    .build();
        }
        pictureComponent.inject(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_result;
    }

    @Override
    public void showGetPictureSuccess(String encodedImage) {
        byte[] imageBytes = Base64.decode(encodedImage, 0);
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        ivResult.setImageBitmap(bitmap);
    }

    @Override
    public void showGetPictureFailed() {
        finish();
    }
}
