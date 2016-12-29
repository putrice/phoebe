package com.putri.phoebe;

import android.app.Application;

import com.karumi.dexter.Dexter;

/**
 * Created by putri on 12/13/16.
 */

public class PhoebeApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Dexter.initialize(this);
    }
}
