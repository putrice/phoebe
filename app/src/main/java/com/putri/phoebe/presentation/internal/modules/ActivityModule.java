package com.putri.phoebe.presentation.internal.modules;

import com.putri.phoebe.presentation.BaseActivity;
import com.putri.phoebe.presentation.internal.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by putri on 1/13/17.
 */
@Module
public class ActivityModule {

    private final BaseActivity baseActivity;

    public ActivityModule(BaseActivity baseActivity) {
        this.baseActivity = baseActivity;
    }

    @Provides
    @PerActivity
    BaseActivity activity() {
        return baseActivity;
    }

}
