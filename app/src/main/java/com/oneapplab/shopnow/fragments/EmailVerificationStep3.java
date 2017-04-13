package com.oneapplab.shopnow.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.oneapplab.shopnow.R;

/**
 * Created by haider on 09-04-2017.
 */

public class EmailVerificationStep3 extends Fragment implements View.OnClickListener{

    private EditText mPhoneNumber;
    private Button mSmsButton;
    private String mCountryIso;
    private TextWatcher mNumberTextWatcher;
    private ImageButton mBtnClose;
    private Button createAccount;
    private EditText joinType;
    private TextInputLayout inputType;
    LinearLayout shopNameLayout;
    View.OnFocusChangeListener listener;
    CharSequence options[] = new CharSequence[] {"User","Shop"};

    private View.OnClickListener mBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.btn_close_emailVerification:
                    FragmentManager manager = getFragmentManager();
                    android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.content_frame, new PhoneVerificationStep1());
                    transaction.commit();
                    break;
                case R.id.createAccount:
                    FragmentManager manager2 = getFragmentManager();
                    android.support.v4.app.FragmentTransaction transaction2 = manager2.beginTransaction();
                    transaction2.replace(R.id.content_frame, new RegistrationDetailsStep4());
                    transaction2.commit();
                    break;

            }

        }
    };


    private View.OnFocusChangeListener focus=(new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if(hasFocus){
               // chooserJoinType();
               // Toast.makeText(getActivity(), "got the focus", Toast.LENGTH_LONG).show();
            }else {
               // Toast.makeText(getActivity(), "lost the focus", Toast.LENGTH_LONG).show();
            }
        }
    });
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_emailverificationstep3, container, false);

        view.findViewById(R.id.userRadio).setOnClickListener(this);
        view.findViewById(R.id.shopRadio).setOnClickListener(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBtnClose = (ImageButton) view.findViewById(R.id.btn_close_emailVerification);
        shopNameLayout= (LinearLayout) view.findViewById(R.id.login_form5);
      //  joinType = (EditText) view.findViewById(R.id.joinType);
      //  inputType = (TextInputLayout) view.findViewById(R.id.input);
      //  inputType.setOnClickListener(edtClick);
        mBtnClose.setOnClickListener(mBtnClickListener);

       // joinType.setOnFocusChangeListener(focus);

        createAccount = (Button) view.findViewById(R.id.createAccount);
        createAccount.setOnClickListener(mBtnClickListener);

    }

   /* public void chooserJoinType(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        builder.setTitle("Select Join Type:");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which){
                    case 0:
                        joinType.setText("User");
                        break;
                    case 1:
                        joinType.setText("Shop");
                }
            }
        });
        builder.setNegativeButton(getString(R.string.cancelDialog), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }
*/


    @Override
    public void onClick(View view) {

        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {

            case R.id.userRadio:

                shopNameLayout.setVisibility(View.GONE);
                break;

            case R.id.shopRadio:

                shopNameLayout.setVisibility(View.VISIBLE);

                break;

        }

    }
}


