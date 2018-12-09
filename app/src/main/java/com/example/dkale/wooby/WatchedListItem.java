package com.example.dkale.wooby;

public class WatchedListItem {
    String TAG = "Firebase Watched Item";
    String animeDescription;
    String animeImage;
    String animeURL;
    String animeName;

    public WatchedListItem(){;}

    public String getAnimeName(){return animeName;}
    public String getAnimeURL(){return animeURL;}
    public String getAnimeDescription(){return animeDescription;}
    public String getAnimeImage(){return animeImage;}

    public String toString(){
        String result = "Anime name: [" + animeName + "] Anime link: [" + animeURL + "] Anime image: [" + animeImage + "Anime Description: [" + animeDescription + "]";
        return result;
    }
}
