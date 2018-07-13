package kz.production.kuanysh.tarelka.ui.fragments;

import android.graphics.Bitmap;

import java.io.File;

import kz.production.kuanysh.tarelka.di.PerActivity;
import kz.production.kuanysh.tarelka.ui.base.MvpPresenter;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by User on 26.06.2018.
 */
@PerActivity
public interface ProfileEditMvpPresenter<V extends ProfileEditMvpView> extends MvpPresenter<V> {

    void onSaveClick(MultipartBody.Part image64, String name, String age, String weight,int height);

    void onAddPhotoClick();
}
