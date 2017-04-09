package com.oneapplab.shopnow.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.oneapplab.shopnow.R;
import com.oneapplab.shopnow.firebase.MyFirebaseMessagingService;
import com.oneapplab.shopnow.sharedpreference.SharedPrefManagerToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.app.Activity.RESULT_OK;


public class DashboardFragment extends Fragment {
    private AppCompatButton btn1;

    private Button btnSubmit;
    private EditText itemName;
    private RadioGroup businessType;
    FirebaseDatabase database;
    DatabaseReference refShop;
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
    LinearLayout editLayout, btnLayout;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    private Uri filePath;


    public DashboardFragment() {
        // Required empty public constructor
    }


    private View.OnClickListener mBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.upload_photo:
                    openImageChooser();

                    break;

                case R.id.takePictures:
                    dispatchTakePicturesIntent();
                    break;
                case R.id.submitPhotos:
                   // submitRequest();
                    //uploadFile();
                    break;
            }
        }
    };


    public static DashboardFragment newInstance(String param1, String param2) {
        DashboardFragment fragment = new DashboardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onViewCreated(View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        itemName = (EditText) view.findViewById(R.id.et1);
        btnSubmit = (Button) view.findViewById(R.id.submitPhotos);
        btnUpload = (Button) view.findViewById(R.id.upload_photo);
        takePictures = (Button) view.findViewById(R.id.takePictures);


        imgView = (AppCompatImageView) view.findViewById(R.id.imgView);
        editLayout = (LinearLayout) view.findViewById(R.id.edittlayout);
        btnLayout = (LinearLayout) view.findViewById(R.id.btnLayout);

        btnSubmit.setOnClickListener(mBtnClickListener);
        btnUpload.setOnClickListener(mBtnClickListener);
        takePictures.setOnClickListener(mBtnClickListener);

        FirebaseStorage storage = FirebaseStorage.getInstance();



    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case SELECT_PICTURE:
                if (resultCode == RESULT_OK) {
                    // Get the url from data
                    filePath = data.getData();
                    if (null != filePath) {
                        // Get the path from the Uri
                        String path = getPathFromURI(filePath);
                        Log.i(TAG, "Image Path : " + path);
                        editLayout.setVisibility(View.VISIBLE);
                        btnLayout.setVisibility(View.VISIBLE);
                        btnUpload.setVisibility(View.GONE);
                        takePictures.setVisibility(View.GONE);


                        // Set the image in ImageView
                        imgView.setImageURI(filePath);
                        break;
                    }
                }
            case REQUEST_IMAGE_CAPTURE:
                if (resultCode == RESULT_OK) {
                    editLayout.setVisibility(View.VISIBLE);
                    btnLayout.setVisibility(View.VISIBLE);
                    btnUpload.setVisibility(View.GONE);
                    takePictures.setVisibility(View.GONE);


                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    imgView.setImageBitmap(imageBitmap);
                    break;

                }
        }
    }

    /* Get the real path from the URI */
    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getApplicationContext().getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;

    }

    private void dispatchTakePicturesIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public void submitRequest() {

        //get token
        tokens = SharedPrefManagerToken.getInstance(getActivity()).getDeviceToken();

        //get shopName
        buyItem = itemName.getText().toString();


        //check token for null values
        if (tokens == null) {
            Toast.makeText(getActivity(), "Token not generated", Toast.LENGTH_LONG).show();
            return;
        }

        database = FirebaseDatabase.getInstance();
        refShop=database.getReference().child("signup").child("Shop");
        refShop.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Get map of users in datasnapshot
                        collectPhoneNumbers((Map<String, Object>) dataSnapshot.getValue());
                        itemName.setText("");
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
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
    private void collectPhoneNumbers(Map<String, Object> token) {

        phoneNumbers = new ArrayList<>();

        //iterate through each user, ignoring their UID
        for (Map.Entry<String, Object> entry : token.entrySet()) {

            //Get user map
            Map singleUser = (Map) entry.getValue();
            //Get phone field and append to list
            phoneNumbers.add((String) singleUser.get("token"));
        }


        sendPost();
    }

    public void sendPost() {

        for (String name : phoneNumbers) {
            /*if(phoneNumbers.equals(token)){
                return;
            }*/
            try {
                JSONObject jsonObject = new JSONObject();
                JSONObject param = new JSONObject();
                jsonObject.put("to", name);
                param.put("body", buyItem);
                jsonObject.put("notification", param);
                post("https://fcm.googleapis.com/fcm/send", jsonObject.toString(), new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                Log.e("Error", e.toString());
                                //Something went wrong
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                if (response.isSuccessful()) {
                                    String responseStr = response.body().string();
                                    Log.d("Response", responseStr);
                                    // Do what you want to do with the response.
                                } else {
                                    // Request not successful
                                }
                            }
                        }
                );
            } catch (JSONException ex) {
                Log.d("Exception", "JSON exception", ex);
            }
        }


    }

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();

    Call post(String url, String json, Callback callback) {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "key=AAAAtPOFsq8:APA91bE43mTqshwz9OH4qjTgTTMY7FhB9R8hY5ZiXCLnt1VP1bDp7AtyPo8D8veRjy46Pf84_6NFvN897Pa8QpLVekw7bGuZSzfcnqhGQ5kEdQ8yV9FjkTjPChBeJGhuFF-otuBTQ7r1")
                .url(url)
                .post(body)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }



}
