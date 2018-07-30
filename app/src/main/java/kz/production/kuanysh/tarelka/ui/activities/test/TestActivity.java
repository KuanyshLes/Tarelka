package kz.production.kuanysh.tarelka.ui.activities.test;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kz.production.kuanysh.tarelka.R;
import kz.production.kuanysh.tarelka.data.network.model.quizquestions.Result;
import kz.production.kuanysh.tarelka.ui.activities.mainactivity.MainActivity;
import kz.production.kuanysh.tarelka.ui.base.BaseActivity;
import kz.production.kuanysh.tarelka.ui.fragments.ProgressFragment;

public class TestActivity extends BaseActivity implements TestMvpView {

    @Inject
    TestPresenter<TestMvpView> mPresenter;

    @BindView(R.id.test_all_question_number)
    TextView allNumber;

    @BindView(R.id.test_current_question_number)
    TextView currentNumber;

    @BindView(R.id.test_cancell)
    TextView cancell;

    @BindView(R.id.test_question)
    TextView question;

    @BindView(R.id.test_radiogroup)
    RadioGroup radioGroup;

    @BindView(R.id.test_ans1)
    RadioButton answer1;

    @BindView(R.id.test_ans2)
    RadioButton answer2;

    @BindView(R.id.test_ans3)
    RadioButton answer3;

    @BindView(R.id.test_ans4)
    RadioButton answer4;

    @BindView(R.id.test_ans5)
    RadioButton answer5;

    @BindView(R.id.test_next)
    ImageView next;

    @BindView(R.id.test_prev)
    ImageView prev;

    private static List<Result> quizQuestion;

    public static int currentTestNumber=0;
    public static int correctAnswer;
    public static String ANSWER;
    public static String correctAnswerString;
    public static String CORRECT_COLOR ="#23eabf";
    public static String WRONG_COLOR ="#bf7669";
    public static String WHITE_COLOR ="#FFFFFF";

    public static final String STATUS_DIALOG_CANCELL="CANCELL";
    public static final String STATUS_DIALOG_SUCCESS="SUCCESS";
    private static Dialog dialog;
    private static AlertDialog.Builder mBuilder;

