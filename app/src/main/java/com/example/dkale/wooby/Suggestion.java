package com.example.dkale.wooby;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import anilist.TestQuery;
import anilist.type.MediaSort;
import okhttp3.OkHttpClient;

import static android.R.*;
import static android.R.layout.*;
import static android.support.constraint.Constraints.TAG;




/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Suggestion.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Suggestion#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Suggestion extends android.support.v4.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button writer;
    private TextView aniName;
    private TextView anidescription;
    private ImageView aniPic;
    private TextView aniUrl;
    private TextView imageURL;
    private Button showImage;
    private Button showSuggestion;
    private int counter = 0;
    private OnFragmentInteractionListener mListener;
    private String name;
    private String desc;
    private String animePageURL;
    private String animeImageURL;
    Random rand = new Random();
    Spinner genreType;
    String spinnerOption;
    ArrayAdapter adapter;
    Spinner genreTest;
    private String[] genreList = {"action", "adventure","comedy","drama","ecchi",
            "fantasy","horror", "mahou shoujo","mecha","music","mystery","psychological",
            "romance","sci-fi","slice of life","sports","supernatural","thriller"};
    String genreRecord;


    public Suggestion() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Suggestion.
     */
    // TODO: Rename and change types and number of parameters
    public static Suggestion newInstance(String param1, String param2) {
        Suggestion fragment = new Suggestion();
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



     //   apolloTest();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        genreTest = (Spinner)findViewById(R.id.sp_maintenance_type);
        return inflater.inflate(R.layout.fragment_suggestion, container, false);

}

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onSuggestionFragmentInteraction(uri);
        }
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        apolloTest();
        initPicasso();
        buttonToWatch();
        buttonWatched();
//        imageView = (ImageView) getView().findViewById(R.id.suggestionImage);
//        Picasso.get().load("https://pm1.narvii.com/6561/78c92e781ab8f833b61fd85d0e8e82dc2a17076d_hq.jpg").into(imageView);
//        initCard();
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onSuggestionFragmentInteraction(Uri uri);
    }

    public void buttonToWatch(){
        writer = (Button) getView().findViewById(R.id.toWatchButton);
        aniName = (TextView) getView().findViewById(R.id.aniName);
        anidescription = (TextView) getView().findViewById(R.id.aniDescription);
        aniPic = (ImageView) getView().findViewById(R.id.aniImages);
        aniUrl = (TextView) getView().findViewById(R.id.aniURL);
        imageURL = (TextView) getView().findViewById(R.id.aimageURL2);
        Log.e("shit2",imageURL.getText().toString());
        writer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Added to 'To Watch' list", Toast.LENGTH_SHORT).show();
                if(getActivity() != null){
                    Log.e("aniname",aniName.getText().toString() + " " + anidescription.getText().toString() + " " + aniUrl.getText().toString() + " " + imageURL.getText().toString());
//                    This is the suggestion activity. This is where the button listener is for the Add to "Watch Later" list. Uncomment the line below and add the proper parameters
                    ((MainActivity) getActivity()).writeToWatchDatabase(aniName.getText().toString(),anidescription.getText().toString(),imageURL.getText().toString(),aniUrl.getText().toString());
//                    ((MainActivity) getActivity()).writeToWatchDatabase("Sailor Moon","Its about a magical girl...","https://upload.wikimedia.org/wikipedia/en/e/e5/SMVolume1.jpg","https://en.wikipedia.org/wiki/Sailor_Moon");
                }
            }
        });
    }

    public void buttonWatched(){
        writer = (Button) getView().findViewById(R.id.watchedButton);
        aniName = (TextView) getView().findViewById(R.id.aniName);
        anidescription = (TextView) getView().findViewById(R.id.aniDescription);
        aniPic = (ImageView) getView().findViewById(R.id.aniImages);
        aniUrl = (TextView) getView().findViewById(R.id.aniURL);
        imageURL = (TextView) getView().findViewById(R.id.aimageURL2);
        writer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Added to 'Watched' list", Toast.LENGTH_SHORT).show();
                if(getActivity() != null){
                    Log.e("aniname",aniName.getText().toString() + " " + anidescription.getText().toString() + " " + aniUrl.getText().toString() + " " + imageURL.getText().toString());
//                    This is the suggestion activity. This is where the button listener is for the Add to "Watch" list. Uncomment the line below and add the proper parameters
                    ((MainActivity) getActivity()).writeWatchedDatabase(aniName.getText().toString(),anidescription.getText().toString(),imageURL.getText().toString(),aniUrl.getText().toString());
                }
            }
        });
    }


    final static String BASE_URL = "https://graphql.anilist.co";


    public void apolloTest(){
        aniName = (TextView) getView().findViewById(R.id.aniName);
        anidescription = (TextView) getView().findViewById(R.id.aniDescription);
        aniPic = (ImageView) getView().findViewById(R.id.aniImages);
        aniUrl = (TextView) getView().findViewById(R.id.aniURL);
        imageURL = (TextView) getView().findViewById(R.id.aimageURL2);
        genreSelectTwo();


        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        ApolloClient apolloClient = ApolloClient.builder().serverUrl(BASE_URL).okHttpClient(okHttpClient).build();
        final anilist.TestQuery testQuery = anilist.TestQuery.builder().genre(new ArrayList<String>(Arrays.asList(genreRecord))).score(0).sort(new ArrayList<MediaSort>(Arrays.asList(MediaSort.SCORE_DESC))).build();
        apolloClient.query(testQuery).enqueue(new ApolloCall.Callback<anilist.TestQuery.Data>() {
            @Override
            public void onResponse(@NotNull Response<anilist.TestQuery.Data> response) {
                final StringBuffer buffer = new StringBuffer();
                TestQuery.Data anime = response.data();
                Log.d("String", "onResponse: " + spinnerOption);
                int max = anime.Page().media().size() - 1;
                int min = 0;
                int rando = (int) (Math.random() * max + min);
                String animenames = anime.Page().media().get(rando).title().romaji();
                String imageURL1 = anime.Page().media().get(rando).coverImage().large();
                String des = anime.Page().media().get(rando).description();
                String animeURL = anime.Page().media().get(rando).siteUrl();
                Log.e("testname",animenames);
                Log.e("testurl", imageURL1);
                Log.e("testdes",des);
                aniName = (TextView) getView().findViewById(R.id.aniName);
                anidescription = (TextView) getView().findViewById(R.id.aniDescription);
                aniPic = (ImageView) getView().findViewById(R.id.aniImages);
                aniUrl = (TextView) getView().findViewById(R.id.aniURL);
                imageURL = (TextView) getView().findViewById(R.id.aimageURL2);


                name = animenames;
                desc = des;
                animePageURL = animeURL;
                animeImageURL = imageURL1;
            }



            @Override
            public void onFailure(@NotNull ApolloException e) {

            }
        });
    }
    public void initPicasso(){
        showSuggestion = (Button) getView().findViewById(R.id.showSudggestion);
        imageURL = (TextView) getView().findViewById(R.id.aimageURL2);
        aniPic = (ImageView) getView().findViewById(R.id.aniImages);
       final String temp = imageURL.getText().toString();
        showSuggestion.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                apolloTest();
                aniName.setText(name);
                anidescription.setText(Html.fromHtml(desc).toString());
                aniUrl.setText(animePageURL);
                imageURL.setText(animeImageURL);
                Picasso.get().load(animeImageURL).into(aniPic);
                counter = 1;
            }

        });

    }

