package kz.production.kuanysh.tarelka.ui.activities.mainactivity;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Calendar;
import java.util.Random;

import javax.inject.Inject;

import butterknife.ButterKnife;
import kz.production.kuanysh.tarelka.R;
import kz.production.kuanysh.tarelka.app.Config;
import kz.production.kuanysh.tarelka.job.AfternoonNotification;
import kz.production.kuanysh.tarelka.job.EveningNotification;
import kz.production.kuanysh.tarelka.job.MorningNotification;
import kz.production.kuanysh.tarelka.push.AfternoonAlarmReceiver;
import kz.production.kuanysh.tarelka.push.EveningAlarmReceiver;
import kz.production.kuanysh.tarelka.push.MorningAlarmReceiver;
import kz.production.kuanysh.tarelka.ui.activities.profileedit.ProfileEditActivity;
import kz.production.kuanysh.tarelka.ui.base.BaseActivity;
import kz.production.kuanysh.tarelka.ui.welcome.LoginActivity;
import kz.production.kuanysh.tarelka.helper.BottomNavigationViewEx;
import kz.production.kuanysh.tarelka.ui.fragments.ChatFragment;
import kz.production.kuanysh.tarelka.ui.fragments.MainTaskFragment;
import kz.production.kuanysh.tarelka.ui.fragments.ProfileFragment;
import kz.production.kuanysh.tarelka.ui.fragments.ProgressFragment;
import kz.production.kuanysh.tarelka.ui.welcome.SplashScreenActivity;
import kz.production.kuanysh.tarelka.utils.AppConstants;

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
    private Boolean exit = false;
    private static Dialog dialog;
    private static AlertDialog.Builder mBuilder;
    private static View mView;
    public static int PHOTO_COUNT=0;


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
                if(mPresenter.getDataManager().getAlarmSetted()!=null){
                    BottomNavigationViewEx bottomNavigationViewEx= (BottomNavigationViewEx) findViewById(R.id.navigation);
                    final Menu menu = bottomNavigationViewEx.getMenu();
                    MenuItem menuItem0 = menu.getItem(1);
                    menuItem0.setChecked(true);
                    mPresenter.onChatClick();
                }else{
                    mPresenter.getMvpView().openLoginActivity();
                }
            }
        }else if(getIntent().getStringExtra(ProfileEditActivity.KEY_EDIT_PROFILE)!=null){
            if(getIntent().getStringExtra(ProfileEditActivity.KEY_EDIT_PROFILE).equals(ProfileEditActivity.KEY_EDIT_PROFILE)){
                BottomNavigationViewEx bottomNavigationViewEx= (BottomNavigationViewEx) findViewById(R.id.navigation);
                final Menu menu = bottomNavigationViewEx.getMenu();
                MenuItem menuItem0 = menu.getItem(3);
                menuItem0.setChecked(true);
                mPresenter.onProfileClick();
            }
        }else if(getIntent().getStringExtra(ChatFragment.CAMERA_KEY)!=null){
            if(getIntent().getStringExtra(ChatFragment.CAMERA_KEY).equals(ChatFragment.CAMERA_KEY)){
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


     /*   if(mPresenter.getDataManager().getAlarmSetted()==null) {
            mPresenter.getMvpView().fireNotificationMorning();
            mPresenter.getMvpView().fireNotificationAfternoon();
            mPresenter.getMvpView().fireNotificationEvening();
            mPresenter.getDataManager().setAlarmSetted("alarm");
        }*/

        if(mPresenter.getDataManager().getAlarmSetted()==null) {
            MorningNotification.schedule();
            AfternoonNotification.schedule();
            EveningNotification.schedule();
            mPresenter.getDataManager().setAlarmSetted("alarm");
        }








    }


    @Override
    public void onBackPressed() {

        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            if (exit) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Нажмите еще раз чтобы выйти!",
                        Toast.LENGTH_SHORT).show();
                exit = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        exit = false;
                    }
                }, 2 * 1000);

            }
        } else {
            getFragmentManager().popBackStack();
        }

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
        mPresenter.onViewPrepared();


    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }


    @Override
    public void openQuestionLikeDialog() {
        mPresenter.getMvpView().dialogBuilder(R.layout.dialog_question_like);
        TextView yes=(TextView)mView.
                findViewById(R.id.dialog_question_yes);
        TextView no=(TextView)mView.
                findViewById(R.id.dialog_question_no);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                mPresenter.getMvpView().openLikeDialog();
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                mPresenter.getMvpView().openDontLikeDialog();
            }
        });
    }

    @Override
    public void openDontLikeDialog() {
        mPresenter.getMvpView().dialogBuilder(R.layout.dialog_dont_like);
        TextView send=(TextView)mView
                .findViewById(R.id.dialog_dontlike_send);
        TextView cancell=(TextView)mView
                .findViewById(R.id.dialog_dontlike_cancell);
        EditText comment=(EditText)mView
                .findViewById(R.id.dialog_dontlike_comment);

        cancell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                mPresenter.getMvpView().openMail(comment.getText().toString());
            }
        });
    }

    @Override
    public void openLikeDialog() {
        mPresenter.getMvpView().dialogBuilder(R.layout.dialog_like);

        TextView yes=(TextView)mView
                .findViewById(R.id.dialog_like_send_to_playmarket);
        TextView no=(TextView)mView
                .findViewById(R.id.dialog_like_cancell);

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                mPresenter.getMvpView().openGooglePlay();
            }
        });
    }

    @Override
    public void onReceivePhotoCount(int count) {
        PHOTO_COUNT=(count*100)/9;

        if(getIntent().getStringExtra(SplashScreenActivity.KEY_LAUNCH)!=null){
            if(getIntent().getStringExtra(SplashScreenActivity.KEY_LAUNCH).equals(SplashScreenActivity.KEY_LAUNCH)){
                if(mPresenter.getDataManager().getRated()==null){
                    if(mPresenter.getDataManager().getAppLaunchCount()>9 && PHOTO_COUNT>=80){
                        mPresenter.getMvpView().openQuestionLikeDialog();
                        mPresenter.getDataManager().setRated("rated");
                    }
                }


                Log.d("launch", "onCreate:  if percent "+PHOTO_COUNT+" "+" launch count "+
                        mPresenter.getDataManager().getAppLaunchCount());

            }
        }
    }

    @Override
    public void openGooglePlay() {
       // final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
        final String appPackageName = "com.whatsapp";  // getPackageName() from Context or Activity object
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        }
        catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    @Override
    public void openMail(String improvement) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"admin@budi.sh"});
        i.putExtra(Intent.EXTRA_SUBJECT, "Исправление");
        i.putExtra(Intent.EXTRA_TEXT   , improvement);
        try {
            startActivity(Intent.createChooser(i, "Отправить через..."));
        } catch (android.content.ActivityNotFoundException ex) {
            mPresenter.getMvpView().showMessage("Установленных почтовых клиентов не найдено");
          }
    }

    @Override
    public void dialogBuilder(int layoutEx) {
        mBuilder= new AlertDialog.Builder(this);
        mView =getLayoutInflater().inflate(layoutEx,null);
        mBuilder.setView(mView);

        dialog=mBuilder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();

        //size
        Rect displayRectangle = new Rect();
        Window window = getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        //set size
        dialog.getWindow().setLayout((int)(displayRectangle.width() *
                0.8f), (int)(displayRectangle.height() * 0.25f));
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


        Intent notificationIntent = new Intent(this, MorningAlarmReceiver.class);
        Random ran = new Random();
        int x = ran.nextInt(100) + 5;
        PendingIntent broadcast =
                PendingIntent.getBroadcast(this, 1, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.set(Calendar.HOUR_OF_DAY, 17);
        cal.set(Calendar.MINUTE, 45);
        cal.set(Calendar.SECOND, 0);
        Log.d("time ", "fireNotificationMorning: "+cal.getTimeInMillis());

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 4 * 60 * 1000, broadcast);

    }
    @Override
    public void fireNotificationAfternoon() {
        AlarmManager alarmManager2 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent notificationIntent = new Intent(this, AfternoonAlarmReceiver.class);
        Random ran = new Random();
        int x = ran.nextInt(1000) + 5;
        PendingIntent broadcast = PendingIntent.getBroadcast(this, 2, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.set(Calendar.HOUR_OF_DAY, 17);
        cal.set(Calendar.MINUTE, 47);
        cal.set(Calendar.SECOND, 0);
        Log.d("time ", "fireNotificationAfternoon: "+cal.getTimeInMillis());
        alarmManager2.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),4 * 60 * 1000, broadcast);
    }

    @Override
    public void fireNotificationEvening() {
        AlarmManager alarmManager3 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent notificationIntent = new Intent(this, EveningAlarmReceiver.class);
        Random ran = new Random();
        int x = ran.nextInt(1000) + 5;
        PendingIntent broadcast = PendingIntent.getBroadcast(this, 3, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.set(Calendar.HOUR_OF_DAY, 17);
        cal.set(Calendar.MINUTE, 49);
        cal.set(Calendar.SECOND, 0);
        Log.d("time ", "fireNotificationEvening: "+cal.getTimeInMillis());

        alarmManager3.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),4 * 60 * 1000, broadcast);
    }

    @Override
    public void openLoginActivity() {
        Intent intent=new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onMessageReceivedNotification(String title, String message) {

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
