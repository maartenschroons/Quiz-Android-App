package be.thomasmore.quiz;

public class Result {
    public String naam;
    public int score;
    public int aantal;
    public long average;
    public String categorie;
    public String difficulty;

    public long getAverage() {
        return average;
    }

    public void setAverage(long average) {
        this.average = average;

    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getAantal() {
        return aantal;
    }

    public void setAantal(int aantal) {
        this.aantal = aantal;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
}
