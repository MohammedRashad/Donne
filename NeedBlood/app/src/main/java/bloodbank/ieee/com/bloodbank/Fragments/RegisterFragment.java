package bloodbank.ieee.com.bloodbank.Fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Locale;

import bloodbank.ieee.com.bloodbank.NeedBlood.ProfileFragment;
import bloodbank.ieee.com.bloodbank.R;

/**
 * Created by rashad on 5/31/16.
 */
public class RegisterFragment extends Fragment {

    int code = 0;

    View root;
    InputStream is;
    Toolbar toolbar;
    Locale[] locale;
    Spinner spinner;
    StringBuilder sb;
    JSONArray peoples;
    ProgressDialog dialog;
    SharedPreferences spf;
    BufferedReader reader;
    Button register, login;
    JSONObject json_data, c;
    Fragment currentFragment;
    ArrayList<String> countries;
    ArrayAdapter<String> adapter;
    String result, country, line;
    SharedPreferences.Editor edit;
    FragmentTransaction fragmentTransaction;
    EditText name, email, phone, blood, facebook, password, emailLogin, passLogin;
    String nameStr, emailStr, phoneStr, bloodStr, facebookStr, passwordStr, emailLoginStr, passLoginStr;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.register, container, false);

        register = (Button) root.findViewById(R.id.reg);
        login = (Button) root.findViewById(R.id.reg1);

        name = (EditText) root.findViewById(R.id.editText);
        email = (EditText) root.findViewById(R.id.editText2);
        password = (EditText) root.findViewById(R.id.editText7);
        phone = (EditText) root.findViewById(R.id.editText3);
        facebook = (EditText) root.findViewById(R.id.editText5);
        blood = (EditText) root.findViewById(R.id.editText4);
        emailLogin = (EditText) root.findViewById(R.id.editText21);
        passLogin = (EditText) root.findViewById(R.id.editText6);

        spinner = (Spinner) root.findViewById(R.id.spinner);


        setupSpinner();


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                int position = spinner.getSelectedItemPosition();

                if (!(position == 0) || !(position == 1)) {

                    country = countries.get(position);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }


        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nameStr = name.getText().toString();
                emailStr = email.getText().toString();
                phoneStr = phone.getText().toString();
                bloodStr = blood.getText().toString();
                facebookStr = facebook.getText().toString();
                passwordStr = password.getText().toString();


                if (nameStr.isEmpty() || emailStr.isEmpty() ||
                        phoneStr.isEmpty() || bloodStr.isEmpty() ||
                        facebookStr.isEmpty() || passwordStr.isEmpty()) {

                    Toast.makeText(getActivity(), "Fill all data please..", Toast.LENGTH_LONG).show();


                }
                new Insert(getActivity()).execute();
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                emailLoginStr = emailLogin.getText().toString();
                passLoginStr = passLogin.getText().toString();

                if (emailLoginStr.isEmpty() || passLoginStr.isEmpty()) {

                    Toast.makeText(getActivity(), "Fill all data please..", Toast.LENGTH_LONG).show();


                }
                new Login(getActivity()).execute();
            }
        });


        return root;


    }


    ////////Methods/////////    ////////Methods/////////    ////////Methods////////


    public void setupSpinner() {

        setupCountriesList();

        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, countries);
        spinner.setAdapter(adapter);


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


    public void loginToDatabase() {

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        nameValuePairs.add(new BasicNameValuePair("password", passLoginStr));
        nameValuePairs.add(new BasicNameValuePair("email", emailLoginStr));

        setupPHPConnection(nameValuePairs, "http://ieee-scu.org/login.php");


        try {

            json_data = new JSONObject(getResponse());
            peoples = json_data.getJSONArray("result");
            c = peoples.getJSONObject(0);

            nameStr = c.getString("name");
            emailStr = c.getString("email");
            phoneStr = c.getString("number");
            bloodStr = c.getString("blood");
            facebookStr = c.getString("facebook");
            country = c.getString("city");
            code = Integer.parseInt(c.getString("result"));

            if (code == 1) {

                Log.e("pass 3", result + "");


                save("name", nameStr);
                save("email", emailStr);
                save("phone", phoneStr);
                save("blood", bloodStr);
                save("country", country);
                save("code", "" + code);
                save("facebook", facebookStr);
                save("password", passwordStr);

                currentFragment = new ProfileFragment();
                setCurrentFragment(currentFragment, "NeedBlood Profile");


            } else {

                Log.e("Fail 3", code + "");

            }

        } catch (Exception e) {

            Log.e("Fail 3", e.toString());
            Log.e("Fail 3", result + "");

        }

    }


    public void insertToDataBase() {


        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        nameValuePairs.add(new BasicNameValuePair("name", nameStr));
        nameValuePairs.add(new BasicNameValuePair("email", emailStr));
        nameValuePairs.add(new BasicNameValuePair("phone", phoneStr));
        nameValuePairs.add(new BasicNameValuePair("blood", bloodStr));
        nameValuePairs.add(new BasicNameValuePair("facebook", facebookStr));
        nameValuePairs.add(new BasicNameValuePair("password", passwordStr));
        nameValuePairs.add(new BasicNameValuePair("country", country));


        setupPHPConnection(nameValuePairs, "http://ieee-scu.org/insert.php");


        try {

            json_data = new JSONObject(getResponse());
            code = json_data.getInt("code");

            Log.e("xv", getResponse() + "");


            if (code == 1) {

                Log.e("pass 3", getResponse() + "");

                save("name", nameStr);
                save("email", emailStr);
                save("phone", phoneStr);
                save("blood", bloodStr);
                save("code", "" + code);
                save("country", country);
                save("facebook", facebookStr);
                save("password", passwordStr);


                currentFragment = new ProfileFragment();
                setCurrentFragment(currentFragment, "NeedBlood Profile");

            } else {

                Log.e("Fail 3", code + "");

            }

        } catch (Exception e) {

            Log.e("Fail 3", e.toString());

        }


    }


    public void save(String key, String value) {

        spf = PreferenceManager.getDefaultSharedPreferences(getActivity());
        edit = spf.edit();
        edit.putString(key, value);
        edit.commit();

    }


    public void setupPHPConnection(ArrayList<NameValuePair> nameValuePairs, String url) {

        try {

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();

            Log.e("pass 1", "connection success ");

        } catch (Exception e) {

            Log.e("Fail 1", e.toString());


        }


    }


    public String getResponse() {

        try {

            reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            sb = new StringBuilder();

            while ((line = reader.readLine()) != null) {

                sb.append(line + "\n");
            }

            is.close();
            result = sb.toString();

            Log.e("pass 2", "connection success ");

        } catch (Exception e) {

            Log.e("Fail 2", e.toString());

        }

        return result;
    }


    public void setCurrentFragment(Fragment newFragment, String title) {

        fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainFrame, newFragment);
        fragmentTransaction.commit();

        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(title);

    }


    ////AsyncTask Classes////   ////AsyncTask Classes////   ////AsyncTask Classes////


    public class Insert extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog;
        Activity mContex;


        public Insert(Activity c) {

            this.mContex = c;
        }

        @Override
        protected void onPreExecute() {

            dialog = ProgressDialog.show(this.mContex, "Sign Up", "Loading. Please wait...", true);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Toast.makeText(this.mContex , "Sign Up Successful" , Toast.LENGTH_LONG).show();

            dialog.dismiss();

        }

        @Override
        protected Void doInBackground(Void... params) {

            insertToDataBase();

            return null;
        }
    }


    public class Login extends AsyncTask<Void, Void, Void> {


        Activity mContex;


        public Login(Activity c) {

            this.mContex = c;
        }

        @Override
        protected void onPreExecute() {

            dialog = ProgressDialog.show(this.mContex, "Log in", "Loading. Please wait...", true);
            super.onPreExecute();
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();


            if (code == 1) {

                Toast.makeText(this.mContex , "Logged in Successfully" , Toast.LENGTH_LONG).show();

                Log.d("NeedBlood", "Logged in Successfully");


            } else {

                Toast.makeText(this.mContex , "Login failed" , Toast.LENGTH_LONG).show();

                Log.d("NeedBlood", "Error..!");

            }

        }

        @Override
        protected Void doInBackground(Void... params) {
            loginToDatabase();
            return null;
        }
    }


}



