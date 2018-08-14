package kz.production.kuanysh.tarelka.ui.fragments;


import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
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
import me.toptas.fancyshowcase.FancyShowCaseView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProgressFragment extends BaseFragment implements ProgressMvpView{

    @Inject
    ProgressPresenter<ProgressMvpView> mPresenter;


    @BindView(R.id.progress_progressbar)
    ProgressBar progressBar;

    @BindView(R.id.progress_recycler)
    RecyclerView progress_recycler;

    @BindView(R.id.quiz_view)
    View quiz_view;

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
        DecimalFormat df = new DecimalFormat("#.##");
        Double total=(progress*13)/100;
        amount.setText("Ваш прогресс: "+(df.format(total))+"%");
        ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress", 0, progress.intValue());
        animation.setDuration(1000);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();
    }

    @Override
    public void setProgressDates(List<Perc> list) {
        mPresenter.getMvpView().updateProgress(Double.valueOf(list.size()));
    }
    public String getEmojiByUnicode(int unicode){
        // String happy = "Feeling happy " + getEmojiByUnicode(unicode);
        return new String(Character.toChars(unicode));
    }


    @Override
    protected void setUp(View view) {
        quizzesList=new ArrayList<>();
        listPerc=new ArrayList<>();
        listStringDates=new ArrayList<>();
        if(mPresenter.getDataManager().getFancyQuiz()!=null){
            int unicode = 0x1F601;
            new FancyShowCaseView.Builder(getActivity())
                    .title(getResources().getString(R.string.fancy_quiz)+" "+getEmojiByUnicode(unicode))
                    .titleStyle(R.style.MyTitleStyle, Gravity.CENTER)
                    .focusOn(quiz_view)
                    .build()
                    .show();
            mPresenter.getDataManager().setFancyQuiz(null);
        }



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

    }

    @Override
    public void onDestroyView() {
        mPresenter.onDetach();
        super.onDestroyView();
    }
}
