package com.putri.phoebe.presentation.internal.modules;

import android.app.Application;
import android.content.Context;

import com.putri.phoebe.domain.picture.repository.PictureRepository;
import com.putri.phoebe.presentation.internal.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by putri on 1/13/17.
 */
@Module
public class ApplicationModule {

    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @PerActivity
    Context getApplicationContext() {
        return application;
    }

    @Provides
    @PerActivity
    PictureRepository providePictureRepository(PictureRepository pictureRepository) {
        return pictureRepository;
    }

}
