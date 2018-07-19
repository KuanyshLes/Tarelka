package kz.production.kuanysh.tarelka.ui.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kz.production.kuanysh.tarelka.R;
import kz.production.kuanysh.tarelka.data.network.model.aim.Result;
import kz.production.kuanysh.tarelka.ui.adapters.AimsAdapter;
import kz.production.kuanysh.tarelka.ui.base.BaseActivity;

public class CreateAimActivity extends BaseActivity implements CreateAimMvpView {

    @Inject
    CreateAimPresenter<CreateAimMvpView> mPresenter;

    @BindView(R.id.aims_next)
    ImageView next;

    @BindView(R.id.gridview_aim)
    GridView aims;

    List<Result> aimsList;

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


    }

    @Override
    protected void setUp() {
        aimsList=new ArrayList<>();
        selectedPosition=new Stack<>();
        aimsAdapter=new AimsAdapter(CreateAimActivity.this, aimsList,selectedPosition);
        aims.setAdapter(aimsAdapter);
        mPresenter.onViewPrepared();
    }

    @OnClick(R.id.aims_next)
    public void goFoodChoose(){
        mPresenter.getMvpView().check();
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
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }
}
