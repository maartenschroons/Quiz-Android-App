package be.thomasmore.quiz;


public class Antwoord{
    private String tekst;
    private int correct;

    public Antwoord() {
    }

    public String getTekst() {
        return tekst;
    }

    public void setTekst(String tekst) {
        tekst = tekst.replaceAll("&quot;", "\"");
        tekst = tekst.replaceAll("&deg;", "°");
        this.tekst = tekst.replaceAll("&#039;", "\'");
    }

    public int isCorrect() {
        return correct;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
    }

    public String toString(){
        return tekst;
    }

}
