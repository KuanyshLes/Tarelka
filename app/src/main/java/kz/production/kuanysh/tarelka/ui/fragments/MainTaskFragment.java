package kz.production.kuanysh.tarelka.ui.fragments;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.production.kuanysh.tarelka.R;
import kz.production.kuanysh.tarelka.data.network.model.main.Task;
import kz.production.kuanysh.tarelka.di.component.ActivityComponent;
import kz.production.kuanysh.tarelka.ui.base.BaseFragment;
import kz.production.kuanysh.tarelka.ui.activities.TaskDetailActivity;
import kz.production.kuanysh.tarelka.ui.adapters.TaskAdapter;
import me.toptas.fancyshowcase.FancyShowCaseView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainTaskFragment extends BaseFragment implements MainTaskMvpView{

    @Inject
    MainTaskPresenter<MainTaskMvpView> mPresenter;

    @BindView(R.id.task_recycler)
    RecyclerView tasks;

    @BindView(R.id.task_view)
    View task_view;

    private static List<Task> taskList;

    @Inject
    TaskAdapter taskAdapter;

    private LinearLayoutManager linearLayoutManager;
    private Intent intent;
    public static final String KEY_MAIN_TASK="keymaintask";


    public MainTaskFragment() {
        // Required empty public constructor
    }

    public static MainTaskFragment newInstance() {
        Bundle args = new Bundle();
        MainTaskFragment fragment = new MainTaskFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_main_task, container, false);

        ActivityComponent component = getActivityComponent();

        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);
        }

        Log.d("myTag", "token: "+mPresenter.getDataManager().getAccessToken());

        //mPresenter.getMvpView().fireNotification();

        return view;
    }



    @Override
    public void openTaskDetailActivity(int position) {
        intent =new Intent(getActivity(), TaskDetailActivity.class);
        getActivity().startActivity(intent);
    }
    public String getEmojiByUnicode(int unicode){
       // String happy = "Feeling happy " + getEmojiByUnicode(unicode);
        return new String(Character.toChars(unicode));
    }


    @Override
    protected void setUp(View view) {
        if(mPresenter.getDataManager().getFancyEducation()!=null){
            int unicode = 0x1F601;
            new FancyShowCaseView.Builder(getActivity())
                    .title(getResources().getString(R.string.fancy_education)+" "+getEmojiByUnicode(unicode))
                    .titleStyle(R.style.MyTitleStyle, Gravity.CENTER)
                    .focusOn(task_view)
                    .build()
                    .show();
            mPresenter.getDataManager().setFancyEducation(null);
        }


        taskList=new ArrayList<>();
        linearLayoutManager=new LinearLayoutManager(getActivity());
        tasks.setLayoutManager(linearLayoutManager);
        tasks.setAdapter(taskAdapter);
        taskAdapter.addContext(getActivity());
        mPresenter.onViewPrepared();
    }

    @Override
    public void updateAimsList(List<Task> tasks) {
        if(tasks!=null){
            taskList.addAll(tasks);
            taskAdapter.addItems(tasks);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void fireNotification() {

        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

        Intent notificationIntent = new Intent("android.media.action.DISPLAY_NOTIFICATION");
        notificationIntent.addCategory("android.intent.category.DEFAULT");

        PendingIntent broadcast = PendingIntent.getBroadcast(getActivity(), 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 51);
        calendar.set(Calendar.SECOND, 0);

        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(Calendar.HOUR_OF_DAY, 12);
        calendar1.set(Calendar.MINUTE, 52);
        calendar1.set(Calendar.SECOND, 0);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(Calendar.HOUR_OF_DAY, 12);
        calendar2.set(Calendar.MINUTE, 53);
        calendar2.set(Calendar.SECOND, 0);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), broadcast);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar1.getTimeInMillis(), broadcast);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar2.getTimeInMillis(), broadcast);

    }

    @Override
    public void onDestroyView() {
        mPresenter.onDetach();
        super.onDestroyView();
    }
}
