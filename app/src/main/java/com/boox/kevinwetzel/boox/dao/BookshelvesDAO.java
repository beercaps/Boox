package com.boox.kevinwetzel.boox.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.boox.kevinwetzel.boox.databases.BooxDbHelper;
import com.google.api.client.util.DateTime;
import com.google.api.services.books.model.Bookshelf;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kevinn on 26.06.2016.
 */
public class BookshelvesDAO {

    private static final String TAG = BookshelvesDAO.class.getSimpleName();

    private SQLiteDatabase database;
    private BooxDbHelper booxDbHelper;

    private String[] columns = {
            BooxDbHelper.C_BOOKSHELVES_ID,
            BooxDbHelper.C_BOOKSHELVES_TITLE,
            BooxDbHelper.C_BOOKSHELVES_ACCESS,
            BooxDbHelper.C_BOOKSHELVES_DESCRIPTION,
            BooxDbHelper.C_BOOKSHELVES_CREATED,
            BooxDbHelper.C_BOOKSHELVES_UPDATED,
            BooxDbHelper.C_BOOKSHELVES_SELFLINK,
            BooxDbHelper.C_BOOKSHELVES_VOLUME_COUNT};

    public BookshelvesDAO(Context context) {
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

    public Bookshelf createBookshelf(int id, String title, String access, String description, long created, long updated, String selflink, int volumeCount){


        ContentValues values = new ContentValues();
        values.put(BooxDbHelper.C_BOOKSHELVES_TITLE, title);
        values.put(BooxDbHelper.C_BOOKSHELVES_ACCESS, access);
        values.put(BooxDbHelper.C_BOOKSHELVES_DESCRIPTION, description);
        values.put(BooxDbHelper.C_BOOKSHELVES_CREATED, created);
        values.put(BooxDbHelper.C_BOOKSHELVES_UPDATED, updated);
        values.put(BooxDbHelper.C_BOOKSHELVES_SELFLINK, selflink);
        values.put(BooxDbHelper.C_BOOKSHELVES_VOLUME_COUNT, volumeCount);


        if (searchBookshelf(id)!= null){
            //modify existing bookshelf
            database.update(BooxDbHelper.T_BOOKSHELVES,
                    values,
                    BooxDbHelper.C_BOOKSHELVES_ID + "=" + id,
                    null);
            Log.d(TAG, "createBookshelf: modified: ");
        }else {
            //create new bookshelf
            values.put(BooxDbHelper.C_BOOKSHELVES_ID, id);
            database.insert(BooxDbHelper.T_BOOKSHELVES, null, values);
            Log.d(TAG, "createBookshelf: created: ");

        }


        Cursor cursor = database.query(BooxDbHelper.T_BOOKSHELVES,
                columns, BooxDbHelper.C_BOOKSHELVES_ID + "=" + id,
                null, null, null, null);
        cursor.moveToFirst();
        Bookshelf bookshelf= cursorToBookshelf(cursor);

            cursor.close();


        Log.d(TAG, "Bookshelf  "+ bookshelf.toString());
        return bookshelf;

    }

    public Bookshelf createBookshelf(Bookshelf bookshelf){


        ContentValues values = new ContentValues();
        values.put(BooxDbHelper.C_BOOKSHELVES_TITLE, bookshelf.getTitle());
        values.put(BooxDbHelper.C_BOOKSHELVES_ACCESS, bookshelf.getAccess());
        values.put(BooxDbHelper.C_BOOKSHELVES_DESCRIPTION, bookshelf.getDescription());
        values.put(BooxDbHelper.C_BOOKSHELVES_CREATED, bookshelf.getCreated().getValue());
        values.put(BooxDbHelper.C_BOOKSHELVES_UPDATED, bookshelf.getUpdated().getValue());
        values.put(BooxDbHelper.C_BOOKSHELVES_SELFLINK, bookshelf.getSelfLink());
        values.put(BooxDbHelper.C_BOOKSHELVES_VOLUME_COUNT, bookshelf.getVolumeCount());

//TODO modify only when updated value of new shelf is different from stored shelf
        if (searchBookshelf(bookshelf.getId())!= null){
            //modify existing bookshelf
            database.update(BooxDbHelper.T_BOOKSHELVES,
                    values,
                    BooxDbHelper.C_BOOKSHELVES_ID + "=" + bookshelf.getId(),
                    null);
            Log.d(TAG, "createBookshelf: modified: ");
        }else {
            //create new bookshelf
            values.put(BooxDbHelper.C_BOOKSHELVES_ID, bookshelf.getId());
            database.insert(BooxDbHelper.T_BOOKSHELVES, null, values);
            Log.d(TAG, "createBookshelf: created: ");

        }


        Cursor cursor = database.query(BooxDbHelper.T_BOOKSHELVES,
                columns, BooxDbHelper.C_BOOKSHELVES_ID + "=" + bookshelf.getId(),
                null, null, null, null);
        cursor.moveToFirst();
        Bookshelf bs= cursorToBookshelf(cursor);

        cursor.close();


        Log.d(TAG, "Bookshelf  "+ bs.toString());
        return bs;

    }

    private Bookshelf cursorToBookshelf(Cursor cursor){
        int columnIndexID = cursor.getColumnIndex(BooxDbHelper.C_BOOKSHELVES_ID);
        int columnIndexTitle = cursor.getColumnIndex(BooxDbHelper.C_BOOKSHELVES_TITLE);
        int columnIndexAccess = cursor.getColumnIndex(BooxDbHelper.C_BOOKSHELVES_ACCESS);
        int columnIndexDescription = cursor.getColumnIndex(BooxDbHelper.C_BOOKSHELVES_DESCRIPTION);
        int columnIndexCreated = cursor.getColumnIndex(BooxDbHelper.C_BOOKSHELVES_CREATED);
        int columnIndexUpdated = cursor.getColumnIndex(BooxDbHelper.C_BOOKSHELVES_UPDATED);
        int columnIndexSelflink = cursor.getColumnIndex(BooxDbHelper.C_BOOKSHELVES_SELFLINK);
        int columnIndexVolumeCount = cursor.getColumnIndex(BooxDbHelper.C_BOOKSHELVES_VOLUME_COUNT);

        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setId(cursor.getInt(columnIndexID));
        bookshelf.setTitle(cursor.getString(columnIndexTitle));
        bookshelf.setAccess(cursor.getString(columnIndexAccess));
        bookshelf.setDescription(cursor.getString(columnIndexDescription));
        bookshelf.setCreated(new DateTime(cursor.getLong(columnIndexCreated)));
        bookshelf.setUpdated(new DateTime(cursor.getLong(columnIndexUpdated)));
        bookshelf.setSelfLink(cursor.getString(columnIndexSelflink));
        bookshelf.setVolumeCount(cursor.getInt(columnIndexVolumeCount));

        return bookshelf;
    }

    public Bookshelf searchBookshelf(int shelfId){
        Bookshelf bookshelf = null;
      Cursor cursor = database.query(BooxDbHelper.T_BOOKSHELVES,columns, BooxDbHelper.C_BOOKSHELVES_ID + "="+shelfId,null, null, null, null);
        if (cursor.moveToFirst()){
            cursor.moveToFirst();
            bookshelf = cursorToBookshelf(cursor);
            cursor.close();
            Log.d(TAG, "searchBookshelf ID "+ bookshelf.getId()+ "Inhalt "+bookshelf.toString());
        }
        return bookshelf;

    }

    public List<Bookshelf>getAllBookshelves(){
        List<Bookshelf> bookshelfList = new ArrayList<>();
        Cursor cursor = database.query(BooxDbHelper.T_BOOKSHELVES,
                columns, null, null, null, null, null );
        cursor.moveToFirst();
        Bookshelf bookshelf;

        while (!cursor.isAfterLast()){
            bookshelf = cursorToBookshelf(cursor);
            bookshelfList.add(bookshelf);
            Log.d(TAG, "getAllBookshelves ID: "+bookshelf.getId()+ " Inhalt: "+bookshelf.toString());
            cursor.moveToNext();
        }
        cursor.close();
        return bookshelfList;
    }
}
