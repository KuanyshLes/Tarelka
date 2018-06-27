package kz.production.kuanysh.tarelka.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import kz.production.kuanysh.tarelka.R;
import kz.production.kuanysh.tarelka.di.component.ActivityComponent;
import kz.production.kuanysh.tarelka.ui.base.BaseFragment;
import kz.production.kuanysh.tarelka.utils.AppConst;
import kz.production.kuanysh.tarelka.data.TarelkaDataFactory;

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

    @BindView(R.id.profile_aim)
    TextView aim;

    @BindView(R.id.profile_phone)
    TextView phone;

    private Bundle bundle;


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
    public void updateInfo(List<String> info) {

    }

    @Override
    public void openEditFragment() {
        ProfileEditFragment profileEditFragment=new ProfileEditFragment();
        bundle=new Bundle();
        bundle.putParcelable(AppConst.KEY_PROFILE, TarelkaDataFactory.getProfile());
        setFragment(profileEditFragment);
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
