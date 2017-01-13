package com.putri.phoebe.data.picture.mapper;

import com.putri.phoebe.data.picture.repository.PictureEntity;
import com.putri.phoebe.domain.picture.Picture;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by putri on 1/13/17.
 */
@Singleton
public class PictureEntityMapper {

    @Inject
    public PictureEntityMapper() {}

    public Picture transform(PictureEntity pictureEntity) {
        return new Picture(pictureEntity.getEncodedImage());
    }

}
