package com.putri.phoebe.presentation.internal.components;

import com.putri.phoebe.presentation.MainActivity;
import com.putri.phoebe.presentation.internal.PerActivity;
import com.putri.phoebe.presentation.internal.modules.ApplicationModule;
import com.putri.phoebe.presentation.internal.modules.PictureModule;
import dagger.Component;

/**
 * Created by putri on 1/13/17.
 */
@PerActivity
@Component(dependencies = ApplicationModule.class, modules = PictureModule.class)
public interface PictureComponent extends ActivityComponent {

    void inject(MainActivity mainActivity);

}
