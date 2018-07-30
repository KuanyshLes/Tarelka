package kz.production.kuanysh.tarelka.ui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import carbon.widget.ImageView;
import kz.production.kuanysh.tarelka.R;
import kz.production.kuanysh.tarelka.data.network.model.quiz.Quiz;
import kz.production.kuanysh.tarelka.data.network.model.quiz.Quizzes;
import kz.production.kuanysh.tarelka.data.network.model.quiz.Result;
import kz.production.kuanysh.tarelka.helper.Listener;
import kz.production.kuanysh.tarelka.ui.fragments.ProgressMvpView;
import kz.production.kuanysh.tarelka.ui.fragments.ProgressPresenter;
import kz.production.kuanysh.tarelka.utils.AppConst;

/**
 * Created by User on 20.06.2018.
 */

public class ProgressAdapter extends RecyclerView.Adapter<ProgressAdapter.ViewHolder> {

    @Inject
    ProgressPresenter<ProgressMvpView> mPresenter;

    private List<Quizzes> progress_task_list;
    private Listener listener;
    Context mContext;


    public ProgressAdapter(List<Quizzes> progress_task_list) {
        this.progress_task_list = progress_task_list;
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
                .inflate(R.layout.progress_row_item, viewGroup, false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        CardView cardView = viewHolder.cardView;

        TextView order = (TextView) cardView.findViewById(R.id.progress_order);
        TextView text= (TextView) cardView.findViewById(R.id.progress_text);
        android.widget.ImageView accessibility=cardView.findViewById(R.id.progress_accessibility);

        order.setText((i+1)+"");
        text.setText(progress_task_list.get(i).getTitle());



        if(progress_task_list.get(i).getAccess()== AppConst.QUIZ_ACCESS_YES){

        }else if(progress_task_list.get(i).getAccess()== AppConst.QUIZ_ACCESS_NO){
            accessibility.setImageResource(R.mipmap.padlock);
            text.setTextColor(mContext.getResources().getColor(R.color.colorNotAccess));
        }

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(progress_task_list.get(i).getAccess()== AppConst.QUIZ_ACCESS_YES){
                    if(listener!=null){
                        listener.onClick(progress_task_list.get(i).getId());
                    }
                }else if(progress_task_list.get(i).getAccess()== AppConst.QUIZ_ACCESS_NO){
                    if(progress_task_list.get(i).getImgIn7days()==false){
                        Toast.makeText(mContext, "Для прохождения , вы должны отправить фото в течение недели!", Toast.LENGTH_SHORT).show();
                    }else{
                        if(progress_task_list.get(i).get7days()==false){
                            Toast.makeText(mContext, "Тест можно пройти только в течения недели!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            }
        });


    }
    @Override
    public int getItemCount() {
        return progress_task_list.size();
    }

    public void addItems(List<Quizzes> progress_task) {
        progress_task_list.addAll(progress_task);
        notifyDataSetChanged();
    }

    public void addContext(Context context){
        mContext=context;
    }


}


