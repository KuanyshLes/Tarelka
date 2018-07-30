package kz.production.kuanysh.tarelka.ui.fragments;

import java.util.List;

import kz.production.kuanysh.tarelka.data.network.model.progress.Perc;
import kz.production.kuanysh.tarelka.data.network.model.quiz.Quizzes;
import kz.production.kuanysh.tarelka.data.network.model.quiz.Result;
import kz.production.kuanysh.tarelka.ui.base.MvpView;

/**
 * Created by User on 26.06.2018.
 */

public interface ProgressMvpView extends MvpView{

    void openTestActivity(int position);

    void updateQuizList(List<Quizzes> quizList);

    void setProgressDates(List<Perc> list);

    void updateProgress(Double position);

}
