package kz.production.kuanysh.tarelka.ui.fragments;

import android.content.Context;
import android.net.Uri;

import kz.production.kuanysh.tarelka.di.PerActivity;
import kz.production.kuanysh.tarelka.ui.base.MvpPresenter;
import okhttp3.MultipartBody;

/**
 * Created by User on 26.06.2018.
 */
@PerActivity
public interface ChatMvpPresenter<V extends ChatMvpView> extends MvpPresenter<V> {

    void onSendClick(String message);

    void onSendImage(Uri uri, String path, Context context);

    void onImageclick();

    void onMailClick();

    void onViewPrepared();



}
