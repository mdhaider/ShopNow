package com.oneapplab.shopnow.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
import com.oneapplab.shopnow.dashboard.DashboardActivity;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {


    ArrayList<String> emailList;
    public AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private Button btnSignUp;
    public static final String TAG_DIALOG = "dialog_add";
    ArrayList<String> allEmails;
    private TextView tv;
    String pass, pass1;
    //firebase auth object
    private FirebaseAuth firebaseAuth;
    Button mEmailSignInButton;

    //progress dialog
    private ProgressDialog progressDialog;

    private View.OnClickListener mBtnAddListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.email_sign_in_button:
                    goToMain();
                    break;
                case R.id.btnSignUp:
                    showDialogAdd();
                    break;
            }
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //getting firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        //if the objects getcurrentuser method is not null
        //means user is already logged in
       /* if(firebaseAuth.getCurrentUser() != null){
            //close this activity
            finish();
            //opening profile activity
            startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
        }
*/
        progressDialog = new ProgressDialog(this);



        mEmailView = (AutoCompleteTextView) findViewById(R.id.emailLogin);
        btnSignUp = (AppCompatButton) findViewById(R.id.btnSignUp);
        mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        btnSignUp.setOnClickListener(mBtnAddListener);
        mEmailSignInButton.setOnClickListener(mBtnAddListener);

        mPasswordView = (EditText) findViewById(R.id.passwordLogin);

        mPasswordView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() <= mPasswordView.getTotalPaddingLeft()) {
                        // your action for drawable click event

                        return true;
                    }
                }
                return false;
            }
        });


    }



   public void goToMain(){

       String email = mEmailView.getText().toString().trim();
       String password  = mPasswordView.getText().toString().trim();


       //checking if email and passwords are empty
       if(TextUtils.isEmpty(email)){
           Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
           return;
       }

       if(TextUtils.isEmpty(password)){
           Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
           return;
       }

       //if the email and password are not empty
       //displaying a progress dialog

       progressDialog.setMessage("Login Please Wait...");
       progressDialog.show();

       //logging in the user
       firebaseAuth.signInWithEmailAndPassword(email, password)
               .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       progressDialog.dismiss();
                       //if the task is successfull
                       if(task.isSuccessful()){
                           //start the profile activity
                           finish();
                           startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                       }
                   }
               });

   }


    private void showDialogAdd() {
      /*  FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container, SignUpFragment,"TAG");
        transaction.addToBackStack(null);
        transaction.commit();*/
    }

   public void  getPassword() {
       String enteredEmail= mEmailView.getText().toString();

       DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
       DatabaseReference passwordinDB = ref.child("signup").child("Shop").child(enteredEmail).child("password");

       passwordinDB.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
                 pass = dataSnapshot.getValue(String.class);
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });

       DatabaseReference passwordUser = ref.child("signup").child("User").child(enteredEmail).child("password");

       passwordUser.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               pass = dataSnapshot.getValue(String.class);
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });



   }





}













