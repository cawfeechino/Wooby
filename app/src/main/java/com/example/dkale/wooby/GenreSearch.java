package com.example.dkale.wooby;

import android.os.Bundle;

public class GenreSearch {
    private String[] genreList = {"action", "adventure","comedy","drama","ecchi",
            "fantasy","horror", "mahou shoujo","mecha","music","mystery","psychological",
            "romance","sci-fi","slice of life","sports","supernatural","thriller"};

    private String genreQuery = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.genre_search);
    }


}

