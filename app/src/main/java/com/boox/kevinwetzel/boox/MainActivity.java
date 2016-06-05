package com.boox.kevinwetzel.boox;

import android.os.Bundle;
import android.util.Log;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.services.books.Books;

import org.json.JSONException;
import org.json.JSONObject;



public class MainActivity extends BaseCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{



    private static final String TAG = "MainActivity";

    private TextView tv_google_user_name;
    private TextView tv_google_user_mail;
    private  GoogleSignInAccount acct;
    private RequestQueue mQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


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
    protected void onStart() {
        super.onStart();
        mQueue = CustomVolleyRequestQueue.getInstance(this.getApplicationContext()).getRequestQueue();
    }

    @Override
    protected void onStop() {
        super.onStop();
        super.onStop();
        if (mQueue != null) {
            mQueue.cancelAll(TAG);
        }
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


            if (currentPerson.hasImage()){
                String personPhoto = currentPerson.getImage().getUrl();
                personPhoto = personPhoto.substring(0,personPhoto.length()-2) + "150"; //change the size of the picture to 150
                new DownloadImageAsynctaskImageViewRound((ImageView)findViewById(R.id.iv_google_user_pic)).execute(personPhoto);
            }
            if (currentPerson.hasCover()){
                Log.d(TAG, "onConnected: cover");
                Person.Cover.CoverPhoto personBackground = currentPerson.getCover().getCoverPhoto();
                String background = personBackground.getUrl();
                new DownloadImageAsyncTaskLinearLayout((LinearLayout) findViewById(R.id.ll_header)).execute(background);
            }
            if (currentPerson.hasDisplayName()){
                String personName = currentPerson.getDisplayName();
            }


            //String url = "https://www.googleapis.com/books/v1/volumes/zyTCAlFPjgYC?key="+ getString(R.string.books_api);
            String url ="https://www.googleapis.com/books/v1/mylibrary/bookshelves/0/volumes?access_token="+acct.getIdToken();

            CustomJSONObjectRequest jsonRequest = new CustomJSONObjectRequest(Request.Method.GET,
                    url, new JSONObject(), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        Log.d(TAG, "shelves"+ response.toString());
                        JSONObject vol = response.getJSONObject("volumeInfo");
                       // Log.d(TAG, "onResponse: selflink "+ response.get("selfLink"));

                      //  Log.d(TAG, "onResponse: title: "+ vol.getString("title"));
                       // Log.d(TAG, "Access token "+ acct.getIdToken());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }
                    ,acct.getIdToken());
            jsonRequest.setTag(TAG);

            mQueue.add(jsonRequest);


        }


       
       


    }
    


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
