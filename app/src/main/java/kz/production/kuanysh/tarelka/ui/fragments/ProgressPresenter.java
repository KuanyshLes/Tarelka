package kz.production.kuanysh.tarelka.ui.fragments;

import com.androidnetworking.error.ANError;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import kz.production.kuanysh.tarelka.data.DataManager;
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

                            getMvpView().showMessage("Quiz list opened!");

                            getMvpView().updateQuizList(quiz.getResult());

                            getMvpView().hideLoading();
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(@NonNull Throwable throwable)
                                throws Exception {
                            if (!isViewAttached()) {
                                return;
                            }

                            getMvpView().showMessage("Quiz list open failed!");

                            getMvpView().hideLoading();

                            // handle the error here
                            if (throwable instanceof ANError) {
                                ANError anError = (ANError) throwable;
                                handleApiError(anError);
                            }
                        }
                    }));
        }
        else{
            getMvpView().showMessage("Something went wrong!");
        }

    }
}
