package kz.production.kuanysh.tarelka.ui.welcome;

import kz.production.kuanysh.tarelka.di.PerActivity;
import kz.production.kuanysh.tarelka.ui.base.MvpPresenter;

/**
 * Created by User on 27.06.2018.
 */
@PerActivity
public interface SplashMvpPresenter <V extends SplashMvpView> extends MvpPresenter<V> {
}
