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
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kz.production.kuanysh.tarelka.R;
import kz.production.kuanysh.tarelka.data.network.model.profile.Authorization;
import kz.production.kuanysh.tarelka.di.component.ActivityComponent;
import kz.production.kuanysh.tarelka.ui.base.BaseFragment;
import kz.production.kuanysh.tarelka.utils.AppConst;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.app.Activity.RESULT_OK;
import static kz.production.kuanysh.tarelka.ui.activities.mainactivity.MainActivity.TAG_PROFILE;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileEditFragment extends BaseFragment implements ProfileEditMvpView{

    @Inject
    ProfileEditPresenter<ProfileEditMvpView> mPresenter;

    @BindView(R.id.profile_edit_photo)
    ImageView photo;

    @BindView(R.id.profile_edit_name)
    EditText name;

    @BindView(R.id.profile_edit_age)
    EditText age;

    @BindView(R.id.profile_edit_weight)
    EditText weight;

   // @BindView(R.id.profile_edit_aim)
    //EditText aim;

    @BindView(R.id.profile_edit_add_photo)
    TextView addPhoto;

    @BindView(R.id.profile_edit_save)
    TextView save;

    @BindView(R.id.profile_edit_height)
    EditText height;

    private static Authorization  profile;
    private static Uri uriImage;
    private static String filePath;
    private static String imageString;
    private static  Bitmap imageMap;


    public ProfileEditFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final Bundle bundle = getArguments();
        if (bundle != null){
            profile = (Authorization) bundle.get(AppConst.KEY_PROFILE);
        }

        View view= inflater.inflate(R.layout.fragment_profile_edit, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);
        }


        return view;
    }

    @OnClick(R.id.profile_edit_save)
    public void save(){
        if(filePath!=null && uriImage!=null){
            Log.i("PATH", uriImage.toString());
            //FileUtils.getFile(uriImage);
            File file = new File(filePath);

            RequestBody filePart= RequestBody.create(MediaType.parse(getActivity().getContentResolver().getType(uriImage)),
                    file);

            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("image", file.getName(), filePart);

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
            mPresenter.getMvpView().showMessage("Please select an image");
        }



    }

    @OnClick(R.id.profile_edit_add_photo)
    public void addPhoto(View view){
        mPresenter.onAddPhotoClick();
    }



    @Override
    public void openProfileFragment() {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .disallowAddToBackStack()
                .replace(R.id.content_frame, ProfileFragment.newInstance(), TAG_PROFILE)
                .commit();
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
                photo.setImageBitmap(imageMap);



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
        name.setText(mPresenter.getDataManager().getCurrentUserName());
        age.setText(mPresenter.getDataManager().getAge());
        weight.setText(mPresenter.getDataManager().getWeight());
        height.setText(mPresenter.getDataManager().getHeight());


        /*Glide.with(this)
                .load(mPresenter.getDataManager().getImage())
                .into(photo);*/
        //aim.setText("");
    }


    @Override
    public void onDestroyView() {
        mPresenter.onDetach();
        super.onDestroyView();
    }

}
