package com.putri.phoebe.data.picture.repository.local;

import android.content.Context;

import com.putri.phoebe.data.DataUtil;
import com.putri.phoebe.data.picture.mapper.PictureEntityMapper;
import com.putri.phoebe.domain.picture.repository.PictureRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * Created by putri on 1/13/17.
 */
@Singleton
public class PictureEntityLocalRepository implements PictureRepository {

    private Context context;

    private PictureEntityMapper pictureEntityMapper;

    @Inject
    public PictureEntityLocalRepository(Context context, PictureEntityMapper pictureEntityMapper) {
        this.context = context;
        this.pictureEntityMapper = pictureEntityMapper;
    }

    @Override
    public Observable<Boolean> savePicture(String encodedImages) {
        boolean result = true;

        try {
            DataUtil dataUtil = new DataUtil(context);
            dataUtil.init();
            dataUtil.savePicture(encodedImages);
        } catch (Exception e) {
            result = false;
        }

        return Observable.just(result);
    }

    @Override
    public Observable<String> getPicture() {
        String encodedImage;
        try {
            DataUtil dataUtil = new DataUtil(context);
            dataUtil.init();
            encodedImage = dataUtil.getPicture();
        } catch (Exception e) {
            encodedImage = null;
        }

        return Observable.just(encodedImage);
    }
}
