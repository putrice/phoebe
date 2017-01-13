package com.putri.phoebe.data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by putri on 1/12/17.
 */

public class DataUtil {

    private static final String PREF_NAME = "PhoebeSharedPref";

    private static final String IMAGES = "images";

    private Context context;

    private SharedPreferences sharedPreferences;

    private SharedPreferences.Editor editor;

    public DataUtil(Context context) {
        this.context = context;
    }

    public void init() {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void savePicture(String encodedImage) {
        editor.putString(IMAGES, encodedImage);
        editor.commit();
    }

    public String getPicture() {
        return sharedPreferences.getString(IMAGES, null);
    }

    public void clearPicture() {
        editor.remove(IMAGES);
        editor.commit();
    }
}
