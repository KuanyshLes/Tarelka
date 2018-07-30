package kz.production.kuanysh.tarelka.ui.fragments;

import kz.production.kuanysh.tarelka.ui.base.MvpView;

/**
 * Created by User on 26.06.2018.
 */

public interface ProfileMvpView extends MvpView {

    void updateInfo(String usernameText ,String statusText,String phoneText,String ageText,String weightText,
                    String aimsText,String imageText,String heightText);

    void openEditFragment();

    void openDialog();

    void openLoginActivity();

}
