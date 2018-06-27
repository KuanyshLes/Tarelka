package kz.production.kuanysh.tarelka.ui.welcome;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.production.kuanysh.tarelka.R;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash_screen);

        getActivityComponent().inject(this);
        setUnBinder(ButterKnife.bind(this));



        ImageView splash=(ImageView)findViewById(R.id.imageView) ;

        Thread timer=new Thread()
        {
            public void run() {
                try {
                    sleep(AppConst.SPLASH_DISPLAY_TIME);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                finally
                {
                    mPresenter.onAttach(SplashScreenActivity.this);
                }
            }
        };
        timer.start();

    }

    @Override
    protected void setUp() {

    }

    @Override
    public void openMainActivity() {
        intent =new Intent(SplashScreenActivity.this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void openLoginActivity() {
        intent =new Intent(SplashScreenActivity.this,LoginActivity.class);
        startActivity(intent);
    }
}
