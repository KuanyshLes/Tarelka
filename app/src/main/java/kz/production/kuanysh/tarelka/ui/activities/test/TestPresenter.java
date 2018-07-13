package kz.production.kuanysh.tarelka.ui.activities.test;

import com.androidnetworking.error.ANError;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import kz.production.kuanysh.tarelka.data.DataManager;
import kz.production.kuanysh.tarelka.data.network.model.quizquestions.Questions;
import kz.production.kuanysh.tarelka.data.network.model.quizquestions.QuizResult;
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
        getMvpView().showResult();
    }

    @Override
    public void onNextClick(int position) {
        getMvpView().updateTest(position);
    }

    @Override
    public void onCancellClick() {
        getMvpView().openProgressFragment();
    }

    @Override
    public void onViewPrepared(String id) {
        getCompositeDisposable().add(getDataManager()
                .getApiHelper().getQuestions(getDataManager().getAccessToken(),id)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<Questions>() {
                    @Override
                    public void accept(@NonNull Questions quiz)
                            throws Exception {

                        getMvpView().showMessage("Test received!");

                        getMvpView().getQuestions(quiz.getResult());
                        getMvpView().hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable)
                            throws Exception {
                        if (!isViewAttached()) {
                            return;
                        }

                        getMvpView().showMessage("Test open failed!");

                        getMvpView().hideLoading();

                        // handle the error here
                        if (throwable instanceof ANError) {
                            ANError anError = (ANError) throwable;
                            handleApiError(anError);
                        }
                    }
                }));
    }

    @Override
    public void sendResult(int quiz_id, int max_answer, int correct_answer) {
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date date=new Date();

        getCompositeDisposable().add(getDataManager()
                .getApiHelper().sendQuizResults(getDataManager().getAccessToken(),quiz_id,max_answer, correct_answer,dateFormat.format(date))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<QuizResult>() {
                    @Override
                    public void accept(@NonNull QuizResult blogResponse)
                            throws Exception {

                        getMvpView().showMessage("Test results sent!");

                        getMvpView().openProgressFragment();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable)
                            throws Exception {
                        if (!isViewAttached()) {
                            return;
                        }

                        getMvpView().showMessage("Results not sent!");


                        // handle the error here
                        if (throwable instanceof ANError) {
                            ANError anError = (ANError) throwable;
                            handleApiError(anError);
                        }
                    }
                }));
    }
}
