package kz.production.kuanysh.tarelka.ui.fragments;

import android.util.Log;

import com.androidnetworking.error.ANError;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import kz.production.kuanysh.tarelka.data.DataManager;
import kz.production.kuanysh.tarelka.data.network.model.profile.Authorization;
import kz.production.kuanysh.tarelka.ui.base.BasePresenter;
import kz.production.kuanysh.tarelka.utils.rx.SchedulerProvider;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by User on 26.06.2018.
 */

public class ProfileEditPresenter<V extends ProfileEditMvpView> extends BasePresenter<V>
        implements ProfileEditMvpPresenter<V> {

    @Inject
    public ProfileEditPresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }



    @Override
    public void onSaveClick(MultipartBody.Part image64, String name, String age, String weight,int height) {

        RequestBody body =
                RequestBody.create(MediaType.parse("text/plain"), getDataManager().getAccessToken());

        if(getDataManager().getAccessToken()!=null){
            getMvpView().showMessage(getDataManager().getAccessToken().toString());
            getCompositeDisposable().add(getDataManager().getImageApiHelper().updateProfileInfo(
                    body,image64,name,Integer.valueOf(weight),Integer.valueOf(age),height)
                    .subscribeOn(getSchedulerProvider().io()).
                            observeOn(getSchedulerProvider().ui())
                    .subscribe(new Consumer<Authorization>() {
                        @Override
                        public void accept(Authorization response) throws Exception {
                            getMvpView().showMessage("Succesfully updated");
                        /*StringBuilder builder = new StringBuilder();
                        for(String s : response.getResult().getGoals()) {
                            builder.append(s+",");
                        }
                        String str = builder.toString();*/
                            getDataManager().updateUserInfo(
                                    response.getResult().getToken(),
                                    response.getResult().getId(),
                                    DataManager.LoggedInMode.LOGGED_IN_MODE_SERVER,
                                    response.getResult().getFio(),
                                    response.getResult().getStatus(),
                                    response.getResult().getPhone(),
                                    response.getResult().getAvatar(),
                                    response.getResult().getAge(),
                                    response.getResult().getWeight(),
                                    getDataManager().getAims(),
                                    response.getResult().getHeight());

                            if (!isViewAttached()) {
                                return;
                            }

                            getMvpView().hideLoading();

                            getMvpView().openProfileFragment();

                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {

                            if (!isViewAttached()) {
                                return;
                            }

                            if(throwable.getMessage() != null)
                                Log.i("MESS", throwable.getMessage());

                            if(throwable.getCause() != null)
                                throwable.printStackTrace();

                            getMvpView().showMessage("Cannot update");

                            getMvpView().hideLoading();

                            // handle the login error here
                            if (throwable instanceof ANError) {
                                ANError anError = (ANError) throwable;
                                handleApiError(anError);
                            }
                        }
                    }));
        }else{
            getMvpView().showMessage("Token is required");
        }

    }

    @Override
    public void onSaveClickWithoutImage(String name, String age, String weight, int height) {
        if(getDataManager().getAccessToken()!=null){
            getMvpView().showMessage(getDataManager().getAccessToken().toString());
            getCompositeDisposable().add(getDataManager().getApiHelper().updateProfileInfo(
                    getDataManager().getAccessToken(),name,Integer.valueOf(weight),Integer.valueOf(age),Integer.valueOf(height))
                    .subscribeOn(getSchedulerProvider().io()).
                            observeOn(getSchedulerProvider().ui())
                    .subscribe(new Consumer<Authorization>() {
                        @Override
                        public void accept(Authorization response) throws Exception {
                            getMvpView().showMessage("Succesfully updated");
                        /*StringBuilder builder = new StringBuilder();
                        for(String s : response.getResult().getGoals()) {
                            builder.append(s+",");
                        }
                        String str = builder.toString();*/
                            getDataManager().updateUserInfo(
                                    response.getResult().getToken(),
                                    response.getResult().getId(),
                                    DataManager.LoggedInMode.LOGGED_IN_MODE_SERVER,
                                    response.getResult().getFio(),
                                    response.getResult().getStatus(),
                                    response.getResult().getPhone(),
                                    response.getResult().getAvatar(),
                                    response.getResult().getAge(),
                                    response.getResult().getWeight(),
                                    getDataManager().getAims(),
                                    response.getResult().getHeight());

                            if (!isViewAttached()) {
                                return;
                            }

                            getMvpView().hideLoading();

                            getMvpView().openProfileFragment();

                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {

                            if (!isViewAttached()) {
                                return;
                            }

                            if(throwable.getMessage() != null)
                                Log.i("MESS", throwable.getMessage());

                            if(throwable.getCause() != null)
                                throwable.printStackTrace();

                            getMvpView().showMessage("Cannot update");

                            getMvpView().hideLoading();

                            // handle the login error here
                            if (throwable instanceof ANError) {
                                ANError anError = (ANError) throwable;
                                handleApiError(anError);
                            }
                        }
                    }));
        }else{
            getMvpView().showMessage("Token is required");
        }
    }

    @Override
    public void onAddPhotoClick() {
        getMvpView().openImagePicker();
    }
}
