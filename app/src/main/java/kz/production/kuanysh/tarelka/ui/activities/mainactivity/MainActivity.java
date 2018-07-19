package kz.production.kuanysh.tarelka.ui.activities.mainactivity;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
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
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Calendar;
import java.util.Random;

import javax.inject.Inject;

import butterknife.ButterKnife;
import kz.production.kuanysh.tarelka.R;
import kz.production.kuanysh.tarelka.app.Config;
import kz.production.kuanysh.tarelka.data.network.model.main.Main;
import kz.production.kuanysh.tarelka.push.AlarmReceiver;
import kz.production.kuanysh.tarelka.services.MyFirebaseMessagingService;
import kz.production.kuanysh.tarelka.ui.base.BaseActivity;
import kz.production.kuanysh.tarelka.ui.welcome.LoginActivity;
import kz.production.kuanysh.tarelka.utils.AppConst;
import kz.production.kuanysh.tarelka.helper.BottomNavigationViewEx;
import kz.production.kuanysh.tarelka.ui.fragments.ChatFragment;
import kz.production.kuanysh.tarelka.ui.fragments.MainTaskFragment;
import kz.production.kuanysh.tarelka.ui.fragments.ProfileFragment;
import kz.production.kuanysh.tarelka.ui.fragments.ProgressFragment;
import kz.production.kuanysh.tarelka.utils.AppConstants;
import kz.production.kuanysh.tarelka.utils.NotificationUtils;

public class MainActivity extends BaseActivity implements MainMvpView{


    @Inject
    MainPresenter<MainMvpView> mPresenter;

    private TextView mTextMessage;

    public static final String TAG_MAINTASK="task";
    public static final String TAG_CHAT="chat";
    public static final String TAG_PROGRESS="progress";
    public static final String TAG_PROFILE="profile";
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    public static final String MAINACTIVITY_KEY="keyTask";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getActivityComponent().inject(this);
        setUnBinder(ButterKnife.bind(this));
        mPresenter.onAttach(this);

        setUp();
        FirebaseMessaging.getInstance().subscribeToTopic(mPresenter.getDataManager().getAccessToken()+Config.TOPIC_GLOBAL);
        if(getIntent().getStringExtra(TAG_PROGRESS)!=null){
            if(getIntent().getStringExtra(TAG_PROGRESS).equals(TAG_PROGRESS)){
                BottomNavigationViewEx bottomNavigationViewEx= (BottomNavigationViewEx) findViewById(R.id.navigation);
                final Menu menu = bottomNavigationViewEx.getMenu();
                MenuItem menuItem0 = menu.getItem(2);
                menuItem0.setChecked(true);
                mPresenter.onProgressClick();
            }
            else{
                mPresenter.onMainCLick();
            }
        }else if(getIntent().getStringExtra(AppConstants.PUSH_ACTION)!=null){
            if(getIntent().getStringExtra(AppConstants.PUSH_ACTION).equals(AppConstants.PUSH_ACTION)){
                BottomNavigationViewEx bottomNavigationViewEx= (BottomNavigationViewEx) findViewById(R.id.navigation);
                final Menu menu = bottomNavigationViewEx.getMenu();
                MenuItem menuItem0 = menu.getItem(1);
                menuItem0.setChecked(true);
                mPresenter.onChatClick();
            }
        }
        else{
            mPresenter.onMainCLick();
        }


        //mPresenter.getMvpView().fireNotificationMorning();
       // mPresenter.getMvpView().fireNotificationAfternoon();
       // mPresenter.getMvpView().fireNotificationEvening();

    }

    @Override
    protected void setUp() {
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationViewEx bottomNavigationViewEx= (BottomNavigationViewEx) findViewById(R.id.navigation);
        final Menu menu = bottomNavigationViewEx.getMenu();


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


        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received
                    String message = intent.getStringExtra(AppConstants.PUSH_KEY);

                    mPresenter.getMvpView().onMessageReceivedNotification("admin",message);
                    mPresenter.getMvpView().showMessage("New from admin:" + message);

                }
            }
        };
    }
    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
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
        int x = ran.nextInt(100) + 5;
        PendingIntent broadcast = PendingIntent.getBroadcast(this, x, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 8);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, broadcast);

    }
    @Override
    public void fireNotificationAfternoon() {
        AlarmManager alarmManager2 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent notificationIntent = new Intent(this, AlarmReceiver.class);
        Random ran = new Random();
        int x = ran.nextInt(1000) + 5;
        PendingIntent broadcast = PendingIntent.getBroadcast(this, x, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 13);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 0);
        alarmManager2.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, broadcast);
    }

    @Override
    public void fireNotificationEvening() {
        AlarmManager alarmManager3 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent notificationIntent = new Intent(this, AlarmReceiver.class);
        Random ran = new Random();
        int x = ran.nextInt(1000) + 5;
        PendingIntent broadcast = PendingIntent.getBroadcast(this, x, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 18);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 0);
        alarmManager3.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, broadcast);
    }

    @Override
    public void onMessageReceivedNotification(String title, String message) {
        mPresenter.getMvpView().showMessage("in realizing"+message);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setTicker("New message from admin")
                        .setContentTitle("Tarelka - Admin")
                        .setContentText(message);

        Notification notification = builder.build();

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);


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
