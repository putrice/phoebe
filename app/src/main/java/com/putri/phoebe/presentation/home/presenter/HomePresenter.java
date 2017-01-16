package com.putri.phoebe.presentation.home.presenter;

import com.putri.phoebe.domain.DefaultSubscriber;
import com.putri.phoebe.domain.picture.interactor.SavePictureUseCase;
import com.putri.phoebe.presentation.base.BasePresenter;
import com.putri.phoebe.presentation.home.HomeContract;
import com.putri.phoebe.presentation.internal.PerActivity;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by putri on 1/16/17.
 */
@PerActivity
public class HomePresenter extends BasePresenter implements HomeContract.Presenter {

    private HomeContract.View view;

    private final SavePictureUseCase savePictureUseCase;

    @Inject
    public HomePresenter(@Named("savePicture") SavePictureUseCase savePictureUseCase) {
        this.savePictureUseCase = savePictureUseCase;
    }

    public void setView(HomeContract.View view) {
        this.view = view;
    }

    @Override
    public void savePicture(String encodedImage) {
        savePictureUseCase.setEncodedImages(encodedImage);
        savePictureUseCase.execute(new DefaultSubscriber<Boolean>() {
            @Override
            public void onNext(Boolean result) {
                if(result) {
                    view.showSavePictureSuccess();
                } else {
                    view.showSavePictureFailed();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        savePictureUseCase.clearAllSubscription();
    }
}
