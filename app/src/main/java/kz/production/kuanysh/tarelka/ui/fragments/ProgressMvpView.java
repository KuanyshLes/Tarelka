package kz.production.kuanysh.tarelka.ui.fragments;

import java.util.List;

import kz.production.kuanysh.tarelka.ui.base.MvpView;

/**
 * Created by User on 26.06.2018.
 */

public interface ProgressMvpView extends MvpView{

    void openTestActivity(int position);

    void updateTestList(List<String> testList);

    void updateDate();

    void updateProgress();
}
