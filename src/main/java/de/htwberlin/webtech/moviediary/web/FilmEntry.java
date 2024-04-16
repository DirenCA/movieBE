package de.htwberlin.webtech.moviediary.web;


public class FilmEntry{
    private String title;
    private String genre;
    private boolean watched;

    public FilmEntry(String title,String genre,boolean watched){
        this.title=title;
        this.genre=genre;
        this.watched=watched;
    }

    public String getTitle(){
        return title;
    }

    public String getDescription(){
        return genre;
    }

    public boolean watched(){
        return watched; //test
    }

}

