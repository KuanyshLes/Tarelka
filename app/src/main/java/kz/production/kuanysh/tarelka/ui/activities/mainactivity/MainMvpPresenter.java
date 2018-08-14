package kz.production.kuanysh.tarelka.ui.activities.mainactivity;

import kz.production.kuanysh.tarelka.di.PerActivity;
import kz.production.kuanysh.tarelka.ui.base.MvpPresenter;

/**
 * Created by User on 26.06.2018.
 */
@PerActivity
public interface MainMvpPresenter <V extends MainMvpView> extends MvpPresenter<V> {

    void onMainCLick();

    void onViewPrepared();

    void onChatClick();

    void onProfileClick();

    void onProgressClick();
}
