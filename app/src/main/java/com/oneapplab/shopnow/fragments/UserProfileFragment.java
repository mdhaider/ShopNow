package com.oneapplab.shopnow.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.oneapplab.shopnow.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;


public class UserProfileFragment extends Fragment {
    ArrayList<String> users;
    private AppCompatEditText userName, userOrganization, userBusinessRole, userPhone, userEmail, userOfficeAddress;
    private AppCompatImageView backButtonArrowIV, selectBusinessRole;
    private String str, allData;
    private Context context;
    private List<String> fullNames;
    private DatabaseReference mDatabase;
    public static String uniqueID = null;
    private static final String PREF_UNIQUE_ID = "PREF_UNIQUE_ID";
    private String item;
    private DatabaseReference ref;
    private Uri filePath;
    private static final int PICK_IMAGE_REQUEST = 234;
    private AppCompatImageView img;


    private View.OnClickListener mBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.profileImg:
                    showFileChooser();
                    break;

            }
        }
    };

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

        id(getActivity());

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference islandRef = storageRef.child("images/profile.jpg");

        final long ONE_MEGABYTE = 1024 * 1024;
        islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {

                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                RoundedBitmapDrawable imga = RoundedBitmapDrawableFactory.create(getResources(),bmp);
                imga.setCircular(true);


                img.setImageDrawable(imga);
             //   img.setImageBitmap(bmp);
                // Data for "images/island.jpg" is returns, use this as needed
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });




        FirebaseDatabase database = FirebaseDatabase.getInstance();

       /* DatabaseReference ref5= database.getReference("signup").child(uniqueID);

        ref5.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    customerType=(child.getKey());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
*/

            DatabaseReference ref = database.getReference("signup").child(uniqueID).child("Shop").child(uniqueID).child("fullName");
            DatabaseReference ref2 = database.getReference("signup").child(uniqueID).child("Shop").child(uniqueID).child("emailID");
            DatabaseReference ref3 = database.getReference("signup").child(uniqueID).child("Shop").child(uniqueID).child("mobileNumber");
            DatabaseReference ref4 = database.getReference("signup").child(uniqueID).child("Shop").child(uniqueID).child("address");

            ref.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(DataSnapshot dataSnapshot) {
                String displayName = dataSnapshot.getValue().toString();

                userName.setText(displayName);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });

        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String displayEmail = dataSnapshot.getValue().toString();

                userEmail.setText(displayEmail);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });

        ref3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String displayPhone = dataSnapshot.getValue().toString();

                userPhone.setText(displayPhone);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });

        ref4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String displayAddress = dataSnapshot.getValue().toString();

                userOfficeAddress.setText(displayAddress);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });


        userName = (AppCompatEditText) view.findViewById(R.id.userNameInProfile);

        userEmail = (AppCompatEditText) view.findViewById(R.id.userEmailInProfile);

        userPhone = (AppCompatEditText) view.findViewById(R.id.userPhoneNumberInProfile);
        userOfficeAddress = (AppCompatEditText) view.findViewById(R.id.userOfcAddressInProfile);
        img = (AppCompatImageView) view.findViewById(R.id.profileImg);
        img.setOnClickListener(mBtnClickListener);
        // setValues();
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

   /* public synchronized String getCustomerType(Context context){

        if(customerType==null){
            FirebaseDatabase database=FirebaseDatabase.getInstance();

            DatabaseReference ref5= database.getReference("signup").child(uniqueID);

            ref5.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        customerType=(child.getKey());
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
        return customerType;


    }*/

    private void uploadFile() {

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();


        //if there is a file to upload
        if (filePath != null) {
            //displaying a progress dialog while upload is going on
            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Uploading");
            progressDialog.show();


            StorageReference riversRef = storageRef.child("images/profile.jpg");
            riversRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //if the upload is successfull
                            //hiding the progress dialog
                            progressDialog.dismiss();

                            //and displaying a success toast
                            Toast.makeText(getActivity(), "File Uploaded ", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            //if the upload is not successfull
                            //hiding the progress dialog
                            progressDialog.dismiss();

                            //and displaying error message
                            Toast.makeText(getActivity(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                    /*.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //calculating progress percentage
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                            //displaying percentage in progress dialog
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    });*/
        }
        //if there is not any file
        else {
            //you can display an error toast
        }

    }

    //method to show file chooser
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    //handling the image chooser activity result
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                RoundedBitmapDrawable imga = RoundedBitmapDrawableFactory.create(getResources(),bitmap);
                imga.setCircular(true);


                img.setImageDrawable(imga);
                uploadFile();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}
