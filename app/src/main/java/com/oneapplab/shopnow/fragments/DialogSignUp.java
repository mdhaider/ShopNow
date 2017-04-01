package com.oneapplab.shopnow.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.oneapplab.shopnow.R;

/**
 * Created by haider on 01-04-2017.
 */

public class DialogSignUp extends DialogFragment
        implements TextView.OnEditorActionListener {

    private Activity mContext;
    //Title of the dialog
    private TextView mTitle;
    //The close button for this dialog
    private ImageButton mBtnClose;
    //The area where the user can type his/her goal
    private EditText mInputWhat;
    //The control with which user can select the date for his/her goal by which they feel they wanna accomplish their goal

    //The button clicking which the goal and date will be added to the database
    private Button mBtnAdd;
    //The object which will be notified when the user hits the "Add Drop" button



    private View.OnClickListener mBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
               /* case R.id.btn_add_it:
                    addAction();
                    break;*/
            }
            dismiss();
        }
    };




    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //The control with which user can select the date for his/her goal by which they feel they wanna accomplish their goal
        //The button clicking which the goal and date will be added to the database

        //monitor the user clicking buttons such as DONE on the virtual keyboard


        //load custom fonts wherever appropriate
        mBtnClose = (ImageButton) view.findViewById(R.id.btn_close);
        mBtnClose.setOnClickListener(mBtnClickListener);

    }





    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            //Hide the keyboard when the user presses done on it

            InputMethodManager imm = (InputMethodManager) mContext.getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(mInputWhat.getWindowToken(), 0);
            return true;
        }
        return false;
    }
}