package com.oneapplab.shopnow.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oneapplab.shopnow.R;
import com.oneapplab.shopnow.baseFragment.BaseFragment;
import com.oneapplab.shopnow.enums.FragmentEnum;
import com.oneapplab.shopnow.firebaseDatabase.DatabaseAccess;

import java.util.List;


public class UserProfileFragment extends BaseFragment {

    private AppCompatEditText userName, userOrganization, userBusinessRole, userPhone,userEmail, userOfficeAddress;
    private AppCompatImageView backButtonArrowIV, selectBusinessRole;
    private String str, allData;
    private DatabaseAccess dbAccess;
    private Context context;
    private List<String> fullNames;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.nav_user_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        handler = new Handler();
        mListener.setToolbarTitle("Profile Settings", FragmentEnum.Dashboard);




        userName = (AppCompatEditText) view.findViewById(R.id.userNameInProfile);

        userEmail = (AppCompatEditText) view.findViewById(R.id.userEmailInProfile);

        userPhone = (AppCompatEditText) view.findViewById(R.id.userPhoneNumberInProfile);
        userOfficeAddress = (AppCompatEditText) view.findViewById(R.id.userOfcAddressInProfile);



        /*if (fullNames.size() > 0) {
            for (int i = 0; i < fullNames.size(); i++) {
                String name = fullNames.get(i);
                userName.setText(name);

            }
            }
*/
    }





    @Override
    public boolean shouldDoNormalOperationOnBackPressed() {

        return false;
    }
    @Override
    public void toolbarTitleClicked(boolean anchor) {

    }





}
