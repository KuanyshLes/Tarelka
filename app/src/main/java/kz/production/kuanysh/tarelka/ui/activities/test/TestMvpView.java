package kz.production.kuanysh.tarelka.ui.activities.test;

import java.util.List;

import kz.production.kuanysh.tarelka.data.network.model.quizquestions.Result;
import kz.production.kuanysh.tarelka.ui.base.MvpView;

/**
 * Created by User on 26.06.2018.
 */

public interface TestMvpView extends MvpView {

    void openProgressFragment();

    void updateTest(int  position);

    void getQuestions(List<Result> quiz);

    void showResult();

}
