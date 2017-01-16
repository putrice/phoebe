package com.putri.phoebe;

import android.app.Application;

import com.karumi.dexter.Dexter;
import com.putri.phoebe.presentation.internal.components.ApplicationComponent;
import com.putri.phoebe.presentation.internal.components.DaggerApplicationComponent;
import com.putri.phoebe.presentation.internal.modules.ApplicationModule;

/**
 * Created by putri on 12/13/16.
 */

public class PhoebeApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        Dexter.initialize(this);
        initializeInjector();
    }

    private void initializeInjector() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
