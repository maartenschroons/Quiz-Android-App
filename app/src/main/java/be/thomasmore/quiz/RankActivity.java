package be.thomasmore.quiz;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.provider.ContactsContract;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class RankActivity extends AppCompatActivity {
    //    String difficulties[] = new String[]{"Easy", "Medium", "Hard"};
//    Categorie categorieen[];
//    Integer aantalVragen[] = new Integer[]{10, 15, 20};
    List<Result> results;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank_per_categorie);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        db = new DatabaseHelper(this);

//        setAmountSpinner();
//        setDifficultySpinner();
//        setCategories();
        results = db.getResults();
        vulTop();
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void vulTop() {

        RankAdapter rankAdapter = new RankAdapter(getApplicationContext(), results);

        ListView View = (ListView) findViewById(R.id.topView);
        View.setAdapter(rankAdapter);
    }

//    public void setDifficultySpinner() {
//        Spinner Spinner = (Spinner) findViewById(R.id.DifficultySpinner);
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, difficulties);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        Spinner.setAdapter(adapter);
//    }
//
//    public void setAmountSpinner() {
//        Spinner Spinner = (Spinner) findViewById(R.id.AmountSpinner);
//        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, aantalVragen);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        Spinner.setAdapter(adapter);
//    }
//
//    public void setCategories() {
//        HttpReader httpReader = new HttpReader();
//        httpReader.setOnResultReadyListener(new HttpReader.OnResultReadyListener() {
//            @Override
//            public void resultReady(String result) {
//                JsonHelper jsonHelper = new JsonHelper();
//                List<Categorie> categorieenLijst = jsonHelper.getCategorieen(result);
//                Categorie random = new Categorie();
//                random.setNaam("Any categorie");
//                random.setId(0);
//                categorieen = new Categorie[categorieenLijst.size() + 1];
//                categorieen[0] = random;
//                for (int i = 0; i < categorieenLijst.size(); i++) {
//                    categorieen[i + 1] = categorieenLijst.get(i);
//                }
//                setCategorieSpinner();
//            }
//        });
//        httpReader.execute("https://opentdb.com/api_category.php");
//    }
//
//    public void setCategorieSpinner() {
//        Spinner Spinner = (Spinner) findViewById(R.id.CategorieSpinner);
//        ArrayAdapter<Categorie> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categorieen);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        Spinner.setAdapter(adapter);
//    }

//    public void filter(View v){
//        Spinner aantalSpin = (Spinner) findViewById(R.id.AmountSpinner);
//        String aantal = aantalSpin.getSelectedItem().toString();
//
//        Spinner catSpin = (Spinner) findViewById(R.id.CategorieSpinner);
//        Categorie cat = (Categorie) catSpin.getSelectedItem();
//        String catS= cat.getNaam();
//
//        Spinner difSpin = (Spinner) findViewById(R.id.DifficultySpinner);
//        String dif = difSpin.getSelectedItem().toString().toLowerCase();
//
//        results = db.getResultsByFilter(aantal, catS, dif);
//        vulTop();
//    }
}
