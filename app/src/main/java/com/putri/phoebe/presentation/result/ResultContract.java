package com.putri.phoebe.presentation.result;

/**
 * Created by putri on 1/16/17.
 */

public interface ResultContract {

    interface View {

        void showGetPictureSuccess(String encodedImage);

        void showGetPictureFailed();

    }

    interface Presenter {

        void getPicture();

    }
}