//    private String genreSelect(){
//        String selectedOption;
//        int genreValue = (int) ((Math.random() * 18 + 1));
//        switch(genreValue){
//            case 1:
//                selectedOption = "Action";
//                break;
//            case 2:
//                selectedOption = "Adventure";
//                break;
//            case 3:
//                selectedOption = "Comedy";
//                break;
//            case 4:
//                selectedOption = "Drama";
//                break;
//            case 5:
//                selectedOption = "Ecchi";
//                break;
//            case 6:
//                selectedOption = "Fantasy";
//                break;
//            case 7:
//                selectedOption = "Horror";
//                break;
//            case 8:
//                selectedOption = "Mahou Shoujo";
//                break;
//            case 9:
//                selectedOption = "Mecha";
//                break;
//            case 10:
//                selectedOption = "Music";
//                break;
//            case 11:
//                selectedOption = "Mystery";
//                break;
//            case 12:
//                selectedOption = "Psychological";
//                break;
//            case 13:
//                selectedOption = "Romance";
//                break;
//            case 14:
//                selectedOption = "Sci-Fi";
//                break;
//            case 15:
//                selectedOption = "Slice of Life";
//                break;
//            case 16:
//                selectedOption = "Sports";
//                break;
//            case 17:
//                selectedOption = "Supernatural";
//                break;
//            case 18:
//                selectedOption = "Thriller";
//                break;
//            default:
//                selectedOption = "Action";
//                break;
//        }
//        spinnerOption = selectedOption;
//        return spinnerOption;
//    }

    public String genreSelectTwo() {

        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, genreList);
        genreTest.setAdapter(adapter);

        genreTest.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position) {
                    case 0:
                        genreRecord = "Action";
                        break;
                    case 1:
                        genreRecord = "Adventure";
                        break;
                    case 2:
                        genreRecord = "Comedy";
                        break;
                    case 3:
                        genreRecord = "Drama";
                        break;
                    case 4:
                        genreRecord = "Ecchi";
                        break;
                    case 5:
                        genreRecord = "Fantasy";
                        break;
                    case 6:
                        genreRecord = "Horror";
                        break;
                    case 7:
                        genreRecord = "Mahou Shoujo";
                        break;
                    case 8:
                        genreRecord = "Mecha";
                        break;
                    case 9:
                        genreRecord = "Music";
                        break;
                    case 10:
                        genreRecord = "Mystery";
                        break;
                    case 11:
                        genreRecord = "Psychological";
                        break;
                    case 12:
                        genreRecord = "Romance";
                        break;
                    case 13:
                        genreRecord = "Sci-Fi";
                        break;
                    case 14:
                        genreRecord = "Slice of Life";
                        break;
                    case 15:
                        genreRecord = "Sports";
                        break;
                    case 16:
                        genreRecord = "Supernatural";
                        break;
                    case 17:
                        genreRecord = "Thriller";
                        break;
                    default:
                        genreRecord = "Action";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return genreRecord;
    }



}
