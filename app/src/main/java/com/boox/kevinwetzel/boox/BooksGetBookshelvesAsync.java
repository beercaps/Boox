package com.boox.kevinwetzel.boox;

import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.json.JsonFactory;
import com.google.api.services.books.Books;
import com.google.api.services.books.BooksRequestInitializer;
import com.google.api.services.books.model.Bookshelf;
import com.google.api.services.books.model.Bookshelves;

/**
 * Please start by reviewing the Google Books API documentation at:
 * http://code.google.com/apis/books/docs/getting_started.html
 */
public class BooksGetBookshelvesAsync extends AsyncTask<Void, Void, Void> {


    private static final String APPLICATION_NAME = "com.boox.kevinwetzel.boox";

    private JsonFactory jsonFactory;
    private static String oauthToken;
    private static final String TAG = BooksGetBookshelvesAsync.class.getSimpleName();

    public BooksGetBookshelvesAsync(JsonFactory jsonFactory, String oauthToken) {
        this.jsonFactory = jsonFactory;

        this.oauthToken = oauthToken;
    }

    private static void queryBookshelves(JsonFactory jsonFactory) throws Exception {

        // Set up Books client.
        final Books books = new Books.Builder(new com.google.api.client.http.javanet.NetHttpTransport(), jsonFactory, null)
                .setApplicationName(APPLICATION_NAME)
                .setGoogleClientRequestInitializer(new BooksRequestInitializer(ApiKeys.BOOKS_API))
                .build();




        Books.Mylibrary.Bookshelves.List mylib = books.mylibrary().bookshelves().list();
        mylib.setOauthToken(oauthToken);

        Bookshelves mlib = mylib.execute();
        for (Bookshelf bookshelf: mlib.getItems()) {
            Log.d(TAG, "queryGoogleBooks: mylib Title "+ bookshelf.getTitle());
            Log.d(TAG, "queryGoogleBooks: mylib ID "+ bookshelf.getId());
            if (bookshelf.getVolumeCount() > 0){
                Log.d(TAG, "queryGoogleBooks: mylib Count "+ bookshelf.getVolumeCount());

            }
            Log.d(TAG, "queryGoogleBooks: mylib SelfLink "+ bookshelf.getSelfLink());
        }


    }


    @Override
    protected Void doInBackground(Void... params) {
        try {
            queryBookshelves(jsonFactory);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}


