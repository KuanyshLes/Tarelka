package kz.production.kuanysh.tarelka.ui.welcome;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import kz.production.kuanysh.tarelka.ui.activities.mainactivity.MainActivity;
import kz.production.kuanysh.tarelka.ui.adapters.WelcomeViewpagerAdapter;
import kz.production.kuanysh.tarelka.ui.base.BaseActivity;
import me.relex.circleindicator.CircleIndicator;

public class LoginActivity extends BaseActivity implements LoginMvpView {

    @Inject
    LoginPresenter<LoginMvpView> mPresenter;

    @BindView(R.id.welcome_viewpager)
    ViewPager viewPager;

    @BindView(R.id.welcome_button)
    Button skip;

    @BindView(R.id.welcome_indicator)
    CircleIndicator indicator;

    private static List<String> imageLink;



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
        imageLink.add("https://dj0j0ofql4htg.cloudfront.net/cms2/image_manager/uploads/News/299128/7/default.jpg");
        imageLink.add("https://static.independent.co.uk/s3fs-public/thumbnails/image/2016/04/29/13/lionel-messi.jpg?w968h681");
        imageLink.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSF9UhXJ30iOSnAILilMbR-FGD0V5gWFxvQWCS9sOhE5FkW5DyUWg");

        WelcomeViewpagerAdapter welcomeViewpagerAdapter=new WelcomeViewpagerAdapter(this,imageLink);
        viewPager.setAdapter(welcomeViewpagerAdapter);
        indicator.setViewPager(viewPager);
    }

    @OnClick(R.id.welcome_button)
    public void skip(){
        if(mPresenter.getDataManager().donePhoneConfirmation()==null){
            mPresenter.getDataManager().setDonePhoneConfirmation("action");
            onSMSLoginFlow();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }



    public void onSMSLoginFlow() {
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


}
