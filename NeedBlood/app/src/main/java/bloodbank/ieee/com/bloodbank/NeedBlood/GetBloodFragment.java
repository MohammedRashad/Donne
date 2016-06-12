package bloodbank.ieee.com.bloodbank.NeedBlood;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Locale;

import bloodbank.ieee.com.bloodbank.Fragments.SearchFragment;
import bloodbank.ieee.com.bloodbank.R;

/**
 * Created by rashad on 6/8/16.
 */


public class GetBloodFragment extends Fragment {

    View root;
    Toolbar toolbar;
    Locale[] locale;
    Button search, all;
    String country, blood;
    SharedPreferences spf;
    Fragment currentFragment;
    ArrayList<String> countries;
    ArrayAdapter<String> adapter;
    SharedPreferences.Editor edit;
    Spinner countrySpinner, bloodSpinner;
    FragmentTransaction fragmentTransaction;
    String[] BloodList = {"Select Blood Type", "-------------------", "O-", "O+", "A+", "A-", "B+", "B-", "AB+" , "AB-"};


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.get, container, false);

        countrySpinner = (Spinner) root.findViewById(R.id.spinner2);
        bloodSpinner = (Spinner) root.findViewById(R.id.spinner3);
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        search = (Button) root.findViewById(R.id.search);
        all = (Button) root.findViewById(R.id.all);

        setupCountriesList();
        setupSpinner();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                save("searchBlood", blood);
                save("searchCountry", country);
                save("getAll", "false");

                Log.e("1", blood);
                Log.e("1", country);
                currentFragment = new SearchFragment();
                setCurrentFragment(currentFragment, "Search Donors");

            }
        });


        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                save("getAll", "true");

                currentFragment = new SearchFragment();
                setCurrentFragment(currentFragment, "All Donors");

            }
        });


        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                int position = countrySpinner.getSelectedItemPosition();

                if (!(position == 0) || !(position == 1)) {

                    country = countries.get(position);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }


        });


        bloodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                int position = bloodSpinner.getSelectedItemPosition();

                if (!(position == 0) || !(position == 1)) {

                    blood = BloodList[position];

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }


        });


        return root;
    }


    public void setCurrentFragment(Fragment newFragment, String title) {

        fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainFrame, newFragment);
        fragmentTransaction.commit();

        toolbar.setTitle(title);

    }


    public void setupSpinner() {

        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, countries);
        countrySpinner.setAdapter(adapter);


        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, BloodList);
        bloodSpinner.setAdapter(adapter);

    }


    public void save(String key, String value) {

        spf = PreferenceManager.getDefaultSharedPreferences(getActivity());
        edit = spf.edit();
        edit.putString(key, value);
        edit.commit();

    }


    public void setupCountriesList() {

        locale = Locale.getAvailableLocales();
        countries = new ArrayList<String>();

        countries.add("Select Country");
        countries.add("--------------------");

        for (Locale loc : locale) {

            country = loc.getDisplayCountry();

            if ((country.length() > 0) && (!countries.contains(country))) {

                countries.add(country);

            }

        }

    }


}