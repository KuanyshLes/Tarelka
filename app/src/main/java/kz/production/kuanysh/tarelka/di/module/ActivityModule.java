
package kz.production.kuanysh.tarelka.di.module;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import kz.production.kuanysh.tarelka.data.network.model.chat.Chat;
import kz.production.kuanysh.tarelka.data.network.model.main.Task;
import kz.production.kuanysh.tarelka.data.network.model.quiz.Quizzes;
import kz.production.kuanysh.tarelka.di.ActivityContext;
import kz.production.kuanysh.tarelka.di.PerActivity;
import kz.production.kuanysh.tarelka.ui.activities.TaskDetailMvpPresenter;
import kz.production.kuanysh.tarelka.ui.activities.TaskDetailMvpView;
import kz.production.kuanysh.tarelka.ui.activities.TaskDetailPresenter;
import kz.production.kuanysh.tarelka.ui.activities.profileedit.ProfileEditNewMvpPresenter;
import kz.production.kuanysh.tarelka.ui.activities.profileedit.ProfileEditNewMvpView;
import kz.production.kuanysh.tarelka.ui.activities.profileedit.ProfileEditNewPresenter;
import kz.production.kuanysh.tarelka.ui.activities.test.TestMvpPresenter;
import kz.production.kuanysh.tarelka.ui.activities.test.TestMvpView;
import kz.production.kuanysh.tarelka.ui.activities.test.TestPresenter;
import kz.production.kuanysh.tarelka.ui.activities.mainactivity.MainMvpPresenter;
import kz.production.kuanysh.tarelka.ui.activities.mainactivity.MainMvpView;
import kz.production.kuanysh.tarelka.ui.activities.mainactivity.MainPresenter;
import kz.production.kuanysh.tarelka.ui.adapters.ChatAdapter;
import kz.production.kuanysh.tarelka.ui.adapters.ProgressAdapter;
import kz.production.kuanysh.tarelka.ui.adapters.TaskAdapter;
import kz.production.kuanysh.tarelka.ui.fragments.ChatMvpPresenter;
import kz.production.kuanysh.tarelka.ui.fragments.ChatMvpView;
import kz.production.kuanysh.tarelka.ui.fragments.ChatPresenter;
import kz.production.kuanysh.tarelka.ui.fragments.MainTaskMvpPresenter;
import kz.production.kuanysh.tarelka.ui.fragments.MainTaskMvpView;
import kz.production.kuanysh.tarelka.ui.fragments.MainTaskPresenter;
import kz.production.kuanysh.tarelka.ui.fragments.ProfileMvpPresenter;
import kz.production.kuanysh.tarelka.ui.fragments.ProfileMvpView;
import kz.production.kuanysh.tarelka.ui.fragments.ProfilePresenter;
import kz.production.kuanysh.tarelka.ui.fragments.ProgressMvpPresenter;
import kz.production.kuanysh.tarelka.ui.fragments.ProgressMvpView;
import kz.production.kuanysh.tarelka.ui.fragments.ProgressPresenter;
import kz.production.kuanysh.tarelka.ui.fragments.SendMessageMvpPresenter;
import kz.production.kuanysh.tarelka.ui.fragments.SendMessageMvpView;
import kz.production.kuanysh.tarelka.ui.fragments.SendMessagePresenter;
import kz.production.kuanysh.tarelka.ui.fragments.social.SocialMvpPresenter;
import kz.production.kuanysh.tarelka.ui.fragments.social.SocialMvpView;
import kz.production.kuanysh.tarelka.ui.fragments.social.SocialPresenter;
import kz.production.kuanysh.tarelka.ui.welcome.ChooseFoodMvpPresenter;
import kz.production.kuanysh.tarelka.ui.welcome.ChooseFoodMvpView;
import kz.production.kuanysh.tarelka.ui.welcome.ChooseFoodPresenter;
import kz.production.kuanysh.tarelka.ui.welcome.CreateAimMvpPresenter;
import kz.production.kuanysh.tarelka.ui.welcome.CreateAimMvpView;
import kz.production.kuanysh.tarelka.ui.welcome.CreateAimPresenter;
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
    ProfileEditNewMvpPresenter<ProfileEditNewMvpView> provideProfileEditNewPresenter(
            ProfileEditNewPresenter<ProfileEditNewMvpView> presenter) {
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

    @Provides
    @PerActivity
    CreateAimMvpPresenter<CreateAimMvpView> provideCreateAimPresenter(
            CreateAimPresenter<CreateAimMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    SocialMvpPresenter<SocialMvpView> provideSocialPresenter(
            SocialPresenter<SocialMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    ChooseFoodMvpPresenter<ChooseFoodMvpView> provideChooseFoodPresenter(
            ChooseFoodPresenter<ChooseFoodMvpView> presenter) {
        return presenter;
    }

    @Provides
    ChatAdapter provideChatAdapter(){
        return new ChatAdapter(new ArrayList<Chat>());
    }

    @Provides
    TaskAdapter provideTaskAdapter(){
        return new TaskAdapter(new ArrayList<Task>());
    }

    @Provides
    ProgressAdapter providerPregressAdapter(){
        return new ProgressAdapter(new ArrayList<Quizzes>());
    }

//    @Provides
//    TaskAdapter providerTaskAdapter(){
//        return new TaskAdapter(new ArrayList<kz.production.kuanysh.tarelka.data.network.module.main.Result>());
//    }
//
//    @Provides
//    AimsAdapter provideAimsAdapter(){
//        return new AimsAdapter(new ArrayList<kz.production.kuanysh.tarelka.data.network.module.aim.Result>());
//    }



}
