package kz.production.kuanysh.tarelka.ui.adapters;

/**
 * Created by User on 19.06.2018.
 */

import android.content.Context;
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

import kz.production.kuanysh.tarelka.R;
import kz.production.kuanysh.tarelka.data.network.model.aim.Result;
import kz.production.kuanysh.tarelka.utils.AppConst;

public class AimsAdapter extends BaseAdapter {

    private final Context mContext;
    private List<Result> aimsList;
    private List<Integer> list;


    public AimsAdapter(Context context, List<Result> aimsList,List<Integer> list) {
        this.mContext = context;
        this.aimsList = aimsList;
        this.list = list;
    }


    @Override
    public int getCount() {
        return aimsList.size();
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public Object getItem(int position) {
        return null;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.aims_grid_item, null);
        }

        final TextView name=(TextView)convertView.findViewById(R.id.aims_name);
        final ImageView image=(ImageView)convertView.findViewById(R.id.aims_grid_item_image);
        name.setPadding(0,20,0,0);

        final ImageView select=(ImageView)convertView.findViewById(R.id.aims_select);


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "clicked", Toast.LENGTH_SHORT).show();
                if(select.getVisibility()==View.GONE){
                    list.add(position);
                    setAnimation(name,select, AppConst.TAG_GONE);
                }else if(select.getVisibility()==View.VISIBLE){
                    list.remove(position);
                    setAnimation(name,select, AppConst.TAG_VISIBLE);
                }
            }
        });



        final ViewHolder viewHolder = new ViewHolder(name,select);
        convertView.setTag(viewHolder);

        final ViewHolder getViewHolder = (ViewHolder)convertView.getTag();
        viewHolder.name.setText(aimsList.get(position).getTitle());

        Glide.with(mContext)
                .load(aimsList.get(position).getImage())
                .into(image);
        Toast.makeText(mContext, aimsList.get(position).getTitle()+"", Toast.LENGTH_SHORT).show();


        return convertView;
    }
    private class ViewHolder {
        private TextView name;
        private ImageView select;

        public ViewHolder(TextView name,ImageView select) {
            this.name = name;
            this.select = select;
        }
    }
    private void setAnimation(TextView name,ImageView select,String status){
        if(status.equals(AppConst.TAG_GONE)){
            Animation animate = new AlphaAnimation(0f, 1.0f);
            animate.setDuration(600);
            animate.setFillAfter(true);
            name.startAnimation(animate);
            name.setPadding(0,0,0,0);
            select.startAnimation(animate);
            select.setVisibility(View.VISIBLE);

        }else if(status.equals(AppConst.TAG_VISIBLE)){
            Animation animate = new AlphaAnimation(0f, 1.0f);
            animate.setDuration(600);
            animate.setFillAfter(true);
            name.startAnimation(animate);
            name.setPadding(0,15,0,0);

            Animation animate1 = new AlphaAnimation(1f, 0f);
            animate.setDuration(600);
            animate.setFillAfter(true);
            select.startAnimation(animate1);
            select.setVisibility(View.GONE);
        }


        //return animate;
    }
    public void addItems(List<Result> aims){
        aimsList=aims;
    }


}