package kz.production.kuanysh.tarelka.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kz.production.kuanysh.tarelka.R;
import kz.production.kuanysh.tarelka.data.network.model.main.Result;
import kz.production.kuanysh.tarelka.data.network.model.main.Task;
import kz.production.kuanysh.tarelka.ui.activities.mainactivity.MainActivity;
import kz.production.kuanysh.tarelka.ui.base.BaseActivity;
import kz.production.kuanysh.tarelka.ui.fragments.MainTaskFragment;
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



    @BindView(R.id.task_webview)
    WebView webView;

    private Task result;
    private static Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);


        getActivityComponent().inject(this);
        setUnBinder(ButterKnife.bind(this));
        mPresenter.onAttach(TaskDetailActivity.this);

        result=getIntent().getParcelableExtra(MainTaskFragment.KEY_MAIN_TASK);
        if(result!=null){
            title.setText(result.getTitle());
            Glide.with(TaskDetailActivity.this).load(AppConst.BASE_URL+result.getImage()).into(image);
            /*if(mPresenter.getMvpView().isNetworkConnected()){
                if (result.getImage().toString().startsWith("http")) {
                    Glide.with(TaskDetailActivity.this).load(result.getImage()).into(image);
                }else{

                }

            }else{
                mPresenter.getMvpView().onError("Нет подключения к интернету!");
            }*/


            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadDataWithBaseURL("", result.getText(), "text/html", "UTF-8", "");

        }else{
            Toast.makeText(this, "Couldn't get task detail:(", Toast.LENGTH_SHORT).show();

        }

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getMvpView().showMessage("Image clicked!");
            }
        });


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
