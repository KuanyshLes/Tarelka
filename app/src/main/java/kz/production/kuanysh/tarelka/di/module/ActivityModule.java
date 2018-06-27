
package kz.production.kuanysh.tarelka.di.module;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import kz.production.kuanysh.tarelka.di.ActivityContext;
import kz.production.kuanysh.tarelka.di.PerActivity;
import kz.production.kuanysh.tarelka.ui.activities.TaskDetailMvpPresenter;
import kz.production.kuanysh.tarelka.ui.activities.TaskDetailMvpView;
import kz.production.kuanysh.tarelka.ui.activities.TaskDetailPresenter;
import kz.production.kuanysh.tarelka.ui.activities.test.TestMvpPresenter;
import kz.production.kuanysh.tarelka.ui.activities.test.TestMvpView;
import kz.production.kuanysh.tarelka.ui.activities.test.TestPresenter;
import kz.production.kuanysh.tarelka.ui.activities.mainactivity.MainMvpPresenter;
import kz.production.kuanysh.tarelka.ui.activities.mainactivity.MainMvpView;
import kz.production.kuanysh.tarelka.ui.activities.mainactivity.MainPresenter;
import kz.production.kuanysh.tarelka.ui.fragments.ChatMvpPresenter;
import kz.production.kuanysh.tarelka.ui.fragments.ChatMvpView;
import kz.production.kuanysh.tarelka.ui.fragments.ChatPresenter;
import kz.production.kuanysh.tarelka.ui.fragments.MainTaskMvpPresenter;
import kz.production.kuanysh.tarelka.ui.fragments.MainTaskMvpView;
import kz.production.kuanysh.tarelka.ui.fragments.MainTaskPresenter;
import kz.production.kuanysh.tarelka.ui.fragments.ProfileEditMvpPresenter;
import kz.production.kuanysh.tarelka.ui.fragments.ProfileEditMvpView;
import kz.production.kuanysh.tarelka.ui.fragments.ProfileEditPresenter;
import kz.production.kuanysh.tarelka.ui.fragments.ProfileMvpPresenter;
import kz.production.kuanysh.tarelka.ui.fragments.ProfileMvpView;
import kz.production.kuanysh.tarelka.ui.fragments.ProfilePresenter;
import kz.production.kuanysh.tarelka.ui.fragments.ProgressMvpPresenter;
import kz.production.kuanysh.tarelka.ui.fragments.ProgressMvpView;
import kz.production.kuanysh.tarelka.ui.fragments.ProgressPresenter;
import kz.production.kuanysh.tarelka.ui.fragments.SendMessageMvpPresenter;
import kz.production.kuanysh.tarelka.ui.fragments.SendMessageMvpView;
import kz.production.kuanysh.tarelka.ui.fragments.SendMessagePresenter;
import kz.production.kuanysh.tarelka.ui.welcome.LoginMvpPresenter;
import kz.production.kuanysh.tarelka.ui.welcome.LoginMvpView;
import kz.production.kuanysh.tarelka.ui.welcome.LoginPresenter;
import kz.production.kuanysh.tarelka.ui.welcome.SplashMvpPresenter;
import kz.production.kuanysh.tarelka.ui.welcome.SplashMvpView;
import kz.production.kuanysh.tarelka.ui.welcome.SplashPresenter;
import kz.production.kuanysh.tarelka.utils.rx.AppSchedulerProvider;
import kz.production.kuanysh.tarelka.utils.rx.SchedulerProvider;


@Module
public class ActivityModule {

    private AppCompatActivity mActivity;

    public ActivityModule(AppCompatActivity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }

    @Provides
    AppCompatActivity provideActivity() {
        return mActivity;
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }

    @Provides
    @PerActivity
    MainMvpPresenter<MainMvpView> provideMainPresenter(
            MainPresenter<MainMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    MainTaskMvpPresenter<MainTaskMvpView> provideMainTaskPresenter(
            MainTaskPresenter<MainTaskMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    TaskDetailMvpPresenter<TaskDetailMvpView> provideTaskDetailPresenter(
            TaskDetailPresenter<TaskDetailMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    ProfileMvpPresenter<ProfileMvpView> provideProfilePresenter(
            ProfilePresenter<ProfileMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    ProfileEditMvpPresenter<ProfileEditMvpView> provideProfileEditPresenter(
            ProfileEditPresenter<ProfileEditMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    ChatMvpPresenter<ChatMvpView> provideChatPresenter(
                    ChatPresenter<ChatMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    SendMessageMvpPresenter<SendMessageMvpView> provideSendMessagePresenter(
            SendMessagePresenter<SendMessageMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    ProgressMvpPresenter<ProgressMvpView> provideProgressPresenter(
            ProgressPresenter<ProgressMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    TestMvpPresenter<TestMvpView> provideTestPresenter(
            TestPresenter<TestMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    SplashMvpPresenter<SplashMvpView> provideSplashPresenter(
            SplashPresenter<SplashMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    LoginMvpPresenter<LoginMvpView> provideLoginPresenter(
            LoginPresenter<LoginMvpView> presenter) {
        return presenter;
    }



}