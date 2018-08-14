package kz.production.kuanysh.tarelka.ui.adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

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

public class ChatAdapter extends RecyclerView.Adapter {
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


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view;
        if(i==VIEW_TYPE_SEND){
            view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.chat_send_row_item, viewGroup, false);
            return new SentMessageHolder(view);
        }else if(i==VIEW_TYPE_RECEIVE){
            view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.chat_receive_row_item, viewGroup, false);
            return new ReceivedMessageHolder(view);
        }
      return null;
    }


    @Override
    public int getItemViewType(int position) {
        if(chat_item.get(position).getFrom().equals("admin")){
            return VIEW_TYPE_RECEIVE;
        }else{
            return  VIEW_TYPE_SEND;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        Chat chat=chat_item.get(i);
        switch (viewHolder.getItemViewType()) {
            case VIEW_TYPE_RECEIVE:
                ((ReceivedMessageHolder) viewHolder).bind(chat);
                break;
            case VIEW_TYPE_SEND:
                ((SentMessageHolder) viewHolder).bind(chat,mContext,String.valueOf(i));
                break;
        }

    }

    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        private TextView received_message,received_time;

        public ReceivedMessageHolder(View itemView) {
            super(itemView);
            received_message = (TextView) itemView.findViewById(R.id.chat_received_message);
            received_time = (TextView) itemView.findViewById(R.id.chat_received_time);
        }

        void bind(Chat chat) {
            String time=chat.getCreatedAt().toString();
            Date format = null;
            try {
                format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            received_message.setText(chat.getMessage());
            if(chat.getMessage()!=null){
                received_message.setText(chat.getMessage().toString());
            }else{
                received_message.setText("");
            }
            received_time.setText(format.getHours()+":"+format.getMinutes());
        }
    }

    private class SentMessageHolder extends RecyclerView.ViewHolder {
        private TextView sended_message,sended_time;
        private ImageView image1;
        private TextView imageCount;
        private ProgressBar progressBar;

        public SentMessageHolder(View itemView) {
            super(itemView);

            sended_message= (TextView) itemView.findViewById(R.id.chat_sended_message);
            sended_time= (TextView) itemView.findViewById(R.id.chat_sended_time);
            imageCount= (TextView) itemView.findViewById(R.id.chat_send_image_count);
            image1= (ImageView) itemView.findViewById(R.id.chat_send_image1);
            progressBar= (ProgressBar) itemView.findViewById(R.id.progressBar);
        }

        void bind(Chat chat,Context context,String position){

            String time=chat.getCreatedAt().toString();
            Date format = null;
            try {
                format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if(chat.getMessage()==null ){




                if(chat.getImage().size()>1){
                    Glide.with(image1.getContext())
                            .load(chat.getImage().get(0).toString()).into(image1);

                    setSize(image1);
                    minSizeLinear(sended_message);
                    image1.setAlpha(115);

                    setSizeLinear(imageCount);
                    imageCount.setText("+"+chat.getImage().size());
                    imageCount.setPadding(0,15,15,0);

                    Log.d("myTag", "onBindViewHolder: multiple image received "+position);
                }else if(chat.getImage().size()==1){

                    minSizeLinear(imageCount);
                    setSize(image1);
                    image1.setAlpha(255);

                    minSizeLinear(sended_message);

                    Glide.with(image1.getContext())
                            .load(chat.getImage().get(0).toString())
                            .into(image1);
                    Log.d("myTag", "onBindViewHolder: image message position "+position);

                }





            }else {
                sended_message.setText(chat.getMessage().toString());
                minSize(image1);
                minSizeLinear(imageCount);
                setSizeWrap(sended_message);
                Log.d("myTag", "bind:message "+chat.getMessage().toString()+" POSITION "+position);
            }

            sended_time.setText(format.getHours()+":"+format.getMinutes());
            image1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                     openDialog(chat,context);
                }
            });

        }


    }


    public void openDialog(Chat chat,Context context){
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

        ImageViewPageAdapter imageViewPageAdapter=new ImageViewPageAdapter(mContext,chat.getImage());
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
    public void setSizeLinear(View view){
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(170, 170);
        view.setLayoutParams(layoutParams);
    }
    public void setSizeWrap(View view){
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);
    }
    public void minSize(View view){
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(0, 0);
        view.setLayoutParams(layoutParams);
    }
    public void minSizeLinear(View view){
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, 0);
        view.setLayoutParams(layoutParams);
    }

    public void addContext(Context context){
        mContext=context;
    }
    public void addImageLink(List<String> add){
        link=add;
    }



}

