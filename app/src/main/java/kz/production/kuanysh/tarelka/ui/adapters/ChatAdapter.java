package kz.production.kuanysh.tarelka.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import kz.production.kuanysh.tarelka.R;
import kz.production.kuanysh.tarelka.model.ChatItem;

import static kz.production.kuanysh.tarelka.utils.AppConst.VIEW_TYPE_RECEIVE;
import static kz.production.kuanysh.tarelka.utils.AppConst.VIEW_TYPE_SEND;

/**
 * Created by User on 21.06.2018.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter .ViewHolder> {
    public List<ChatItem> chat_item;
    public Context context;
    

    public ChatAdapter(List<ChatItem> chat_item, Context context) {
        this.chat_item = chat_item;
        this.context = context;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView sended_message,received_message,sended_time,received_time;


        public ViewHolder(View itemView,int viewType) {
            super(itemView);
            if(viewType==VIEW_TYPE_SEND){
                sended_message= (TextView) itemView.findViewById(R.id.chat_sended_message);
                sended_time= (TextView) itemView.findViewById(R.id.chat_sended_time);
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
        if(chat_item.get(position).getWhoIam() == VIEW_TYPE_RECEIVE){
            viewType=VIEW_TYPE_RECEIVE;
        }
        return viewType;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        switch (viewHolder.getItemViewType()) {
            case VIEW_TYPE_RECEIVE:
                viewHolder.received_message.setText(chat_item.get(i).getMessage().toString());
                viewHolder.received_time.setText(chat_item.get(i).getDate().toString());
                break;
            case VIEW_TYPE_SEND:
                viewHolder.sended_message.setText(chat_item.get(i).getMessage().toString());
                viewHolder.sended_time.setText(chat_item.get(i).getDate().toString());
                break;
        }
    }
    

    @Override
    public int getItemCount() {
        return chat_item.size();
    }


}

