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
    public static final int DB_VERSION = 13;

    //----------------------------------------------------------------------------------------//
    //                                          Bookshelves                                   //
    //----------------------------------------------------------------------------------------//
    public  static  final String T_BOOKSHELVES = "t_bookshelves";
    public  static  final String C_BOOKSHELVES_ID = "_id";          //PK
    public  static final String C_BOOKSHELVES_TITLE = "c_title";
    public  static final String C_BOOKSHELVES_ACCESS = "c_access";
    public  static final String C_BOOKSHELVES_DESCRIPTION = "c_description";
    public  static final String C_BOOKSHELVES_CREATED = "c_created";
    public  static final String C_BOOKSHELVES_UPDATED = "c_updated";
    public  static  final String C_BOOKSHELVES_SELFLINK = "c_selflink";
    public  static  final String C_BOOKSHELVES_VOLUME_COUNT = "c_volume_count";

    //----------------------------------------------------------------------------------------//
    //                                          VOLUME                                        //
    //----------------------------------------------------------------------------------------//
    public  static final String T_VOLUME = "t_volume";
    public  static  final String C_VOLUME_ID = "_id";                //PK
    public  static  final String C_VOLUME_ACCESS = "c_access";
    public  static  final String C_VOLUME_KIND = "c_kind";

    // RECOMMENDED INFO
    public static final String C_VOLUME_RECOMMENDEDINFO_EXPLANATION = "c_recommendedinfo_explanation";                              //STRING

    //SALE INFO
    public static final String C_VOLUME_SALEINFO_BUYLINK                        = "c_volume_saleinfo_buylink";                      //STRING
    public static final String C_VOLUME_SALEINFO_COUNTRY                        = "c_volume_saleinfo_country";                      //STRING
    public static final String C_VOLUME_SALEINFO_ISEBOOK                        = "c_volume_saleinfo_isebook";                      //BOOLEAN
    public static final String C_VOLUME_SALEINFO_ONSALEDATE                     = "c_volume_saleinfo_onsaledate";                   //DATETIME
    // public static final String C_VOLUME_SALEINFO_RETAILPRICE          EXTRA TABLE
    // public static final String C_VOLUME_SALEINFO_LISTPRICE            EXTRA TABLE
    // public static final String C_VOLUME_SALEINFO_OFFERS               EXTRA TABLE

    //SEARCH INFO
    public static final String C_VOLUME_SEARCHINFO_TEXTSNIPPET                  = "c_volume_searchinfo_textsnippet";                //STRING

    //USER INFO
    public static final String C_VOLUME_USERINFO_ENTITLEMENTTYPE                = "c_volume_userinfo_entitlementtype";              //INT
    public static final String C_VOLUME_USERINFO_ISINMYBOOKS                    = "c_volume_userinfo_isinmybooks";                  //BOOLEAN
    public static final String C_VOLUME_USERINFO_ISPREORDERED                   = "c_volume_userinfo_ispreordered";                 //BOOLEAN
    public static final String C_VOLUME_USERINFO_ISPURCHASED                    = "c_volume_userinfo_ispurchased";                  //BOOLEAN
    public static final String C_VOLUME_USERINFO_UPDATED                        = "c_volume_userinfo_updated";                      //DATETIME
    //public static final String C_VOLUME_USERINFO_READINGPOSITION      EXTRA TABLE
    // public static final String C_VOLUME_USERINFO_REVIEW              EXTRA TABLE

    //VOLUME INFO
    public static final String C_VOLUME_VOLUMEINFO_CANONICALVOLUMELINK          = "c_volume_volumeinfo_canonicalvolumelink";        //STRING
    public static final String C_VOLUME_VOLUMEINFO_CONTENTVERSION               = "c_volume_volumeinfo_contentversion";             //STRING
    public static final String C_VOLUME_VOLUMEINFO_DESCRIPTION                  = "c_volume_volumeinfo_description";                //STRING
    public static final String C_VOLUME_VOLUMEINFO_DIMENSIONS_HEIGHT            = "c_volume_volumeinfo_dimensions_height";          //STRING
    public static final String C_VOLUME_VOLUMEINFO_DIMENSIONS_THICKNESS         = "c_volume_volumeinfo_dimensions_thickness";       //STRING
    public static final String C_VOLUME_VOLUMEINFO_DIMENSIONS_WIDTH             = "c_volume_volumeinfo_dimensions_width";           //STRING
    public static final String C_VOLUME_VOLUMEINFO_IMAGELINKS_EXTRALARGE        = "c_volume_volumeinfo_imglinks_extralarge";        //STRING
    public static final String C_VOLUME_VOLUMEINFO_IMAGELINKS_LARGE             = "c_volume_volumeinfo_imglinks_large";             //STRING
    public static final String C_VOLUME_VOLUMEINFO_IMAGELINKS_MEDIUM            = "c_volume_volumeinfo_imglinks_medium";            //STRING
    public static final String C_VOLUME_VOLUMEINFO_IMAGELINKS_SMALL             = "c_volume_volumeinfo_imglinks_small";             //STRING
    public static final String C_VOLUME_VOLUMEINFO_IMAGELINKS_SMALLTHUMBNAIL    = "c_volume_volumeinfo_imglinks_smallthumbnail";    //STRING
    public static final String C_VOLUME_VOLUMEINFO_IMAGELINKS_THUMBNAIL         = "c_volume_volumeinfo_imglinks_thumbnail";         //STRING
    public static final String C_VOLUME_VOLUMEINFO_LANGUAGE                     = "c_volume_volumeinfo_language";                   //STRING
    public static final String C_VOLUME_VOLUMEINFO_MAINCATEGORY                 = "c_volume_volumeinfo_maincategory";               //STRING
    public static final String C_VOLUME_VOLUMEINFO_MATURITYRATING               = "c_volume_volumeinfo_maturityrating";             //STRING
    public static final String C_VOLUME_VOLUMEINFO_PAGECOUNT                    = "c_volume_volumeinfo_pagecount";                  //INTEGER
    public static final String C_VOLUME_VOLUMEINFO_PREVIEWLINK                  = "c_volume_volumeinfo_previewlink";                //STRING
    public static final String C_VOLUME_VOLUMEINFO_PUBLISHEDDATE                = "c_volume_volumeinfo_publisheddate";              //STRING
    public static final String C_VOLUME_VOLUMEINFO_PUBLISHER                    = "c_volume_volumeinfo_publisher";                  //STRING
    public static final String C_VOLUME_VOLUMEINFO_RATINGSCOUNT                 = "c_volume_volumeinfo_ratingscount";               //INTEGER
    public static final String C_VOLUME_VOLUMEINFO_TITLE                        = "c_volume_volumeinfo_title";                      //STRING
    public static final String C_VOLUME_VOLUMEINFO_SUBTITLE                     = "c_volume_volumeinfo_subtitle";                   //STRING
    public static final String C_VOLUME_VOLUMEINFO_AVGRATING                    = "c_volume_volumeinfo_avgrating";                  //DOUBLE
    //public static final String C_VOLUME_VOLUMEINFO_CATEGORIES          EXTRA TABLE!!
    //public static final String C_VOLUME_VOLUMEINFO_AUTHORS            EXTRA TABLE!!

    //----------------------------------------------------------------------------------------//
    //                              Bookshelves Volumes association                           //
    //----------------------------------------------------------------------------------------//
    public static final String T_BOOKSHELVES_VOLUMES_ASSOCIATION = "t_bookshelves_volumes_association";
    public static final String C_BOOKSHELVES_VOLUMES_ASSOCIATION_BOOKSHELVES_ID ="c_bookshelves_id";
    public static final String C_BOOKSHELVES_VOLUMES_ASSOCIATION_VOLUME_ID ="c_volume_id";


    

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
                    C_VOLUME_ID + " TEXT PRIMARY KEY, " +
                    C_VOLUME_ACCESS + " TEXT, " +
                    C_VOLUME_KIND + " TEXT, " +
                    C_VOLUME_RECOMMENDEDINFO_EXPLANATION + " TEXT, " +
                    C_VOLUME_SALEINFO_BUYLINK + " TEXT, " +
                    C_VOLUME_SALEINFO_COUNTRY + " TEXT, " +
                    C_VOLUME_SALEINFO_ISEBOOK + " Boolean, " +
                    C_VOLUME_SEARCHINFO_TEXTSNIPPET + " TEXT, " +
                    C_VOLUME_USERINFO_ENTITLEMENTTYPE + " INTEGER, " +
                    C_VOLUME_USERINFO_ISINMYBOOKS + " Boolean, " +
                    C_VOLUME_USERINFO_ISPREORDERED + " Boolean, " +
                    C_VOLUME_USERINFO_ISPURCHASED + " Boolean, " +
                    C_VOLUME_USERINFO_UPDATED + " INTEGER, " +
                    C_VOLUME_VOLUMEINFO_CANONICALVOLUMELINK + " TEXT, " +
                    C_VOLUME_VOLUMEINFO_DESCRIPTION + " TEXT, " +
                    C_VOLUME_VOLUMEINFO_CONTENTVERSION + " TEXT, " +
                    C_VOLUME_VOLUMEINFO_DIMENSIONS_HEIGHT + " TEXT, " +
                    C_VOLUME_VOLUMEINFO_DIMENSIONS_THICKNESS + " TEXT, " +
                    C_VOLUME_VOLUMEINFO_DIMENSIONS_WIDTH + " TEXT, " +
                    C_VOLUME_VOLUMEINFO_IMAGELINKS_EXTRALARGE + " TEXT, " +
                    C_VOLUME_VOLUMEINFO_IMAGELINKS_LARGE + " TEXT, " +
                    C_VOLUME_VOLUMEINFO_IMAGELINKS_MEDIUM + " TEXT, " +
                    C_VOLUME_VOLUMEINFO_IMAGELINKS_SMALL + " TEXT, " +
                    C_VOLUME_VOLUMEINFO_IMAGELINKS_SMALLTHUMBNAIL + " TEXT, " +
                    C_VOLUME_VOLUMEINFO_IMAGELINKS_THUMBNAIL + " TEXT, " +
                    C_VOLUME_VOLUMEINFO_LANGUAGE + " TEXT, " +
                    C_VOLUME_VOLUMEINFO_MAINCATEGORY + " TEXT, " +
                    C_VOLUME_VOLUMEINFO_MATURITYRATING + " TEXT, " +
                    C_VOLUME_VOLUMEINFO_PAGECOUNT + " Integer, " +
                    C_VOLUME_VOLUMEINFO_PREVIEWLINK + " TEXT, " +
                    C_VOLUME_VOLUMEINFO_PUBLISHEDDATE + " TEXT, " +
                    C_VOLUME_VOLUMEINFO_PUBLISHER + " TEXT, " +
                    C_VOLUME_VOLUMEINFO_RATINGSCOUNT + " Integer, " +
                    C_VOLUME_VOLUMEINFO_TITLE + " TEXT, " +
                    C_VOLUME_VOLUMEINFO_SUBTITLE + " TEXT, " +
                    C_VOLUME_VOLUMEINFO_AVGRATING + " Integer " +
                    " );";


    public static final String SQL_CREATE_BOOKSHELVES_VOLUMES_ASSOCIATION =
            "CREATE TABLE " + T_BOOKSHELVES_VOLUMES_ASSOCIATION +
                    "(" +
                    C_BOOKSHELVES_VOLUMES_ASSOCIATION_BOOKSHELVES_ID + " INTEGER, " +
                    C_BOOKSHELVES_VOLUMES_ASSOCIATION_VOLUME_ID + " TEXT," +
                    "PRIMARY KEY( "+C_BOOKSHELVES_VOLUMES_ASSOCIATION_BOOKSHELVES_ID+", "+
                                    C_BOOKSHELVES_VOLUMES_ASSOCIATION_VOLUME_ID+"),"+
                    "FOREIGN KEY("+ C_BOOKSHELVES_VOLUMES_ASSOCIATION_BOOKSHELVES_ID+ ") REFERENCES "+ T_BOOKSHELVES+"("+C_BOOKSHELVES_ID+"),"+
                    "FOREIGN KEY("+ C_BOOKSHELVES_VOLUMES_ASSOCIATION_VOLUME_ID+ ") REFERENCES "+ T_VOLUME+"("+C_VOLUME_ID+")"+
                    " );";
    

    public BooxDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        Log.d(TAG, "BooxDbHelper hat die Datenbank "+ getDatabaseName()+ "erzeugt");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d(TAG, "Die Datenbanktabellen werden mit SQL-Befehl: "+ SQL_CREATE_BOOKSHELVES + "angelegt");
        Log.d(TAG, "Die Datenbanktabellen werden mit SQL-Befehl: "+ SQL_CREATE_VOLUME + "angelegt");
        Log.d(TAG, "Die Datenbanktabellen werden mit SQL-Befehl: "+ SQL_CREATE_BOOKSHELVES_VOLUMES_ASSOCIATION + "angelegt");
        try {
            sqLiteDatabase.execSQL(SQL_CREATE_BOOKSHELVES);
            sqLiteDatabase.execSQL(SQL_CREATE_VOLUME);
            sqLiteDatabase.execSQL(SQL_CREATE_BOOKSHELVES_VOLUMES_ASSOCIATION);


        }catch (SQLException e){
            Log.e(TAG, "Fehler beim Anlegen der Tabellen: "+ e.getMessage() );
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        try {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ BooxDbHelper.T_BOOKSHELVES);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ BooxDbHelper.T_VOLUME);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ BooxDbHelper.T_BOOKSHELVES_VOLUMES_ASSOCIATION);
            onCreate(sqLiteDatabase);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


}
