package bloodbank.ieee.com.bloodbank.Main;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;

import bloodbank.ieee.com.bloodbank.Fragments.AboutFragment;
import bloodbank.ieee.com.bloodbank.Fragments.HomeFragment;
import bloodbank.ieee.com.bloodbank.Fragments.LearnFragment;
import bloodbank.ieee.com.bloodbank.Fragments.SpreadFragment;
import bloodbank.ieee.com.bloodbank.NeedBlood.GetBloodFragment;
import bloodbank.ieee.com.bloodbank.NeedBlood.ProfileFragment;
import bloodbank.ieee.com.bloodbank.NeedBlood.RegisterWelcomeFragment;
import bloodbank.ieee.com.bloodbank.NeedBlood.ScheduleFragment;
import bloodbank.ieee.com.bloodbank.QuizFragments.TakeQuizFragment;
import bloodbank.ieee.com.bloodbank.R;
import bloodbank.ieee.com.bloodbank.SearchFiles.DataObject;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    int id;

    String logged;
    Toolbar toolbar;
    DrawerLayout drawer;
    Fragment newFragment;
    SharedPreferences sse;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    FragmentTransaction fragmentTransaction;
    public static ArrayList<DataObject> models;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        models = new ArrayList<>();

        setupToolbar();
        setupNavigationActivity();
        loadPreferences();

        newFragment = new HomeFragment();
        setCurrentFragment(newFragment, "NeedBlood");


    }


    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {

            drawer.closeDrawer(GravityCompat.START);

        } else {

            super.onBackPressed();

        }

    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        id = item.getItemId();
        navigationView.getMenu().findItem(R.id.home).setChecked(false);


        switch (id) {

            case R.id.about:

                newFragment = new AboutFragment();
                setCurrentFragment(newFragment, "About");

                break;

            case R.id.home:

                newFragment = new HomeFragment();
                setCurrentFragment(newFragment, "NeedBlood");

                break;

            case R.id.learn:

                newFragment = new LearnFragment();
                setCurrentFragment(newFragment, "Learn");

                break;

            case R.id.spread:

                newFragment = new SpreadFragment();
                setCurrentFragment(newFragment, "Spread The Word");

                break;

            case R.id.check:

                newFragment = new TakeQuizFragment();
                setCurrentFragment(newFragment, "Check Eligibility");

                break;

            case R.id.donor:

                if (logged.equals("1")) {

                    newFragment = new ProfileFragment();
                    setCurrentFragment(newFragment, "NeedBlood Profile");

                } else {

                    newFragment = new RegisterWelcomeFragment();
                    setCurrentFragment(newFragment, "Register/Login");

                }

                break;

            case R.id.get:

                newFragment = new GetBloodFragment();
                setCurrentFragment(newFragment, "Search for donors");

                break;

            case R.id.bloodsched:

                newFragment = new ScheduleFragment();
                setCurrentFragment(newFragment, "Donation Schedule");

                break;


        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }


    public void setCurrentFragment(Fragment newFragment, String title) {

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainFrame, newFragment);
        fragmentTransaction.commit();

        toolbar.setTitle(title);

    }



    public void loadPreferences() {

        sse = PreferenceManager.getDefaultSharedPreferences(this);
        logged = sse.getString("code", "0");

        if (logged.equals("1")) {

            MenuItem nav_donor = navigationView.getMenu().findItem(R.id.donor);
            nav_donor.setTitle("NeedBlood Profile");

        } else {

            MenuItem nav_donor = navigationView.getMenu().findItem(R.id.donor);
            nav_donor.setTitle("Join NeedBlood");

        }

    }


    public void setupNavigationActivity() {

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().findItem(R.id.home).setChecked(true);

    }


    public void setupToolbar() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

}