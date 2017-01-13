package com.putri.phoebe.presentation.internal.components;

import com.putri.phoebe.presentation.BaseActivity;
import com.putri.phoebe.presentation.internal.PerActivity;
import com.putri.phoebe.presentation.internal.modules.ActivityModule;

import dagger.Component;

/**
 * Created by putri on 1/13/17.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    BaseActivity activity();

}
