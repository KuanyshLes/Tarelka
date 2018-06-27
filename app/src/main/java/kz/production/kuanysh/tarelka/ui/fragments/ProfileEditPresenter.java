package kz.production.kuanysh.tarelka.ui.fragments;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import kz.production.kuanysh.tarelka.data.DataManager;
import kz.production.kuanysh.tarelka.ui.base.BasePresenter;
import kz.production.kuanysh.tarelka.utils.rx.SchedulerProvider;

/**
 * Created by User on 26.06.2018.
 */

public class ProfileEditPresenter<V extends ProfileEditMvpView> extends BasePresenter<V>
        implements ProfileEditMvpPresenter<V> {

    @Inject
    public ProfileEditPresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onSaveClick() {
        getMvpView().openProfileFragment();
    }

    @Override
    public void onAddPhotoClick() {
        getMvpView().openImagePicker();
    }
}
