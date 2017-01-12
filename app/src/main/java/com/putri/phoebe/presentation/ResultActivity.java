package com.putri.phoebe.presentation;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import com.putri.phoebe.R;
import butterknife.BindView;

/**
 * Created by putri on 1/11/17.
 */

public class ResultActivity extends BaseActivity {

    @BindView(R.id.iv_result)
    ImageView ivResult;

    @BindView(R.id.btn_share)
    Button btnShare;

    @Override
    public void setup() {
        Bundle extras = getIntent().getExtras();
        byte[] bytes = extras.getByteArray("result");
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

        ivResult.setImageBitmap(bitmap);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_result;
    }
}
