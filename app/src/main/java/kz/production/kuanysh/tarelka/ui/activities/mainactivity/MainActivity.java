package kz.production.kuanysh.tarelka.ui.activities.mainactivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.accountkit.AccessToken;
import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.PhoneNumber;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;

import java.util.Calendar;
import java.util.Random;

import javax.inject.Inject;

import butterknife.ButterKnife;
import kz.production.kuanysh.tarelka.R;
import kz.production.kuanysh.tarelka.push.AlarmReceiver;
import kz.production.kuanysh.tarelka.ui.base.BaseActivity;
import kz.production.kuanysh.tarelka.ui.welcome.LoginActivity;
import kz.production.kuanysh.tarelka.utils.AppConst;
import kz.production.kuanysh.tarelka.helper.BottomNavigationViewEx;
import kz.production.kuanysh.tarelka.ui.fragments.ChatFragment;
import kz.production.kuanysh.tarelka.ui.fragments.MainTaskFragment;
import kz.production.kuanysh.tarelka.ui.fragments.ProfileFragment;
import kz.production.kuanysh.tarelka.ui.fragments.ProgressFragment;

public class MainActivity extends BaseActivity implements MainMvpView{


    @Inject
    MainPresenter<MainMvpView> mPresenter;

    private TextView mTextMessage;

    public static final String TAG_MAINTASK="task";
    public static final String TAG_CHAT="chat";
    public static final String TAG_PROGRESS="progress";
    public static final String TAG_PROFILE="profile";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getActivityComponent().inject(this);
        setUnBinder(ButterKnife.bind(this));
        mPresenter.onAttach(this);

        setUp();
        if(getIntent().getStringExtra(TAG_PROGRESS)!=null){
            if(getIntent().getStringExtra(TAG_PROGRESS).equals(TAG_PROGRESS)){
                mPresenter.onProgressClick();
            }
            else{
                mPresenter.onMainCLick();
            }
        }else{
            mPresenter.onMainCLick();
        }


        mPresenter.getMvpView().fireNotificationMorning();
        mPresenter.getMvpView().fireNotificationAfternoon();
        mPresenter.getMvpView().fireNotificationEvening();
        mPresenter.onMainCLick();

    }

    @Override
    protected void setUp() {
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationViewEx bottomNavigationViewEx= (BottomNavigationViewEx) findViewById(R.id.navigation);
        final Menu menu = bottomNavigationViewEx.getMenu();

        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
        bottomNavigationViewEx.enableAnimation(false);
        bottomNavigationViewEx.enableItemShiftingMode(false);
        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.setTextVisibility(false);


        bottomNavigationViewEx.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_main:
                        MenuItem menuItem0 = menu.getItem(0);
                        menuItem0.setChecked(true);
                        mPresenter.onMainCLick();
                        return true;
                    case R.id.navigation_chat:
                        MenuItem menuItem1 = menu.getItem(0);
                        menuItem1.setChecked(true);
                        mPresenter.onChatClick();
                        return true;
                    case R.id.navigation_useful:
                        MenuItem menuItem2 = menu.getItem(0);
                        menuItem2.setChecked(true);
                        mPresenter.onProgressClick();
                        return true;
                    case R.id.navigation_profile:
                        MenuItem menuItem3 = menu.getItem(0);
                        menuItem3.setChecked(true);
                        mPresenter.onProfileClick();
                        return true;
                }
                return false;
            }
        });
    }


    @Override
    public void openChooseAimActivity() {

    }

    @Override
    public void openMainTaskFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .disallowAddToBackStack()
                .replace(R.id.content_frame, MainTaskFragment.newInstance(), TAG_MAINTASK)
                .commit();
    }

    @Override
    public void openChatFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .disallowAddToBackStack()
                .replace(R.id.content_frame, ChatFragment.newInstance(), TAG_CHAT)
                .commit();
    }

    @Override
    public void openProfileFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .disallowAddToBackStack()
                .replace(R.id.content_frame, ProfileFragment.newInstance(), TAG_PROFILE)
                .commit();
    }

    @Override
    public void openProgressFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .disallowAddToBackStack()
                .replace(R.id.content_frame, ProgressFragment.newInstance(), TAG_PROGRESS)
                .commit();
    }

    @Override
    public void fireNotificationMorning() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent notificationIntent = new Intent(this, AlarmReceiver.class);
        Random ran = new Random();
        int x = ran.nextInt(1000) + 5;
        PendingIntent broadcast = PendingIntent.getBroadcast(this, x, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 14);
        cal.set(Calendar.MINUTE, 2);
        cal.set(Calendar.SECOND, 0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, broadcast);
    }
    @Override
    public void fireNotificationAfternoon() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent notificationIntent = new Intent(this, AlarmReceiver.class);
        Random ran = new Random();
        int x = ran.nextInt(1000) + 5;
        PendingIntent broadcast = PendingIntent.getBroadcast(this, x, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 14);
        cal.set(Calendar.MINUTE, 3);
        cal.set(Calendar.SECOND, 0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, broadcast);
    }

    @Override
    public void fireNotificationEvening() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent notificationIntent = new Intent(this, AlarmReceiver.class);
        Random ran = new Random();
        int x = ran.nextInt(1000) + 5;
        PendingIntent broadcast = PendingIntent.getBroadcast(this, x, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 14);
        cal.set(Calendar.MINUTE, 4);
        cal.set(Calendar.SECOND, 0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, broadcast);
    }

    @Override
    public void onFragmentAttached() {

    }

    @Override
    public void onFragmentDetached(String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment != null) {
            fragmentManager
                    .beginTransaction()
                    .disallowAddToBackStack()
                    .remove(fragment)
                    .commitNow();
        }
    }
}
