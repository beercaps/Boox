package com.boox.kevinwetzel.boox.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.boox.kevinwetzel.boox.databases.BooxDbHelper;
import com.boox.kevinwetzel.boox.model.BookshelfVolumeAssociation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kevinn on 26.06.2016.
 */
public class BookshelvesVolumesAssocDAO {

    private static final String TAG = BookshelvesVolumesAssocDAO.class.getSimpleName();

    private SQLiteDatabase database;
    private BooxDbHelper booxDbHelper;

    private String[] columns = {
            BooxDbHelper.C_BOOKSHELVES_VOLUMES_ASSOCIATION_BOOKSHELVES_ID,
            BooxDbHelper.C_BOOKSHELVES_VOLUMES_ASSOCIATION_VOLUME_ID,
           };

    public BookshelvesVolumesAssocDAO(Context context) {
        this.booxDbHelper = new BooxDbHelper(context);
       }

    public void open(){
        database = booxDbHelper.getWritableDatabase();
        Log.d(TAG, "Datenbank-Referenz erhalten. Pfad zur Datenbank: " + database.getPath());
    }

    public void close(){
        booxDbHelper.close();
        Log.d(TAG, "Datenbank mit Hilfe des BooxDbHelpers geschlossen.");
    }

    public BookshelfVolumeAssociation createBookshelfVolumeAssoc(int bookshelfId, String volumeID){


        ContentValues values = new ContentValues();
        values.put(BooxDbHelper.C_BOOKSHELVES_VOLUMES_ASSOCIATION_BOOKSHELVES_ID, bookshelfId);
        values.put(BooxDbHelper.C_BOOKSHELVES_VOLUMES_ASSOCIATION_VOLUME_ID, volumeID);


            Log.d(TAG, "createBookshelfVolumeAssoc: ");
            database.insert(BooxDbHelper.T_BOOKSHELVES_VOLUMES_ASSOCIATION, null, values);


        Cursor cursor = database.query(BooxDbHelper.T_BOOKSHELVES_VOLUMES_ASSOCIATION,
                columns, BooxDbHelper.C_BOOKSHELVES_VOLUMES_ASSOCIATION_BOOKSHELVES_ID + "=" + bookshelfId +" AND "+
                         BooxDbHelper.C_BOOKSHELVES_VOLUMES_ASSOCIATION_VOLUME_ID+ "='"+volumeID+"'" ,
                null, null, null, null);
        cursor.moveToFirst();
        BookshelfVolumeAssociation bookshelfVolumeAssociation= cursorToBookshelf(cursor);
        cursor.close();


        return bookshelfVolumeAssociation;
    }



    private BookshelfVolumeAssociation cursorToBookshelf(Cursor cursor){
        int columnIndexBookshelvesId = cursor.getColumnIndex(BooxDbHelper.C_BOOKSHELVES_VOLUMES_ASSOCIATION_BOOKSHELVES_ID);
        int columnIndexVolumeId = cursor.getColumnIndex(BooxDbHelper.C_BOOKSHELVES_VOLUMES_ASSOCIATION_VOLUME_ID);

       BookshelfVolumeAssociation bookshelfVolumeAssociation = new BookshelfVolumeAssociation();
       bookshelfVolumeAssociation.setBookshelfId(cursor.getInt(columnIndexBookshelvesId));
       bookshelfVolumeAssociation.setVolumeId(cursor.getString(columnIndexVolumeId));

        return bookshelfVolumeAssociation;
    }

    public BookshelfVolumeAssociation searchBookshelfVolumeAssociation(int bookshelfId, String volumeID){
        BookshelfVolumeAssociation bookshelfVolumeAssociation = null;
      Cursor cursor = database.query(BooxDbHelper.T_BOOKSHELVES_VOLUMES_ASSOCIATION,columns, BooxDbHelper.C_BOOKSHELVES_VOLUMES_ASSOCIATION_BOOKSHELVES_ID + "="+bookshelfId +" AND "+
                                                                                             BooxDbHelper.C_BOOKSHELVES_VOLUMES_ASSOCIATION_VOLUME_ID + "="+volumeID   ,null, null, null, null);
        if (cursor.moveToFirst()){
            cursor.moveToFirst();
            bookshelfVolumeAssociation = cursorToBookshelf(cursor);
            cursor.close();
            Log.d(TAG, "searchBookshelfVolumeAccociation ShelfID "+ bookshelfVolumeAssociation.getBookshelfId() +"VolId "+ bookshelfVolumeAssociation.getVolumeId());
        }
        return bookshelfVolumeAssociation;

    }

    public List<String>getAllVolumeIDsForBookshelf(int bookshelfId){
        List<String> volumeIDs = new ArrayList<>();
        Cursor cursor = database.query(BooxDbHelper.T_BOOKSHELVES_VOLUMES_ASSOCIATION,
                columns,BooxDbHelper.C_BOOKSHELVES_VOLUMES_ASSOCIATION_BOOKSHELVES_ID + "="+bookshelfId, null, null, null, null );
        cursor.moveToFirst();
        BookshelfVolumeAssociation bookshelfVolumeAssociation;

        while (!cursor.isAfterLast()){
            bookshelfVolumeAssociation = cursorToBookshelf(cursor);
            volumeIDs.add(bookshelfVolumeAssociation.getVolumeId());
            Log.d(TAG, "getAllVolumeIDsForBookshelf Id: "+bookshelfVolumeAssociation.getVolumeId());
            cursor.moveToNext();
        }
        cursor.close();
        return volumeIDs;
    }
}
