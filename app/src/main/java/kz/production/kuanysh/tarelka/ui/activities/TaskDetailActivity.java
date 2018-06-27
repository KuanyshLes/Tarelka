package kz.production.kuanysh.tarelka.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import kz.production.kuanysh.tarelka.R;
import kz.production.kuanysh.tarelka.ui.activities.mainactivity.MainActivity;
import kz.production.kuanysh.tarelka.ui.base.BaseActivity;
import kz.production.kuanysh.tarelka.utils.AppConst;

public class TaskDetailActivity extends BaseActivity implements TaskDetailMvpView{

    @Inject
    TaskDetailPresenter<TaskDetailMvpView> mPresenter;

    @BindView(R.id.taskDetailBack)
    ImageView back;

    @BindView(R.id.taskDetailImage)
    ImageView image;

    @BindView(R.id.taskDetailTitle)
    TextView title;

    @BindView(R.id.taskDetailText)
    TextView text;

    private String titleExtra,textExtra;
    private static Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_task_detail);


        getActivityComponent().inject(this);
        setUnBinder(ButterKnife.bind(this));
        mPresenter.onAttach(TaskDetailActivity.this);


        textExtra=getIntent().getStringExtra(AppConst.TASK_KEY);
        if(textExtra!=null){
            Toast.makeText(this, "extra", Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(this, "error:(", Toast.LENGTH_SHORT).show();

        }

        }

    @Override
    protected void setUp() {

    }


    @OnClick(R.id.taskDetailBack)
    public void back(){
        mPresenter.onBackClick();
    }

    @Override
    public void openMainTaskFragment() {
        intent=new Intent(TaskDetailActivity.this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void updateFull(List<String> blog) {

    }
}
