package com.boox.kevinwetzel.boox;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;


public class MainActivity extends BaseCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{



    private static final String TAG = "MainActivity";
  //  private GoogleSignInAccount acct;
    private TextView tv_google_user_name;
    private TextView tv_google_user_mail;
    private  GoogleSignInAccount acct;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



       // Bundle bundle  =  getIntent().getParcelableExtra(LoginActivity.PARCEL_GOOGLE_SIGN_IN_ACCOUNT);
       //‚ acct = bundle.getParcelable(LoginActivity.PARCEL_GOOGLE_SIGN_IN_ACCOUNT);
        acct = getIntent().getParcelableExtra(LoginActivity.PARCEL_GOOGLE_SIGN_IN_ACCOUNT);

        Log.d(TAG, "onCreate: "+ acct.getId());
        Log.d(TAG, "onCreate: "+ acct.getEmail());
        Log.d(TAG, "onCreate: "+ acct.getDisplayName());
        Log.d(TAG, "onCreate"+ acct.getPhotoUrl());



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        tv_google_user_name = (TextView) header.findViewById(R.id.tv_google_user_name);
        tv_google_user_mail = (TextView) header.findViewById(R.id.tv_google_user_mail);
        tv_google_user_mail.setText(acct.getEmail());
        tv_google_user_name.setText(acct.getDisplayName());


    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case R.id.action_sign_out: signOut(); break;
        }




        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onConnected(Bundle bundle) {

        if (Plus.PeopleApi.getCurrentPerson(getmGoogleApiClient()) != null) {
            Person currentPerson = Plus.PeopleApi.getCurrentPerson(getmGoogleApiClient());
            String personName = currentPerson.getDisplayName();
            String personPhoto = currentPerson.getImage().getUrl();
            String personGooglePlusProfile = currentPerson.getUrl();
            Person.Cover.CoverPhoto personBackground = currentPerson.getCover().getCoverPhoto();
            String background = personBackground.getUrl();

            Log.d(TAG, "onConnected: "+ personName);
            Log.d(TAG, "onConnected: "+ personPhoto);
            Log.d(TAG, "onConnected: "+ personGooglePlusProfile);
            Log.d(TAG, "onConnected: "+ background);

        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
