package com.boox.kevinwetzel.boox;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.InputStream;

/**
 * Created by Kevinn on 28.05.2016.
 */
public class DownloadImageAsyncTaskLinearLayout extends AsyncTask<String, Void, Bitmap> {


    private LinearLayout bmImage;

    public DownloadImageAsyncTaskLinearLayout(LinearLayout bmImage) {
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        BitmapDrawable bmpd = new BitmapDrawable(result);
        bmImage.setBackground(bmpd);
    }
}
