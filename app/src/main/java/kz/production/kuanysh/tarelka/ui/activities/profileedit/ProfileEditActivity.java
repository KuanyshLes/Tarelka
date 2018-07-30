package kz.production.kuanysh.tarelka.ui.activities.profileedit;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kz.production.kuanysh.tarelka.R;
import kz.production.kuanysh.tarelka.data.network.model.profile.Authorization;
import kz.production.kuanysh.tarelka.ui.activities.mainactivity.MainActivity;
import kz.production.kuanysh.tarelka.ui.base.BaseActivity;
import kz.production.kuanysh.tarelka.ui.welcome.CreateAimActivity;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ProfileEditActivity extends BaseActivity implements ProfileEditNewMvpView{

    @Inject
    ProfileEditNewPresenter<ProfileEditNewMvpView> mPresenter;

    @BindView(R.id.profile_edit_photo_new)
    ImageView photo;

    @BindView(R.id.profile_edit_back)
    ImageView back;

    @BindView(R.id.profile_edit_name_new)
    EditText name;

    @BindView(R.id.profile_edit_age_new)
    EditText age;

    @BindView(R.id.profile_edit_weight_new)
    EditText weight;

    @BindView(R.id.profile_edit_aim_new)
    TextView aim;

    @BindView(R.id.profile_edit_aim_new_card)
    CardView profile_edit_aim_new_card;

    @BindView(R.id.profile_edit_add_photo_new)
    TextView addPhoto;

    @BindView(R.id.profile_edit_save_new)
    TextView save;

    @BindView(R.id.profile_edit_height_new)
    EditText height;

    private static Authorization profile;
    private static Uri uriImage;
    private static String filePath;
    private static String imageString;
    private static Bitmap imageMap;
    public static String KEY_EDIT_PROFILE="qwer";
    public static String KEY_EDIT_AIM="aims";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        getActivityComponent().inject(this);
        setUnBinder(ButterKnife.bind(this));
        mPresenter.onAttach(ProfileEditActivity.this);
        setUp();

    }

    @Override
    protected void setUp() {
        name.setText(mPresenter.getDataManager().getCurrentUserName().replace("\"",""));
        age.setText(mPresenter.getDataManager().getAge());
        weight.setText(mPresenter.getDataManager().getWeight());
        height.setText(mPresenter.getDataManager().getHeight());
        aim.setText(mPresenter.getDataManager().getAims());

        if(mPresenter.getDataManager().getImage()!=null){
            Glide.with(this)
                    .load(mPresenter.getDataManager().getImage())
                    .into(photo);
        }

    }

    @OnClick(R.id.profile_edit_aim_new_card)
    public void openEdit(){
        mPresenter.onEditCardClick();
    }

    @OnClick(R.id.profile_edit_back)
    public void setGo(){
        mPresenter.onBackClick();
    }

    @OnClick(R.id.profile_edit_save_new)
    public void save(){
        if(filePath!=null && uriImage!=null){
            Log.i("PATH", uriImage.toString());
            //FileUtils.getFile(uriImage);
            File file = new File(filePath);

            RequestBody fbody = RequestBody.create(MediaType.parse("image/*"), file);
            //RequestBody filePart= RequestBody.create(MediaType.parse(getContentResolver().getType(uriImage)), file);

            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("image", file.getName(), fbody);

            /*String descriptionString = "image/*";
            RequestBody description =
                    RequestBody.create(
                            okhttp3.MultipartBody.FORM, descriptionString);*/

            mPresenter.onSaveClick(body,
                    name.getText().toString(),
                    age.getText().toString(),
                    weight.getText().toString(),
                    Integer.valueOf(height.getText().toString()));
        }
        else{
            mPresenter.onSaveClickWithoutImage(name.getText().toString(),
                    age.getText().toString(),
                    weight.getText().toString(),
                    Integer.valueOf(height.getText().toString()));
        }



    }

    @OnClick(R.id.profile_edit_add_photo_new)
    public void addPhoto(View view){
        mPresenter.onAddPhotoClick();
    }



    @Override
    public void openProfileFragment() {
        Intent intent=new Intent(ProfileEditActivity.this, MainActivity.class);
        intent.putExtra(KEY_EDIT_PROFILE,KEY_EDIT_PROFILE);
        startActivity(intent);
    }


    @Override
    public void openImagePicker() {
        com.esafirm.imagepicker.features.ImagePicker.create(this)
                // set whether pick and / or camera action should return immediate result or not.
                .folderMode(true) // folder mode (false by default)
                .toolbarFolderTitle("Folder") // folder selection title
                .toolbarImageTitle("Нажмите чтобы выбрать") // image selection title
                .toolbarArrowColor(Color.BLACK) // Toolbar 'up' arrow color
                .includeVideo(true) // Show video on image picker
                .single()
                .limit(1) // max images can be selected (99 by default)
                .showCamera(true) // show camera or not (true by default)
                .imageDirectory("Tarelka") // directory name for captured image  ("Camera" folder by default)
                // .theme(R.style.CustomImagePickerTheme) // must inherit ef_BaseTheme. please refer to sample
                .enableLog(false) // disabling log
                //.imageLoader(new GrayscaleImageLoder()) // custom image loader, must be serializeable
                .start(); // start image picker activity with request code
    }

    @Override
    public void openAimsEditActivity() {
        Intent intent=new Intent(ProfileEditActivity.this, CreateAimActivity.class);
        intent.putExtra(KEY_EDIT_AIM,KEY_EDIT_AIM);
        startActivity(intent);
    }

    @Override
    public  void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (com.esafirm.imagepicker.features.ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            Image image = ImagePicker.getFirstImageOrNull(data);
            File file = new File(image.getPath());

            uriImage = Uri.fromFile(file);
            filePath=image.getPath();

            final InputStream inputStream;
            try {
                inputStream = getContentResolver().openInputStream(uriImage);
                imageMap = BitmapFactory.decodeStream(inputStream);
                photo.setImageBitmap(imageMap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
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
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }
}
