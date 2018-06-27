package kz.production.kuanysh.tarelka.ui.activities;

import kz.production.kuanysh.tarelka.di.PerActivity;
import kz.production.kuanysh.tarelka.ui.base.MvpPresenter;

/**
 * Created by User on 26.06.2018.
 */
@PerActivity
public interface TaskDetailMvpPresenter <V extends TaskDetailMvpView> extends MvpPresenter<V> {

    void onBackClick();
}
