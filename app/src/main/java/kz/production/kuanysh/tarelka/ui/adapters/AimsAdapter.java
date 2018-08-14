package kz.production.kuanysh.tarelka.ui.adapters;

/**
 * Created by User on 19.06.2018.
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Stack;

import kz.production.kuanysh.tarelka.R;
import kz.production.kuanysh.tarelka.data.network.model.aim.Result;
import kz.production.kuanysh.tarelka.utils.AppConst;

public class AimsAdapter extends RecyclerView.Adapter<AimsAdapter.ViewHolder> {

    private final Context mContext;
    private List<Result> aimsList;
    private Stack<Integer> list;
    private LinearLayoutManager mLinearLayoutManager;
    private static int selected;
    private static int beforeSelected=Integer.MAX_VALUE;
    private static Boolean aBoolean;


    public AimsAdapter(Context context, List<Result> aimsList,Stack<Integer> list) {
        this.mContext = context;
        this.aimsList = aimsList;
        this.list=list;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
         private CardView cardView;
         public ViewHolder(CardView itemView) {
             super(itemView);
             cardView=itemView;
         }

    }


    @Override
    public AimsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        CardView cv = (CardView) LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.aims_grid_item, viewGroup, false);
        return new AimsAdapter.ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CardView cardView = holder.cardView;

        final TextView name=(TextView)cardView.findViewById(R.id.aims_name);
        final ImageView image=(ImageView)cardView.findViewById(R.id.aims_grid_item_image);
        final ImageView select=(ImageView)cardView.findViewById(R.id.aims_select);


        name.setText(aimsList.get(position).getTitle());

        Glide.with(image.getContext())
                .load(aimsList.get(position).getImage())
                .into(image);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.push(position);
                selected=position;

                if(beforeSelected!=Integer.MAX_VALUE){
                    setAnimation(mLinearLayoutManager.getChildAt(beforeSelected),1);
                }

                setAnimation(mLinearLayoutManager.getChildAt(position),2);
                beforeSelected=position;




            }
        });
    }

    @Override
    public int getItemCount() {
        return aimsList.size();
    }
    private void setAnimation(View viewItem,int code){
        TextView name= (TextView) viewItem.findViewById(R.id.aims_name);
        ImageView select= (ImageView) viewItem.findViewById(R.id.aims_select);

        if(code==1){
            Animation animate = new AlphaAnimation(0f, 1.0f);
            animate.setDuration(600);
            animate.setFillAfter(true);
            name.startAnimation(animate);

            Animation animate1 = new AlphaAnimation(1f, 0f);
            animate.setDuration(600);
            animate.setFillAfter(true);
            select.startAnimation(animate1);
            select.setVisibility(View.GONE);
        }else if(code==2){
            Animation animate = new AlphaAnimation(0f, 1.0f);
            animate.setDuration(600);
            animate.setFillAfter(true);
            name.startAnimation(animate);
            select.startAnimation(animate);
            select.setVisibility(View.VISIBLE);

        }
    }
    public void addItems(List<Result> aims){
        aimsList.addAll(aims);
    }
    public void addManager(LinearLayoutManager linearLayoutManager){
           mLinearLayoutManager=linearLayoutManager;
    }


}