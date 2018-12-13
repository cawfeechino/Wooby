package com.example.dkale.wooby;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Spinner;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;

public class GenreSearch extends AppCompatActivity{

    Spinner genreType;
//    public static final String EXTRA_REPLY_MAINTENANCE_TYPE = "com.example.android.loglistsql.MAINTTYPE";

//    private String[] genreList = {"action", "adventure","comedy","drama","ecchi",
//            "fantasy","horror", "mahou shoujo","mecha","music","mystery","psychological",
//            "romance","sci-fi","slice of life","sports","supernatural","thriller"};

//    private String genreQuery = "";
    String spinnerOption;
    TextInputLayout MaitType;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.genre_search);

    }

    private boolean validateGenre(){
        String selectedOption = String.valueOf(genreType.getSelectedItem());
        String[] options = getResources().getStringArray(R.array.genre_options);

        if(selectedOption.equals("Choose an option...")){
            MaitType.setError("Please Select a Genre");
            requestFocus(genreType);
            return false;
        }
        else if(selectedOption.equals("Other")){
            spinnerOption = "hentai";
        }
        else{
            spinnerOption = selectedOption;
            Log.d("genre selected", "validateGenre: " + spinnerOption);
        }
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private boolean submit(){
        if(!validateGenre()){
            return false;
        }
        return true;
    }

}

