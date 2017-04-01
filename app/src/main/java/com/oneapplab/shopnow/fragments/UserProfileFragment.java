package com.oneapplab.shopnow.fragments;

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


public class UserProfileFragment extends BaseFragment {

    private AppCompatEditText userName, userOrganization, userBusinessRole, userPhone,userEmail, userOfficeAddress;
    private AppCompatImageView backButtonArrowIV, selectBusinessRole;
    private String str, allData;


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



        if (fragmentMessageContainer != null) {
            if (fragmentMessageContainer.isComingback()) {

                allData = fragmentMessageContainer.getComingData().toString();

                for (int i = 0; i < allData.length(); i++) {
                    int a = allData.indexOf("businessRoleText='");
                    int b = allData.lastIndexOf("',");
                    str = allData.substring(a + 18, b);
                }
            }
        }

    }





    @Override
    public boolean shouldDoNormalOperationOnBackPressed() {

        return false;
    }
    @Override
    public void toolbarTitleClicked(boolean anchor) {

    }





}
