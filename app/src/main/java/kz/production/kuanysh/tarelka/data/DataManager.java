package kz.production.kuanysh.tarelka.data;

import io.reactivex.Observable;
import kz.production.kuanysh.tarelka.data.network.ApiHelper;
import kz.production.kuanysh.tarelka.data.prefs.PreferencesHelper;

/**
 * Created by User on 24.06.2018.
 */

public interface DataManager extends ApiHelper,PreferencesHelper {
    void updateApiHeader(Long userId, String accessToken);

    void setUserAsLoggedOut();



    void updateUserInfo(
            String accessToken,
            Long userId,
            LoggedInMode loggedInMode,
            String userName,
            String email,
            String profilePicPath);

    enum LoggedInMode {

        LOGGED_IN_MODE_LOGGED_OUT(0),
        LOGGED_IN_MODE_GOOGLE(1),
        LOGGED_IN_MODE_FB(2),
        LOGGED_IN_MODE_SERVER(3);

        private final int mType;

        LoggedInMode(int type) {
            mType = type;
        }

        public int getType() {
            return mType;
        }
    }
}
