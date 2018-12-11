package com.example.dkale.wooby;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class GenreSpinner extends Activity implements AdapterView.OnItemSelectedListener {

    Spinner spinner = (Spinner) findViewById(R.id.genre_spinner);
    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.ani_genres,android.R.layout.simple_spinner_item);


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        Log.e("clicked","this worked");

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {


    }

    public class SpinnerActivity extends Activity implements AdapterView.OnItemSelectedListener{

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Spinner spinner = (Spinner) findViewById(R.id.genre_spinner);
            spinner.setOnItemSelectedListener(this);
            Log.e("clicked","this worked2");
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}
