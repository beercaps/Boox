package com.boox.kevinwetzel.boox.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.boox.kevinwetzel.boox.ApiKeys;
import com.boox.kevinwetzel.boox.dao.BookshelvesDAO;
import com.boox.kevinwetzel.boox.dao.VolumesDAO;
import com.google.api.client.json.JsonFactory;
import com.google.api.services.books.Books;
import com.google.api.services.books.BooksRequestInitializer;
import com.google.api.services.books.model.Volume;

import java.util.ArrayList;

/**
 * Please start by reviewing the Google Books API documentation at:
 * http://code.google.com/apis/books/docs/getting_started.html
 */


public class BooksGetVolumesFromBookshelfAsync extends AsyncTask<Integer, Void, Void> {


    private static final String APPLICATION_NAME = "com.boox.kevinwetzel.boox";

    private JsonFactory jsonFactory;
    private static String oauthToken;
    private static final String TAG = BooksGetVolumesFromBookshelfAsync.class.getSimpleName();
    private VolumesDAO volDao;
    private BookshelvesDAO bookshelvesDao;
    private ArrayList<Books.Mylibrary.Bookshelves.Volumes.List> volumesList;

    public BooksGetVolumesFromBookshelfAsync(JsonFactory jsonFactory, String oauthToken, Context context) {
        this.jsonFactory = jsonFactory;
        this.oauthToken = oauthToken;
        this.volDao = new VolumesDAO(context);
        this.bookshelvesDao = new BookshelvesDAO(context);
    }

    private void queryVolumesFromBookshelf(JsonFactory jsonFactory, Integer[] bookshelfId) throws Exception {

        // Set up Books client.
        final Books books = new Books.Builder(new com.google.api.client.http.javanet.NetHttpTransport(), jsonFactory, null)
                .setApplicationName(APPLICATION_NAME)
                .setGoogleClientRequestInitializer(new BooksRequestInitializer(ApiKeys.BOOKS_API))
                .build();


        for (int i = 0; i < bookshelfId.length; i++) {
            //check if the bookshelf contains volumes
            bookshelvesDao.open();
            int volCount = bookshelvesDao.searchBookshelf(bookshelfId[i]).getVolumeCount();
            bookshelvesDao.close();
            if (volCount > 0) {
                Log.d(TAG, "queryVolumesFromBookshelf: Get Volumes for Bookshelf ID " + bookshelfId[i]);
                Books.Mylibrary.Bookshelves.Volumes.List listItem = books.mylibrary().bookshelves().volumes().list(String.valueOf(bookshelfId[i]));
                listItem.setOauthToken(oauthToken);


                volDao.open();
                for (Volume volume : listItem.execute().getItems()) {
                    volDao.createVolume(volume,bookshelfId[i]);
                }
                volDao.close();

            }
        }

        }


         




    @Override
    protected Void doInBackground(Integer... integers) {

        try {
            queryVolumesFromBookshelf(jsonFactory, integers);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}


