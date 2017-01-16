package com.putri.phoebe.presentation.internal.components;

import com.putri.phoebe.presentation.home.view.HomeActivity;
import com.putri.phoebe.presentation.internal.modules.ActivityModule;
import com.putri.phoebe.presentation.internal.PerActivity;
import com.putri.phoebe.presentation.internal.modules.PictureModule;
import com.putri.phoebe.presentation.result.view.ResultActivity;

import dagger.Component;

/**
 * Created by putri on 1/13/17.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, PictureModule.class})
public interface PictureComponent extends ActivityComponent {

    void inject(HomeActivity homeActivity);

    void inject(ResultActivity resultActivity);

}
