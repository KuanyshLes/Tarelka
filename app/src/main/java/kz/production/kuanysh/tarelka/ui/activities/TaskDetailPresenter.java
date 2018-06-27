package kz.production.kuanysh.tarelka.ui.activities;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import kz.production.kuanysh.tarelka.data.DataManager;
import kz.production.kuanysh.tarelka.ui.base.BasePresenter;
import kz.production.kuanysh.tarelka.utils.rx.SchedulerProvider;

/**
 * Created by User on 26.06.2018.
 */

public class TaskDetailPresenter <V extends TaskDetailMvpView> extends BasePresenter<V>
        implements TaskDetailMvpPresenter<V>  {


    @Inject
    public TaskDetailPresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onBackClick() {
        getMvpView().openMainTaskFragment();
    }
}
