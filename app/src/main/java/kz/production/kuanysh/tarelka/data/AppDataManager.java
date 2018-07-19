package kz.production.kuanysh.tarelka.data;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import butterknife.OnClick;
import io.reactivex.Single;
import kz.production.kuanysh.tarelka.R;
import kz.production.kuanysh.tarelka.data.network.ApiHeader;
import kz.production.kuanysh.tarelka.data.network.ApiHelper;
import kz.production.kuanysh.tarelka.data.network.RestApi;
import kz.production.kuanysh.tarelka.data.network.model.aim.Aim;
import kz.production.kuanysh.tarelka.data.network.model.chat.ChatInfo;
import kz.production.kuanysh.tarelka.data.network.model.goal.SendGoal;
import kz.production.kuanysh.tarelka.data.network.model.main.Main;
import kz.production.kuanysh.tarelka.data.network.model.profile.Authorization;
import kz.production.kuanysh.tarelka.data.network.model.progress.Progress;
import kz.production.kuanysh.tarelka.data.network.model.quiz.Quiz;
import kz.production.kuanysh.tarelka.data.network.model.quizquestions.Questions;
import kz.production.kuanysh.tarelka.data.network.model.quizquestions.QuizResult;
import kz.production.kuanysh.tarelka.data.network.model.sendmeal.SendMeal;
import kz.production.kuanysh.tarelka.data.prefs.PreferencesHelper;
import kz.production.kuanysh.tarelka.di.ApplicationContext;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by User on 24.06.2018.
 */
@Singleton
public class AppDataManager implements DataManager {

    private static final String TAG = "AppDataManager";


    private final Context mContext;
    private final PreferencesHelper mPreferencesHelper;
    private final ApiHelper mApiHelper;

    @Inject
    public AppDataManager(@ApplicationContext Context context, PreferencesHelper preferencesHelper,
                          ApiHelper apiHelper) {
        mContext = context;
        mPreferencesHelper = preferencesHelper;
        mApiHelper = apiHelper;
    }



    @Override
    public ApiHeader getApiHeader() {
        return mApiHelper.getApiHeader();
    }

    @Override
    public Single<Authorization> getAccessTokenAndLogin(String phone) {
        return mApiHelper.getAccessTokenAndLogin(phone);
    }

    @Override
    public Single<Authorization> getProfileInfo(String token) {
        return mApiHelper.getProfileInfo(token);
    }

    @Override
    public Single<Aim> getAimsApi() {
        return mApiHelper.getAimsApi();
    }

    @Override
    public Single<Aim> getFoodsApi() {
        return mApiHelper.getFoodsApi();
    }

    @Override
    public Single<Main> getMainTasks() {
        return mApiHelper.getMainTasks();
    }

    @Override
    public Single<Authorization> updateProfileInfo(String token, String name, int weight, int age,int height) {
        return mApiHelper.updateProfileInfo(token,name,weight,age,height);

    }

    @Override
    public Single<Authorization> updateInfoSuccess(String token, Bitmap imagebase64, String fio, String weight, String age) {
        return mApiHelper.updateInfoSuccess(token,imagebase64,fio,weight,age);
    }

    @Override
    public Single<ChatInfo> getChats(String token,int currentPage) {
        return mApiHelper.getChats(token,currentPage);
    }

    @Override
    public Single<ChatInfo> sendMessage(String token, String message, String sended_date, String sended_time) {
        return mApiHelper.sendMessage(token,message,sended_date,sended_time);
    }

    @Override
    public Single<Quiz> getQuizList(String token) {
        return mApiHelper.getQuizList(token);
    }

    @Override
    public Single<Authorization> updateProfileInfo(RequestBody token, MultipartBody.Part image, String fio, int weight, int age,int height) {
        return mApiHelper.updateProfileInfo(token,image,fio,weight,age,height);
    }

    @Override
    public Single<Questions> getQuestions(String token, String quiz_id) {
        return mApiHelper.getQuestions(token,quiz_id);
    }

    @Override
    public Single<QuizResult> sendQuizResults(String token, int quiz_id, int max_answer, int correct_answer,String date) {
        return mApiHelper.sendQuizResults(token,quiz_id,max_answer,correct_answer,date);
    }

    @Override
    public Single<kz.production.kuanysh.tarelka.data.network.model.chat.receive.Result> sendImageMessage(RequestBody token, RequestBody description, List<MultipartBody.Part> part) {
        return mApiHelper.sendImageMessage(token,description,part);
    }

