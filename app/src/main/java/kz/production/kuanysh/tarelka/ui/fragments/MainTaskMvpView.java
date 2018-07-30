package kz.production.kuanysh.tarelka.ui.fragments;

import java.util.List;

import kz.production.kuanysh.tarelka.data.network.model.main.Result;
import kz.production.kuanysh.tarelka.data.network.model.main.Task;
import kz.production.kuanysh.tarelka.ui.base.MvpView;

/**
 * Created by User on 26.06.2018.
 */

public interface MainTaskMvpView extends MvpView{

    void openTaskDetailActivity(int position);

    void updateAimsList(List<Task> tasks);

    void fireNotification();
}
