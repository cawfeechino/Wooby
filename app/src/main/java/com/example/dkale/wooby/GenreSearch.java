package com.example.dkale.wooby;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.view.WindowManager;
import android.widget.Spinner;
import android.support.design.widget.TextInputLayout;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class GenreSearch extends AppCompatActivity{

    Spinner genreType;
    public static final String EXTRA_REPLY_MAINTENANCE_TYPE = "com.example.android.loglistsql.MAINTTYPE";

    private String[] genreList = {"action", "adventure","comedy","drama","ecchi",
            "fantasy","horror", "mahou shoujo","mecha","music","mystery","psychological",
            "romance","sci-fi","slice of life","sports","supernatural","thriller"};

    private String genreQuery = "";

    TextInputEditText mMaintenanceLocation, mCurrentMiles, mPriceOfMaintenance, mNotes, mOtherOption;
    String spinnerOption;
    TextInputLayout MaintLoc, CurrentMiles, PriceOfMait, Notes, MaitType;



//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.genre_search);
//    }

    private boolean validateType(){
        String selectedOption = String.valueOf(genreType.getSelectedItem());
        String[] options = getResources().getStringArray(R.array.genre_options);

        if(selectedOption.equals("Choose an option...")){
            MaitType.setError("Please Select a Maintenance Option");
            requestFocus(genreType);
            return false;
        } else if(selectedOption.equals("Other")){
            if(mOtherOption.getText().toString().isEmpty()){
                MaitType.setError("Please Type in the 'Other' Option");
                requestFocus(mOtherOption);
                return false;
            } else {
                spinnerOption = mOtherOption.getText().toString();
            }


//            return false;
        } else{
            spinnerOption = selectedOption;
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


}

