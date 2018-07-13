package kz.production.kuanysh.tarelka.ui.fragments;

import com.androidnetworking.error.ANError;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import kz.production.kuanysh.tarelka.data.DataManager;
import kz.production.kuanysh.tarelka.data.network.model.main.Main;
import kz.production.kuanysh.tarelka.ui.base.BasePresenter;
import kz.production.kuanysh.tarelka.utils.rx.SchedulerProvider;

/**
 * Created by User on 26.06.2018.
 */

public class MainTaskPresenter<V extends MainTaskMvpView> extends BasePresenter<V>
        implements MainTaskMvpPresenter<V> {

    @Inject
    public MainTaskPresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onItemClick(int position) {
        getMvpView().openTaskDetailActivity(position);
    }

    @Override
    public void onViewPrepared() {
        //getMvpView().showLoading();
        getCompositeDisposable().add(getDataManager()
                .getApiHelper().getMainTasks()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<Main>() {
                    @Override
                    public void accept(@NonNull Main blogResponse)
                            throws Exception {
                        getMvpView().hideLoading();
                        getMvpView().showMessage("Tasks get successfully!");
                        getMvpView().updateAimsList(blogResponse.getResult());

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable)
                            throws Exception {
                        if (!isViewAttached()) {
                            return;
                        }
                        getMvpView().hideLoading();

                        getMvpView().showMessage("Tasks get error!");


                        // handle the error here
                        if (throwable instanceof ANError) {
                            ANError anError = (ANError) throwable;
                            handleApiError(anError);
                        }
                    }
                }));
    }
}
