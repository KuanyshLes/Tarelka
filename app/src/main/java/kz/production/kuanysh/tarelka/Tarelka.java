package kz.production.kuanysh.tarelka;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.interceptors.HttpLoggingInterceptor;
import com.facebook.accountkit.AccountKit;
import com.yandex.metrica.YandexMetrica;
import com.yandex.metrica.YandexMetricaConfig;

import javax.inject.Inject;

import kz.production.kuanysh.tarelka.data.DataManager;
import kz.production.kuanysh.tarelka.di.component.*;
import kz.production.kuanysh.tarelka.di.module.ApplicationModule;
import kz.production.kuanysh.tarelka.utils.AppConst;

/**
 * Created by User on 26.06.2018.
 */

public class Tarelka extends Application {
    @Inject
    DataManager mDataManager;


    private ApplicationComponent mApplicationComponent;



    @Override
    public void onCreate() {
        super.onCreate();

        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();

        mApplicationComponent.inject(this);
        AccountKit.initialize(getApplicationContext());

        YandexMetricaConfig.Builder configBuilder = YandexMetricaConfig.newConfigBuilder(AppConst.APP_METRICA_API_KEY);
        YandexMetrica.activate(getApplicationContext(), configBuilder.build());
        YandexMetrica.enableActivityAutoTracking(this);

        //AppLogger.init();

        AndroidNetworking.initialize(getApplicationContext());
        if (BuildConfig.DEBUG) {
            AndroidNetworking.enableLogging(HttpLoggingInterceptor.Level.BODY);
        }

    }

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }


    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }
}


