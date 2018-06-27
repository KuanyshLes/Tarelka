package kz.production.kuanysh.tarelka.ui.activities.test;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.production.kuanysh.tarelka.R;
import kz.production.kuanysh.tarelka.ui.base.BaseActivity;

public class TestActivity extends BaseActivity implements TestMvpView {

    @Inject
    TestPresenter<TestMvpView> mPresenter;

   // @BindView(R.id.test_current_question_number)



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_test);

        getActivityComponent().inject(this);
        setUnBinder(ButterKnife.bind(this));
        mPresenter.onAttach(TestActivity.this);


    }

    @Override
    protected void setUp() {

    }

    @Override
    public void openProgressFragment() {

    }

    @Override
    public void updateTest() {

    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }
}
