package kz.production.kuanysh.tarelka.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import kz.production.kuanysh.tarelka.R;
import kz.production.kuanysh.tarelka.data.network.model.main.Result;
import kz.production.kuanysh.tarelka.helper.Listener;
import kz.production.kuanysh.tarelka.ui.activities.TaskDetailActivity;
import kz.production.kuanysh.tarelka.ui.fragments.MainTaskFragment;

/**
 * Created by User on 20.06.2018.
 */
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter .ViewHolder> {
    private List<Result> task_list;
    private Context context;
    private Listener listener;



    public TaskAdapter (List<Result> task_list,Context context) {
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
        name.setText(task_list.get(i).getTitle().toString());

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, TaskDetailActivity.class);
                intent.putExtra(MainTaskFragment.KEY_MAIN_TASK,task_list.get(i));
                context.startActivity(intent);

            }
        });
    }
    @Override
    public int getItemCount() {
        return task_list.size();
    }

   /* public void addItems(List<Result> mains){
        task_list=mains;
        notifyDataSetChanged();
    }*/

}

