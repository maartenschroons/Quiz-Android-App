package be.thomasmore.quiz;

public class Categorie {
    private int id;
    private String naam;

    public int getId() {
        return id;
    }

    public Categorie() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String toString(){
        return naam;
    }
}
