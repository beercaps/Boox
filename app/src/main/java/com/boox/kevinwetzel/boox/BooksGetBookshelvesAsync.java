package com.boox.kevinwetzel.boox;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.boox.kevinwetzel.boox.dao.BookshelvesDAO;
import com.google.api.client.json.JsonFactory;
import com.google.api.services.books.Books;
import com.google.api.services.books.BooksRequestInitializer;
import com.google.api.services.books.model.Bookshelf;
import com.google.api.services.books.model.Bookshelves;

/**
 * Please start by reviewing the Google Books API documentation at:
 * http://code.google.com/apis/books/docs/getting_started.html
 */

//TODO öffentliche bookshelves abfragen ->userid aus selflink extrahieren
public class BooksGetBookshelvesAsync extends AsyncTask<Void, Void, Void> {


    private static final String APPLICATION_NAME = "com.boox.kevinwetzel.boox";

    private JsonFactory jsonFactory;
    private static String oauthToken;
    private static final String TAG = BooksGetBookshelvesAsync.class.getSimpleName();
    private BookshelvesDAO bsdao;

    public BooksGetBookshelvesAsync(JsonFactory jsonFactory, String oauthToken, Context context) {
        this.jsonFactory = jsonFactory;
        this.oauthToken = oauthToken;
        this.bsdao = new BookshelvesDAO(context);
    }

    private void queryBookshelves(JsonFactory jsonFactory) throws Exception {

        // Set up Books client.
        final Books books = new Books.Builder(new com.google.api.client.http.javanet.NetHttpTransport(), jsonFactory, null)
                .setApplicationName(APPLICATION_NAME)
                .setGoogleClientRequestInitializer(new BooksRequestInitializer(ApiKeys.BOOKS_API))
                .build();




        Books.Mylibrary.Bookshelves.List mylib = books.mylibrary().bookshelves().list();
        mylib.setOauthToken(oauthToken);

        Bookshelves mlib = mylib.execute();
        for (Bookshelf bookshelf: mlib.getItems()) {
            bsdao.open();
            Log.d(TAG, "Adding bookshelves to DB");
            bsdao.createBookshelf(bookshelf);
            bsdao.close();

         
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


