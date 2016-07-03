package com.boox.kevinwetzel.boox;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.HeaderViewListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.boox.kevinwetzel.boox.asyncTasks.BooksGetBookshelvesAsync;
import com.boox.kevinwetzel.boox.asyncTasks.BooksGetVolumesFromBookshelfAsync;
import com.boox.kevinwetzel.boox.asyncTasks.DownloadImageAsyncTaskLinearLayout;
import com.boox.kevinwetzel.boox.asyncTasks.DownloadImageAsynctaskImageViewRound;
import com.boox.kevinwetzel.boox.dao.BookshelvesDAO;
import com.boox.kevinwetzel.boox.dao.BookshelvesVolumesAssocDAO;
import com.boox.kevinwetzel.boox.dao.VolumesDAO;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.books.model.Bookshelf;

import java.util.List;


public class MainActivity extends BaseCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{



    private static final String TAG = "MainActivity";

    private TextView tv_google_user_name;
    private TextView tv_google_user_mail;
    private  GoogleSignInAccount acct;
    private RequestQueue mQueue;
    private NavigationView navigationView;
    private BookshelvesDAO bookshelvesDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       bookshelvesDAO = new BookshelvesDAO(this);
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
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
            @Override
            public void onDrawerClosed(View drawerView) {
                Log.d(TAG, "onDrawerClosed");
                super.onDrawerClosed(drawerView);
                fillNavigationDrawer();
            }

        };

        drawer.setDrawerListener(toggle);
        toggle.syncState();



        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        fillNavigationDrawer();

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
            case R.id.action_get_volumes: getVolumesTest();break;
            case R.id.action_saveVolumesFromBookshelves: saveVolumesFromBookshelvesTest();break;
        }

        return super.onOptionsItemSelected(item);
    }




    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        //id for handling navigation drawer


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



          //new BooksFullSearchAsync(JacksonFactory.getDefaultInstance(), "Harry Potter").execute();
           new BooksGetBookshelvesAsync(JacksonFactory.getDefaultInstance(), super.getAccess_token(), this).execute();




        }
    }

    // TODO: 03.07.2016  just for testing -->delete method when threads synchronized
    private void getVolumesTest() {
        bookshelvesDAO.open();
        for (Bookshelf shelf:bookshelvesDAO.getAllBookshelves()) {

            BookshelvesVolumesAssocDAO bookshelvesVolumesAssocDAO = new BookshelvesVolumesAssocDAO(this);
            bookshelvesVolumesAssocDAO.open();
            VolumesDAO volumesDAO = new VolumesDAO(this);
            volumesDAO.open();
            volumesDAO.searchVolume(bookshelvesVolumesAssocDAO.getAllVolumeIDsForBookshelf(shelf.getId()));
            volumesDAO.close();
            bookshelvesVolumesAssocDAO.close();
        }
        bookshelvesDAO.close();
    }
    // TODO: 03.07.2016  just for testing -->delete method when threads synchronized
    private void saveVolumesFromBookshelvesTest(){
        bookshelvesDAO.open();
        for (Bookshelf shelf:bookshelvesDAO.getAllBookshelves()) {
            new BooksGetVolumesFromBookshelfAsync(JacksonFactory.getDefaultInstance(),super.getAccess_token(), this).execute(shelf.getId());
        }
        bookshelvesDAO.close();

    }
    
    public void fillNavigationDrawer(){
        bookshelvesDAO.open();
        List<Bookshelf> bookshelvesList = bookshelvesDAO.getAllBookshelves();
        bookshelvesDAO.close();

        if (bookshelvesList.size() >0) {
            navigationView.getMenu().clear();

            Menu menu = navigationView.getMenu();
            SubMenu subMenu = menu.addSubMenu("Bookshelves");
            Log.d(TAG, "Adding Nav Drawer Items");

            for (Bookshelf bookshelf : bookshelvesList) {
               subMenu.add(R.id.group2, bookshelf.getId(), Menu.NONE, bookshelf.getTitle() );
            }
            refreshNavigationView();
        }
}


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        finish();

    }

    private void refreshNavigationView(){
        for (int i = 0, count = navigationView.getChildCount(); i < count; i++) {
            final View child = navigationView.getChildAt(i);
            if (child != null && child instanceof ListView) {
                final ListView menuView = (ListView) child;
                final HeaderViewListAdapter adapter = (HeaderViewListAdapter) menuView.getAdapter();
                final BaseAdapter wrapped = (BaseAdapter) adapter.getWrappedAdapter();
                wrapped.notifyDataSetChanged();
            }
        }
    }
}
