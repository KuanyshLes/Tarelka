/*
 * Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://mindorks.com/license/apache-v2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package kz.production.kuanysh.tarelka.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import kz.production.kuanysh.tarelka.data.DataManager;
import kz.production.kuanysh.tarelka.di.ApplicationContext;
import kz.production.kuanysh.tarelka.di.PreferenceInfo;
import kz.production.kuanysh.tarelka.utils.AppConstants;


/**
 * Created by janisharali on 27/01/17.
 */

@Singleton
public class AppPreferencesHelper implements PreferencesHelper {

    private static final String PREF_KEY_FIREBASE_TOKEN= "PREF_KEY_FIREBASE_TOKEN";
    private static final String PREF_KEY_USER_LOGGED_IN_MODE = "PREF_KEY_USER_LOGGED_IN_MODE";
    private static final String PREF_KEY_CURRENT_USER_ID = "PREF_KEY_CURRENT_USER_ID";
    private static final String PREF_KEY_ACCESS_TOKEN = "PREF_KEY_ACCESS_TOKEN";

    private static final String PREF_KEY_CURRENT_USER_NAME = "PREF_KEY_CURRENT_USER_NAME";
    private static final String PREF_KEY_CURRENT_USER_STATUS= "PREF_KEY_CURRENT_USER_STATUS";
    private static final String PREF_KEY_CURRENT_USER_IMAGE= "PREF_KEY_CURRENT_USER_IMAGE";
    private static final String PREF_KEY_CURRENT_USER_AGE= "PREF_KEY_CURRENT_USER_AGE";
    private static final String PREF_KEY_CURRENT_USER_WEIGHT= "PREF_KEY_CURRENT_USER_WEIGHT";
    private static final String PREF_KEY_CURRENT_USER_AIMS= "PREF_KEY_CURRENT_USER_AIMS";
    private static final String PREF_KEY_CURRENT_USER_PHONE= "PREF_KEY_CURRENT_USER_PHONE";
    private static final String PREF_KEY_CURRENT_USER_HEIGHT= "PREF_KEY_CURRENT_USER_HEIGHT";



    private final SharedPreferences mPrefs;

    @Inject
    public AppPreferencesHelper(@ApplicationContext Context context,
                                @PreferenceInfo String prefFileName) {
        mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }

    @Override
    public Long getCurrentUserId() {
        long userId = mPrefs.getLong(PREF_KEY_CURRENT_USER_ID, AppConstants.NULL_INDEX);
        return userId == AppConstants.NULL_INDEX ? null : userId;
    }

    @Override
    public void setCurrentUserId(Integer userId) {
        long id = userId == null ? AppConstants.NULL_INDEX : userId;
        mPrefs.edit().putLong(PREF_KEY_CURRENT_USER_ID, id).apply();
    }

    @Override
    public String getCurrentUserName() {
        return mPrefs.getString(PREF_KEY_CURRENT_USER_NAME, null);
    }

    @Override
    public void setCurrentUserName(String userName) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_USER_NAME, userName).apply();
    }


    @Override
    public String getFirebaseToken() {
        return mPrefs.getString(PREF_KEY_FIREBASE_TOKEN, null);
    }

    @Override
    public void setFirebaseToken(String refreshedToken) {
        mPrefs.edit().putString(PREF_KEY_FIREBASE_TOKEN, refreshedToken).apply();
    }

    @Override
    public int getCurrentUserLoggedInMode() {
        return mPrefs.getInt(PREF_KEY_USER_LOGGED_IN_MODE,
                DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT.getType());
    }

    @Override
    public void setCurrentUserLoggedInMode(DataManager.LoggedInMode mode) {
        mPrefs.edit().putInt(PREF_KEY_USER_LOGGED_IN_MODE, mode.getType()).apply();
    }

    @Override
    public String getAccessToken() {
        return mPrefs.getString(PREF_KEY_ACCESS_TOKEN, null);
    }

    @Override
    public void setAccessToken(String accessToken) {
        mPrefs.edit().putString(PREF_KEY_ACCESS_TOKEN, accessToken).apply();
    }

    @Override
    public String getPhoneNumber() {
        return mPrefs.getString(PREF_KEY_CURRENT_USER_PHONE, null);
    }

    @Override
    public void setPhoneNumber(String phone) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_USER_PHONE, phone).apply();
    }

    @Override
    public String getImage() {
        return mPrefs.getString(PREF_KEY_CURRENT_USER_IMAGE, null);
    }

    @Override
    public void setImage(String phone) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_USER_IMAGE, phone).apply();
    }


    @Override
    public String getAge() {
        return mPrefs.getString(PREF_KEY_CURRENT_USER_AGE, null);
    }

    @Override
    public void setAge(String phone) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_USER_AGE, phone).apply();
    }

    @Override
    public String getWeight() {
        return mPrefs.getString(PREF_KEY_CURRENT_USER_WEIGHT, null);
    }

    @Override
    public void setWeight(String phone) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_USER_WEIGHT, phone).apply();
    }

    @Override
    public String getAims() {
        return mPrefs.getString(PREF_KEY_CURRENT_USER_AIMS, null);
    }

    @Override
    public void setAims(String phone) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_USER_AIMS, phone).apply();
    }

    @Override
    public String getHeight() {
        return mPrefs.getString(PREF_KEY_CURRENT_USER_HEIGHT, null);
    }

    @Override
    public void setHeight(String height) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_USER_HEIGHT, height).apply();
    }

    @Override
    public String getCurrentStatus() {
        return mPrefs.getString(PREF_KEY_CURRENT_USER_STATUS, null);
    }

    @Override
    public void setCurrentStatus(String status) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_USER_STATUS, status).apply();
    }
}
