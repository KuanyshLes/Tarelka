package kz.production.kuanysh.tarelka.ui.fragments;


import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kz.production.kuanysh.tarelka.R;
import kz.production.kuanysh.tarelka.data.network.model.quiz.Result;
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

    @Inject
    ProgressAdapter progressAdapter;

    public static final String KEY_QUIZ_TEST="test";

    private LinearLayoutManager linearLayoutManager;
    private static String textSpinner;
    private Intent intent;
    public static String SELECTED_DATE;


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
        return view;
    }


    @Override
    public void openTestActivity(int position) {
        intent=new Intent(getActivity(), TestActivity.class);
        intent.putExtra(KEY_QUIZ_TEST,position+"");
        startActivity(intent);
    }

    @Override
    public void updateQuizList(List<Result> quizList) {
        progressAdapter.addItems(quizList);
    }

    @Override
    public void updateProgress(int progress) {
        ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress", progressBar.getProgress(), progress);
        animation.setDuration(1000);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();
    }

    @Override
    public void onSetSpinnerDate(List<String> dates) {
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, dates);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDate.setAdapter(categoryAdapter);
    }

    @Override
    protected void setUp(View view) {
        linearLayoutManager=new LinearLayoutManager(getActivity());
        progress_recycler.setLayoutManager(linearLayoutManager);
        progress_recycler.setAdapter(progressAdapter);

        progressAdapter.setListener(new Listener() {
            @Override
            public void onClick(int position) {
                mPresenter.onTestItemClick(position);
            }
        });
        mPresenter.onViewPrepared();
        mPresenter.setDates();


        spinnerDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Calendar cal = Calendar.getInstance();
                switch (position) {
                    case 0:
                        cal.add(Calendar.DATE, -2);
                        SELECTED_DATE=format.format(cal.getTime());
                        break;
                    case 1:
                        cal.add(Calendar.DATE, -1);
                        SELECTED_DATE=format.format(cal.getTime());
                        break;
                    case 2:
                        cal.add(Calendar.DATE, 0);
                        SELECTED_DATE=format.format(cal.getTime());
                        break;
                    case 3:
                        cal.add(Calendar.DATE, 1);
                        SELECTED_DATE=format.format(cal.getTime());
                        break;
                    case 4:
                        cal.add(Calendar.DATE, 2);
                        SELECTED_DATE=format.format(cal.getTime());
                        break;
                    case 5:
                        cal.add(Calendar.DATE, 3);
                        SELECTED_DATE=format.format(cal.getTime());
                        break;
                }
                mPresenter.onSendShowProgress(SELECTED_DATE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, -2);
                SELECTED_DATE=format.format(cal.getTime());
                mPresenter.onSendShowProgress(SELECTED_DATE);
            }
        });
    }

    @Override
    public void onDestroyView() {
        mPresenter.onDetach();
        super.onDestroyView();
    }
}
