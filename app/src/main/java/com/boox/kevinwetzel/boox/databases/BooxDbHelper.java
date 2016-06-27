package com.boox.kevinwetzel.boox.databases;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Kevinn on 26.06.2016.
 */
public class BooxDbHelper extends SQLiteOpenHelper {
    private static final String TAG = BooxDbHelper.class.getSimpleName();

    public  static   String DB_NAME = "default";
    public static final int DB_VERSION = 1;

    public  static  final String T_BOOKSHELVES = "t_bookshelves";
    public  static  final String C_BOOKSHELVES_ID = "_id";          //PK
    public  static final String C_BOOKSHELVES_TITLE = "c_title";
    public  static final String C_BOOKSHELVES_ACCESS = "c_access";
    public  static final String C_BOOKSHELVES_DESCRIPTION = "c_description";
    public  static final String C_BOOKSHELVES_CREATED = "c_created";
    public  static final String C_BOOKSHELVES_UPDATED = "c_updated";
    public  static  final String C_BOOKSHELVES_SELFLINK = "c_selflink";
    public  static  final String C_BOOKSHELVES_VOLUME_COUNT = "c_volume_count";

    public  static final String T_VOLUME = "t_volume";
    public  static  final String C_VOLUME_ID = "_id";                //PK
    public  static  final String C_VOLUME_BOOKSHELF = "c_bookshelf"; // FK
    public  static  final String C_VOLUME_ACCESS = "c_access";
    public  static  final String C_VOLUME_KIND = "c_kind";
    public  static  final String C_VOLUME_DESCRIPTION = "c_description";
    public  static  final String C_VOLUME_TITLE = "c_title";
    public  static  final String C_VOLUME_SUBTITLE = "c_subtitle";
    public  static  final String C_VOLUME_RATING = "c_rating";




    

    public static final String SQL_CREATE_BOOKSHELVES =
            "CREATE TABLE " + T_BOOKSHELVES +
                    "(" +
                    C_BOOKSHELVES_ID + " INTEGER PRIMARY KEY, " +
                    C_BOOKSHELVES_TITLE + " TEXT NOT NULL, " +
                    C_BOOKSHELVES_ACCESS + " TEXT NOT NULL, " +
                    C_BOOKSHELVES_DESCRIPTION + " TEXT , " +
                    C_BOOKSHELVES_CREATED + " INTEGER , " +
                    C_BOOKSHELVES_UPDATED + " INTEGER , " +
                    C_BOOKSHELVES_SELFLINK + " TEXT , " +
                    C_BOOKSHELVES_VOLUME_COUNT + " INTEGER " +
                    " );";

    public static final String SQL_CREATE_VOLUME =
            "CREATE TABLE " + T_VOLUME +
                    "(" +
                    C_VOLUME_ID + " String PRIMARY KEY, " +
                    C_VOLUME_BOOKSHELF + " INTEGER, " +
                    C_VOLUME_TITLE + " TEXT NOT NULL, " +
                    C_VOLUME_ACCESS + " TEXT NOT NULL, " +
                    C_VOLUME_DESCRIPTION + " TEXT , " +
                    C_VOLUME_RATING + " INTEGER , " +
                    C_VOLUME_KIND + " TEXT , " +
                    C_VOLUME_SUBTITLE + " TEXT, " +
                    "FOREIGN KEY "+C_VOLUME_BOOKSHELF + " REFERENCES " +T_BOOKSHELVES+"("+C_BOOKSHELVES_ID+")"+
                    " );";
    

    public BooxDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        Log.d(TAG, "BooxDbHelper hat die Datenbank "+ getDatabaseName()+ "erzeugt");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d(TAG, "Die Datenbanktabellen werden mit SQL-Befehl: "+ SQL_CREATE_BOOKSHELVES + "angelegt");
        Log.d(TAG, "Die Datenbanktabellen werden mit SQL-Befehl: "+ SQL_CREATE_VOLUME + "angelegt");
        try {
            sqLiteDatabase.execSQL(SQL_CREATE_BOOKSHELVES);
            sqLiteDatabase.execSQL(SQL_CREATE_VOLUME);


        }catch (SQLException e){
            Log.e(TAG, "Fehler beim Anlegen der Tabellen: "+ e.getMessage() );
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
