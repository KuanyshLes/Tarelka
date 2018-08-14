package kz.production.kuanysh.tarelka.ui.activities.mainactivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import kz.production.kuanysh.tarelka.data.DataManager;
import kz.production.kuanysh.tarelka.data.network.model.rating.Rating;
import kz.production.kuanysh.tarelka.data.network.model.sendmeal.SendMeal;
import kz.production.kuanysh.tarelka.ui.base.BasePresenter;
import kz.production.kuanysh.tarelka.utils.rx.SchedulerProvider;

/**
 * Created by User on 26.06.2018.
 */

public class MainPresenter <V extends MainMvpView> extends BasePresenter<V>
        implements MainMvpPresenter<V> {

    @Inject
    public MainPresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onMainCLick() {
        getMvpView().openMainTaskFragment();
    }

    @Override
    public void onViewPrepared() {
        if (getMvpView().isNetworkConnected()) {
            getCompositeDisposable().add(getDataManager()
                    .getApiHelper().getRating(getDataManager().getAccessToken())
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(new Consumer<Rating>() {
                        @Override
                        public void accept(@NonNull Rating rating)
                                throws Exception {

                            if(rating.getStatusCode()==200){
                                getMvpView().onReceivePhotoCount(rating.getResult());
                            }else if(rating.getStatusCode()==201){
                                // getMvpView().showMessage("Это блюдо уже выбрано");
                            }


                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(@NonNull Throwable throwable)
                                throws Exception {
                            if (!isViewAttached()) {
                                return;
                            }

                            getMvpView().showMessage("Возникло ошибка!");

                        }
                    }));
        }else{
            getMvpView().onError("Нет подключения к интернету!");
        }
    }

    @Override
    public void onChatClick() {
        getMvpView().openChatFragment();
    }

    @Override
    public void onProfileClick() {
        getMvpView().openProfileFragment();
    }

    @Override
    public void onProgressClick() {
        getMvpView().openProgressFragment();
    }
}
