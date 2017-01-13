package com.putri.phoebe.domain.picture;

/**
 * Created by putri on 1/13/17.
 */

public class Picture {

    private String encodedImage;

    public Picture(String encodedImage) {
        this.encodedImage = encodedImage;
    }

    public String getEncodedImage() {
        return encodedImage;
    }
}
