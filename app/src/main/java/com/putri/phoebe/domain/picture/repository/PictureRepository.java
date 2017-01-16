package com.putri.phoebe.domain.picture.repository;

import rx.Observable;

/**
 * Created by putri on 1/13/17.
 */

public interface PictureRepository {

    Observable<Boolean> savePicture(String encodedImages);

    Observable<String> getPicture();

}
