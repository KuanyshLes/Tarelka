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
import kz.production.kuanysh.tarelka.data.network.model.main.Result;
import kz.production.kuanysh.tarelka.di.component.ActivityComponent;
import kz.production.kuanysh.tarelka.push.AlarmReceiver;
import kz.production.kuanysh.tarelka.ui.activities.mainactivity.MainActivity;
import kz.production.kuanysh.tarelka.ui.base.BaseFragment;
import kz.production.kuanysh.tarelka.utils.AppConst;
import kz.production.kuanysh.tarelka.data.TarelkaDataFactory;
import kz.production.kuanysh.tarelka.ui.activities.TaskDetailActivity;
import kz.production.kuanysh.tarelka.ui.adapters.TaskAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainTaskFragment extends BaseFragment implements MainTaskMvpView{

    @Inject
    MainTaskPresenter<MainTaskMvpView> mPresenter;

    @BindView(R.id.task_recycler)
    RecyclerView tasks;

    private static List<Result> main;

    private TaskAdapter taskAdapter;

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


        //mPresenter.getMvpView().fireNotification();

        return view;
    }


    @Override
    public void openTaskDetailActivity(int position) {
        intent =new Intent(getActivity(), TaskDetailActivity.class);
        intent.putExtra(AppConst.TASK_KEY,TarelkaDataFactory.getFoodsList().get(position));
        getActivity().startActivity(intent);
    }


    @Override
    protected void setUp(View view) {
        main=new ArrayList<>();
        linearLayoutManager=new LinearLayoutManager(getActivity());
        tasks.setLayoutManager(linearLayoutManager);
        taskAdapter=new TaskAdapter(main,getActivity());
        tasks.setAdapter(taskAdapter);
        mPresenter.onViewPrepared();
    }

    @Override
    public void updateAimsList(List<Result> tasks) {
        main.addAll(tasks);
        taskAdapter.notifyDataSetChanged();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void fireNotification() {
        /*Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 11);
        calendar.set(Calendar.SECOND, 0);

        Calendar calendar1 = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 12);
        calendar.set(Calendar.SECOND, 0);

        Calendar calendar2 = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 13);
        calendar.set(Calendar.SECOND, 0);

        Intent intent1 = new Intent(getActivity(), AlarmReceiver.class);
        PendingIntent broadcast = PendingIntent.getBroadcast(getActivity(), 100,intent1, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager am = (AlarmManager) getActivity().getSystemService(getActivity().ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, broadcast);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar1.getTimeInMillis(), AlarmManager.INTERVAL_DAY, broadcast);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar2.getTimeInMillis(), AlarmManager.INTERVAL_DAY, broadcast);
//        am.setRepeating((AlarmManager.RTC_WAKEUP), calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY, broadcast);
//        //am.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), broadcast);
//        am.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), broadcast);
//        am.setExact(AlarmManager.RTC_WAKEUP, calendar1.getTimeInMillis(), broadcast);
//        am.setExact(AlarmManager.RTC_WAKEUP, calendar2.getTimeInMillis(), broadcast);*/
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
