package kz.production.kuanysh.tarelka.ui.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kz.production.kuanysh.tarelka.R;
import kz.production.kuanysh.tarelka.data.network.model.aim.Result;
import kz.production.kuanysh.tarelka.ui.activities.profileedit.ProfileEditActivity;
import kz.production.kuanysh.tarelka.ui.adapters.AimsAdapter;
import kz.production.kuanysh.tarelka.ui.base.BaseActivity;

public class CreateAimActivity extends BaseActivity implements CreateAimMvpView {

    @Inject
    CreateAimPresenter<CreateAimMvpView> mPresenter;

    @BindView(R.id.aims_next)
    ImageView next;

    @BindView(R.id.recycler_aim)
    RecyclerView aims;

    @BindView(R.id.back_aim_edit)
    ImageView back;

    @BindView(R.id.aim_change)
    TextView text;

    List<Result> aimsList;

    LinearLayoutManager linearLayoutManager;

    public static AimsAdapter aimsAdapter;
    private Intent intent;
    public static Stack<Integer> selectedPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_create_aim);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(CreateAimActivity.this);

        setUp();

        if(getIntent().getStringExtra(ProfileEditActivity.KEY_EDIT_AIM)!=null){
            if(getIntent().getStringExtra(ProfileEditActivity.KEY_EDIT_AIM).equals(ProfileEditActivity.KEY_EDIT_AIM)){
                text.setVisibility(View.INVISIBLE);
                text.setEnabled(false);
            }
        }else{
            back.setVisibility(View.INVISIBLE);
            back.setEnabled(false);
        }


    }

    @OnClick(R.id.back_aim_edit)
    public void goBack(){
        mPresenter.getMvpView().openProfileEditActivity();
    }

    @Override
    protected void setUp() {
        aimsList=new ArrayList<>();
        selectedPosition=new Stack<>();
        linearLayoutManager=new LinearLayoutManager(this);
        aimsAdapter=new AimsAdapter(CreateAimActivity.this, aimsList,selectedPosition);
        aims.setAdapter(aimsAdapter);
        aims.setLayoutManager(linearLayoutManager);
        mPresenter.onViewPrepared();
        aimsAdapter.addManager(linearLayoutManager);
    }

    @OnClick(R.id.aims_next)
    public void goFoodChoose(){
        if(getIntent().getStringExtra(ProfileEditActivity.KEY_EDIT_AIM)!=null) {
            if (getIntent().getStringExtra(ProfileEditActivity.KEY_EDIT_AIM).equals(ProfileEditActivity.KEY_EDIT_AIM)) {
                if(!selectedPosition.isEmpty()){
                    mPresenter.onBackClick(aimsList.get(selectedPosition.peek()).getId());
                }else{
                    mPresenter.getMvpView().showMessage("Выберите одну из целей");
                  //  mPresenter.getMvpView().openProfileEditActivity();
                }
            }
        }else{
            mPresenter.getMvpView().check();
        }

    }


    @Override
    public void openFoodsActivity() {
        intent=new Intent(CreateAimActivity.this,ChooseFoodActivity.class);
        startActivity(intent);
    }

    @Override
    public void updateAims(List<Result> aims) {
        aimsList=aims;
        aimsAdapter.addItems(aimsList);
        aimsAdapter.notifyDataSetChanged();
    }

    @Override
    public void check() {
        if(!selectedPosition.isEmpty()){
            mPresenter.onNextClick(aimsList.get(selectedPosition.peek()).getId());
        }else{
            mPresenter.getMvpView().showMessage("Please select one of the items");
        }
    }

    @Override
    public void openProfileEditActivity() {
        Intent intent =new Intent(CreateAimActivity.this, ProfileEditActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }
}
