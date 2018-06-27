package kz.production.kuanysh.tarelka.ui.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.production.kuanysh.tarelka.R;
import kz.production.kuanysh.tarelka.data.TarelkaDataFactory;
import kz.production.kuanysh.tarelka.di.component.ActivityComponent;
import kz.production.kuanysh.tarelka.ui.activities.test.TestActivity;
import kz.production.kuanysh.tarelka.helper.Listener;
import kz.production.kuanysh.tarelka.ui.adapters.ProgressAdapter;
import kz.production.kuanysh.tarelka.ui.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProgressFragment extends BaseFragment implements ProgressMvpView{

    @Inject
    ProgressPresenter<ProgressMvpView> mPresenter;

    @BindView(R.id.progress_spinner)
    Spinner spinnerDate;

    @BindView(R.id.progress_progressbar)
    ProgressBar progressBar;

    @BindView(R.id.progress_recycler)
    RecyclerView progress_recycler;

    private LinearLayoutManager linearLayoutManager;
    private static String textSpinner;
    private ProgressAdapter progressAdapter;
    private Intent intent;


    public ProgressFragment() {
        // Required empty public constructor
    }
    public static ProgressFragment newInstance() {
        Bundle args = new Bundle();
        ProgressFragment fragment = new ProgressFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_progress, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);
        }


        textSpinner=getDate(TarelkaDataFactory.getDateList());
        progressBar.setProgress(56);
        setUp(view);

        return view;
    }

    private String getDate(final List<String> dateList){
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, dateList);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDate.setAdapter(categoryAdapter);

        return spinnerDate.getSelectedItem().toString();
    }



    @Override
    public void openTestActivity(int position) {
        intent=new Intent(getActivity(), TestActivity.class);
        startActivity(intent);
    }

    @Override
    public void updateTestList(List<String> testList) {

    }

    @Override
    public void updateDate() {

    }

    @Override
    public void updateProgress() {

    }

    @Override
    protected void setUp(View view) {
        linearLayoutManager=new LinearLayoutManager(getActivity());
        progress_recycler.setLayoutManager(linearLayoutManager);

        progressAdapter=new ProgressAdapter(TarelkaDataFactory.getProgress_task_list(),getActivity());
        progress_recycler.setAdapter(progressAdapter);

        progressAdapter.setListener(new Listener() {
            @Override
            public void onClick(int position) {
                mPresenter.onTestItemClick(position);
            }
        });
    }

    @Override
    public void onDestroyView() {
        mPresenter.onDetach();
        super.onDestroyView();
    }
}
