package kz.production.kuanysh.tarelka.ui.fragments;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.androidnetworking.error.ANError;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import kz.production.kuanysh.tarelka.data.DataManager;
import kz.production.kuanysh.tarelka.data.network.model.chat.ChatInfo;
import kz.production.kuanysh.tarelka.ui.base.BasePresenter;
import kz.production.kuanysh.tarelka.utils.rx.SchedulerProvider;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by User on 26.06.2018.
 */

public class ChatPresenter<V extends ChatMvpView> extends BasePresenter<V>
        implements ChatMvpPresenter<V> {

    @Inject
    public ChatPresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onSendClick(String message) {

        if(getDataManager().getAccessToken()!=null){
            if(message.length()!=0){

                DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
                Date date=new Date();

                getCompositeDisposable().add(getDataManager()
                        .getApiHelper().sendMessage(getDataManager().getAccessToken(),message,dateFormat.format(date),
                                new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()))
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(new Consumer<ChatInfo>() {
                            @Override
                            public void accept(@NonNull ChatInfo blogResponse)
                                    throws Exception {

                                getMvpView().showMessage("Message sent!");
                                onViewPrepared();
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(@NonNull Throwable throwable)
                                    throws Exception {
                                if (!isViewAttached()) {
                                    return;
                                }

                                getMvpView().showMessage("Message not sent!");


                                // handle the error here
                                if (throwable instanceof ANError) {
                                    ANError anError = (ANError) throwable;
                                    handleApiError(anError);
                                }
                            }
                        }));
            }else{
                getMvpView().showMessage("image field is null");
            }

        }
        else{
            getMvpView().showMessage("Something went wrong!");
        }
    }

    @Override
    public void onSendImage(Uri uriImage, String filePath, Context context) {
        if(filePath!=null && uriImage!=null){
            Log.i("PATH", uriImage.toString());
            File file = new File(filePath);

            RequestBody body =
                    RequestBody.create(MediaType.parse("text/plain"), getDataManager().getAccessToken());

            RequestBody filePart= RequestBody.create(MediaType.parse(context.getContentResolver().getType(uriImage)), file);
            MultipartBody.Part part =
                    MultipartBody.Part.createFormData("images[0]", file.getName(), filePart);

            String descriptionString = "images";
            RequestBody description =
                    RequestBody.create(
                            okhttp3.MultipartBody.FORM, descriptionString);
            List<MultipartBody.Part> images=new ArrayList<>();
            images.add(part);

            if(getDataManager().getAccessToken()!=null){
                getMvpView().showLoading();
                getCompositeDisposable().add(getDataManager()
                        .getImageApiHelper().sendImageMessage(body,description,images)
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(new Consumer<kz.production.kuanysh.tarelka.data.network.model.chat.receive.Result>() {
                            @Override
                            public void accept(@NonNull kz.production.kuanysh.tarelka.data.network.model.chat.receive.Result blogResponse)
                                    throws Exception {

                                getMvpView().hideLoading();
                                getMvpView().showMessage("Image sent!");
                                onViewPrepared();
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(@NonNull Throwable throwable)
                                    throws Exception {
                                if (!isViewAttached()) {
                                    return;
                                }
                                getMvpView().hideLoading();
                                getMvpView().showMessage("Image not sent!");


                                // handle the error here
                                if (throwable instanceof ANError) {
                                    ANError anError = (ANError) throwable;
                                    handleApiError(anError);
                                }
                            }
                        }));
            }
            else{
                getMvpView().showMessage("Something went wrong!");
            }

        }
        else{
            getMvpView().showMessage("Uri and filePath are null");
        }

    }


    @Override
    public void onImageclick() {
        getMvpView().openImagePicker();
    }



    @Override
    public void onMailClick() {
        getMvpView().openSendAsSocial();
    }

    @Override
    public void onViewPrepared() {
        if(getDataManager().getAccessToken()!=null){
            getCompositeDisposable().add(getDataManager()
                    .getApiHelper().getChats(getDataManager().getAccessToken())
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(new Consumer<ChatInfo>() {
                        @Override
                        public void accept(@NonNull ChatInfo blogResponse)
                                throws Exception {
                            getMvpView().updateChat(blogResponse.getResult().getChats());

                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(@NonNull Throwable throwable)
                                throws Exception {
                            if (!isViewAttached()) {
                                return;
                            }


                            // handle the error here
                            if (throwable instanceof ANError) {
                                ANError anError = (ANError) throwable;
                                handleApiError(anError);
                            }
                        }
                    }));
        }
        else{
            getMvpView().showMessage("Something went wrong!");
        }

    }
}
