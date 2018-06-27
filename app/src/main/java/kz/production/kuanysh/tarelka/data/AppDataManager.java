package kz.production.kuanysh.tarelka.data;

import android.content.Context;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import kz.production.kuanysh.tarelka.data.network.ApiHeader;
import kz.production.kuanysh.tarelka.data.network.ApiHelper;
import kz.production.kuanysh.tarelka.data.prefs.PreferencesHelper;
import kz.production.kuanysh.tarelka.di.ApplicationContext;

/**
 * Created by User on 24.06.2018.
 */
@Singleton
public class AppDataManager implements DataManager {

    private static final String TAG = "AppDataManager";


    private final Context mContext;
    private final PreferencesHelper mPreferencesHelper;
    private final ApiHelper mApiHelper;

    @Inject
    public AppDataManager(@ApplicationContext Context context, PreferencesHelper preferencesHelper,
                          ApiHelper apiHelper) {
        mContext = context;
        mPreferencesHelper = preferencesHelper;
        mApiHelper = apiHelper;
    }

    @Override
    public ApiHeader getApiHeader() {
        return mApiHelper.getApiHeader();
    }

    @Override
    public int getCurrentUserLoggedInMode() {
        return mPreferencesHelper.getCurrentUserLoggedInMode();
    }

    @Override
    public void setCurrentUserLoggedInMode(LoggedInMode mode) {
        mPreferencesHelper.setCurrentUserLoggedInMode(mode);
    }

    @Override
    public Long getCurrentUserId() {
        return mPreferencesHelper.getCurrentUserId();
    }

    @Override
    public void setCurrentUserId(Long userId) {
        mPreferencesHelper.setCurrentUserId(userId);
    }

    @Override
    public String getCurrentUserName() {
        return mPreferencesHelper.getCurrentUserName();
    }

    @Override
    public void setCurrentUserName(String userName) {
        mPreferencesHelper.setCurrentUserName(userName);
    }

    @Override
    public String getCurrentStatus() {
        return mPreferencesHelper.getCurrentStatus();
    }

    @Override
    public void setCurrentStatus(String status) {
        mPreferencesHelper.setCurrentStatus(status);
    }


    @Override
    public String getAccessToken() {
        return mPreferencesHelper.getAccessToken();
    }

    @Override
    public void setAccessToken(String accessToken) {
        mPreferencesHelper.setAccessToken(accessToken);
        mApiHelper.getApiHeader().getProtectedApiHeader().setAccessToken(accessToken);
    }

    @Override
    public String getPhoneNumber() {
        return mPreferencesHelper.getPhoneNumber();
    }

    @Override
    public void setPhoneNumber(String phone) {
        mPreferencesHelper.setPhoneNumber(phone);
    }

    @Override
    public void updateApiHeader(Long userId, String accessToken) {
        mApiHelper.getApiHeader();
    }

    @Override
    public void setUserAsLoggedOut() {
        updateUserInfo(
                null,
                null,
                DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT,
                null,
                null,
                null);
    }


    @Override
    public void updateUserInfo(String accessToken,
                               Long userId,
                               LoggedInMode loggedInMode,
                               String userName,
                               String phone,
                               String status) {
        setAccessToken(accessToken);
        setCurrentUserId(userId);
        setCurrentUserLoggedInMode(loggedInMode);
        setCurrentUserName(userName);
        setPhoneNumber(phone);
        setCurrentStatus(status);

        updateApiHeader(userId, accessToken);
    }
}
