package kz.production.kuanysh.tarelka.ui.fragments;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import kz.production.kuanysh.tarelka.R;
import kz.production.kuanysh.tarelka.di.component.ActivityComponent;
import kz.production.kuanysh.tarelka.ui.base.BaseFragment;
import kz.production.kuanysh.tarelka.utils.AppConst;
import kz.production.kuanysh.tarelka.model.Profile;

import static android.app.Activity.RESULT_OK;
import static kz.production.kuanysh.tarelka.ui.activities.mainactivity.MainActivity.TAG_PROFILE;
import static kz.production.kuanysh.tarelka.utils.AppConst.TAG_FRAGMENT;

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

    @BindView(R.id.profile_edit_aim)
    EditText aim;

    @BindView(R.id.profile_edit_add_photo)
    TextView addPhoto;

    @BindView(R.id.profile_edit_save)
    TextView save;

    private static Profile profile;


    public ProfileEditFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final Bundle bundle = getArguments();
        if (bundle != null){
            profile = (Profile) bundle.get(AppConst.KEY_PROFILE);
            Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
        }

        View view= inflater.inflate(R.layout.fragment_profile_edit, container, false);
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

    @OnClick(R.id.profile_edit_save)
    public void save(View view){
        mPresenter.onSaveClick();
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
                .add(R.id.content_frame, ProfileFragment.newInstance(), TAG_PROFILE)
                .commit();
    }

    @Override
    public void updateInfo() {

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
                final Uri uriImage = data.getData();
                final InputStream inputStream = getActivity().getContentResolver().openInputStream(uriImage);
                final Bitmap imageMap = BitmapFactory.decodeStream(inputStream);
                photo.setImageBitmap(imageMap);
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Image was not found", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    protected void setUp(View view) {
        /*name.setText(profile.getName());
        age.setText(profile.getAge());
        weight.setText(profile.getWeight());
        aim.setText(profile.getAim());*/
    }


    @Override
    public void onDestroyView() {
        mPresenter.onDetach();
        super.onDestroyView();
    }

}
