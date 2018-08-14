package kz.production.kuanysh.tarelka.ui.welcome;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.production.kuanysh.tarelka.R;
import kz.production.kuanysh.tarelka.data.DataManager;
import kz.production.kuanysh.tarelka.ui.base.BaseActivity;
import kz.production.kuanysh.tarelka.utils.AppConst;
import kz.production.kuanysh.tarelka.ui.activities.mainactivity.MainActivity;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashScreenActivity extends BaseActivity implements SplashMvpView {

    @Inject
    SplashPresenter<SplashMvpView> mPresenter;

    private Intent intent;
    public static final String KEY_LAUNCH="lauch";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash_screen);

        getActivityComponent().inject(this);
        setUnBinder(ButterKnife.bind(this));

        if(mPresenter.getDataManager().getAppLaunchCount()==0){
            mPresenter.getDataManager().setAppLaunchCount(1);
        }else{
            mPresenter.getDataManager().setAppLaunchCount(mPresenter.getDataManager().getAppLaunchCount()+1);
        }


        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                if (mPresenter.getDataManager().getCurrentUserLoggedInMode() == DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT.getType()) {
                    intent =new Intent(SplashScreenActivity.this,LoginActivity.class);
                    startActivity(intent);
                } else {
                    intent =new Intent(SplashScreenActivity.this,MainActivity.class);
                    intent.putExtra(KEY_LAUNCH,KEY_LAUNCH);
                    startActivity(intent);
                }
            }
        }, AppConst.SPLASH_DISPLAY_TIME);

    }

    @Override
    protected void setUp() {

    }

    @Override
    public void openMainActivity() {
        Intent intent =new Intent(SplashScreenActivity.this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void openLoginActivity() {
        intent =new Intent(SplashScreenActivity.this,LoginActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }
}
