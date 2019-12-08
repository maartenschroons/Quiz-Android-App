package be.thomasmore.quiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 2;
    // Database Name
    private static final String DATABASE_NAME = "quiz";

    // uitgevoerd bij instantiatie van DatabaseHelper
    // -> als database nog niet bestaat, dan creëren (call onCreate)
    // -> als de DATABASE_VERSION hoger is dan de huidige versie,
    //    dan upgraden (call onUpgrade)

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // methode wordt uitgevoerd als de database gecreëerd wordt
    // hierin de tables creëren en opvullen met gegevens
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_RESULT = "CREATE TABLE result (" +
                "id INTEGER PRIMARY KEY," +
                "naam TEXT," +
                "score INTEGER," +
                "aantal INTEGER," +
                "average DOUBLE," +
                "categorie TEXT," +
                "difficulty TEXT" +
                ")";
        db.execSQL(CREATE_TABLE_RESULT);

        String CREATE_TABLE_QUESTION = "CREATE TABLE vraag (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "naam TEXT," +
                "categorie TEXT, " +
                "difficulty TEXT " +
                ")";
        db.execSQL(CREATE_TABLE_QUESTION);

        String CREATE_TABLE_ANSWER = "CREATE TABLE antwoord (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tekst TEXT," +
                "correct INTEGER," +
                "vraagId INTEGER," +
                "FOREIGN KEY (vraagId) REFERENCES vraag(id))";
        db.execSQL(CREATE_TABLE_ANSWER);
    }

    // methode wordt uitgevoerd als database geupgrade wordt
    // hierin de vorige tabellen wegdoen en opnieuw creëren
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS vraag");
        db.execSQL("DROP TABLE IF EXISTS antwoord");
        db.execSQL("DROP TABLE IF EXISTS result");
        // Create tables again
        onCreate(db);
    }

    //-------------------------------------------------------------------------------------------------
    //  CRUD Operations
    //-------------------------------------------------------------------------------------------------

    // insert-methode met ContentValues
    public long insertVraag(Vraag vraag) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("naam", vraag.getNaam());
        values.put("categorie", vraag.getCategorie());
        values.put("difficulty", vraag.getDifficulty());

        long id = db.insert("vraag", null, values);

        for (int i = 0; i < vraag.getAntwoorden().size(); i++) {
            Antwoord antwoord = vraag.getAntwoorden().get(i);
            ContentValues value = new ContentValues();

            value.put("tekst", antwoord.getTekst());
            value.put("correct", antwoord.isCorrect());
            value.put("vraagId", id);
            long idA = db.insert("antwoord", null, value);
        }

        db.close();
        return id;
    }

    public long insertResult(Result result) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("naam", result.getNaam());
        values.put("score", result.getScore());
        values.put("aantal", result.getAantal());
        values.put("categorie", result.getCategorie());
        values.put("difficulty", result.getDifficulty());
        double average = (double)result.getScore() / (double)result.getAantal();
        values.put("average", average);

        long id = db.insert("result", null, values);

        db.close();
        return id;
    }

    // rawQuery-methode
    public List<Result> getResults() {
        List<Result> lijst = new ArrayList<Result>();

        String selectQuery = "SELECT * FROM result ORDER BY average DESC LIMIT 10";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Result result = new Result();
                result.setAantal(cursor.getInt(3));
                result.setScore(cursor.getInt(2));
                result.setNaam(cursor.getString(1));
                result.setCategorie(cursor.getString(5));
                result.setDifficulty(cursor.getString(6));

                lijst.add(result);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return lijst;
    }

//    // rawQuery-methode
//    public List<Result> getResultsByFilter(String aantal, String cat, String dif) {
//        List<Result> lijst = new ArrayList<Result>();
//
//        String selectQuery = "SELECT * FROM result WHERE categorie='" + cat + "' AND difficulty='" + dif + "' AND aantal='" + aantal + "' ORDER BY average DESC LIMIT 5";
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        if (cursor.moveToFirst()) {
//            do {
//                Result result = new Result();
//                result.setAantal(cursor.getInt(4));
//                result.setNaam(cursor.getString(2));
//                result.setCategorie(cursor.getString(6));
//                result.setDifficulty(cursor.getString(7));
//
//                lijst.add(result);
//            } while (cursor.moveToNext());
//        }
//
//        cursor.close();
//        db.close();
//
//        List<Result> lijstR = new ArrayList<Result>();
//        for(int i =0; i<5 || i<lijst.size(); i++){
//            lijstR.add(lijst.get(i));
//        }
//        return lijstR;
//    }

    // rawQuery-methode
    public List<Vraag> getVragen() {
        List<Vraag> lijst = new ArrayList<Vraag>();

        String selectQuery = "SELECT  * FROM vraag";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Vraag vraag = new Vraag();

                vraag.setNaam(cursor.getString(1));
                vraag.setCategorie(cursor.getString(2));
                vraag.setDifficulty(cursor.getString(3));

                List<Antwoord> antwoorden = getAntwoordenByVraag(cursor.getInt(0));
                vraag.setAntwoorden(antwoorden);

                lijst.add(vraag);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return lijst;
    }

    public List<Antwoord> getAntwoordenByVraag(int id) {
        List<Antwoord> lijst = new ArrayList<Antwoord>();

        String selectQuery = "SELECT  * FROM antwoord WHERE vraagId=" + id;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Antwoord antwoord = new Antwoord();
                antwoord.setTekst(cursor.getString(1));
                antwoord.setCorrect(cursor.getInt(2));

                lijst.add(antwoord);
            } while (cursor.moveToNext());
        }
        Collections.shuffle(lijst);
        cursor.close();
        db.close();
        return lijst;
    }

    public void del(){
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("delete from vraag");
        db.execSQL("delete from antwoord");
        db.execSQL("delete from antwoord");
    }

}