    private static List<String> userAnswers;
    private static List<String> correctAnswers;
    private static int passedTestCount=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_test);
        getActivityComponent().inject(this);
        setUnBinder(ButterKnife.bind(this));
        mPresenter.onAttach(TestActivity.this);

        setUp();
    }

    @OnClick(R.id.test_cancell)
    public void goQuiz(){
            mPresenter.onCancellClick();
    }

    @OnClick(R.id.test_next)
    public void next(){
        if(currentTestNumber<passedTestCount){
            currentTestNumber++;
            mPresenter.onNextClick(currentTestNumber);
        }else {
            if(answer1.isChecked() || answer2.isChecked() || answer3.isChecked() || answer4.isChecked() || answer5.isChecked()){
                currentTestNumber++;

               // mPresenter.getMvpView().showMessage("Next if");
                if(currentTestNumber!=quizQuestion.size()){
                    setDefault();
                    setChecked();
                    setClickable(true);
                    mPresenter.onNextClick(currentTestNumber);
                }
                else if(currentTestNumber==quizQuestion.size()){
                    //mPresenter.getMvpView().showMessage("Next else if");
                    mPresenter.onNextClick(currentTestNumber);
                }
            }else{
                mPresenter.getMvpView().showMessage("Please select one item");
            }
        }


    }
    @OnClick(R.id.test_prev)
    public void prev(){
        currentTestNumber--;
        mPresenter.onPrevClick(currentTestNumber);
        setClickable(false);
    }


    @OnClick(R.id.test_ans1)
    public void ans1(){
        mPresenter.onAnswerClick();
        passedTestCount++;
        setClickable(false);
    }
    @OnClick(R.id.test_ans2)
    public void ans2(){
        mPresenter.onAnswerClick();
        passedTestCount++;
        setClickable(false);
    }
    @OnClick(R.id.test_ans3)
    public void ans3(){
        mPresenter.onAnswerClick();
        passedTestCount++;
        setClickable(false);
    }
    @OnClick(R.id.test_ans4)
    public void ans4(){
        mPresenter.onAnswerClick();
        passedTestCount++;
        setClickable(false);
    }
    @OnClick(R.id.test_ans5)
    public void ans5(){
        mPresenter.onAnswerClick();
        passedTestCount++;
        setClickable(false);
    }

    @Override
    protected void setUp() {
        if(getIntent().getStringExtra(ProgressFragment.KEY_QUIZ_TEST) != null){
            String extra=getIntent().getStringExtra(ProgressFragment.KEY_QUIZ_TEST);
            mPresenter.onViewPrepared(extra);
        }else {
            mPresenter.getMvpView().showMessage("error");
        }
        userAnswers=new ArrayList<>();
        correctAnswers=new ArrayList<>();
    }
    private void setClickable(Boolean clickableFalse){
        answer1.setClickable(clickableFalse);
        answer2.setClickable(clickableFalse);
        answer3.setClickable(clickableFalse);
        answer4.setClickable(clickableFalse);
        answer5.setClickable(clickableFalse);
    }
    private void setDefault(){
        answer1.setTextColor(Color.parseColor(WHITE_COLOR));
        answer2.setTextColor(Color.parseColor(WHITE_COLOR));
        answer3.setTextColor(Color.parseColor(WHITE_COLOR));
        answer4.setTextColor(Color.parseColor(WHITE_COLOR));
        answer5.setTextColor(Color.parseColor(WHITE_COLOR));
    }
    private void setChecked(){
        answer1.setChecked(false);
        answer2.setChecked(false);
        answer3.setChecked(false);
        answer4.setChecked(false);
        answer5.setChecked(false);
    }


    @Override
    public void showResult() {
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.test_ans1:
                ANSWER="A";
                break;
            case R.id.test_ans2:
                ANSWER="B";
                break;
            case R.id.test_ans3:
                ANSWER="C";
                break;
            case R.id.test_ans4:
                ANSWER="D";
                break;
            case R.id.test_ans5:
                ANSWER="E";
                break;
        }
        correctAnswerString=quizQuestion.get(currentTestNumber).getRightAnswer();
        if (correctAnswerString.equals(ANSWER)){
            correctAnswer++;
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                switch (ANSWER) {
                    case "A":
                        answer1.setTextColor(Color.parseColor(CORRECT_COLOR));
                        break;
                    case "B":
                        answer2.setTextColor(Color.parseColor(CORRECT_COLOR));
                        break;
                    case "C":
                        answer3.setTextColor(Color.parseColor(CORRECT_COLOR));
                        break;
                    case "D":
                        answer4.setTextColor(Color.parseColor(CORRECT_COLOR));
                        break;
                    case "E":
                        answer5.setTextColor(Color.parseColor(CORRECT_COLOR));
                        break;
                }

                mPresenter.getMvpView().showMessage("True! Cool");

                userAnswers.add(ANSWER);
                correctAnswers.add(correctAnswerString);
            }

        }else{
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                switch (ANSWER) {
                    case "A":
                        answer1.setTextColor(Color.parseColor(WRONG_COLOR));
                        break;
                    case "B":
                        answer2.setTextColor(Color.parseColor(WRONG_COLOR));
                        break;
                    case "C":
                        answer3.setTextColor(Color.parseColor(WRONG_COLOR));
                        break;
                    case "D":
                        answer4.setTextColor(Color.parseColor(WRONG_COLOR));
                        break;
                    case "E":
                        answer5.setTextColor(Color.parseColor(WRONG_COLOR));
                        break;
                }
                switch (correctAnswerString) {
                    case "A":
                        answer1.setTextColor(Color.parseColor(CORRECT_COLOR));
                        answer1.setChecked(true);
                        break;
                    case "B":
                        answer2.setTextColor(Color.parseColor(CORRECT_COLOR));
                        answer2.setChecked(true);
                        break;
                    case "C":
                        answer3.setTextColor(Color.parseColor(CORRECT_COLOR));
                        answer3.setChecked(true);
                        break;
                    case "D":
                        answer4.setTextColor(Color.parseColor(CORRECT_COLOR));
                        answer4.setChecked(true);
                        break;
                    case "E":
                        answer5.setTextColor(Color.parseColor(CORRECT_COLOR));
                        answer5.setChecked(true);
                        break;
                }
                mPresenter.getMvpView().showMessage("Not correct answer:(");
                userAnswers.add(ANSWER);
                correctAnswers.add(correctAnswerString);
            }

        }


        int minus=quizQuestion.size()-currentTestNumber;
        if(minus==1){
            mPresenter.getMvpView().showSuccessDialog();
        }
    }

    @Override
    public void showCancellDialog() {
        mBuilder= new AlertDialog.Builder(this);
        View mView =getLayoutInflater().inflate(R.layout.dialog_cancell,null);
        mBuilder.setView(mView);

        dialog=mBuilder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();

        //size
        Rect displayRectangle = new Rect();
        Window window = this.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        //set size
        dialog.getWindow().setLayout((int)(displayRectangle.width() *
                0.84f), (int)(displayRectangle.height() * 0.22f));


        TextView ok=(TextView)mView.findViewById(R.id.dialog_cancell_ok);
        TextView cancell=(TextView)mView.findViewById(R.id.dialog_cancell_back);
        cancell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next.setVisibility(View.INVISIBLE);
                next.setEnabled(false);
                mPresenter.sendResult(Integer.parseInt(getIntent().getStringExtra(ProgressFragment.KEY_QUIZ_TEST)),
                        currentTestNumber+1,correctAnswer);
            }
        });
    }

    @Override
    public void showSuccessDialog() {
        mBuilder= new AlertDialog.Builder(this);
        View mView =getLayoutInflater().inflate(R.layout.dialog_success,null);
        mBuilder.setView(mView);

        dialog=mBuilder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();

        //size
        Rect displayRectangle = new Rect();
        Window window = this.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        //set size
        dialog.getWindow().setLayout((int)(displayRectangle.width() *
                0.84f), (int)(displayRectangle.height() * 0.22f));


        TextView ok=(TextView)mView.findViewById(R.id.dialog_success_ok);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mPresenter.getMvpView().showMessage(correctAnswer+" correct answer");
                mPresenter.sendResult(Integer.parseInt(getIntent().getStringExtra(ProgressFragment.KEY_QUIZ_TEST)),
                        currentTestNumber+1,correctAnswer);

            }
        });
    }


    @Override
    public void openProgressFragment() {
        Intent intent=new Intent(TestActivity.this,MainActivity.class);
        intent.putExtra(MainActivity.TAG_PROGRESS,MainActivity.TAG_PROGRESS);
        startActivity(intent);
    }

    @Override
    public void updateTest(int position) {
        if(quizQuestion.size()>position){
            prev.setEnabled(true);
            prev.setVisibility(View.VISIBLE);
            if(position==0){
                prev.setVisibility(View.INVISIBLE);
                prev.setEnabled(false);
            }
            if(position==quizQuestion.size()-1){
                next.setVisibility(View.INVISIBLE);
                next.setEnabled(false);

            }
            //mPresenter.getMvpView().showMessage("update test position "+position);
            setDefault();
            setClickable(true);
            setChecked();


            allNumber.setText("из "+quizQuestion.size()+"");
            currentNumber.setText(String.valueOf(position+1));
            question.setText("  " +quizQuestion.get(position).getQuestion().toString());
            answer1.setText("  " +quizQuestion.get(position).getAnswerA().toString());
            answer2.setText("  " +quizQuestion.get(position).getAnswerB().toString());
            answer3.setText("  " +quizQuestion.get(position).getAnswerC().toString());
            answer4.setText("  " +quizQuestion.get(position).getAnswerD().toString());
            answer5.setText("  " +quizQuestion.get(position).getAnswerE().toString());
        }
        if(currentTestNumber<passedTestCount){
            if(correctAnswers.get(position).equals(userAnswers.get(position))){
                setDefault();
                //mPresenter.getMvpView().showMessage("was correct");
                switch (correctAnswers.get(position)) {
                    case "A":
                        answer1.setTextColor(Color.parseColor(CORRECT_COLOR));
                        answer1.setChecked(true);
                        break;
                    case "B":
                        answer2.setTextColor(Color.parseColor(CORRECT_COLOR));
                        answer2.setChecked(true);
                        break;
                    case "C":
                        answer3.setTextColor(Color.parseColor(CORRECT_COLOR));
                        answer3.setChecked(true);
                        break;
                    case "D":
                        answer4.setTextColor(Color.parseColor(CORRECT_COLOR));
                        answer4.setChecked(true);
                        break;
                    case "E":
                        answer5.setTextColor(Color.parseColor(CORRECT_COLOR));
                        answer5.setChecked(true);
                        break;
                }
            }else {
                setDefault();
                //mPresenter.getMvpView().showMessage("was incorrect");
                switch (userAnswers.get(position)) {
                    case "A":
                        answer1.setTextColor(Color.parseColor(WRONG_COLOR));
                        break;
                    case "B":
                        answer2.setTextColor(Color.parseColor(WRONG_COLOR));
                        break;
                    case "C":
                        answer3.setTextColor(Color.parseColor(WRONG_COLOR));
                        break;
                    case "D":
                        answer4.setTextColor(Color.parseColor(WRONG_COLOR));
                        break;
                    case "E":
                        answer5.setTextColor(Color.parseColor(WRONG_COLOR));
                        break;
                }
                switch (correctAnswers.get(position)) {
                    case "A":
                        answer1.setTextColor(Color.parseColor(CORRECT_COLOR));
                        answer1.setChecked(true);
                        break;
                    case "B":
                        answer2.setTextColor(Color.parseColor(CORRECT_COLOR));
                        answer2.setChecked(true);
                        break;
                    case "C":
                        answer3.setTextColor(Color.parseColor(CORRECT_COLOR));
                        answer3.setChecked(true);
                        break;
                    case "D":
                        answer4.setTextColor(Color.parseColor(CORRECT_COLOR));
                        answer4.setChecked(true);
                        break;
                    case "E":
                        answer5.setTextColor(Color.parseColor(CORRECT_COLOR));
                        answer5.setChecked(true);
                        break;
                }
            }
        }

    }

    @Override
    public void getQuestions(List<Result> quiz) {
        quizQuestion=new ArrayList<>();
        quizQuestion.addAll(quiz);
        //mPresenter.getMvpView().showMessage(quizQuestion.size()+" size");
        mPresenter.getMvpView().updateTest(currentTestNumber);

    }



    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }
}
