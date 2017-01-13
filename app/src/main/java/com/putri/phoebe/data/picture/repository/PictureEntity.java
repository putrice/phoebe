package com.putri.phoebe.data.picture.repository;

/**
 * Created by putri on 1/13/17.
 */

public class PictureEntity {

    private String encodedImage;

    public PictureEntity(String encodedImage) {
        this.encodedImage = encodedImage;
    }

    public String getEncodedImage() {
        return encodedImage;
    }

}
