package com.putri.phoebe.domain.picture.interactor;


import com.putri.phoebe.domain.UseCase;
import com.putri.phoebe.domain.picture.repository.PictureRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by putri on 1/12/17.
 */

public class SavePictureUseCase extends UseCase {

    private PictureRepository pictureRepository;

    private String encodedImages;

    @Inject
    public SavePictureUseCase(PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;
    }

    public void setEncodedImages(String encodedImages) {
        this.encodedImages = encodedImages;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return null;
    }
}
