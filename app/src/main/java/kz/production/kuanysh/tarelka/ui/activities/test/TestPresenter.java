package kz.production.kuanysh.tarelka.ui.activities.test;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import kz.production.kuanysh.tarelka.data.DataManager;
import kz.production.kuanysh.tarelka.ui.base.BasePresenter;
import kz.production.kuanysh.tarelka.utils.rx.SchedulerProvider;

/**
 * Created by User on 26.06.2018.
 */

public class TestPresenter<V extends TestMvpView> extends BasePresenter<V>
        implements TestMvpPresenter<V> {

    @Inject
    public TestPresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onAnswerClick() {

    }

    @Override
    public void onNextClick() {

    }

    @Override
    public void onCancellClick() {

    }
}
