package com.boox.kevinwetzel.boox.asyncTasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.boox.kevinwetzel.boox.BaseCompatActivity;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;

import java.io.IOException;

/**
 * Created by Kevinn on 05.06.2016.
 */
public class GenerateAsyncToken extends AsyncTask<Void, Void, Void> {

    private static final String TAG = GenerateAsyncToken.class.getSimpleName();

        private String token;
        private Activity mActivity;
        private String mScope;
        private String mEmail;

    public GenerateAsyncToken(Activity activity, String email, String scope) {
        this.mActivity = activity;
        this.mScope = scope;
        this.mEmail = email;
        }

/**
 * Executes the asynchronous job. This runs when you call execute()
 * on the AsyncTask instance.
 */
@Override
protected Void doInBackground(Void... params) {
        try {
        token = fetchToken();

        } catch (IOException e) {
        // The fetchToken() method handles Google-specific exceptions,
        // so this indicates something went wrong at a higher level.
        // TIP: Check for network connectivity before starting the AsyncTask.
            e.printStackTrace();

        }
        return null;
        }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        BaseCompatActivity.setAccess_token(token);
        Log.d(TAG, "onPostExecute: set token "+ token);
    }

    /**
 * Gets an authentication token from Google and handles any
 * GoogleAuthException that may occur.
 */
protected String fetchToken() throws IOException {
        try {
        return GoogleAuthUtil.getToken(mActivity, mEmail, mScope);
        } catch (UserRecoverableAuthException userRecoverableException) {
            userRecoverableException.printStackTrace();
        // GooglePlayServices.apk is either old, disabled, or not present
        // so we need to show the user some UI in the activity to recover.
       // mActivity.handleException(userRecoverableException);
        } catch (GoogleAuthException fatalException) {
            fatalException.printStackTrace();
        // Some other type of unrecoverable exception has occurred.
        // Report and log the error as appropriate for your app.

        }
        return null;
        }
}
