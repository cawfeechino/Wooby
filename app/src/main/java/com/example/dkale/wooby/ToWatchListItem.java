package com.example.dkale.wooby;

public class ToWatchListItem {
    String TAG = "Firebase Watched Item";
    String animeDescription;
    String animeImage;
    String animeURL;
    String animeName;

    public ToWatchListItem(){;}

    public String getAnimeName(){return animeName;}
    public String getAnimeURL(){return animeURL;}
    public String getAnimeDescription(){return animeDescription;}
    public String getAnimeImage(){return animeImage;}
    public void setAnimeName(String animeName){
        this.animeName = animeName;
    }
    public void setAnimeURL(String animeURL){
        this.animeURL = animeURL;
    }
    public void setAnimeDescription(String animeDescription){
        this.animeDescription = animeDescription;
    }
    public void setAnimeImage(String animeImage){
        this.animeImage = animeImage;
    }


    public String toString(){
        String result = "Anime name: [" + animeName + "] Anime link: [" + animeURL + "] Anime image: [" + animeImage + "Anime Description: [" + animeDescription + "]";
        return result;
    }
}
