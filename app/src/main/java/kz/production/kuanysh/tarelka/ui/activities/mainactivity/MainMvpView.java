package kz.production.kuanysh.tarelka.ui.activities.mainactivity;

import kz.production.kuanysh.tarelka.ui.base.MvpView;

/**
 * Created by User on 26.06.2018.
 */

public interface MainMvpView extends MvpView {

    void openChooseAimActivity();

    void openMainTaskFragment();

    void openChatFragment();

    void openProfileFragment();

    void openProgressFragment();

}
