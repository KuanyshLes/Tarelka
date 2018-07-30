package kz.production.kuanysh.tarelka.ui.fragments;

import android.content.Context;
import android.net.Uri;

import java.util.List;

import kz.production.kuanysh.tarelka.di.PerActivity;
import kz.production.kuanysh.tarelka.ui.base.MvpPresenter;
import okhttp3.MultipartBody;

/**
 * Created by User on 26.06.2018.
 */
@PerActivity
public interface ChatMvpPresenter<V extends ChatMvpView> extends MvpPresenter<V> {

    void onSendClick(String message,int currentPage);

    void onSendImage(List<Uri> uri, List<String> path, Context context, int currentPage);

    void onImageclick();

    void onMailClick();

    void onViewPrepared(int page);



}