    @Override
    public Single<SendGoal> sendGoal(String token, int id) {
        return mApiHelper.sendGoal(token,id);
    }

    @Override
    public Single<SendMeal> sendMeals(Map<String, String> meals) {
        return mApiHelper.sendMeals(meals);
    }

    @Override
    public Single<Progress> getProgress(String token, String date) {
        return mApiHelper.getProgress(token,date);
    }


    @Override
    public String getFirebaseToken() {
        return mPreferencesHelper.getFirebaseToken();
    }

    @Override
    public void setFirebaseToken(String refreshedToken) {
        mPreferencesHelper.setFirebaseToken(refreshedToken);
    }

    @Override
    public int getCurrentUserLoggedInMode() {
        return mPreferencesHelper.getCurrentUserLoggedInMode();
    }

    @Override
    public void setCurrentUserLoggedInMode(LoggedInMode mode) {
        mPreferencesHelper.setCurrentUserLoggedInMode(mode);
    }

    @Override
    public Long getCurrentUserId() {
        return mPreferencesHelper.getCurrentUserId();
    }

    @Override
    public void setCurrentUserId(Integer userId) {
        mPreferencesHelper.setCurrentUserId(userId);
    }

    @Override
    public String getCurrentUserName() {
        return mPreferencesHelper.getCurrentUserName();
    }

    @Override
    public void setCurrentUserName(String userName) {
        mPreferencesHelper.setCurrentUserName(userName);
    }

    @Override
    public String getCurrentStatus() {
        return mPreferencesHelper.getCurrentStatus();
    }

    @Override
    public void setCurrentStatus(String status) {
        mPreferencesHelper.setCurrentStatus(status);
    }


    @Override
    public String getAccessToken() {
        return mPreferencesHelper.getAccessToken();
    }

    @Override
    public void setAccessToken(String accessToken) {
        mPreferencesHelper.setAccessToken(accessToken);
        mApiHelper.getApiHeader().getProtectedApiHeader().setAccessToken(accessToken);
    }

    @Override
    public String getPhoneNumber() {
        return mPreferencesHelper.getPhoneNumber();
    }

    @Override
    public void setPhoneNumber(String phone) {
        mPreferencesHelper.setPhoneNumber(phone);
    }

    @Override
    public String getImage() {
        return mPreferencesHelper.getImage();
    }

    @Override
    public void setImage(String phone) {
        mPreferencesHelper.setImage(phone);
    }

    @Override
    public String getAge() {
        return mPreferencesHelper.getAge();
    }

    @Override
    public void setAge(String age) {
        mPreferencesHelper.setAge(age);
    }

    @Override
    public String getWeight() {
        return mPreferencesHelper.getWeight();
    }

    @Override
    public void setWeight(String weight) {
        mPreferencesHelper.setWeight(weight);
    }

    @Override
    public String getAims() {
        return mPreferencesHelper.getAims();
    }

    @Override
    public void setAims(String aims) {
        mPreferencesHelper.setAims(aims);
    }

    @Override
    public String getHeight() {
        return mPreferencesHelper.getHeight();
    }

    @Override
    public void setHeight(String height) {
        mPreferencesHelper.setHeight(height);
    }

    @Override
    public void updateApiHeader(Integer userId, String accessToken) {
        mApiHelper.getApiHeader();
    }

    @Override
    public void setUserAsLoggedOut() {
        updateUserInfo(
                null,
                null,
                DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT,
                null,
                null,
                null,null,null,null,null,null);
    }

    @Override
    public ApiHelper getApiHelper() {
        return RestApi.getApiHelper();
    }

    @Override
    public ApiHelper getImageApiHelper() {
        return RestApi.getApiImageHelper();
    }

    @Override
    public void updateFirebaseToken(String token) {
        mPreferencesHelper.setFirebaseToken(token);
    }


    @Override
    public void updateUserInfo(String accessToken,
                               Integer userId,
                               LoggedInMode loggedInMode,
                               String userName,
                               String status,
                               String phone,
                               String image,
                               String age,
                               String weight,
                               String aims,
                               String height) {
        setAccessToken(accessToken);
        setCurrentUserId(userId);
        setCurrentUserLoggedInMode(loggedInMode);
        setCurrentUserName(userName);
        setCurrentStatus(status);
        setPhoneNumber(phone);
        setImage(image);
        setAge(age);
        setWeight(weight);
        setAims(aims);
        setHeight(height);

        updateApiHeader(userId, accessToken);
    }
}
