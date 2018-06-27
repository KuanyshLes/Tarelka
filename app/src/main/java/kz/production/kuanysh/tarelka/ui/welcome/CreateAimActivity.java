package kz.production.kuanysh.tarelka.ui.welcome;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kz.production.kuanysh.tarelka.R;
import kz.production.kuanysh.tarelka.data.TarelkaDataFactory;
import kz.production.kuanysh.tarelka.ui.adapters.AimsAdapter;

public class CreateAimActivity extends AppCompatActivity {
    @BindView(R.id.aims_next)
    ImageView next;

    @BindView(R.id.gridview_aim)
    GridView aims;

    private AimsAdapter aimsAdapter;
    private Intent intent;
    public List<Integer> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_create_aim);

        list=new ArrayList<>();

        ButterKnife.bind(this);


        aimsAdapter=new AimsAdapter(CreateAimActivity.this, TarelkaDataFactory.getAimsList(),list);
        aims.setAdapter(aimsAdapter);

    }

    @OnClick(R.id.aims_next)
    public void goFoodChoose(){
        intent=new Intent(CreateAimActivity.this,ChooseFoodActivity.class);
        startActivity(intent);
    }


}
