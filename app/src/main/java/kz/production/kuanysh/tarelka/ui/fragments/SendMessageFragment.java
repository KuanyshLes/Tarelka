package kz.production.kuanysh.tarelka.ui.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import kz.production.kuanysh.tarelka.R;
import kz.production.kuanysh.tarelka.di.component.ActivityComponent;
import kz.production.kuanysh.tarelka.ui.base.BaseFragment;
import kz.production.kuanysh.tarelka.ui.fragments.social.SocialMediaDirectFragment;

import static kz.production.kuanysh.tarelka.ui.activities.mainactivity.MainActivity.TAG_CHAT;
import static kz.production.kuanysh.tarelka.utils.AppConst.TAG_FRAGMENT;

/**
 * A simple {@link Fragment} subclass.
 */
public class SendMessageFragment extends BaseFragment implements SendMessageMvpView {

    @Inject
    SendMessagePresenter<SendMessageMvpView> mPresenter;

    @BindView(R.id.send_message_send)
    ImageView send;

    @BindView(R.id.send_message_back)
    ImageView back;

    @BindView(R.id.send_message_text)
    EditText message;

    private Bundle bundle;
    public static final String EXTRA_SOCIAL_MESSAGE="EXTRA_SOCIAL_MESSAGE";


    public SendMessageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_send_message, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);
        }

        return view;

    }

    @OnClick(R.id.send_message_back)
    public void goBack(){
        mPresenter.onBackClick();
    }

    @OnClick(R.id.send_message_send)
    public void sendAsSocial() {
        if(!TextUtils.isEmpty(message.getText())){
            mPresenter.getMvpView().openSocialFragment();
        }else {
            mPresenter.getMvpView().onError("Text field is empty!");
        }
        //mPresenter.onSendAsSocialClick();
    }

    @Override
    public void openChatFragment() {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .disallowAddToBackStack()
                .replace(R.id.content_frame, ChatFragment.newInstance(), TAG_CHAT)
                .commit();
    }

    @Override
    public void sendMessageAsIntent() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);

        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, message.getText().toString());
        startActivityForResult(Intent.createChooser(intent, "Share"),7);

    }

    @Override
    public void openSocialFragment() {
        SocialMediaDirectFragment socialMediaDirectFragment=new SocialMediaDirectFragment();
        bundle=new Bundle();
        bundle.putString(EXTRA_SOCIAL_MESSAGE,message.getText().toString());
        socialMediaDirectFragment.setArguments(bundle);
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .disallowAddToBackStack()
                .replace(R.id.content_frame, socialMediaDirectFragment, TAG_CHAT)
                .commit();
    }


    @Override
    protected void setUp(View view) {

    }

    @Override
    public void onDestroyView() {
        mPresenter.onDetach();
        super.onDestroyView();
    }
}
