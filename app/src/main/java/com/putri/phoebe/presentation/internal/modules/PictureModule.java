package com.putri.phoebe.presentation.internal.modules;

import com.putri.phoebe.domain.picture.interactor.GetPictureUseCase;
import com.putri.phoebe.domain.picture.interactor.SavePictureUseCase;
import com.putri.phoebe.domain.picture.repository.PictureRepository;
import com.putri.phoebe.presentation.internal.PerActivity;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by putri on 1/13/17.
 */
@Module
public class PictureModule {

    public PictureModule() {}

    @Provides
    @PerActivity
    @Named("savePicture")
    SavePictureUseCase provideSavePictureUseCase(PictureRepository pictureRepository) {
        return new SavePictureUseCase(pictureRepository);
    }

    @Provides
    @PerActivity
    @Named("getPicture")
    GetPictureUseCase provideGetPictureUseCase(PictureRepository pictureRepository) {
        return new GetPictureUseCase(pictureRepository);
    }

}
