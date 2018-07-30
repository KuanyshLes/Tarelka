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
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.production.kuanysh.tarelka.R;
import kz.production.kuanysh.tarelka.data.network.model.progress.Perc;
import kz.production.kuanysh.tarelka.data.network.model.quiz.Quizzes;
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

    @BindView(R.id.progress_amount)
    TextView amount;

    private static List<Perc> listPerc;

    private static List<String> listStringDates;

    private static List<Quizzes> quizzesList;

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
    public void updateQuizList(List<Quizzes> quizList) {
        quizzesList.addAll(quizList);
        progressAdapter.addItems(quizList);
    }

    @Override
    public void updateProgress(Double progress) {
        DecimalFormat df = new DecimalFormat("#.#");

        amount.setText("Ваш прогресс: "+df.format(progress)+"%");
        ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress", progressBar.getProgress(), progress.intValue());
        animation.setDuration(1000);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();
    }

    @Override
    public void setProgressDates(List<Perc> list) {
        //mPresenter.getMvpView().showMessage("setting dates");
        listPerc.addAll(list);
        if(listPerc!=null){
            for(int i=0;i<listPerc.size();i++){
                /*if(i==listPerc.size()-2){
                    listStringDates.add("Вчера");
                }else if(i==listPerc.size()-1){
                    listStringDates.add("Сегодня");
                }else{*/
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date newDate1=format1.parse(listPerc.get(i).getDate().toString());

                    SimpleDateFormat format2 = new SimpleDateFormat("dd MMM");
                    String newDate2=format2.format(newDate1);
                    listStringDates.add(newDate2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        }
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, listStringDates);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDate.setAdapter(categoryAdapter);
    }


    @Override
    protected void setUp(View view) {
        quizzesList=new ArrayList<>();
        listPerc=new ArrayList<>();
        listStringDates=new ArrayList<>();
        linearLayoutManager=new LinearLayoutManager(getActivity());
        progress_recycler.setLayoutManager(linearLayoutManager);
        progress_recycler.setAdapter(progressAdapter);

        progressAdapter.addContext(getActivity());

        progressAdapter.setListener(new Listener() {
            @Override
            public void onClick(int position) {
                mPresenter.onTestItemClick(position);
            }
        });
        mPresenter.onViewPrepared();
        mPresenter.onSendShowProgress();


        spinnerDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mPresenter.getMvpView().updateProgress(listPerc.get(position).getPercentage());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mPresenter.getMvpView().updateProgress(listPerc.get(listPerc.size()-1).getPercentage());
            }
        });
    }

    @Override
    public void onDestroyView() {
        mPresenter.onDetach();
        super.onDestroyView();
    }
}
