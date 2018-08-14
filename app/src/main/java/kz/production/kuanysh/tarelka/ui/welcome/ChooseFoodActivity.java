package kz.production.kuanysh.tarelka.ui.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kz.production.kuanysh.tarelka.R;
import kz.production.kuanysh.tarelka.data.network.model.aim.Result;
import kz.production.kuanysh.tarelka.ui.activities.mainactivity.MainActivity;
import kz.production.kuanysh.tarelka.ui.activities.profileedit.ProfileEditActivity;
import kz.production.kuanysh.tarelka.ui.adapters.FoodsAdapter;
import kz.production.kuanysh.tarelka.ui.base.BaseActivity;

import static kz.production.kuanysh.tarelka.ui.welcome.LoginActivity.KEY_PRO_REG;

public class ChooseFoodActivity extends BaseActivity implements ChooseFoodMvpView {

    @Inject
    ChooseFoodPresenter<ChooseFoodMvpView> mPresenter;

    @BindView(R.id.foods_next)
    ImageView next;

    @BindView(R.id.gridview_food)
    GridView foods;

    @BindView(R.id.food_title_text)
    TextView foods_title;

    List<Result> foodsList;
    List<Result> newfoodsList;

    @Inject
    FoodsAdapter adapter;

    private Intent intent;
    public HashSet<Integer> list;
    public static int STEP=0;


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
            if(STEP!=1){
                STEP++;
                mPresenter.onNextClick(list);
                foods_title.setText("Что из этого Вы особенно любите?");

                foodsList.clear();
                list.clear();
                adapter.addItems(newfoodsList);
                adapter.addNextItems(foodsList);
                adapter.notifyDataSetChanged();
            }else if(STEP==1){
                mPresenter.onSendFavourite(list);
            }

        }else {
            mPresenter.getMvpView().showMessage("Выберите из этого списка");
        }

    }

    @Override
    protected void setUp() {
        foodsList=new ArrayList<>();
        list=new HashSet<>();
        newfoodsList=new ArrayList<>();
       // adapter=new FoodsAdapter(ChooseFoodActivity.this, foodsList,list,newfoodsList);
        adapter.addContext(this);
        adapter.addItems(foodsList);
        adapter.addSelectableList(list);
        adapter.addNextItems(newfoodsList);
        foods.setAdapter(adapter);
        mPresenter.onViewPrepared();
    }


    @Override
    public void updateFoods(List<Result> foods) {
        foodsList.clear();
        foodsList.addAll(foods);

      //  adapter.addItems(foodsList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void openMainActivity() {
        intent=new Intent(ChooseFoodActivity.this,ProfileEditActivity.class);
        intent.putExtra(KEY_PRO_REG,KEY_PRO_REG);
        startActivity(intent);
    }


    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }
}

