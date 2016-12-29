package com.putri.phoebe.data;

import android.database.Observable;

/**
 * Created by putri on 12/7/16.
 */

public interface PersonRepository {

    Observable<Person> getDetectedPerson();

}