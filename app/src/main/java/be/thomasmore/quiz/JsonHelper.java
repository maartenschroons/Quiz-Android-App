package be.thomasmore.quiz;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JsonHelper {

    public List<Categorie> getCategorieen(String jsonTekst) {
        List<Categorie> categorieen = new ArrayList<>();

        try {
            JSONObject jsonObjectcategorieen = new JSONObject(jsonTekst);
            JSONArray jsonArraycategorieen = jsonObjectcategorieen.getJSONArray("trivia_categories");
            for (int i = 0; i < jsonArraycategorieen.length(); i++) {
                Categorie categorie = new Categorie();
                JSONObject jsonObjectCompetitie = jsonArraycategorieen.getJSONObject(i);
                categorie.setNaam(jsonObjectCompetitie.getString("name"));
                categorie.setId(jsonObjectCompetitie.getInt("id"));
                categorieen.add(categorie);
            }
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        return categorieen;
    }

    public List<Vraag> getVragen(String jsonTekst) {
        List<Vraag> vragen = new ArrayList<>();

        try {
            JSONObject jsonObjectvragen = new JSONObject(jsonTekst);
            JSONArray jsonArrayvragen = jsonObjectvragen.getJSONArray("results");
            for (int i = 0; i < jsonArrayvragen.length(); i++) {
                JSONObject jsonObjectCompetitie = jsonArrayvragen.getJSONObject(i);
                List<Antwoord> antwoorden = new ArrayList<>();
                Vraag vraag = new Vraag();
                vraag.setNaam(jsonObjectCompetitie.getString("question"));
                vraag.setDifficulty(jsonObjectCompetitie.getString("difficulty"));
                vraag.setCategorie(jsonObjectCompetitie.getString("category"));

                JSONArray verkeerdeAntwoorden = jsonObjectCompetitie.getJSONArray("incorrect_answers");
                for (int j = 0; j < verkeerdeAntwoorden.length(); j++) {
                    Antwoord antwoord = new Antwoord();
                    antwoord.setTekst(verkeerdeAntwoorden.get(j).toString());
                    antwoord.setCorrect(0);
                    antwoorden.add(antwoord);
                }
                Antwoord antwoordC = new Antwoord();
                antwoordC.setTekst(jsonObjectCompetitie.getString("correct_answer"));
                antwoordC.setCorrect(1);
                antwoorden.add(antwoordC);
                Collections.shuffle(antwoorden);
                vraag.setAntwoorden(antwoorden);
                vragen.add(vraag);
            }
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        return vragen;
    }
}
