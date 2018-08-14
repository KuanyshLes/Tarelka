package kz.production.kuanysh.tarelka.ui.welcome;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.accountkit.AccessToken;
import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.PhoneNumber;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kz.production.kuanysh.tarelka.R;
import kz.production.kuanysh.tarelka.data.prefs.Board;
import kz.production.kuanysh.tarelka.ui.activities.mainactivity.MainActivity;
import kz.production.kuanysh.tarelka.ui.activities.profileedit.ProfileEditActivity;
import kz.production.kuanysh.tarelka.ui.adapters.WelcomeViewpagerAdapter;
import kz.production.kuanysh.tarelka.ui.base.BaseActivity;
import me.relex.circleindicator.CircleIndicator;

public class LoginActivity extends BaseActivity implements LoginMvpView {

    @Inject
    LoginPresenter<LoginMvpView> mPresenter;

    @BindView(R.id.welcome_viewpager)
    ViewPager viewPager;

    @BindView(R.id.welcome_button)
    TextView skip;

    @BindView(R.id.welcome_indicator)
    CircleIndicator indicator;


    @BindView(R.id.welcome_title)
    TextView title;

    private static List<String> imageLink;
    private static  List<Board> listBoard;
    private static int current=0;
    public static final String KEY_PRO_REG="b jnkl";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //AccountKit.sdkInitialize(getApplicationContext());
        //mCallbackmanager = CallbackManager.Factory.create();

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(LoginActivity.this);
        setUp();

    }

    @Override
    protected void setUp() {
        imageLink=new ArrayList<>();
        listBoard=new ArrayList<>();

        listBoard.add(new Board("Это приложение для желающих " +
                "скорректировать фигуру по уникальной концепсии где 80% резуль" +
                "тата - это то, что вы едите, а 20% - это физическая активность",R.drawable.group146));
        listBoard.add(new Board("Это приложение имеет обратную\n" +
                "связь с профессионалами, которые помогут достичь необходимых результатов и будут следить, что вы едите и пьете бесплатно.",R.drawable.chat_icon));
        listBoard.add(new Board("Вы достигнете нужных результатов,немного изменив привычки питания под присмотром " +
                "профессионалов.",R.drawable.group129));

        WelcomeViewpagerAdapter welcomeViewpagerAdapter=new WelcomeViewpagerAdapter(this,listBoard);
        viewPager.setAdapter(welcomeViewpagerAdapter);
        indicator.setViewPager(viewPager);

        mPresenter.getMvpView().checkState();

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mPresenter.getMvpView().checkState();
                current=position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick(R.id.welcome_button)
    public void skip(){
        if(current!=2){
            current++;
            viewPager.setCurrentItem(current,true);
            mPresenter.getMvpView().checkState();
        }else{
            mPresenter.getMvpView().showLoading();
            onSMSLoginFlow();
            current=0;

        }

    }

    @Override
    protected void onResume() {

        super.onResume();
    }



    public void onSMSLoginFlow() {
        mPresenter.getMvpView().hideLoading();
        final Intent intent = new Intent(this, AccountKitActivity.class);
        AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                new AccountKitConfiguration.AccountKitConfigurationBuilder(
                        LoginType.PHONE,
                        AccountKitActivity.ResponseType.TOKEN); // or .ResponseType.TOKEN
        // ... perform additional configuration ...
        intent.putExtra(
                AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
                configurationBuilder.build());
        startActivityForResult(intent, 99);
    }

    @Override
    protected void onActivityResult(
            final int requestCode,
            final int resultCode,
            final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 99) { // confirm that this response matches your request
            AccountKitLoginResult loginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
            String toastMessage= "";
            if (loginResult.getError() != null) {
                toastMessage = loginResult.getError().getErrorType().getMessage();
            } else if (loginResult.wasCancelled()) {
                toastMessage = "Login Cancelled";
            } else {
                if (loginResult.getAccessToken() != null) {
                    toastMessage = "Success:" + loginResult.getAccessToken().getAccountId();
                    getAccount();
                }
            }
            // Surface the result to your user in an appropriate way.
            //Toast.makeText(this, toastMessage, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Gets current account from Facebook Account Kit which include user's phone number.
     */
    private void getAccount(){
        AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
            @Override
            public void onSuccess(final Account account) {
                // Get Account Kit ID
                String accountKitId = account.getId();

                // Get phone number
                PhoneNumber phoneNumber = account.getPhoneNumber();
                String phoneNumberString = phoneNumber.toString();
                mPresenter.onPhone(phoneNumberString);

                // Surface the result to your user in an appropriate way.
                //Toast.makeText(LoginActivity.this, phoneNumberString, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(final AccountKitError error) {
                Log.e("AccountKit",error.toString());
                // Handle Error
            }
        });

    }

    @Override
    public void openAimsActivity() {
        Intent intent =new Intent(LoginActivity.this,CreateAimActivity.class);
        startActivity(intent);
    }

    @Override
    public void openMainActivity() {
        Intent intent =new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void checkState() {

        if(viewPager.getCurrentItem()==0){
            title.setText("Привет! Вас приветствует Тарелка");
            skip.setTextColor(getResources().getColor(R.color.carbon_grey_400));
            skip.setText("Далее");
        }
        else if(viewPager.getCurrentItem()==1){
            title.setText("Без диет и спортзала!");
            skip.setTextColor(getResources().getColor(R.color.carbon_grey_400));
            skip.setText("Далее");
        }
        else if(viewPager.getCurrentItem()==2){
            skip.setTextColor(getResources().getColor(R.color.colorPrimary));
            skip.setText("Понятно, приступаем");
            title.setText("Приложение бесплатно!");
        }
    }


}
