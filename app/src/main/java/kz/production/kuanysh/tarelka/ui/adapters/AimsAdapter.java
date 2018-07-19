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
import java.util.Stack;

import kz.production.kuanysh.tarelka.R;
import kz.production.kuanysh.tarelka.data.network.model.aim.Result;
import kz.production.kuanysh.tarelka.utils.AppConst;

public class AimsAdapter extends BaseAdapter {

    private final Context mContext;
    private List<Result> aimsList;
    private Stack<Integer> list;


    public AimsAdapter(Context context, List<Result> aimsList,Stack<Integer> list) {
        this.mContext = context;
        this.aimsList = aimsList;
        this.list=list;
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
        final ImageView select=(ImageView)convertView.findViewById(R.id.aims_select);

        final ViewHolder viewHolder = new ViewHolder(name,select);
        convertView.setTag(viewHolder);

        final ViewHolder getViewHolder = (ViewHolder)convertView.getTag();
        viewHolder.name.setText(aimsList.get(position).getTitle());

        Glide.with(image.getContext())
                .load(aimsList.get(position).getImage())
                .into(image);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.push(position);
                for(int i=0;i<aimsList.size();i++){
                    View viewItem=parent.getChildAt(i);
                    if(position!=i){
                        setAnimation(viewItem,1);
                    }else{
                        setAnimation(viewItem,2);

                    }
                }


            }
        });

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


}