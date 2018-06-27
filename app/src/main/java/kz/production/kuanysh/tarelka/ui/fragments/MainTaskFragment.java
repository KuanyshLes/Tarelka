package kz.production.kuanysh.tarelka.ui.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import kz.production.kuanysh.tarelka.R;
import kz.production.kuanysh.tarelka.di.component.ActivityComponent;
import kz.production.kuanysh.tarelka.ui.base.BaseFragment;
import kz.production.kuanysh.tarelka.utils.AppConst;
import kz.production.kuanysh.tarelka.data.TarelkaDataFactory;
import kz.production.kuanysh.tarelka.ui.activities.TaskDetailActivity;
import kz.production.kuanysh.tarelka.helper.Listener;
import kz.production.kuanysh.tarelka.ui.adapters.TaskAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainTaskFragment extends BaseFragment implements MainTaskMvpView{

    @Inject
    MainTaskPresenter<MainTaskMvpView> mPresenter;

    @BindView(R.id.task_recycler)
    RecyclerView tasks;

    private TaskAdapter taskAdapter;
    private LinearLayoutManager linearLayoutManager;
    private Intent intent;


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
        setUp(view);

        return view;
    }



    @Override
    public void openTaskDetailActivity(int position) {
        intent =new Intent(getActivity(), TaskDetailActivity.class);
        intent.putExtra(AppConst.TASK_KEY,TarelkaDataFactory.getFoodsList().get(position));
        getActivity().startActivity(intent);
    }

    @Override
    public void updateAimsList(List<String> aims) {

    }

    @Override
    protected void setUp(View view) {
        linearLayoutManager=new LinearLayoutManager(getActivity());
        tasks.setLayoutManager(linearLayoutManager);

        taskAdapter=new TaskAdapter(TarelkaDataFactory.getTaskList(),getActivity());
        tasks.setAdapter(taskAdapter);

        taskAdapter.setListener(new Listener() {
            @Override
            public void onClick(int position) {
                mPresenter.onItemClick(position);
            }
        });
    }

    @Override
    public void onDestroyView() {
        mPresenter.onDetach();
        super.onDestroyView();
    }
}
