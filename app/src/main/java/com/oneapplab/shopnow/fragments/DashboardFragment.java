package com.oneapplab.shopnow.fragments;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.oneapplab.shopnow.R;
import com.oneapplab.shopnow.baseFragment.BaseFragment;
import com.oneapplab.shopnow.enums.FragmentEnum;
import com.oneapplab.shopnow.firebase.MyFirebaseMessagingService;
import com.oneapplab.shopnow.fragmentHelper.FragmentMessageContainer;

import java.util.ArrayList;


public class DashboardFragment extends BaseFragment {
    private AppCompatButton btn1;

    private Button btnSubmit;
    private EditText itemName;
    private RadioGroup businessType;
    FirebaseDatabase database;
    DatabaseReference myRef, shopRef;
    private static final String TAG = "myfirebsae";

    String token;
    private static String uniqueID = null;
    private static final String PREF_UNIQUE_ID = "PREF_UNIQUE_ID";
    ;
    String customerType, cust;
    String buyItem;
    String userId;

    String item;

    double latitude;

    double longitude;
    double latitudes, longitudes;
    static final int REQUEST_TAKE_PHOTO = 1;

    String tokens;
    MyFirebaseMessagingService service;
    ArrayList<String> phoneNumbers;
    AppCompatImageView imgView;
    private Button btnUpload, takePictures;
    private static final int SELECT_PICTURE = 101;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    String mCurrentPhotoPath;
    LinearLayout editLayout,btnLayout;

    private View.OnClickListener sendToFrag1 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            mListener.openDesiredFragment(new FragmentMessageContainer(
                    FragmentEnum.OneTimePassword,
                    FragmentEnum.Dashboard,
                    null,
                    true,
                    null));
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mListener.setToolbarTitle(getString(R.string.dashboard_fragment), FragmentEnum.Dashboard);





    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void toolbarTitleClicked(boolean anchor) {

    }

    @Override
    public boolean shouldDoNormalOperationOnBackPressed() {
        return false;
    }



}
