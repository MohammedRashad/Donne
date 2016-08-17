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

import com.ieee.donne.R;

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
import bloodbank.ieee.com.bloodbank.SearchFiles.DataObject;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    int id;


    String logged;
    Toolbar toolbar;
    MenuItem nav_donor;
    DrawerLayout drawer;
    SharedPreferences sse;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    FragmentTransaction fragmentTransaction;
    public static ArrayList<DataObject> models;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupToolbar();
        setupNavigationActivity();
        loadPreferences();


        setCurrentFragment(new HomeFragment(), "NeedBlood");

    }


    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {

            drawer.closeDrawer(GravityCompat.START);


        } else if (getSupportFragmentManager().getBackStackEntryCount() > 0) {

            if (getSupportFragmentManager().findFragmentById(R.id.mainFrame) instanceof HomeFragment) {


                new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Exit ?")
                        .setCancelText("No")
                        .setConfirmText("Yes")
                        .showCancelButton(true)
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {

                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {

                                finish();
                            }
                        }).show();

            } else {
                getSupportFragmentManager().popBackStack();
            }
        }

    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        id = item.getItemId();
        navigationView.getMenu().findItem(R.id.home).setChecked(false);


        switch (id) {

            case R.id.about:

                setCurrentFragment(new AboutFragment(), "About");

                break;

            case R.id.home:

                setCurrentFragment(new HomeFragment(), "NeedBlood");

                break;

            case R.id.learn:

                setCurrentFragment(new LearnFragment(), "Learn");

                break;

            case R.id.spread:

                setCurrentFragment(new SpreadFragment(), "Spread The Word");

                break;

            case R.id.check:

                setCurrentFragment(new TakeQuizFragment(), "Check Eligibility");

                break;

            case R.id.donor:

                if (logged.equals("1")) {

                    setCurrentFragment(new ProfileFragment(), "Donné Profile");

                } else {

                    setCurrentFragment(new RegisterWelcomeFragment(), "Register/Login");

                }

                break;

            case R.id.get:

                setCurrentFragment(new GetBloodFragment(), "Search for donors");

                break;

            case R.id.bloodsched:

                setCurrentFragment(new ScheduleFragment(), "Donation Schedule");

                break;


        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }


    public void setCurrentFragment(Fragment newFragment, String title) {

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        fragmentTransaction.replace(R.id.mainFrame, newFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        toolbar.setTitle(title);

    }


    public void loadPreferences() {

        sse = PreferenceManager.getDefaultSharedPreferences(this);
        logged = sse.getString("code", "0");

        if (logged.equals("1")) {

            nav_donor = navigationView.getMenu().findItem(R.id.donor);
            nav_donor.setTitle("Donné Profile");

        } else {

            nav_donor = navigationView.getMenu().findItem(R.id.donor);
            nav_donor.setTitle("Join Donné");

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

        toolbar.setTitle("Donné");

    }

}
