package kz.production.kuanysh.tarelka.ui.activities.mainactivity;

import android.content.Intent;
import android.text.Layout;
import android.view.View;

import kz.production.kuanysh.tarelka.ui.base.MvpView;

/**
 * Created by User on 26.06.2018.
 */

public interface MainMvpView extends MvpView {

    void openQuestionLikeDialog();

    void openDontLikeDialog();

    void openLikeDialog();

    void onReceivePhotoCount(int count);

    void openGooglePlay();

    void openMail(String improvement);

    void dialogBuilder(int dialog_question_like);

    void openChooseAimActivity();

    void openMainTaskFragment();

    void openChatFragment();

    void openProfileFragment();

    void openProgressFragment();

    void fireNotificationMorning();

    void fireNotificationAfternoon();

    void fireNotificationEvening();

    void openLoginActivity();

    void onMessageReceivedNotification(String title, String message);
}
