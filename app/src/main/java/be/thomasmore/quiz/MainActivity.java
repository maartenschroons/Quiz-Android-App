package be.thomasmore.quiz;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    public void beginQuiz(View v){
        Intent nextActivity = new Intent(v.getContext(), QuizSettingsActivity.class);
        startActivity(nextActivity);
    }



    public void toonRankPerCat(View v){
        Intent nextActivity = new Intent(v.getContext(), RankActivity.class);
        startActivity(nextActivity);
    }


    public void toonInfo(View v) {
        Intent nextActivity = new Intent(v.getContext(), InfoActivity.class);
        startActivity(nextActivity);
    }

    public void toonVragen(View v){
        Intent nextActivity = new Intent(v.getContext(), SavedQuestions.class);
        startActivity(nextActivity);
    }

    public void voegToe(View v){
        Intent nextActivity = new Intent(v.getContext(), AddQuestionActivity.class);
        startActivity(nextActivity);
    }

    public void delete(View v){
       DatabaseHelper db = new DatabaseHelper(this);
       db.del();
    }
}
