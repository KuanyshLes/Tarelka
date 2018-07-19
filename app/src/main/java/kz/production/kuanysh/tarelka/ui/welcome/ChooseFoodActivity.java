package kz.production.kuanysh.tarelka.ui.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kz.production.kuanysh.tarelka.R;
import kz.production.kuanysh.tarelka.data.network.model.aim.Result;
import kz.production.kuanysh.tarelka.ui.activities.mainactivity.MainActivity;
import kz.production.kuanysh.tarelka.ui.adapters.FoodsAdapter;
import kz.production.kuanysh.tarelka.ui.base.BaseActivity;

public class ChooseFoodActivity extends BaseActivity implements ChooseFoodMvpView {

    @Inject
    ChooseFoodPresenter<ChooseFoodMvpView> mPresenter;

    @BindView(R.id.foods_next)
    ImageView next;

    @BindView(R.id.gridview_food)
    GridView foods;

    List<Result> foodsList;

    private FoodsAdapter adapter;
    private Intent intent;
    public HashSet<Integer> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_choose_food);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(ChooseFoodActivity.this);
        setUp();

    }

    @OnClick(R.id.foods_next)
    public void openmain(){
        if(!list.isEmpty()){
            mPresenter.onNextClick(list);
        }else {
            mPresenter.getMvpView().showMessage("Please select a meal(meals)");
        }

    }

    @Override
    protected void setUp() {
        foodsList=new ArrayList<>();
        list=new HashSet<>();
        adapter=new FoodsAdapter(ChooseFoodActivity.this, foodsList,list);
        foods.setAdapter(adapter);
        mPresenter.onViewPrepared();
    }


    @Override
    public void updateFoods(List<Result> foods) {
        foodsList=foods;
        adapter.addItems(foodsList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void openMainActivity() {
        intent=new Intent(ChooseFoodActivity.this,MainActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }
}

