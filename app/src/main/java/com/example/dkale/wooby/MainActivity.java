package com.example.dkale.wooby;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dkale.wooby.dummy.DummyContent;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;

import anilist.TestQuery;
import anilist.type.MediaSort;
import okhttp3.OkHttpClient;


import fr.tkeunebr.gravatar.Gravatar;

import static com.google.firebase.auth.FirebaseAuth.getInstance;

public class MainActivity extends AppCompatActivity implements ChatMessageFragment.OnFragmentInteractionListener, HistoryFragment.OnListFragmentInteractionListener, ToWatchFragment.OnListFragmentInteractionListener, Suggestion.OnFragmentInteractionListener{
    final String TAG = "FirebaseTest";

    FirebaseApp mApp;
    FirebaseAuth mAuth;

    FirebaseAuth.AuthStateListener mAuthListener;
    String mDisplayName;
    TextView mScreenMessage;
    ViewPager mViewPager;
    FragmentAdapter mFragmentAdapter;
    TabLayout mTabLayout;
    FirebaseDatabase mDatabase;
    ImageView mAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFirebase();
        initViewPager();
//        initDatabase();
//        aniList = (TextView) findViewById(R.id.anilist);
        aniName = (TextView) findViewById(R.id.aniName);
        aniGenre = (TextView) findViewById(R.id.aniGenre);
        aniRating = (TextView) findViewById(R.id.aniRating);
        imageView = (ImageView) findViewById(R.id.imageView);
        apolloTest();
    }

    private void initFirebase() {

        mApp = FirebaseApp.getInstance();
        mAuth = FirebaseAuth.getInstance(mApp);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null) {
                    Log.e(TAG,"AUTH STATE UPDATE : Valid user logged in [" + user.getEmail() + "]" );
                    mScreenMessage = (TextView) findViewById(R.id.userName);
                    mScreenMessage.setText(user.getEmail());
                    initGravatars();
                    String myEmail = mAuth.getCurrentUser().getEmail();
                    int index = myEmail.indexOf('@');
                    String mDisplayName = myEmail.substring(0,index);
                    initDatabase(mDisplayName);
                } else {
                    Log.e(TAG,"AUTH STATE UPDATE : NO valid user logged in");
                    mDisplayName = "No Valid User";

                    mAuth.removeAuthStateListener(mAuthListener);
                    Intent signInIntent = new Intent(getApplicationContext(), SignIn.class);
                    startActivityForResult(signInIntent, 101);
                }
            }
        };
        mAuth.addAuthStateListener(mAuthListener);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.e(TAG,"Activity returned");

        if (resultCode == RESULT_OK) {
            if (requestCode == 101) {

                mDisplayName = data.getStringExtra("email");
                Log.e(TAG,"Intent returned Email [" + mAuth.getCurrentUser().getEmail().toString() + "]");

                mAuth.addAuthStateListener(mAuthListener);
                initGravatars();
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.menu_logout) {
            Log.e(TAG,"Logout selected");

            mAuth.getInstance().signOut();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void initViewPager(){
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mFragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mFragmentAdapter);
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    public void onFragmentInteraction(Uri uri){
        Log.e(TAG,"Fragment Interaction Listener");
    }
    public void onHistoryListFragmentInteraction(WatchedListItem item){
        Log.e(TAG,"History Fragment");
    }
    public void onToWatchListFragmentInteraction(DummyContent.DummyItem item){
        Log.e(TAG,"To Watch Fragment");
    }
    public void onSuggestionFragmentInteraction(Uri uri){
        Log.e(TAG,"Suggestion Interaction Listener");
    }

    public void initGravatars(){
        String myEmail = mAuth.getCurrentUser().getEmail();
        int index = myEmail.indexOf('@');
        String mDisplayName = myEmail.substring(0,index);
        String gravatarURL = Gravatar.init().with(myEmail).size(100).build();
        DatabaseReference ref = mDatabase.getInstance().getReference("userGravatars").child(mDisplayName);
        ref.setValue(gravatarURL);
        String https = "https://secure"+gravatarURL.substring(10);

        mAvatar = (ImageView) findViewById(R.id.user_avatar);
        Picasso.get()
                .load(https)
                .resize(59, 59)
                .into(mAvatar);
        Log.e("Heres the gravatar link",https);
    }

    public void initDatabase(final String compareName){
        mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ref = mDatabase.getReference("userWatchedList");
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HistoryFragment history = (HistoryFragment)mFragmentAdapter.getItem(1);
                history.resetArray();
                for(DataSnapshot child : dataSnapshot.getChildren()){
                    for(DataSnapshot childAnime : child.getChildren()) {
                        if(child.getKey().equals(compareName)) {
                            WatchedListItem item = childAnime.getValue(WatchedListItem.class);
                            history.routeWatchedItem(item);
                            Log.e(TAG, "Adding Child " + item.toString() + " To Email " + mAuth.getCurrentUser().getEmail());
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        ref.addValueEventListener(listener);
    }

//    private TextView aniList;
//    final static String BASE_URL = "https://graphql.anilist.co";
//
//
//    public void apolloTest(){
//
//        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
//        ApolloClient apolloClient = ApolloClient.builder().serverUrl(BASE_URL).okHttpClient(okHttpClient).build();
//        final TestQuery testQuery = TestQuery.builder().genre(new ArrayList<String>(Arrays.asList("hentai"))).score(60).sort(new ArrayList<MediaSort>(Arrays.asList(MediaSort.SCORE_DESC))).build();
//        apolloClient.query(testQuery).enqueue(new ApolloCall.Callback<TestQuery.Data>() {
//            @Override
//            public void onResponse(@NotNull Response<TestQuery.Data> response) {
//                final StringBuffer buffer = new StringBuffer();
//                TestQuery.Data anime = response.data();
//                for(int x = 0; x < anime.Page().media().size(); x++){
//                    buffer.append("id: " + anime.Page().media().get(x).id());
//                    buffer.append("title: " + anime.Page().media().get(x).title());
//                    buffer.append("averageScore: " + anime.Page().media().get(x).averageScore());
//                    buffer.append("\n~~~~~~~~~~~");
//                    buffer.append("\n\n");
//                }
//
//                MainActivity.this.runOnUiThread(new Runnable() {
//                    @Override public void run() {
//                        TextView txtResponse = aniList;
//                        txtResponse.setText(buffer.toString());
//                    }
//                });
//
//            }
//
//            @Override
//            public void onFailure(@NotNull ApolloException e) {
//
//            }
//        });
//    }

    private TextView aniName;
    private TextView aniGenre;
    private TextView aniRating;
    private ImageView imageView;
    final static String BASE_URL = "https://graphql.anilist.co";


    public void apolloTest(){

        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        ApolloClient apolloClient = ApolloClient.builder().serverUrl(BASE_URL).okHttpClient(okHttpClient).build();
        final TestQuery testQuery = TestQuery.builder().genre(new ArrayList<String>(Arrays.asList("hentai"))).score(60).sort(new ArrayList<MediaSort>(Arrays.asList(MediaSort.SCORE_DESC))).build();
        apolloClient.query(testQuery).enqueue(new ApolloCall.Callback<TestQuery.Data>() {
            @Override
            public void onResponse(@NotNull Response<TestQuery.Data> response) {
                final StringBuffer title = new StringBuffer();
                final StringBuffer genre = new StringBuffer();
                final StringBuffer rating = new StringBuffer();
                //final StringBuffer img = new StringBuffer();
                TestQuery.Data anime = response.data();
                for(int x = 0; x < anime.Page().media().size(); x++){
                    title.append("title: " + anime.Page().media().get(x).title());
                    rating.append("averageScore: " + anime.Page().media().get(x).averageScore());
                    //img.append("coverImage: " + anime.Page().media().get(x).coverImage());
                    genre.append("genres: " + anime.Page().media().get(x).genres());
                }

                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override public void run() {
                        TextView titleRes = aniName;
                        TextView genreRes = aniGenre;
                        TextView ratingRes = aniRating;
                        //ImageView imgRes = imageView;
                        titleRes.setText(title.toString());
                        genreRes.setText(genre.toString());
                        ratingRes.setText(rating.toString());
                    }
                });

            }

            @Override
            public void onFailure(@NotNull ApolloException e) {

            }
        });
    }
}