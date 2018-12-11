//package com.example.dkale.wooby;
//
//import android.os.Bundle;
//
//public class AnimeCyclerView {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        initFirebase();
//        initViewPager();
//        initDatabase();
//        aniList = (TextView) findViewById(R.id.anilist);
//        aniName = (TextView) findViewById(R.id.aniName);
//        aniGenre = (TextView) findViewById(R.id.aniGenre);
//        aniRating = (TextView) findViewById(R.id.aniRating);
//        imageView = (ImageView) findViewById(R.id.imageView);
//        apolloTest();
 //   }


    //    private TextView aniName;
//    private TextView aniGenre;
//    private TextView aniRating;
//    private ImageView imageView;
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
//                final StringBuffer title = new StringBuffer();
//                final StringBuffer genre = new StringBuffer();
//                final StringBuffer rating = new StringBuffer();
//                //final StringBuffer img = new StringBuffer();
//                TestQuery.Data anime = response.data();
//                for(int x = 0; x < anime.Page().media().size(); x++){
//                    title.append("title: " + anime.Page().media().get(x).title());
//                    rating.append("averageScore: " + anime.Page().media().get(x).averageScore());
//                    //img.append("coverImage: " + anime.Page().media().get(x).coverImage());
//                    genre.append("genres: " + anime.Page().media().get(x).genres());
//                }
//
//                MainActivity.this.runOnUiThread(new Runnable() {
//                    @Override public void run() {
//                        TextView titleRes = aniName;
//                        TextView genreRes = aniGenre;
//                        TextView ratingRes = aniRating;
//                        //ImageView imgRes = imageView;
//                        titleRes.setText(title.toString());
//                        genreRes.setText(genre.toString());
//                        ratingRes.setText(rating.toString());
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
//}
