package be.thomasmore.quiz;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddQuestionActivity extends AppCompatActivity {
    String difficulties[] = new String[]{"Easy", "Medium", "Hard"};
    Categorie categorieen[];
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setDifficultySpinner();
        setCategories();
        db= new DatabaseHelper(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void save(View v) {
        Vraag vraag = new Vraag();
        EditText vraagview = (EditText)findViewById(R.id.question);
        String tekst = vraagview.getText().toString();
        List<Antwoord> antwoorden = new ArrayList<Antwoord>();
        vraag.setNaam(tekst);

        EditText correctview = (EditText)findViewById(R.id.correct);
        Antwoord correct = new Antwoord();
        correct.setTekst(correctview.getText().toString());
        correct.setCorrect(1);
        antwoorden.add(correct);

        EditText incorrectview = (EditText)findViewById(R.id.incorrect);
        Antwoord incorrect = new Antwoord();
        incorrect.setTekst(incorrectview.getText().toString());
        incorrect.setCorrect(0);
        antwoorden.add(incorrect);

        EditText incorrectAview = (EditText)findViewById(R.id.incorrectA);
        Antwoord incorrectA = new Antwoord();
        incorrectA.setTekst(incorrectAview.getText().toString());
        incorrectA.setCorrect(0);
        if (incorrectA.getTekst()!=null && !incorrectA.getTekst().isEmpty()) {
            antwoorden.add(incorrectA);
        }
        EditText incorrectBview = (EditText)findViewById(R.id.incorrectB);
        Antwoord incorrectB = new Antwoord();
        incorrectB.setTekst(incorrectBview.getText().toString());
        incorrectB.setCorrect(0);
        if (incorrectB.getTekst() !=null && !incorrectB.getTekst().isEmpty()) {
            antwoorden.add(incorrectB);
        }

        vraag.setAntwoorden(antwoorden);

        Spinner difSpin = (Spinner) findViewById(R.id.DifficultySpinner);
        vraag.setDifficulty(difSpin.getSelectedItem().toString().toLowerCase());

        Spinner catSpin = (Spinner) findViewById(R.id.CategorieSpinner);
        vraag.setCategorie(catSpin.getSelectedItem().toString().toLowerCase());

        db.insertVraag(vraag);
        Toast.makeText(getApplicationContext(),"Vraag is toegevoegd!", Toast.LENGTH_SHORT).show();

        Intent nextActivity = new Intent(v.getContext(), SavedQuestions.class);
        startActivity(nextActivity);
    }

    public void setCategories() {
        HttpReader httpReader = new HttpReader();
        httpReader.setOnResultReadyListener(new HttpReader.OnResultReadyListener() {
            @Override
            public void resultReady(String result) {
                JsonHelper jsonHelper = new JsonHelper();
                List<Categorie> categorieenLijst = jsonHelper.getCategorieen(result);
                Categorie random = new Categorie();
                random.setNaam("Any categorie");
                random.setId(0);
                categorieen = new Categorie[categorieenLijst.size() + 1];
                categorieen[0] = random;
                for (int i = 0; i < categorieenLijst.size(); i++) {
                    categorieen[i + 1] = categorieenLijst.get(i);
                }
                setCategorieSpinner();
            }
        });
        httpReader.execute("https://opentdb.com/api_category.php");
    }

    public void setCategorieSpinner() {
        Spinner Spinner = (Spinner) findViewById(R.id.CategorieSpinner);
        ArrayAdapter<Categorie> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categorieen);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner.setAdapter(adapter);
    }

    public void setDifficultySpinner() {
        Spinner Spinner = (Spinner) findViewById(R.id.DifficultySpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, difficulties);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner.setAdapter(adapter);
    }

}
