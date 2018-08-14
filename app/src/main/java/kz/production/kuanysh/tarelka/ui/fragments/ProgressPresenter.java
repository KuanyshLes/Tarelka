package kz.production.kuanysh.tarelka.ui.fragments;

import android.text.format.DateFormat;
import android.util.Log;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import kz.production.kuanysh.tarelka.data.DataManager;
import kz.production.kuanysh.tarelka.data.network.model.progress.Progress;
import kz.production.kuanysh.tarelka.data.network.model.quiz.Quiz;
import kz.production.kuanysh.tarelka.ui.base.BasePresenter;
import kz.production.kuanysh.tarelka.utils.rx.SchedulerProvider;

/**
 * Created by User on 26.06.2018.
 */

public class ProgressPresenter<V extends ProgressMvpView> extends BasePresenter<V>
        implements ProgressMvpPresenter<V> {

    @Inject
    public ProgressPresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onTestItemClick(int position) {
        getMvpView().openTestActivity(position);
    }

    @Override
    public void onViewPrepared() {
        if (getMvpView().isNetworkConnected()) {
            if(getDataManager().getAccessToken()!=null){
                //getMvpView().showLoading();
                getCompositeDisposable().add(getDataManager()
                        .getApiHelper().getQuizList(getDataManager().getAccessToken())
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(new Consumer<Quiz>() {
                            @Override
                            public void accept(@NonNull Quiz quiz)
                                    throws Exception {


                                getMvpView().updateQuizList(quiz.getResult().getQuizzes());

                                getMvpView().hideLoading();
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(@NonNull Throwable throwable)
                                    throws Exception {
                                if (!isViewAttached()) {
                                    return;
                                }

                                getMvpView().showMessage("Убедитесь что вы подключены к интернету!");

                                getMvpView().hideLoading();

                            }
                        }));
            }
            else{
                getMvpView().showMessage("Что-то пошло не так!");
            }
        }else{
            getMvpView().onError("Нет подключения к интернету!");
        }


    }

    @Override
    public void onSendShowProgress() {
        if (getMvpView().isNetworkConnected()) {
            getCompositeDisposable().add(getDataManager()
                    .getApiHelper().getProgress(getDataManager().getAccessToken())
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(new Consumer<Progress>() {
                        @Override
                        public void accept(@NonNull Progress progress)
                                throws Exception {
                            getMvpView().hideLoading();
                            if(progress.getStatusCode()==200){
                                getMvpView().setProgressDates(progress.getResult().getPerc());
                            }else if(progress.getStatusCode()==404){
                                getMvpView().showMessage(progress.getMessage());

                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(@NonNull Throwable throwable)
                                throws Exception {
                            if (!isViewAttached()) {
                                return;
                            }

                            getMvpView().hideLoading();
                         //   getMvpView().showMessage("Error occured");
                            Log.d("myTag", "accept: exception " + throwable.getMessage());

                        }
                    }));
        }else{
            getMvpView().onError("Нет подключения к интернету!");
        }

    }
}
