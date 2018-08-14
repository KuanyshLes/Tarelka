package kz.production.kuanysh.tarelka.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import kz.production.kuanysh.tarelka.R;
import kz.production.kuanysh.tarelka.data.network.model.main.Result;
import kz.production.kuanysh.tarelka.data.network.model.main.Task;
import kz.production.kuanysh.tarelka.helper.Listener;
import kz.production.kuanysh.tarelka.ui.activities.TaskDetailActivity;
import kz.production.kuanysh.tarelka.ui.fragments.MainTaskFragment;
import kz.production.kuanysh.tarelka.utils.AppConst;

/**
 * Created by User on 20.06.2018.
 */
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter .ViewHolder> {
    private List<Task> task_list;
    private Context context;
    private Listener listener;

    @Inject
    Context mContext;

    public TaskAdapter (List<Task> task_list) {
        this.task_list = task_list;
        this.context = context;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        public ViewHolder(CardView itemView) {
            super(itemView);
            cardView=itemView;

        }
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        CardView cv = (CardView) LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.main_tasks_row_item, viewGroup, false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        CardView cardView = viewHolder.cardView;

        TextView name = (TextView) cardView.findViewById(R.id.task_name);
        if(task_list.get(i).getTitle().toString()!=null){
            name.setText(task_list.get(i).getTitle().toString());
        }

        ImageView accessibility = (ImageView) cardView.findViewById(R.id.task_accessibility);


        if(task_list.get(i).getAccess()== AppConst.TASK_ACCESS_YES){
            if(task_list.get(i).getStatus()==AppConst.TASK_ACCESS_YES){
                accessibility.setVisibility(View.VISIBLE);
                name.setTextColor(context.getResources().getColor(R.color.carbon_grey_800));
            }
            if(task_list.get(i).getStatus()==AppConst.TASK_ACCESS_NO){
                accessibility.setVisibility(View.INVISIBLE);
                name.setTextColor(context.getResources().getColor(R.color.carbon_grey_800));
            }
        }else if(task_list.get(i).getAccess()== AppConst.TASK_ACCESS_NO){
            accessibility.setImageResource(R.mipmap.padlock);
            name.setTextColor(context.getResources().getColor(R.color.carbon_grey_400));
        }

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(task_list.get(i).getAccess()== AppConst.TASK_ACCESS_YES){
                    Intent intent=new Intent(context, TaskDetailActivity.class);
                    intent.putExtra(MainTaskFragment.KEY_MAIN_TASK,task_list.get(i));
                    context.startActivity(intent);
                }else if(task_list.get(i).getAccess()== AppConst.TASK_ACCESS_NO){
                    Toast.makeText(context, "Задание недоступно!    ", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
    @Override
    public int getItemCount() {
        return task_list.size();
    }

  public void addItems(List<Task> mains){
        task_list.addAll(mains);
        notifyDataSetChanged();
    }
    public void addContext(Context contextMethod){
        context=contextMethod;
    }

}

