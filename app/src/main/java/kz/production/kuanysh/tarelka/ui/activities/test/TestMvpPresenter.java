package kz.production.kuanysh.tarelka.ui.activities.test;

import kz.production.kuanysh.tarelka.di.PerActivity;
import kz.production.kuanysh.tarelka.ui.base.MvpPresenter;

/**
 * Created by User on 26.06.2018.
 */
@PerActivity
public interface TestMvpPresenter <V extends TestMvpView> extends MvpPresenter<V> {

    void onAnswerClick();

    void onNextClick();

    void onCancellClick();
}
