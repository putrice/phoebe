package com.putri.phoebe.presentation.home;

/**
 * Created by putri on 1/16/17.
 */

public interface HomeContract {

    interface View {

        void showSavePictureSuccess();

        void showSavePictureFailed();

    }

    interface Presenter {

        void savePicture(String encodedImage);

    }
}
