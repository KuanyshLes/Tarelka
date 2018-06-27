package kz.production.kuanysh.tarelka.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import kz.production.kuanysh.tarelka.R;
import kz.production.kuanysh.tarelka.di.component.ActivityComponent;
import kz.production.kuanysh.tarelka.ui.base.BaseFragment;
import kz.production.kuanysh.tarelka.utils.AppConst;
import kz.production.kuanysh.tarelka.model.ChatItem;
import kz.production.kuanysh.tarelka.ui.adapters.ChatAdapter;

import static kz.production.kuanysh.tarelka.utils.AppConst.TAG_FRAGMENT;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends BaseFragment implements ChatMvpView {

    @Inject
    ChatPresenter<ChatMvpView> mPresenter;

    @BindView(R.id.chat_new_mail)
    ImageView mail;

    @BindView(R.id.chat_message)
    EditText message;

    @BindView(R.id.chat_recycler)
    RecyclerView chats;

    @BindView(R.id.chat_send)
    TextView send;

    private LinearLayoutManager linearLayoutManager;
    private List<ChatItem> chatItemList;
    private static ChatAdapter chatAdapter;

    public ChatFragment() {
        // Required empty public constructor
    }
    public static ChatFragment newInstance() {
        Bundle args = new Bundle();
        ChatFragment fragment = new ChatFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_chat, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);
        }

        setUp(view);

        return view;
    }


    @OnClick(R.id.chat_new_mail)
    public void onMailClick(View view){
        mPresenter.onMailClick();
    }

    @OnClick(R.id.chat_send)
    public void sendMessage(View view){
        Random r = new Random();
        int randInt = r.nextInt(2);
        chatItemList.add(new ChatItem(randInt,message.getText().toString(),"22.04"));
        chatItemList.add(new ChatItem(AppConst.VIEW_TYPE_RECEIVE,message.getText().toString(),"22.04"));
        chatAdapter.notifyDataSetChanged();
        chats.getLayoutManager().scrollToPosition(chatItemList.size()-1);

        message.setText("");
    }

    private void setCustomChat(){
        chatItemList=new ArrayList<>();
        chatItemList.add(new ChatItem(AppConst.VIEW_TYPE_SEND,"Hello","22.04"));
        chatItemList.add(new ChatItem(AppConst.VIEW_TYPE_RECEIVE,"Hello my dear friend","22.04"));
        chatItemList.add(new ChatItem(AppConst.VIEW_TYPE_SEND,"How are you?","22.04"));
        chatItemList.add(new ChatItem(AppConst.VIEW_TYPE_RECEIVE,"Fine! And you?","22.04"));
        chatItemList.add(new ChatItem(AppConst.VIEW_TYPE_SEND,"Good!","22.04"));
        chatItemList.add(new ChatItem(AppConst.VIEW_TYPE_RECEIVE,"What's the news?","22.04"));
        chatItemList.add(new ChatItem(AppConst.VIEW_TYPE_SEND,"I am leaning MVP design pattern","22.04"));
        chatItemList.add(new ChatItem(AppConst.VIEW_TYPE_RECEIVE,"Oh, that's good!","22.04"));
    }




    @Override
    public void openSendAsSocial() {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, new SendMessageFragment(), TAG_FRAGMENT)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void updateChat() {

    }

    @Override
    protected void setUp(View view) {
        setCustomChat();
        linearLayoutManager=new LinearLayoutManager(getActivity());
        chats.setLayoutManager(linearLayoutManager);

        chatAdapter=new ChatAdapter(chatItemList,getActivity());
        chats.getLayoutManager().scrollToPosition(chatItemList.size()-1);
        chats.setAdapter(chatAdapter);
    }

    @Override
    public void onDestroyView() {
        mPresenter.onDetach();
        super.onDestroyView();
    }
}
