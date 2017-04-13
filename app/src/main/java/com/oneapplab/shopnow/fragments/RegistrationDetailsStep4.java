package com.oneapplab.shopnow.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import com.oneapplab.shopnow.R;

public class RegistrationDetailsStep4 extends Fragment implements View.OnClickListener {

    private String mParam1;
    private String mParam2;
    private AppCompatCheckBox checkbox1, checkbox2;


    public RegistrationDetailsStep4() {
        // Required empty public constructor
    }

    public static RegistrationDetailsStep4 newInstance(String param1, String param2) {
        RegistrationDetailsStep4 fragment = new RegistrationDetailsStep4();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_registration_details_step4, container, false);

        // Set the onClick for each of our views as the one implemented by this Fragment
        view.findViewById(R.id.maleRadio).setOnClickListener(this);
        view.findViewById(R.id.femaleRadio).setOnClickListener(this);
        return view;

    }

    @Override
    public void onViewCreated(View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


    @Override
    public void onClick(View view) {

        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {

            case R.id.maleRadio:
                Toast.makeText(getActivity(), "A", Toast.LENGTH_LONG).show();
                break;

            case R.id.femaleRadio:
                Toast.makeText(getActivity(), "B", Toast.LENGTH_LONG).show();
                break;

        }

    }
}