package kz.production.kuanysh.tarelka.ui.adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import carbon.Carbon;
import carbon.widget.ConstraintLayout;
import carbon.widget.LinearLayout;
import kz.production.kuanysh.tarelka.R;
import kz.production.kuanysh.tarelka.data.network.model.chat.Chat;
import kz.production.kuanysh.tarelka.ui.fragments.ChatMvpView;
import kz.production.kuanysh.tarelka.ui.fragments.ChatPresenter;
import me.relex.circleindicator.CircleIndicator;

import static kz.production.kuanysh.tarelka.utils.AppConst.VIEW_TYPE_RECEIVE;
import static kz.production.kuanysh.tarelka.utils.AppConst.VIEW_TYPE_SEND;

/**
 * Created by User on 21.06.2018.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter .ViewHolder> {
    public List<Chat> chat_item;
    public List<String> link;
    Context mContext;
    private static Dialog dialog;
    private static AlertDialog.Builder mBuilder;

    @Inject
    ChatPresenter<ChatMvpView> mPresenter;

    public ChatAdapter(List<Chat> chatList) {
        this.chat_item = chatList;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView sended_message,received_message,sended_time,received_time;
        private ImageView image1;
        private TextView imageCount;

        public ViewHolder(View itemView,int viewType) {
            super(itemView);
            if(viewType==VIEW_TYPE_SEND){
                sended_message= (TextView) itemView.findViewById(R.id.chat_sended_message);
                sended_time= (TextView) itemView.findViewById(R.id.chat_sended_time);
                imageCount= (TextView) itemView.findViewById(R.id.chat_send_image_count);
                image1= (ImageView) itemView.findViewById(R.id.chat_send_image1);
            }else if(viewType==VIEW_TYPE_RECEIVE){
                received_message = (TextView) itemView.findViewById(R.id.chat_received_message);
                received_time = (TextView) itemView.findViewById(R.id.chat_received_time);
            }

        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        ViewHolder viewHolder=null;
        switch (i) {
            case VIEW_TYPE_SEND:
                View v = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.chat_send_row_item, viewGroup, false);
                viewHolder= new ViewHolder(v,i);
                return viewHolder;
            case VIEW_TYPE_RECEIVE:
                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.chat_receive_row_item, viewGroup, false);
                viewHolder= new ViewHolder(view,i);
                return viewHolder;
        }
        return viewHolder;
    }



    @Override
    public int getItemViewType(int position) {
        int viewType=VIEW_TYPE_SEND;
        if(chat_item.get(position).getFrom().equals("admin")){
            viewType=VIEW_TYPE_RECEIVE;
        }
        return viewType;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        String time=chat_item.get(i).getCreatedAt().toString();
        Date format = null;
        try {
            format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        switch (viewHolder.getItemViewType()) {
            case VIEW_TYPE_RECEIVE:
                if(chat_item.get(i).getMessage()!=null){
                    viewHolder.received_message.setText(chat_item.get(i).getMessage().toString());
                }else{
                    viewHolder.received_message.setText("");
                }
                viewHolder.received_time.setText(format.getHours()+":"+format.getMinutes());
                break;
            case VIEW_TYPE_SEND:
                if(chat_item.get(i).getMessage()!=null ){
                    viewHolder.sended_message.setText(chat_item.get(i).getMessage().toString());
                    minSize(viewHolder.image1);
                }else if(chat_item.get(i).getMessage()==null && chat_item.get(i).getImage()!=null ){
                    //minSize(viewHolder.imageCount);
                    //viewHolder.sended_message.setVisibility(View.GONE);
                    if(chat_item.get(i).getImage().size()>1){
                        Glide.with(viewHolder.image1.getContext())
                                .load(chat_item.get(i).getImage().get(0).toString())
                                .into(viewHolder.image1);
                        setSize(viewHolder.image1);
                        viewHolder.image1.setAlpha(115);
                        //setSize(viewHolder.imageCount);
                        //viewHolder.imageCount.setText("+"+chat_item.get(i).getImage().size());
                        //viewHolder.imageCount.setPadding(63,63,0,0);
                        Log.d("myTag", "onBindViewHolder: multiple image received");
                    }else {
                        //minSize(viewHolder.imageCount);
                        Glide.with(viewHolder.image1.getContext())
                                .load(chat_item.get(i).getImage().get(0).toString())
                                .into(viewHolder.image1);
                        setSize(viewHolder.image1);
                        Log.d("myTag", "onBindViewHolder: image message position " + i);

                    }

                }
                viewHolder.sended_time.setText(format.getHours()+":"+format.getMinutes());
                viewHolder.image1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openDialog(i,mContext);
                    }
                });

                break;
        }


    }

    public void openDialog(int position,Context context){
        mBuilder= new AlertDialog.Builder(context);
        View mView =LayoutInflater.from(context).inflate(R.layout.dialog_open_image,null);
        mBuilder.setView(mView);

        dialog=mBuilder.create();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();

        Window window = dialog.getWindow();
        window.setLayout(android.widget.LinearLayout.LayoutParams.MATCH_PARENT, android.widget.LinearLayout.LayoutParams.MATCH_PARENT);

        ImageViewPageAdapter imageViewPageAdapter=new ImageViewPageAdapter(mContext,chat_item.get(position).getImage());
        ViewPager pager=(ViewPager)dialog.findViewById(R.id.viewpager);
        pager.setAdapter(imageViewPageAdapter);

        CircleIndicator indicator = (CircleIndicator) dialog.findViewById(R.id.indicator);
        indicator.setViewPager(pager);


        TextView back=(TextView) mView.findViewById(R.id.viewpager_cancell);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return chat_item.size();
    }
    public void addItems(List<Chat> chats) {
        chat_item=chats;
        notifyDataSetChanged();
    }
    public void removeItems(){
        chat_item.clear();
    }

    public void setSize(View view){
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(170, 170);
        view.setLayoutParams(layoutParams);
    }
    public void minSize(View view){
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(0, 0);
        view.setLayoutParams(layoutParams);
    }

    public void addContext(Context context){
        mContext=context;
    }
    public void addImageLink(List<String> add){
        link=add;
    }



}

