package kz.production.kuanysh.tarelka.ui.welcome;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import kz.production.kuanysh.tarelka.R;
import kz.production.kuanysh.tarelka.data.TarelkaDataFactory;
import kz.production.kuanysh.tarelka.ui.activities.mainactivity.MainActivity;
import kz.production.kuanysh.tarelka.ui.adapters.AimsAdapter;

public class ChooseFoodActivity extends AppCompatActivity {

    private AimsAdapter adapter;
    private Intent intent;
    private GridView foods;
    public List<Integer> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_choose_food);

        list=new ArrayList<>();




        foods=(GridView)findViewById(R.id.gridview_food);
        adapter=new AimsAdapter(ChooseFoodActivity.this, TarelkaDataFactory.getFoodsList(),list);
        foods.setAdapter(adapter);


        ImageView next=(ImageView)findViewById(R.id.foods_next);
        next.setBackgroundColor(Color.parseColor("#5F000000"));


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(ChooseFoodActivity.this,MainActivity.class);
                startActivity(intent);
                for(int i=0;i<list.size();i++){
                    Toast.makeText(ChooseFoodActivity.this, TarelkaDataFactory.getFoodsList().get(list.get(i))+"   Item" , Toast.LENGTH_LONG).show();

                }
                //Toast.makeText(ChooseFoodActivity.this, list.size()+" Selected item", Toast.LENGTH_SHORT).show();
            }
        });

    }


}

