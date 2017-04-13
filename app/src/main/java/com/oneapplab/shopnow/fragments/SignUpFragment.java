package com.oneapplab.shopnow.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.digits.sdk.android.AuthCallback;
import com.digits.sdk.android.Digits;
import com.digits.sdk.android.DigitsAuthButton;
import com.digits.sdk.android.DigitsException;
import com.digits.sdk.android.DigitsSession;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.oneapplab.shopnow.R;
import com.oneapplab.shopnow.models.SignUp;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;

import java.util.ArrayList;
import java.util.UUID;

import io.fabric.sdk.android.Fabric;

/**
 * Created by haider on 01-04-2017.
 */

public class SignUpFragment extends Fragment
        implements TextView.OnEditorActionListener {

    private Activity mContext;
    private ImageButton mBtnClose;
    private Button signUpBtn;
    private String token;
    View myView;
    String userId;

    String randomNumber;
    private ProgressDialog progressDialog;


    private Context context;
    public static String uniqueID = null;
    private static final String PREF_UNIQUE_ID = "PREF_UNIQUE_ID";
    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "xCY2q3J2DxDZTf69gjPxO4I8b";
    private static final String TWITTER_SECRET = "KBlLvk3XpaykUly7zmV3MSsSjYPgjFto2kHB5JXKTY1VrgEOt8";

    private RadioGroup businessType;
    String customerType;
    RadioButton radioButton;
    int selectedId;


    private AutoCompleteTextView fname;
    private EditText phone;
    private EditText emailid;
    private EditText passwords;
    private EditText address;

    private TextInputLayout inputLayoutPhone;
    private FirebaseDatabase database;
    private DatabaseReference signUpRef,signUpAccess;
    ArrayList<String> phoneNumbers;
    private FirebaseAuth  mAuth;

    RadioGroup radioGroup;
    SignUp user;


    public static SignUpFragment newInstance() {
        SignUpFragment fragmentSignup = new SignUpFragment();
        return fragmentSignup;


    }



    private View.OnClickListener mBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.sign_up_button:
                    addUser();
                    break;

                case R.id.btn_close:
                    FragmentManager manager = getFragmentManager();
                    android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.content_frame, new LoginFragment());
                    transaction.commit();
                    break;
            }

        }
    };






    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }

    /*@Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogTheme);
    }*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        myView = inflater.inflate(R.layout.fragment_signup, container, false);
        radioGroup = (RadioGroup) myView.findViewById(R.id.businessGroup);


        return myView;


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth= FirebaseAuth.getInstance();


        mBtnClose = (ImageButton) view.findViewById(R.id.btn_close);
        mBtnClose.setOnClickListener(mBtnClickListener);

        signUpBtn = (Button) view.findViewById(R.id.sign_up_button);
        signUpBtn.setOnClickListener(mBtnClickListener);

        fname = (AutoCompleteTextView) view.findViewById(R.id.fullname);
        phone = (EditText) view.findViewById(R.id.phone);
        emailid = (EditText) view.findViewById(R.id.emailedt);
        passwords = (EditText) view.findViewById(R.id.password);
        address = (EditText) view.findViewById(R.id.address);

        progressDialog = new ProgressDialog(getActivity());
        inputLayoutPhone = (TextInputLayout) view.findViewById(R.id.input_layout_phone);

        phone.addTextChangedListener(new MyTextWatcher(phone));

        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(getActivity(),new TwitterCore(authConfig), new Digits.Builder().build());


        DigitsAuthButton digitsButton = (DigitsAuthButton) view.findViewById(R.id.auth_button);
        digitsButton.setCallback(new AuthCallback() {
            @Override
            public void success(DigitsSession session, String phoneNumber) {
                // TODO: associate the session userID with your user model
                Toast.makeText(getActivity(), "Authentication successful for "
                        + phoneNumber, Toast.LENGTH_LONG).show();
            }

            @Override
            public void failure(DigitsException exception) {
                Log.d("Digits", "Sign in with Digits failure", exception);
            }
        });

        id(mContext);



    }


    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            //Hide the keyboard when the user presses done on it

            InputMethodManager imm = (InputMethodManager) mContext.getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(address.getWindowToken(), 0);
            return true;
        }
        return false;
    }

    public void addUser() {

        if(!validatePhone()){
            return;
        }
       /* token= SharedPrefManagerToken.getInstance(context).getDeviceToken();


        if (token == null) {
            Toast.makeText(context, "Token not generated", Toast.LENGTH_LONG).show();
            return;
        }*/

        int selectedId = radioGroup.getCheckedRadioButtonId();

        radioButton = (RadioButton) myView.findViewById(selectedId);

        customerType = (String) radioButton.getText();



        //getRandomNumber();

        String fullName = fname.getText().toString();
        final String mobileNumber = phone.getText().toString();
        final String email = emailid.getText().toString();
        String password = passwords.getText().toString();
        String addresses = address.getText().toString();




        //checking if email and passwords are empty


        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();


        database = FirebaseDatabase.getInstance();


         user = new SignUp();
        user.setFullName(fullName);
        user.setMobileNumber(mobileNumber);
        user.setEmailID(email);
      //  user.setPassword(password);
        user.setAddress(addresses);
        user.setToken(token);

        signUpRef = database.getReference();
        userId = signUpRef.push().getKey();

        signUpRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        //creating a new user
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if(task.isSuccessful()){
                            progressDialog.setMessage("Registration successful...");

                            signUpRef.child("signup").child(uniqueID).child(customerType).child(uniqueID).setValue(user);
                            // display some message here
                          Toast.makeText(getActivity(),"Successfully registered"+" "+email,Toast.LENGTH_LONG).show();
                        }else{
                            progressDialog.setMessage("Registration not successful..");
                            //display some message here
                           Toast.makeText(getActivity(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });


    }

    public synchronized static String id(Context context) {
        if (uniqueID == null) {
            SharedPreferences sharedPrefs = context.getSharedPreferences(
                    PREF_UNIQUE_ID, Context.MODE_PRIVATE);
            uniqueID = sharedPrefs.getString(PREF_UNIQUE_ID, null);

            if (uniqueID == null) {
                uniqueID = UUID.randomUUID().toString();
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putString(PREF_UNIQUE_ID, uniqueID);
                editor.apply();
            }
        }

        return uniqueID;
    }

  /*  public static int getRandomNumber() {
        int max = 999999;
        int min = 100000;
        int joinid = (int) Math.round(Math.random() * (max - min + 1) + min);

      return joinid;*/

    private boolean validatePhone() {
        if (phone.getText().toString().trim().isEmpty()) {
            inputLayoutPhone.setError(getString(R.string.error_phone_digit));
            requestFocus(phone);
            return false;
        } else {
            inputLayoutPhone.setErrorEnabled(false);
        }

        return true;
    }
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.phone:
                    validatePhone();
                    break;

            }
        }
    }


}