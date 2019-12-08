package be.thomasmore.quiz;

import android.os.Parcel;
import android.os.Parcelable;


import java.util.List;

public class Vraag {
    private String naam;
    private List<Antwoord> antwoorden;
    private String difficulty;
    private String categorie;


    public String getDifficulty() {
        return difficulty;
    }

    public Vraag() {
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }


    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        naam = naam.replaceAll("&quot;", "\"");
        naam = naam.replaceAll("&deg;", "°");
        this.naam = naam.replaceAll("&#039;", "\'");
    }

    public List<Antwoord> getAntwoorden() {
        return antwoorden;
    }

    public void setAntwoorden(List<Antwoord> antwoorden) {
        this.antwoorden = antwoorden;
    }

    public String toString() {

        return naam;
    }

}
