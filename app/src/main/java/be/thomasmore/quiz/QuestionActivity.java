package be.thomasmore.quiz;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class QuestionActivity extends AppCompatActivity {

    final ArrayList<Vraag> vragen = new ArrayList<>();
    int aantal, score;
    String dif, catId, aantalString, cat;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        db = new DatabaseHelper(this);
        if(getIntent().getStringExtra("soort").equals("api")){
            setVragenuitApi();
        }
        else{

            Button knop = (Button) findViewById(R.id.knop);
            knop.setVisibility(View.GONE);
            setVragenuitSQLite();
        }


        ListView antwoordenView = (ListView) findViewById(R.id.antwoordenView);
        antwoordenView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                if (vragen.get(aantal).getAntwoorden().get(position).isCorrect() == 1) {
                    score++;
                }
                if (aantal > 0) {
                    aantal--;
                    setQuestion();
                } else {
                    Intent nextActivity = new Intent(v.getContext(), ResultActivity.class);
                    nextActivity.putExtra("aantalvragen", Integer.toString(vragen.size()));
                    nextActivity.putExtra("score", Integer.toString(score));
                    nextActivity.putExtra("dif", dif);
                    nextActivity.putExtra("cat", cat);
                    startActivity(nextActivity);
                }
            }
        });

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void setQuestion() {
        TextView difView = (TextView) findViewById(R.id.difficulty);
        difView.setText("Moeilijkheid: " + vragen.get(aantal).getDifficulty());

        TextView catView = (TextView) findViewById(R.id.category);
        catView.setText("Categorie: " + vragen.get(aantal).getCategorie());

        TextView vraagView = (TextView) findViewById(R.id.vraag);
        vraagView.setText(vragen.get(aantal).getNaam());

        List<Antwoord> antwoorden;
        antwoorden = vragen.get(aantal).getAntwoorden();

        ListView antwoordenView = (ListView) findViewById(R.id.antwoordenView);
        ArrayAdapter<Antwoord> arrayAdapter = new ArrayAdapter<Antwoord>
                (this, android.R.layout.simple_list_item_1, antwoorden);
        antwoordenView.setAdapter(arrayAdapter);
    }

    public void setVragenuitApi() {
        dif = getIntent().getStringExtra("dif");
        catId = getIntent().getStringExtra("catId");
        aantalString = getIntent().getStringExtra("aantalString");
        aantal = getIntent().getIntExtra("aantal", 0);
        cat = getIntent().getStringExtra("cat");
        HttpReader httpReader = new HttpReader();
        httpReader.setOnResultReadyListener(new HttpReader.OnResultReadyListener() {
            @Override
            public void resultReady(String result) {
                JsonHelper jsonHelper = new JsonHelper();
                List<Vraag> vragenLijst = jsonHelper.getVragen(result);
                for (int i = 0; i < vragenLijst.size(); i++) {
                    vragen.add(vragenLijst.get(i));
                }
                setQuestion();
            }
        });
        httpReader.execute("https://opentdb.com/api.php?amount=" + aantalString + "&difficulty=" + dif + "&category=" + catId);
    }

    public void setVragenuitSQLite(){
        List<Vraag> vragenLijst = db.getVragen();
        aantal = vragenLijst.size() -1;
        cat="Opgeslagen vragen";
        dif="/";
        for (int i = 0; i < vragenLijst.size(); i++) {
            vragen.add(vragenLijst.get(i));
        }
        setQuestion();
    }

    public void save(View v) {
        db.insertVraag(vragen.get(aantal));
        Toast.makeText(getApplicationContext(),"Vraag is toegevoegd!", Toast.LENGTH_SHORT).show();
    }
}
