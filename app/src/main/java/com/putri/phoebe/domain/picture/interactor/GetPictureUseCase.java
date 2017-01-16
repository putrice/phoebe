package com.putri.phoebe.domain.picture.interactor;

import com.putri.phoebe.domain.UseCase;
import com.putri.phoebe.domain.picture.repository.PictureRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by putri on 1/16/17.
 */

public class GetPictureUseCase extends UseCase {

    private PictureRepository pictureRepository;

    @Inject
    public GetPictureUseCase(PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return pictureRepository.getPicture();
    }
}
