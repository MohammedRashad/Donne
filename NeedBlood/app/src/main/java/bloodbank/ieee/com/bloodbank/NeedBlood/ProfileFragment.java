package bloodbank.ieee.com.bloodbank.NeedBlood;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import bloodbank.ieee.com.bloodbank.R;

/**
 * .
 * Created by rashad on 5/31/16.
 * .
 */


public class ProfileFragment extends Fragment {

    int code = 0;
    StringBuilder sb;
    BufferedReader reader;
    JSONObject json_data, c, peoples;
    InputStream is;
    SharedPreferences sse;
    Button logout, delete;
    String nameStr, emailStr, phoneStr, bloodStr, facebookStr, countryStr, result, line;
    TextView name, email, phone, blood, facebook, country;
    ProgressDialog dialog;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.profile, container, false);
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("NeedBlood Profile");

        name = (TextView) root.findViewById(R.id.name);
        email = (TextView) root.findViewById(R.id.email);
        country = (TextView) root.findViewById(R.id.country);
        phone = (TextView) root.findViewById(R.id.phone);
        facebook = (TextView) root.findViewById(R.id.facebook);
        blood = (TextView) root.findViewById(R.id.blood);

        logout = (Button) root.findViewById(R.id.logout);
        delete = (Button) root.findViewById(R.id.delete);

        sse = PreferenceManager.getDefaultSharedPreferences(getActivity());

        nameStr = sse.getString("name", "0");
        emailStr = sse.getString("email", "0");
        phoneStr = sse.getString("phone", "0");
        bloodStr = sse.getString("blood", "0");
        facebookStr = sse.getString("facebook", "0");
        countryStr = sse.getString("country", "Egypt");

        name.setText("Name : " + nameStr);
        email.setText("Email : " + emailStr);
        country.setText("Country : " + countryStr);
        phone.setText("Phone : 0" + phoneStr);
        facebook.setText("Facebook : " + facebookStr);
        blood.setText("Blood Type : " + bloodStr);


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                save("name", "0");
                save("email", "0");
                save("phone", "0");
                save("blood", "0");
                save("facebook", "0");
                save("country", "0");
                save("code", "0");

                getActivity().recreate();

            }
        });


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Delete().execute();

            }
        });

        return root;

    }


    public void save(String key, String value) {

        SharedPreferences spf = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor edit = spf.edit();
        edit.putString(key, value);
        edit.commit();

    }


    public class Delete extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {

            dialog = ProgressDialog.show(getActivity(), "Getting data", "Loading, Please wait...", true);
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();


            if (code == 1) {

                Log.d("NeedBlood", "Operation Successful");

                save("name", "0");
                save("email", "0");
                save("phone", "0");
                save("blood", "0");
                save("facebook", "0");
                save("country", "0");
                save("code", "0");

                getActivity().recreate();


            } else {

                Log.d("NeedBlood", "Error..!");

            }


        }

        @Override
        protected Void doInBackground(Void... params) {

            delete();
            return null;

        }
    }

    public void delete() {

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        nameValuePairs.add(new BasicNameValuePair("email", emailStr));

        setupPHPConnection(nameValuePairs, "http://ieee-scu.org/delete.php");

        parseJSON();

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


    public void parseJSON() {

        try {

            json_data = new JSONObject(getResponse());
            code = json_data.getInt("code");

            if (code == 1) {

                Log.e("pass 3", result + "");

            } else {

                Log.e("Fail 3", code + "");

            }

        } catch (Exception e) {

            Log.e("Fail 3", e.toString());

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

            Toast.makeText(getActivity(), "Invalid IP Address", Toast.LENGTH_LONG).show();

        }


    }


}
