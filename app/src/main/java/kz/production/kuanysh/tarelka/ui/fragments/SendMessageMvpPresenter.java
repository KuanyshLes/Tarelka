package kz.production.kuanysh.tarelka.ui.fragments;

import kz.production.kuanysh.tarelka.di.PerActivity;
import kz.production.kuanysh.tarelka.ui.base.MvpPresenter;

/**
 * Created by User on 26.06.2018.
 */
@PerActivity
public interface SendMessageMvpPresenter <V extends SendMessageMvpView> extends MvpPresenter<V> {

    void onBackClick();

    void onSendAsSocialClick();
}
