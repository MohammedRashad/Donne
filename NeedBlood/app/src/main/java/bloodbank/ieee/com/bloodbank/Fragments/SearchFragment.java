package bloodbank.ieee.com.bloodbank.Fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import bloodbank.ieee.com.bloodbank.Main.MainActivity;
import bloodbank.ieee.com.bloodbank.R;
import bloodbank.ieee.com.bloodbank.SearchFiles.DataAdapter;
import bloodbank.ieee.com.bloodbank.SearchFiles.DataObject;
import bloodbank.ieee.com.bloodbank.SearchFiles.DividerItemDecoration;

/**
 * .
 * Created by rashad on 5/31/16.
 * .
 */

public class SearchFragment extends Fragment {

    int code = 0;

    View root;
    DataObject obj;
    InputStream is;
    StringBuilder sb;
    JSONArray peoples;
    BufferedReader reader;
    ProgressDialog dialog;
    SharedPreferences sse;
    JSONObject json_data, c;
    RecyclerView mRecyclerView;
    ArrayList<DataObject> results;
    String country, blood, getAll, result, line, data;


    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.ItemDecoration itemDecoration;
    SwipeRefreshLayout mSwipeRefreshLayout;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.search, container, false);

        results = new ArrayList<>();

        loadPreferences();

        new getDonors(getActivity()).execute();


        mSwipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.swipeRefreshLayout);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items

                new Refresh().execute();
            }
        });


        mRecyclerView = (RecyclerView) root.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        return root;

    }


    //////////////////////Methods////////////////////Methods//////////////////////////////////


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


    public void loadPreferences() {

        sse = PreferenceManager.getDefaultSharedPreferences(getActivity());
        blood = sse.getString("searchBlood", "bla");
        country = sse.getString("searchCountry", "bla");
        getAll = sse.getString("getAll", "false");

        Log.e("1", blood);
        Log.e("1", getAll);
        Log.e("1", country);

    }


    public void selectDonors() {

        if (getAll.equals("true")) {

            Log.e("1", "all");

            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("getAll", getAll));
            nameValuePairs.add(new BasicNameValuePair("blood", ""));
            nameValuePairs.add(new BasicNameValuePair("country", ""));
            setupPHPConnection(nameValuePairs, "http://ieee-scu.org/donor.php");

        } else {

            Log.e("1", "some");

            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("getAll", getAll));
            nameValuePairs.add(new BasicNameValuePair("blood", blood));
            nameValuePairs.add(new BasicNameValuePair("country", country));
            setupPHPConnection(nameValuePairs, "http://ieee-scu.org/donor.php");

        }

        parseJSON();
    }


    public void parseJSON() {

        try {


            json_data = new JSONObject(getResponse());
            peoples = json_data.getJSONArray("result");

            Log.d("sgfs", peoples.length() + "");
            for (int i = 0; i < peoples.length(); i++) {

                c = peoples.getJSONObject(i);

                obj = new DataObject(c.getString("name")
                        , "Email : " + c.getString("email")
                        , "Facebook : " + c.getString("facebook")
                        , "Number : " + c.getString("number")
                        , "Blood Type : " + c.getString("blood"));


                results.add(i, obj);
                code = Integer.parseInt(c.getString("result"));

                Log.d("sgfs", "Inside for");

            }

            MainActivity.models = results;

            Log.d("uyg", "" + results.size());

            if (code == 1) {

                Log.e("pass 3", result + "");


            } else {

                Log.e("Fail 3", code + "");
                Log.e("Fail 3", result + "");


            }

        } catch (Exception e) {

            Log.e("Fail 3", e.toString());
            Log.e("Fail 3", result + "");

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


    //////////////////////AsyncTask////////////////////AsyncTask//////////////////////////////////

    public class getDonors extends AsyncTask<Void, Void, Void> {


        Activity mContex;


        public getDonors(Activity c) {

            this.mContex = c;
        }


        @Override
        protected void onPreExecute() {


            dialog = ProgressDialog.show(this.mContex, "Getting data", "Loading, Please wait...", true);
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();

            if (code == 1) {

                Toast.makeText(this.mContex , "Data Received Successfully" , Toast.LENGTH_LONG).show();
                Log.d("NeedBlood", "Data Received Successfully");

                mAdapter = new DataAdapter(MainActivity.models);
                mRecyclerView.setAdapter(mAdapter);

                itemDecoration = new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL);
                mRecyclerView.addItemDecoration(itemDecoration);

                ((DataAdapter) mAdapter).setOnItemClickListener(new DataAdapter.MyClickListener() {
                    @Override
                    public void onItemClick(int position, View v) {

                        Log.i("fhdf", " Clicked on Item " + position);

                        final String[] itemsList = {"Copy Donor Number", "Copy Donor Facebook Profile", "Copy Donor email"};

                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("Donor Options");
                        builder.setItems(itemsList, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item) {

                                obj = MainActivity.models.get(item);

                                if (item == 0) {

                                    ClipboardManager clipMan = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);

                                    data = "0" + (obj.getmText4()).replace("Number : ", "");

                                    android.content.ClipData clip = android.content.ClipData.newPlainText("label", data);

                                    clipMan.setPrimaryClip(clip);

                                    Toast.makeText(getActivity(), "Copied!", Toast.LENGTH_SHORT).show();


                                } else if (item == 1) {

                                    ClipboardManager clipMan = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);

                                    data = (obj.getmText3()).replace("Facebook : ", "");

                                    android.content.ClipData clip = android.content.ClipData.newPlainText("label", data);

                                    clipMan.setPrimaryClip(clip);

                                    Toast.makeText(getActivity(), "Copied!", Toast.LENGTH_SHORT).show();


                                } else if (item == 2) {

                                    ClipboardManager clipMan = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);

                                    data = (obj.getmText2()).replace("Email : ", "");

                                    android.content.ClipData clip = android.content.ClipData.newPlainText("label", data);

                                    clipMan.setPrimaryClip(clip);

                                    Toast.makeText(getActivity(), "Copied!", Toast.LENGTH_SHORT).show();


                                }


                            }
                        });


                        AlertDialog alert = builder.create();
                        alert.show();


                    }

                });

            } else {

                Log.d("NeedBlood", "Error..!");

            }


        }

        @Override
        protected Void doInBackground(Void... params) {

            selectDonors();
            return null;

        }
    }


    public class Refresh extends AsyncTask<Void, Void, Void> {




        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            MainActivity.models.clear();

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            mSwipeRefreshLayout.setRefreshing(false);

            if (code == 1) {

                Log.d("NeedBlood", "Data Received Successfully");


                mAdapter = new DataAdapter(MainActivity.models);
                mRecyclerView.setAdapter(mAdapter);

                itemDecoration = new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL);
                mRecyclerView.addItemDecoration(itemDecoration);

                ((DataAdapter) mAdapter).setOnItemClickListener(new DataAdapter.MyClickListener() {
                    @Override
                    public void onItemClick(int position, View v) {

                        Log.i("fhdf", " Clicked on Item " + position);

                        final String[] itemsList = {"Copy Donor Number", "Copy Donor Facebook Profile", "Copy Donor email"};

                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("Donor Options");
                        builder.setItems(itemsList, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item) {

                                obj = MainActivity.models.get(item);

                                if (item == 0) {

                                    ClipboardManager clipMan = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);

                                    data = "0" + (obj.getmText4()).replace("Number : ", "");

                                    android.content.ClipData clip = android.content.ClipData.newPlainText("label", data);

                                    clipMan.setPrimaryClip(clip);

                                    Toast.makeText(getActivity(), "Copied!", Toast.LENGTH_SHORT).show();


                                } else if (item == 1) {

                                    ClipboardManager clipMan = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);

                                    data = (obj.getmText3()).replace("Facebook : ", "");

                                    android.content.ClipData clip = android.content.ClipData.newPlainText("label", data);

                                    clipMan.setPrimaryClip(clip);

                                    Toast.makeText(getActivity(), "Copied!", Toast.LENGTH_SHORT).show();


                                } else if (item == 2) {

                                    ClipboardManager clipMan = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);

                                    data = (obj.getmText2()).replace("Email : ", "");

                                    android.content.ClipData clip = android.content.ClipData.newPlainText("label", data);

                                    clipMan.setPrimaryClip(clip);

                                    Toast.makeText(getActivity(), "Copied!", Toast.LENGTH_SHORT).show();


                                }


                            }
                        });


                        AlertDialog alert = builder.create();
                        alert.show();


                    }

                });

            } else {

                Log.d("NeedBlood", "Error..!");

            }


        }

        @Override
        protected Void doInBackground(Void... params) {

            selectDonors();
            return null;

        }
    }

}