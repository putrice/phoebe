package com.putri.phoebe.presentation.internal.components;

import android.content.Context;

import com.putri.phoebe.domain.picture.repository.PictureRepository;
import com.putri.phoebe.presentation.base.BaseActivity;
import com.putri.phoebe.presentation.internal.modules.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by putri on 1/13/17.
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(BaseActivity activity);

    Context context();

    PictureRepository pictureRepository();

}
