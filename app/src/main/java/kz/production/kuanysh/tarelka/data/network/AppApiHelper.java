package kz.production.kuanysh.tarelka.data.network;

import android.graphics.Bitmap;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import kz.production.kuanysh.tarelka.data.network.model.admin.AdminInfo;
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
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by User on 24.06.2018.
 */
@Singleton
public class AppApiHelper implements ApiHelper {

    private ApiHeader mApiHeader;



    @Inject
    public AppApiHelper(ApiHeader apiHeader) {
        mApiHeader = apiHeader;
    }

    @Override
    public ApiHeader getApiHeader() {
        return mApiHeader;
    }

    @Override
    public Single<Authorization> getAccessTokenAndLogin(String phone) {
        return RestApi.getApiHelper().getAccessTokenAndLogin(phone);
    }

    @Override
    public Single<Authorization> getProfileInfo(String token) {
        return RestApi.getApiHelper().getAccessTokenAndLogin(token);
    }

    @Override
    public Single<Aim> getAimsApi() {
        return RestApi.getApiHelper().getAimsApi();
    }

    @Override
    public Single<Aim> getFoodsApi() {
        return RestApi.getApiHelper().getFoodsApi();
    }

    @Override
    public Single<Main> getMainTasks(String token) {
        return RestApi.getApiHelper().getMainTasks(token);
    }

    @Override
    public Single<Authorization> updateProfileInfo(String token,String name, int weight, int age,int height) {

        return RestApi.getApiHelper().updateProfileInfo(token,name,age,weight,height);
    }

    @Override
    public Single<Authorization> updateInfoSuccess(String token, Bitmap imagebase64, String fio, String weight, String age) {
        return RestApi.getApiHelper().updateInfoSuccess(token,imagebase64,fio,weight,age);
    }

    @Override
    public Single<ChatInfo> getChats(String token,int currentPage) {
        return RestApi.getApiHelper().getChats(token,currentPage);
    }

    @Override
    public Single<ChatInfo> sendMessage(String token, String message, String sended_date, String sended_time) {
        return RestApi.getApiHelper().sendMessage(token,message,sended_date,sended_time);
    }

    @Override
    public Single<Quiz> getQuizList(String token) {
        return RestApi.getApiHelper().getQuizList(token);
    }



    @Override
    public Single<Authorization> updateProfileInfo(RequestBody token, MultipartBody.Part image, String fio, int weight, int age,int height) {
        return RestApi.getApiImageHelper().updateProfileInfo(token,image,fio,weight,age,height);
    }

    @Override
    public Single<Questions> getQuestions(String token, String quiz_id) {
        return RestApi.getApiHelper().getQuestions(token,quiz_id);
    }

    @Override
    public Single<QuizResult> sendQuizResults(String token, int quiz_id, int max_answer, int correct_answer,String date) {
        return RestApi.getApiHelper().sendQuizResults(token,quiz_id,max_answer,correct_answer,date);
    }

    @Override
    public Single<kz.production.kuanysh.tarelka.data.network.model.chat.receive.Result> sendImageMessage(RequestBody token, RequestBody description, List<MultipartBody.Part> part) {
        return RestApi.getApiImageHelper().sendImageMessage(token,description,part);
    }

    @Override
    public Single<SendGoal> sendGoal(String token, int id) {
        return RestApi.getApiHelper().sendGoal(token,id);
    }

    @Override
    public Single<SendMeal> sendMeals(Map<String, String> meals) {
        return RestApi.getApiHelper().sendMeals(meals);
    }

    @Override
    public Single<Progress> getProgress(String token) {
        return RestApi.getApiHelper().getProgress(token);
    }

    @Override
    public Single<AdminInfo> getAdminInfo() {
        return RestApi.getApiHelper().getAdminInfo();
    }


}
