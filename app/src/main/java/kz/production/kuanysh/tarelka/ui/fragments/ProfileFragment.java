package kz.production.kuanysh.tarelka.ui.fragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kz.production.kuanysh.tarelka.R;
import kz.production.kuanysh.tarelka.di.component.ActivityComponent;
import kz.production.kuanysh.tarelka.ui.activities.profileedit.ProfileEditActivity;
import kz.production.kuanysh.tarelka.ui.base.BaseFragment;
import kz.production.kuanysh.tarelka.ui.welcome.LoginActivity;
import kz.production.kuanysh.tarelka.utils.AppConst;

import static kz.production.kuanysh.tarelka.utils.AppConst.TAG_FRAGMENT;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends BaseFragment implements ProfileMvpView{


    @Inject
    ProfilePresenter<ProfileMvpView> mPresenter;

    @BindView(R.id.profile_edit)
    ImageView edit;

    @BindView(R.id.profile_photo)
    ImageView photo;

    @BindView(R.id.profile_pro)
    TextView pro;

    @BindView(R.id.profile_name)
    TextView name;

    @BindView(R.id.profile_age)
    TextView age;

    @BindView(R.id.profile_weight)
    TextView weight;

    @BindView(R.id.profile_heigth)
    TextView height;

    @BindView(R.id.profile_aim)
    TextView aim;

    @BindView(R.id.profile_phone)
    TextView phone;

    @BindView(R.id.logout_card)
    CardView logoutCard;

    private Bundle bundle;
    private static Dialog dialog;
    private static AlertDialog.Builder mBuilder;


    public ProfileFragment() {
        // Required empty public constructor
    }
    public static ProfileFragment newInstance() {
        Bundle args = new Bundle();
        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile, container, false);


        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);

        }
        return  view;
    }

    @OnClick(R.id.logout_card)
    public void onExit(){
        mPresenter.onExitClick();
    }

    @OnClick(R.id.profile_edit)
    public void edit(){
        mPresenter.onEditClick();
    }


    public void setFragment(Fragment fragment){
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, fragment, TAG_FRAGMENT)
                .addToBackStack(null)
                .commit();
    }


    @Override
    public void updateInfo(String usernameText, String statusText, String phoneText, String ageText, String weightText, String aimsText, String imageText,String heightText) {
        if(usernameText!=null){
            name.setText(usernameText.replace("\"",""));

        }
        if(ageText.length()!=0){
            age.setText(ageText + AppConst.YEAR);
        }if(weightText.length()!=0){
            weight.setText(weightText + AppConst.WEIGHT);
        }if(heightText.length()!=0){
            height.setText(heightText + AppConst.HEIGHT);
        }
        if(!aimsText.equals("")){
            aim.setText(aimsText.toString());
        }else{
            aim.setText("");
        }
        phone.setText(phoneText);
        if(imageText.length()!=0){
            Glide.with(this)
                    .load(imageText)
                    .into(photo);
        }

    }

    @Override
    public void openEditFragment() {
        Intent intent=new Intent(getActivity(), ProfileEditActivity.class);
        startActivity(intent);
    }

    @Override
    public void openDialog() {
        mBuilder= new AlertDialog.Builder(getActivity());
        View mView =getLayoutInflater().inflate(R.layout.dialog_exit,null);
        mBuilder.setView(mView);

        dialog=mBuilder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();

        //size
        Rect displayRectangle = new Rect();
        Window window = getActivity().getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        //set size
        dialog.getWindow().setLayout((int)(displayRectangle.width() *
                0.75f), (int)(displayRectangle.height() * 0.22f));


        TextView ok=(TextView)mView.findViewById(R.id.dialog_exit_ok);
        TextView cancell=(TextView)mView.findViewById(R.id.dialog_exit_cancell);
        cancell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getMvpView().openLoginActivity();
            }
        });
    }

    @Override
    public void openLoginActivity() {
        Intent intent=new Intent(getActivity(), LoginActivity.class);
        getActivity().startActivity(intent);
    }

    @Override
    protected void setUp(View view) {

        mPresenter.onViewPrepared();
    }

    @Override
    public void onDestroyView() {
        mPresenter.onDetach();
        super.onDestroyView();
    }
}
