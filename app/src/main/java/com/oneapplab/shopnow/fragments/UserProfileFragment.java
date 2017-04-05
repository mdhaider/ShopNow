package com.oneapplab.shopnow.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oneapplab.shopnow.R;

import java.util.List;


public class UserProfileFragment extends Fragment {

    private AppCompatEditText userName, userOrganization, userBusinessRole, userPhone,userEmail, userOfficeAddress;
    private AppCompatImageView backButtonArrowIV, selectBusinessRole;
    private String str, allData;
    private Context context;
    private List<String> fullNames;

    public static UserProfileFragment newInstance() {
        UserProfileFragment userProfileFragments = new UserProfileFragment();
        return userProfileFragments;


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);




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

}
