package kz.production.kuanysh.tarelka.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import carbon.widget.LinearLayout;
import kz.production.kuanysh.tarelka.R;
import kz.production.kuanysh.tarelka.data.network.model.chat.Chat;
import kz.production.kuanysh.tarelka.ui.fragments.ChatMvpView;
import kz.production.kuanysh.tarelka.ui.fragments.ChatPresenter;

import static kz.production.kuanysh.tarelka.utils.AppConst.VIEW_TYPE_RECEIVE;
import static kz.production.kuanysh.tarelka.utils.AppConst.VIEW_TYPE_SEND;

/**
 * Created by User on 21.06.2018.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter .ViewHolder> {
    public List<Chat> chat_item;

    @Inject
    ChatPresenter<ChatMvpView> mPresenter;

    public ChatAdapter(List<Chat> chatList) {
        this.chat_item = chatList;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView sended_message,received_message,sended_time,received_time;
        private ImageView image1,image2,image3,image4,image5;

        public ViewHolder(View itemView,int viewType) {
            super(itemView);
            if(viewType==VIEW_TYPE_SEND){
                sended_message= (TextView) itemView.findViewById(R.id.chat_sended_message);
                sended_time= (TextView) itemView.findViewById(R.id.chat_sended_time);
                image1= (ImageView) itemView.findViewById(R.id.chat_send_image1);
                image2= (ImageView) itemView.findViewById(R.id.chat_send_image2);
                image3= (ImageView) itemView.findViewById(R.id.chat_send_image3);
                image4= (ImageView) itemView.findViewById(R.id.chat_send_image4);
                image5= (ImageView) itemView.findViewById(R.id.chat_send_image5);
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
                }else if(chat_item.get(i).getMessage()==null){
                    viewHolder.received_message.setText("");
                }
                viewHolder.received_time.setText(format.getHours()+":"+format.getMinutes());
                break;
            case VIEW_TYPE_SEND:
                if(chat_item.get(i).getMessage()!=null ){
                    viewHolder.sended_message.setText(chat_item.get(i).getMessage().toString());
                }else if(chat_item.get(i).getMessage()==null ){
                    if (chat_item.get(i).getImage()!=null) {
                        Glide.with(viewHolder.image1.getContext()).load(chat_item.get(i).getImage().get(0).toString()).into(viewHolder.image1);
                        Log.d("myTag", "onBindViewHolder: "+chat_item.get(i).getImage().get(0).toString());
                        setSize(viewHolder.image1);
                    }
                    Log.d("myTag", "onBindViewHolder: image message position " + i);

                }
                viewHolder.sended_time.setText(format.getHours()+":"+format.getMinutes());
                break;
        }
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
        Collections.reverse(chats);
        chat_item.addAll(chats);
        notifyDataSetChanged();
    }
    public void setSize(View view){
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(170, 170);
        view.setLayoutParams(layoutParams);
    }

}

