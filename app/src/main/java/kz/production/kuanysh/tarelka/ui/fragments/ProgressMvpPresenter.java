package kz.production.kuanysh.tarelka.ui.fragments;

import kz.production.kuanysh.tarelka.di.PerActivity;
import kz.production.kuanysh.tarelka.ui.base.MvpPresenter;

/**
 * Created by User on 26.06.2018.
 */
@PerActivity
public interface ProgressMvpPresenter<V extends ProgressMvpView> extends MvpPresenter<V> {

    void onTestItemClick(int position);

    void onViewPrepared();


}
