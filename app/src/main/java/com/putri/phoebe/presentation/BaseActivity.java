package com.putri.phoebe.presentation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by putri on 1/11/17.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        unbinder = ButterKnife.bind(this);
        setup();
    }

    public abstract void setup();

    public abstract int getLayout();

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }
}
