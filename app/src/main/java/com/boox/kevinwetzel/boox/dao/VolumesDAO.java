package com.boox.kevinwetzel.boox.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.boox.kevinwetzel.boox.databases.BooxDbHelper;
import com.google.api.services.books.model.Bookshelf;
import com.google.api.services.books.model.Volume;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kevinn on 26.06.2016.
 */
public class VolumesDAO {

    private static final String TAG = VolumesDAO.class.getSimpleName();

    private SQLiteDatabase database;
    private BooxDbHelper booxDbHelper;

    private String[] columns = {
            BooxDbHelper.C_VOLUME_ID,
            BooxDbHelper.C_VOLUME_BOOKSHELF,
            BooxDbHelper.C_VOLUME_ACCESS,
            BooxDbHelper.C_VOLUME_KIND,
            BooxDbHelper.C_VOLUME_DESCRIPTION,
            BooxDbHelper.C_VOLUME_TITLE,
            BooxDbHelper.C_VOLUME_SUBTITLE,
            BooxDbHelper.C_VOLUME_RATING,
            };



    public VolumesDAO(Context context) {
        Log.d(TAG, "ProductDataSource erzeugt den DBhelper");
        this.booxDbHelper = new BooxDbHelper(context);
       }

    public void open(){
        Log.d(TAG, "Eine Referenz auf die Datenbank wird jetzt angefragt.");
        database = booxDbHelper.getWritableDatabase();
        Log.d(TAG, "Datenbank-Referenz erhalten. Pfad zur Datenbank: " + database.getPath());
    }

    public void close(){
        booxDbHelper.close();
        Log.d(TAG, "Datenbank mit Hilfe des BooxDbHelpers geschlossen.");
    }



    public Volume createVolume(Volume volume, int shelfId){


        ContentValues values = new ContentValues();
        values.put(BooxDbHelper.C_VOLUME_BOOKSHELF, shelfId);
        values.put(BooxDbHelper.C_VOLUME_ACCESS, volume.getAccessInfo().getAccessViewStatus());
        values.put(BooxDbHelper.C_VOLUME_KIND, volume.getKind());
        values.put(BooxDbHelper.C_VOLUME_DESCRIPTION, volume.getVolumeInfo().getDescription());
        values.put(BooxDbHelper.C_VOLUME_TITLE, volume.getVolumeInfo().getTitle());
        values.put(BooxDbHelper.C_VOLUME_SUBTITLE, volume.getVolumeInfo().getSubtitle());
        values.put(BooxDbHelper.C_VOLUME_RATING, volume.getVolumeInfo().getAverageRating());

        if (searchVolume(volume.getId())!= null){
            //modify existing bookshelf
            database.update(BooxDbHelper.T_BOOKSHELVES,
                    values,
                    BooxDbHelper.C_BOOKSHELVES_ID + "=" + volume.getId(),
                    null);
            Log.d(TAG, "createBookshelf: modified: ");
        }else {
            //create new bookshelf
            values.put(BooxDbHelper.C_BOOKSHELVES_ID, volume.getId());
            database.insert(BooxDbHelper.T_BOOKSHELVES, null, values);
            Log.d(TAG, "createBookshelf: created: ");

        }


        Cursor cursor = database.query(BooxDbHelper.T_BOOKSHELVES,
                columns, BooxDbHelper.C_BOOKSHELVES_ID + "=" + volume.getId(),
                null, null, null, null);
        cursor.moveToFirst();
        Volume vol= cursorToVolume(cursor);

        cursor.close();


        Log.d(TAG, "Volume  "+ vol.toString());
        return vol;

    }

    private Volume cursorToVolume(Cursor cursor){
        int columnIndexID = cursor.getColumnIndex(BooxDbHelper.C_VOLUME_ID);
        int columnIndexTitle = cursor.getColumnIndex(BooxDbHelper.C_VOLUME_TITLE);
        int columnIndexSubtitle = cursor.getColumnIndex(BooxDbHelper.C_VOLUME_SUBTITLE);
        int columnIndexAccess = cursor.getColumnIndex(BooxDbHelper.C_VOLUME_ACCESS);
        int columnIndexDescription = cursor.getColumnIndex(BooxDbHelper.C_VOLUME_DESCRIPTION);
        int columnIndexKind = cursor.getColumnIndex(BooxDbHelper.C_VOLUME_KIND);
        int columnIndexRating = cursor.getColumnIndex(BooxDbHelper.C_VOLUME_RATING);

        Volume volume = new Volume();

        volume.setId(cursor.getString(columnIndexID));
        volume.setVolumeInfo(new Volume.VolumeInfo().setTitle(cursor.getString(columnIndexTitle)));
        volume.setAccessInfo(new Volume.AccessInfo().setAccessViewStatus(cursor.getString(columnIndexAccess)));
        volume.setVolumeInfo(new Volume.VolumeInfo().setDescription(cursor.getString(columnIndexDescription)));
        volume.setVolumeInfo(new Volume.VolumeInfo().setSubtitle(cursor.getString(columnIndexSubtitle)));
        volume.setKind(cursor.getString(columnIndexKind));
        volume.setVolumeInfo(new Volume.VolumeInfo().setAverageRating(cursor.getDouble(columnIndexRating)));


        return volume;
    }

    public Volume searchVolume(String volumeID){
        Volume volume = null;
      Cursor cursor = database.query(BooxDbHelper.T_VOLUME,columns, BooxDbHelper.C_VOLUME_ID + "="+volumeID,null, null, null, null);
        if (cursor.moveToFirst()){
            cursor.moveToFirst();
            volume = cursorToVolume(cursor);
            cursor.close();
            Log.d(TAG, "searchBooksheld ID"+ volume.getId()+ "Inhalt "+volume.toString());
        }
        return volume;

    }

    //TODO getAllVolumes
//    public List<Bookshelf>getAllBookshelves(){
//        List<Bookshelf> bookshelfList = new ArrayList<>();
//        Cursor cursor = database.query(BooxDbHelper.T_BOOKSHELVES,
//                columns, null, null, null, null, null );
//        cursor.moveToFirst();
//        Bookshelf bookshelf;
//
//        while (!cursor.isAfterLast()){
//            bookshelf = cursorToVolume(cursor);
//            bookshelfList.add(bookshelf);
//            Log.d(TAG, "getAllBookshelves ID: "+bookshelf.getId()+ " Inhalt: "+bookshelf.toString());
//            cursor.moveToNext();
//        }
//        cursor.close();
//        return bookshelfList;
//    }
}
