package com.putri.phoebe.data.picture.repository;

import com.putri.phoebe.data.picture.repository.local.PictureEntityLocalRepository;
import com.putri.phoebe.domain.picture.repository.PictureRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * Created by putri on 1/13/17.
 */
@Singleton
public class PictureEntityRepository implements PictureRepository {

    private PictureEntityLocalRepository pictureEntityLocalRepository;

    @Inject
    public PictureEntityRepository(PictureEntityLocalRepository pictureEntityLocalRepository) {
        this.pictureEntityLocalRepository = pictureEntityLocalRepository;
    }

    @Override
    public Observable<Boolean> savePicture(String encodedImages) {
        return pictureEntityLocalRepository.savePicture(encodedImages);
    }

    @Override
    public Observable<String> getPicture() {
        return pictureEntityLocalRepository.getPicture();
    }
}
