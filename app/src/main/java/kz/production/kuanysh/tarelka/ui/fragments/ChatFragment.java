package kz.production.kuanysh.tarelka.ui.fragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.esafirm.imagepicker.features.ReturnMode;
import com.nguyenhoanglam.imagepicker.model.Image;
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kz.production.kuanysh.tarelka.R;
import kz.production.kuanysh.tarelka.app.Config;
import kz.production.kuanysh.tarelka.data.network.model.chat.Chat;
import kz.production.kuanysh.tarelka.di.component.ActivityComponent;
import kz.production.kuanysh.tarelka.ui.base.BaseFragment;
import kz.production.kuanysh.tarelka.ui.adapters.ChatAdapter;
import kz.production.kuanysh.tarelka.utils.NotificationUtils;

import static android.app.Activity.RESULT_OK;
import static com.facebook.accountkit.internal.AccountKitController.getApplicationContext;
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

    /*@BindView(R.id.chat_progressbar)
    ProgressBar progressBar;*/

    @Inject
    ChatAdapter chatAdapter;

    @BindView(R.id.chat_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private int page_number=0;

    private static int all_page_number;
    private static Boolean isImageSended=false;

    private static List<Chat> chatsListFra;
    private static ArrayList<Image> imageSelected;

    private LinearLayoutManager linearLayoutManager;
    private static Uri uriImage;
    private static String filePath;
    private static String imageString;
    private static  Bitmap imageMap;
    private static Dialog dialog;
    private static AlertDialog.Builder mBuilder;
    private static String mCurrentPhotoPath;
    private static int IMAGE_CAPTURE=31;
    public static String CAMERA_KEY="camera key";
    public static List<Uri> uriList;
    public static List<String> filePathList;

    private BroadcastReceiver mRegistrationBroadcastReceiver;


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
        chatsListFra.clear();
        mPresenter.onSendClick(message.getText().toString(),0);
        message.setText("");
        if(chatsListFra.size()!=0){
            chats.getLayoutManager().scrollToPosition(chatsListFra.size()-1);
        }
        page_number=0;


    }

    @OnClick(R.id.chat_image_message)
    public void sendImage(){
        page_number=0;
        mPresenter.getMvpView().openImagePicker();
    }

    @Override
    public void openSendAsSocial() {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, new SendMessageFragment(), TAG_FRAGMENT)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void updateChat(List<Chat> chatList,int page_numberInt) {
        swipeRefreshLayout.setRefreshing(false);
        all_page_number=page_numberInt;
        for(int i=0;i<chatList.size();i++){
            chatsListFra.add(0,chatList.get(i));
        }
        chatAdapter.addItems(chatsListFra);

        chats.getLayoutManager().scrollToPosition(chatList.size()-1);

    }

    @Override
    public void openImagePicker() {
        com.esafirm.imagepicker.features.ImagePicker.create(this)
                // set whether pick and / or camera action should return immediate result or not.
                .folderMode(true) // folder mode (false by default)
                .toolbarFolderTitle("Folder") // folder selection title
                .toolbarImageTitle("Tap to select") // image selection title
                .toolbarArrowColor(Color.BLACK) // Toolbar 'up' arrow color
                .includeVideo(true) // Show video on image picker
                .multi() // multi mode (default mode)
                .limit(5) // max images can be selected (99 by default)
                .showCamera(true) // show camera or not (true by default)
                .imageDirectory("Tarelka") // directory name for captured image  ("Camera" folder by default)
               // .theme(R.style.CustomImagePickerTheme) // must inherit ef_BaseTheme. please refer to sample
                .enableLog(false) // disabling log
                //.imageLoader(new GrayscaleImageLoder()) // custom image loader, must be serializeable
                .start(); // start image picker activity with request code
    }


    @Override
    public  void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (com.esafirm.imagepicker.features.ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            uriList.clear();
            filePathList.clear();
            // Get a list of picked images
            List<com.esafirm.imagepicker.model.Image> images = com.esafirm.imagepicker.features.ImagePicker.getImages(data);
            for(int i=0;i<images.size();i++){
                File file = new File(images.get(i).getPath());
                Uri imageUri = Uri.fromFile(file);
                uriList.add(imageUri);
                filePathList.add(images.get(i).getPath());
            }
            mPresenter.onSendImage(uriList, filePathList, getActivity(), 0);
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
        List<String> link=new ArrayList<>();
        filePathList=new ArrayList<>();
        uriList=new ArrayList<>();
        imageSelected=new ArrayList<>();
        linearLayoutManager=new LinearLayoutManager(getActivity());
        linearLayoutManager.setStackFromEnd(true);
        chats.setLayoutManager(linearLayoutManager);
        chats.setAdapter(chatAdapter);
        chatAdapter.addContext(getActivity());
        //progressBar.setVisibility(View.VISIBLE);
        mPresenter.onViewPrepared(page_number);
        chatsListFra=new ArrayList<>();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(all_page_number!=page_number){
                    page_number++;
                    mPresenter.onViewPrepared(page_number);

                }else {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    chatsListFra.clear();
                    mPresenter.onViewPrepared(0);
                    Toast.makeText(context, "New message", Toast.LENGTH_SHORT).show();

                }
            }
        };


    }

    @Override
    public void onResume() {
        super.onResume();
        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());

    }

    @Override
    public void onDestroyView() {
        mPresenter.onDetach();
        super.onDestroyView();
    }
}
