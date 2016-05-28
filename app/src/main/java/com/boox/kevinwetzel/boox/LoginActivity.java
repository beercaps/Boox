package com.boox.kevinwetzel.boox;


import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;


/**
 * A login screen
 */
public class LoginActivity extends BaseCompatActivity {

    public  static final String PARCEL_GOOGLE_SIGN_IN_ACCOUNT = "com.boox.LoginActivity.GoogleSignInAccount";
    private static final String TAG = LoginActivity.class.getSimpleName();


    private SignInButton bt_signIn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //----------- START initialize widgets--------------

        bt_signIn = (SignInButton) findViewById(R.id.bt_sign_in);
        //-----------END initialize widgets--------------



        bt_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.bt_sign_in:
                        signIn();
                        break;
                    // ...
                }
            }
        });



    }


    public void onStart() {
        super.onStart();


    }

    @Override
    protected void handleSignInResult(GoogleSignInResult result) {
        super.handleSignInResult(result);

        //protected void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
             GoogleSignInAccount acct = result.getSignInAccount();

            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(PARCEL_GOOGLE_SIGN_IN_ACCOUNT, acct);
            startActivity(intent);

        }else{
            Log.d(TAG, "handleSignInResult: nicht erfolgreich eingeloggt");
            Snackbar.make(findViewById(R.id.bt_sign_in), "LogIn failed! Please try again", Snackbar.LENGTH_LONG).show();
            //nicht erfolgreich eingelogt!
        }

    }







}

