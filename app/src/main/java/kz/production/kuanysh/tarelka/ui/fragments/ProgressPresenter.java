package kz.production.kuanysh.tarelka.ui.fragments;

import android.text.format.DateFormat;

import com.androidnetworking.error.ANError;

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

    @Override
    public void setDates() {
        List<String> sixDays=new ArrayList<>();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        Calendar cal3 = Calendar.getInstance();
        Calendar cal4 = Calendar.getInstance();
        Calendar cal5 = Calendar.getInstance();
        Calendar cal6 = Calendar.getInstance();

        cal1.add(Calendar.DATE, -2);
        String day1=(String) DateFormat.format("dd",cal1);
        String month=(String) DateFormat.format("MMM",cal1);
        String firstday=day1+" "+month;

        cal2.add(Calendar.DATE, -1);
        String day2=(String) DateFormat.format("dd",cal2);
        String secondday="Вчера";

        cal3.add(Calendar.DATE, 0);
        String dayToday=(String) DateFormat.format("dd",cal3);
        String thirdday=dayToday+" "+month;

        cal4.add(Calendar.DATE, 1);
        String day4=(String) DateFormat.format("dd",cal4);
        String fourthday=day4+" "+month;

        cal5.add(Calendar.DATE, 2);
        String day5=(String) DateFormat.format("dd",cal5);
        String fifthday=day5+" "+month;

        cal6.add(Calendar.DATE, 3);
        String day6=(String) DateFormat.format("dd",cal6);
        String sixthday=day6+" "+month;

        sixDays.add(firstday);
        sixDays.add(secondday);
        sixDays.add(thirdday);
        sixDays.add(fourthday);
        sixDays.add(fifthday);
        sixDays.add(sixthday);

       getMvpView().onSetSpinnerDate(sixDays);
    }

    @Override
    public void onSendShowProgress(String date) {
        getCompositeDisposable().add(getDataManager()
                .getApiHelper().getProgress(getDataManager().getAccessToken(),date)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<Progress>() {
                    @Override
                    public void accept(@NonNull Progress progress)
                            throws Exception {
                        getMvpView().hideLoading();
                        if(progress.getStatusCode()==200){
                            getMvpView().updateProgress(progress.getResult().getPerc());
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

                        // handle the error here
                        if (throwable instanceof ANError) {
                            ANError anError = (ANError) throwable;
                            handleApiError(anError);
                        }
                    }
                }));
    }
}
