package com.example.dkale.wooby;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

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

public class MainActivity extends AppCompatActivity {
    private TextView aniList;
    final static String BASE_URL = "https://graphql.anilist.co";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        aniList = (TextView) findViewById(R.id.anilist);
        apolloTest();
    }

    public void apolloTest(){

        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        ApolloClient apolloClient = ApolloClient.builder().serverUrl(BASE_URL).okHttpClient(okHttpClient).build();
        final TestQuery testQuery = TestQuery.builder().genre(new ArrayList<String>(Arrays.asList("hentai"))).score(60).sort(new ArrayList<MediaSort>(Arrays.asList(MediaSort.SCORE_DESC))).build();
        apolloClient.query(testQuery).enqueue(new ApolloCall.Callback<TestQuery.Data>() {
            @Override
            public void onResponse(@NotNull Response<TestQuery.Data> response) {
                final StringBuffer buffer = new StringBuffer();
                TestQuery.Data anime = response.data();
                for(int x = 0; x < anime.Page().media().size(); x++){
                    buffer.append("id: " + anime.Page().media().get(x).id());
                    buffer.append("title: " + anime.Page().media().get(x).title());
                    buffer.append("averageScore: " + anime.Page().media().get(x).averageScore());
                    buffer.append("\n~~~~~~~~~~~");
                    buffer.append("\n\n");
                }

                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override public void run() {
                        TextView txtResponse = aniList;
                        txtResponse.setText(buffer.toString());
                    }
                });

            }

            @Override
            public void onFailure(@NotNull ApolloException e) {

            }
        });
    }
}
