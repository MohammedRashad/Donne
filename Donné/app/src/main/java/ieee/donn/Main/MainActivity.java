package ieee.donn.Main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import cn.pedant.SweetAlert.SweetAlertDialog;
import ieee.donn.Fragments.AboutFragment;
import ieee.donn.Fragments.HomeFragment;
import ieee.donn.Fragments.LearnFragment;
import ieee.donn.Fragments.SpreadFragment;
import ieee.donn.NeedBlood.GetBloodFragment;
import ieee.donn.NeedBlood.ProfileFragment;
import ieee.donn.NeedBlood.ScheduleFragment;
import ieee.donn.QuizFragments.TakeQuizFragment;
import ieee.donn.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    int id;

    String mUserId;
    Toolbar toolbar;
    MenuItem nav_donor;
    DrawerLayout drawer;
    FirebaseUser mFirebaseUser;
    FirebaseAuth mFirebaseAuth;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupToolbar();
        setupNavigationActivity();
        loadPreferences();

        if (getIntent().getExtras() != null) {

            for (String key : getIntent().getExtras().keySet()) {

                String value = getIntent().getExtras().getString(key);

                if (key.equals("AnotherActivity")) {

                    Toast.makeText(this, value, Toast.LENGTH_LONG).show();

                }

            }
        }

        setCurrentFragment(new HomeFragment(), "Donné");

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        if (mFirebaseUser == null) {

            startActivity(new Intent(this, LoginSignUp.class));
            finish();

        } else {

            mUserId = mFirebaseUser.getUid();

        }

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


                setCurrentFragment(new ProfileFragment(), "Donné Profile");


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

        nav_donor = navigationView.getMenu().findItem(R.id.donor);
        nav_donor.setTitle("Donné Profile");

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
