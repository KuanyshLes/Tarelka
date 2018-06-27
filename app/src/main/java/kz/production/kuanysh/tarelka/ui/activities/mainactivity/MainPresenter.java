package kz.production.kuanysh.tarelka.ui.activities.mainactivity;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import kz.production.kuanysh.tarelka.data.DataManager;
import kz.production.kuanysh.tarelka.ui.base.BasePresenter;
import kz.production.kuanysh.tarelka.utils.rx.SchedulerProvider;

/**
 * Created by User on 26.06.2018.
 */

public class MainPresenter <V extends MainMvpView> extends BasePresenter<V>
        implements MainMvpPresenter<V> {

    @Inject
    public MainPresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onMainCLick() {
        getMvpView().openMainTaskFragment();
    }

    @Override
    public void onChatClick() {
        getMvpView().openChatFragment();
    }

    @Override
    public void onProfileClick() {
        getMvpView().openProfileFragment();
    }

    @Override
    public void onProgressClick() {
        getMvpView().openProgressFragment();
    }
}
