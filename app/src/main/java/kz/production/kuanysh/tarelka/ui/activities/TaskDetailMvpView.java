package kz.production.kuanysh.tarelka.ui.activities;

import java.util.List;

import kz.production.kuanysh.tarelka.ui.base.MvpView;
import kz.production.kuanysh.tarelka.ui.base.SubMvpView;

/**
 * Created by User on 26.06.2018.
 */

public interface TaskDetailMvpView extends MvpView {

    void openMainTaskFragment();

    void updateFull(List<String> blog);


}
