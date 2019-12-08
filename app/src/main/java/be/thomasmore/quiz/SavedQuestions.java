package be.thomasmore.quiz;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class SavedQuestions extends AppCompatActivity {

    private DatabaseHelper db;
    private List<Vraag> vragen;
    List<Antwoord> antwoorden;

    LinearLayout layout;
    ListView list;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_questions);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        db = new DatabaseHelper(this);
        dialog = new Dialog(SavedQuestions.this);
        layout = new LinearLayout(this);
        list = new ListView(this);
        vragen = db.getVragen();

        ListView questionView = (ListView) findViewById(R.id.questionView);
        ArrayAdapter<Vraag> arrayAdapter = new ArrayAdapter<Vraag>
                (this, android.R.layout.simple_list_item_1, vragen);
        questionView.setAdapter(arrayAdapter);

        questionView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                setPopup(position);
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void setPopup(int pos){
        layout.removeAllViews();

//        ArrayAdapter<Antwoord> arrayAdapter = new ArrayAdapter<Antwoord>
//                (SavedQuestions.this, android.R.layout.simple_list_item_1, vragen.get(pos).getAntwoorden());
//        list.setAdapter(arrayAdapter);
//        layout.addView(list);
        antwoorden = vragen.get(pos).getAntwoorden();
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        for (int i=0; i<antwoorden.size(); i++){
            TextView antwoord = new TextView(this);
            antwoord.setText(antwoorden.get(i).toString());
            antwoord.setTextSize(20);
            if (antwoorden.get(i).isCorrect()==1){
                antwoord.setTextColor(Color.GREEN);
            }
            else{
                antwoord.setTextColor(Color.RED);
            }
            layout.addView(antwoord);
        }

        dialog.setContentView(layout);

        dialog.setCancelable(true);
        dialog.setTitle(vragen.get(pos).getNaam());
        dialog.show();

    }

    public void start(View v){
        Intent nextActivity = new Intent(v.getContext(), QuestionActivity.class);
        nextActivity.putExtra("soort", "saved");
        startActivity(nextActivity);
    }

}
