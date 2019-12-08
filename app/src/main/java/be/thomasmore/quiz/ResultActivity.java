package be.thomasmore.quiz;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {
    private DatabaseHelper db;
    String score, aantal, dif, cat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        db = new DatabaseHelper(this);
        dif = getIntent().getStringExtra("dif");
        cat = getIntent().getStringExtra("cat");
        score = getIntent().getStringExtra("score");
        aantal = getIntent().getStringExtra("aantalvragen");
        vulView();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    public void save(View v){
        EditText name = (EditText)findViewById(R.id.naam);
        Result result = new Result();
        result.setDifficulty(dif);
        result.setCategorie(cat);
        result.setNaam(name.getText().toString());
        result.setAantal(Integer.parseInt(aantal));
        result.setScore(Integer.parseInt(score));
        db.insertResult(result);

        Intent nextActivity = new Intent(v.getContext(), RankActivity.class);
        startActivity(nextActivity);
    }

    public void vulView(){
        int res = (Integer.parseInt(score)*10/ Integer.parseInt(aantal));
        TextView berichtview = (TextView) findViewById(R.id.bericht);
        if (res <= 2){
            berichtview.setText("Het is best dat u een andere categorie of moeilijkheid kiest...");
        } else if (res >2 && res <=5) {
            berichtview.setText("Volgende keer beter!");
        }
        else if(res >5 && res <=8){
            berichtview.setText("Goed zo!");
        }
        else{
            berichtview.setText("Gefeliciteerd!");
        }
        TextView Resview = (TextView) findViewById(R.id.resultaat);
        Resview.setText("U behaalde "+score+"/"+aantal);
    }

}
