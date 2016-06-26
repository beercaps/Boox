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

    public  static  final String DB_NAME = "boox.db";
    public static final int DB_VERSION = 1;

    public  static  final String T_BOOKSHELVES = "t_bookshelves";
    public  static  final String C_ID = "_id";
    public  static final String C_TITLE = "c_title";
    public  static final String C_ACCESS = "c_access";
    public  static final String C_DESCRIPTION= "c_description";
    public  static final String C_CREATED = "c_created";
    public  static final String C_UPDATED = "c_updated";
    public  static  final String C_SELFLINK = "c_selflink";
    public  static  final String C_VOLUME_COUNT = "c_volume_count";







    public  static  final String T_VOLUMES = "t_VOLUMES";

    public static final String SQL_CREATE_BOOKSHELVES =
            "CREATE TABLE " + T_BOOKSHELVES +
                    "(" +
                    C_ID + " INTEGER PRIMARY KEY, " +
                    C_TITLE + " TEXT NOT NULL, " +
                    C_ACCESS + " TEXT NOT NULL, " +
                    C_DESCRIPTION + " TEXT , " +
                    C_CREATED + " INTEGER , " +
                    C_UPDATED + " INTEGER , " +
                    C_SELFLINK + " TEXT , " +
                    C_VOLUME_COUNT + " INTEGER " +
                    " );";

    public BooxDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        Log.d(TAG, "BooxDbHelper hat die Datenbank "+ getDatabaseName()+ "erzeugt");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d(TAG, "Die Datenbanktabellen werden mit SQL-Befehl: "+ SQL_CREATE_BOOKSHELVES + "angelegt");
        try {
            sqLiteDatabase.execSQL(SQL_CREATE_BOOKSHELVES);

        }catch (SQLException e){
            Log.e(TAG, "Fehler beim Anlegen der Tabellen: "+ e.getMessage() );
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
