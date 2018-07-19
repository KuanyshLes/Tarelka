package kz.production.kuanysh.tarelka.ui.fragments;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kz.production.kuanysh.tarelka.R;
import kz.production.kuanysh.tarelka.data.network.model.chat.Chat;
import kz.production.kuanysh.tarelka.di.component.ActivityComponent;
import kz.production.kuanysh.tarelka.ui.base.BaseFragment;
import kz.production.kuanysh.tarelka.ui.adapters.ChatAdapter;

import static android.app.Activity.RESULT_OK;
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

    @BindView(R.id.chat_image_message)
    ImageView imageMessage;

    @BindView(R.id.chat_progressbar)
    ProgressBar progressBar;

    @Inject
    ChatAdapter chatAdapter;

    private int page_number=0;

    private static int all_page_number;

    private boolean isLoading=true;
    private int pastVisibleItems,visibleItemCount,totalItemCount,previous_total=0;
    private int view_threshold=15;

    private static List<Chat> chatsListFra;

    private LinearLayoutManager linearLayoutManager;
    private static Uri uriImage;
    private static String filePath;
    private static String imageString;
    private static  Bitmap imageMap;


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

        return view;
    }


    @OnClick(R.id.chat_new_mail)
    public void onMailClick(View view){
        mPresenter.onMailClick();
    }

    @OnClick(R.id.chat_send)
    public void sendMessage(){
        mPresenter.onSendClick(message.getText().toString(),page_number);
        message.setText("");
        if(chatsListFra.size()!=0){
            chats.getLayoutManager().scrollToPosition(chatsListFra.size()-1);
        }


    }

    @OnClick(R.id.chat_image_message)
    public void sendImage(){
        mPresenter.onImageclick();
    }

    @Override
    public void openSendAsSocial() {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, new SendMessageFragment(), TAG_FRAGMENT)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void updateChat(List<Chat> chatList,int page_number) {
        all_page_number=page_number;
        progressBar.setVisibility(View.INVISIBLE);
        //chatAdapter.removeItems();
        for(int i=0;i<chatList.size();i++){
            chatsListFra.add(0,chatList.get(i));
        }
        chatAdapter.addItems(chatsListFra);

        chats.getLayoutManager().scrollToPosition(chatList.size()-1);
    }

    @Override
    public void openImagePicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }
    @Override
    public  void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
            try{
                uriImage = data.getData();
                filePath=getPath(getActivity(),uriImage);
                mPresenter.getMvpView().showMessage(filePath+"");
                final InputStream inputStream = getActivity().getContentResolver().openInputStream(uriImage);
                imageMap = BitmapFactory.decodeStream(inputStream);

                mPresenter.getMvpView().showMessage("result on activity");
                mPresenter.onSendImage(uriImage,filePath,getActivity(),page_number);


            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Image was not found", Toast.LENGTH_SHORT).show();
            }

        }
    }

    public static String getPath(Context context, Uri uri ) {
        String result = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver( ).query( uri, proj, null, null, null );
        if(cursor != null){
            if ( cursor.moveToFirst( ) ) {
                int column_index = cursor.getColumnIndexOrThrow( proj[0] );
                result = cursor.getString( column_index );
            }
            cursor.close( );
        }
        if(result == null) {
            result = "Not found";
        }
        return result;
    }



    @Override
    protected void setUp(View view) {
        linearLayoutManager=new LinearLayoutManager(getActivity());
        chats.setLayoutManager(linearLayoutManager);
        chats.setAdapter(chatAdapter);
        progressBar.setVisibility(View.VISIBLE);
        mPresenter.onViewPrepared(page_number);
        chatsListFra=new ArrayList<>();

        /*Log.d("myTag", "onScrolled: visibleItem"+linearLayoutManager.getChildCount());
        Log.d("myTag", "onScrolled: totalItemCount"+linearLayoutManager.getItemCount());
        Log.d("myTag", "onScrolled: pastVisibleItems"+linearLayoutManager.findFirstVisibleItemPosition());*/


        chats.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                visibleItemCount=linearLayoutManager.getChildCount();
                totalItemCount=linearLayoutManager.getItemCount();
                pastVisibleItems=linearLayoutManager.findFirstVisibleItemPosition();

                Log.d("myTag", "onScrolled: ------------------------------------------------------------");
                Log.d("myTag", "onScrolled: visibleItem"+linearLayoutManager.getChildCount());
                Log.d("myTag", "onScrolled: totalItemCount"+linearLayoutManager.getItemCount());
                Log.d("myTag", "onScrolled: pastVisibleItems"+linearLayoutManager.findFirstVisibleItemPosition());


                if(dy<0){
                    if(isLoading){
                        if(totalItemCount>previous_total){
                            isLoading=false;
                            previous_total=totalItemCount;
                        }
                    }
                    if(!isLoading && (pastVisibleItems + visibleItemCount >= totalItemCount)){
                        if(all_page_number!=page_number){
                            page_number++;
                            progressBar.setVisibility(View.VISIBLE);
                            mPresenter.onViewPrepared(page_number);
                            isLoading=true;
                        }

                    }
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        mPresenter.onDetach();
        super.onDestroyView();
    }
}
