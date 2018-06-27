package kz.production.kuanysh.tarelka.ui.fragments;

import kz.production.kuanysh.tarelka.ui.base.MvpView;

/**
 * Created by User on 26.06.2018.
 */

public interface SendMessageMvpView extends MvpView {

    void openChatFragment();

    void sendMessageAsIntent();
}
