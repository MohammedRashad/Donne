package bloodbank.ieee.com.bloodbank.Fragments;

import android.app.Activity;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ieee.donne.R;

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

import bloodbank.ieee.com.bloodbank.NeedBlood.ProfileFragment;
import dmax.dialog.SpotsDialog;

/**
 * Created by rashad on 7/15/16.
 */


public class LoginFragment extends Fragment {

    int code = 0;

    InputStream is;
    Toolbar toolbar;
    StringBuilder sb;
    Button login;
    JSONArray peoples;
    SpotsDialog dialog;
    SharedPreferences spf;
    BufferedReader reader;
    JSONObject json_data, c;
    Fragment currentFragment;
    String result, country, line;
    SharedPreferences.Editor edit;
    FragmentTransaction fragmentTransaction;
    EditText emailLogin, passLogin;
    String nameStr, emailStr, phoneStr, bloodStr, facebookStr, passwordStr, emailLoginStr, passLoginStr;

    View root;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.login, container, false);

        login = (Button) root.findViewById(R.id.reg1);
        emailLogin = (EditText) root.findViewById(R.id.editText21);
        passLogin = (EditText) root.findViewById(R.id.editText6);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                emailLoginStr = emailLogin.getText().toString();
                passLoginStr = passLogin.getText().toString();

                if (emailLoginStr.isEmpty() || passLoginStr.isEmpty()) {

                    Toast.makeText(getActivity(), "Fill all data please..", Toast.LENGTH_LONG).show();


                } else {
                    new Login(getActivity()).execute();

                }
            }
        });


        return root;

    }


    public class Login extends AsyncTask<Void, Void, Void> {


        Activity mContex;


        public Login(Activity c) {

            this.mContex = c;
        }

        @Override
        protected void onPreExecute() {


            dialog = new SpotsDialog(this.mContex, R.style.Custom);
            dialog.show();



            super.onPreExecute();
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();


            if (code == 1) {

                Toast.makeText(this.mContex, "Logged in Successfully", Toast.LENGTH_LONG).show();

                Log.d("NeedBlood", "Logged in Successfully");


            } else {

                Toast.makeText(this.mContex, "Login failed", Toast.LENGTH_LONG).show();

                Log.d("NeedBlood", "Error..!");

            }

        }

        @Override
        protected Void doInBackground(Void... params) {
            loginToDatabase();
            return null;
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
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        fragmentTransaction.replace(R.id.mainFrame, newFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(title);

    }

    public void save(String key, String value) {

        spf = PreferenceManager.getDefaultSharedPreferences(getActivity());
        edit = spf.edit();
        edit.putString(key, value);
        edit.commit();

    }


}