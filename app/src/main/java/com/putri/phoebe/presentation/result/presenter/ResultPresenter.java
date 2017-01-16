package com.putri.phoebe.presentation.result.presenter;

import com.putri.phoebe.domain.DefaultSubscriber;
import com.putri.phoebe.domain.picture.interactor.GetPictureUseCase;
import com.putri.phoebe.presentation.base.BasePresenter;
import com.putri.phoebe.presentation.internal.PerActivity;
import com.putri.phoebe.presentation.result.ResultContract;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by putri on 1/16/17.
 */
@PerActivity
public class ResultPresenter extends BasePresenter implements ResultContract.Presenter {

    private ResultContract.View view;

    private final GetPictureUseCase getPictureUseCase;

    @Inject
    public ResultPresenter(@Named("getPicture") GetPictureUseCase getPictureUseCase) {
        this.getPictureUseCase = getPictureUseCase;
    }

    public void setView(ResultContract.View view) {
        this.view = view;
    }

    @Override
    public void getPicture() {
        getPictureUseCase.execute(new DefaultSubscriber<String>() {
            @Override
            public void onNext(String result) {
                if(result != null) {
                    view.showGetPictureSuccess(result);
                } else {
                    view.showGetPictureFailed();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        getPictureUseCase.clearAllSubscription();
    }
}
