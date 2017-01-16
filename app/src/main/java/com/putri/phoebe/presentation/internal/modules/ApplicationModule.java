package com.putri.phoebe.presentation.internal.modules;

import android.app.Application;
import android.content.Context;

import com.putri.phoebe.data.picture.repository.PictureEntityRepository;
import com.putri.phoebe.domain.picture.repository.PictureRepository;
import com.putri.phoebe.presentation.internal.PerActivity;

import javax.inject.Singleton;

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
    @Singleton
    Context getApplicationContext() {
        return application;
    }

    @Provides
    @Singleton
    PictureRepository providePictureRepository(PictureEntityRepository pictureRepository) {
        return pictureRepository;
    }

}
