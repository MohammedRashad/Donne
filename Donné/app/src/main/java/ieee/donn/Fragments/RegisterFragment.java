package ieee.donn.Fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import dmax.dialog.SpotsDialog;
import ieee.donn.Main.MainActivity;
import ieee.donn.R;

/**
 * Created by rashad on 5/31/16.
 * .
 */
public class RegisterFragment extends Fragment {

    View root;
    Toolbar toolbar;
    Locale[] locale;
    Spinner spinner;
    Button register;
    String country;
    SharedPreferences spf;
    SharedPreferences.Editor edit;
    ArrayList<String> countries;
    ArrayAdapter<String> adapter;
    EditText name, email, phone, blood, facebook, password;
    String nameStr, emailStr, phoneStr, bloodStr, facebookStr, passwordStr;

    String mUserId;
    FirebaseAuth mFirebaseAuth;
    DatabaseReference mDatabase;
    FirebaseUser mFirebaseUser;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.register, container, false);
        register = (Button) root.findViewById(R.id.reg);
        name = (EditText) root.findViewById(R.id.editText);
        email = (EditText) root.findViewById(R.id.editText2);
        password = (EditText) root.findViewById(R.id.editText7);
        phone = (EditText) root.findViewById(R.id.editText3);
        facebook = (EditText) root.findViewById(R.id.editText5);
        blood = (EditText) root.findViewById(R.id.editText4);
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


                } else {


                    final AlertDialog dialog = new SpotsDialog(getActivity(), "Loginn' in..");
                    dialog.show();

                    mFirebaseAuth = FirebaseAuth.getInstance();

                    mFirebaseAuth.createUserWithEmailAndPassword(emailStr, passwordStr)
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {

                                        mFirebaseAuth = FirebaseAuth.getInstance();
                                        mFirebaseUser = mFirebaseAuth.getCurrentUser();
                                        mUserId = mFirebaseUser.getUid();
                                        mDatabase = FirebaseDatabase.getInstance().getReference();

                                        mDatabase.child("users").child(mUserId).child("data").child("email").setValue((email.getText().toString()));
                                        mDatabase.child("users").child(mUserId).child("data").child("name").setValue((name.getText().toString()));
                                        mDatabase.child("users").child(mUserId).child("data").child("phone").setValue((phone.getText().toString()));
                                        mDatabase.child("users").child(mUserId).child("data").child("password").setValue((password.getText().toString()));
                                        mDatabase.child("users").child(mUserId).child("data").child("facebook").setValue((facebook.getText().toString()));
                                        mDatabase.child("users").child(mUserId).child("data").child("blood").setValue((blood.getText().toString().toUpperCase()));
                                        mDatabase.child("users").child(mUserId).child("data").child("city").setValue((country));


                                        save("email" , emailStr);
                                        save("phone", phoneStr);
                                        save("facebook", facebookStr);
                                        save("name", nameStr);


                                        mFirebaseAuth.signInWithEmailAndPassword(emailStr, passwordStr)
                                                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                                        if (task.isSuccessful()) {

                                                            FirebaseMessaging.getInstance().subscribeToTopic("Ap");
                                                            FirebaseMessaging.getInstance().subscribeToTopic("Am");
                                                            FirebaseMessaging.getInstance().subscribeToTopic("Bp");
                                                            FirebaseMessaging.getInstance().subscribeToTopic("Bm");
                                                            FirebaseMessaging.getInstance().subscribeToTopic("Op");
                                                            FirebaseMessaging.getInstance().subscribeToTopic("Om");
                                                            FirebaseMessaging.getInstance().subscribeToTopic("ABp");
                                                            FirebaseMessaging.getInstance().subscribeToTopic("ABm");


                                                            Log.d("AndroidBash", "Subscribed");
                                                            Toast.makeText(getActivity(), "Subscribed", Toast.LENGTH_SHORT).show();
                                                            String token = FirebaseInstanceId.getInstance().getToken();
                                                            Log.d("AndroidBash", token);


                                                            dialog.dismiss();
                                                            startActivity(new Intent(getActivity(), MainActivity.class));
                                                            getActivity().finish();
                                                            save("code", "1");

                                                        } else {

                                                            new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                                                                    .setTitleText("Error")
                                                                    .setContentText(task.getException().getMessage())
                                                                    .setConfirmText("Retry")
                                                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                                        @Override
                                                                        public void onClick(SweetAlertDialog sDialog) {
                                                                            sDialog.dismissWithAnimation();
                                                                        }
                                                                    })
                                                                    .show();

                                                        }
                                                    }
                                                });


                                    } else {

                                        new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                                                .setTitleText("Error")
                                                .setContentText(task.getException().getMessage())
                                                .setConfirmText("Retry")
                                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                    @Override
                                                    public void onClick(SweetAlertDialog sDialog) {
                                                        sDialog.dismissWithAnimation();
                                                    }
                                                })
                                                .show();
                                    }

                                }
                            });

                }
            }
        });


        return root;


    }


    ////////Methods/////////    ////////Methods/////////    ////////Methods////////

    public void setupSpinner() {

        setupCountriesList();

        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, countries);
        spinner.setAdapter(adapter);


    }

    public void setupCountriesList() {

        locale = Locale.getAvailableLocales();
        countries = new ArrayList<String>();

        countries.add("Select City");
        countries.add("Cairo");
        countries.add("Alexandria");
        countries.add("Giza");
        countries.add("Shubra El Kheima");
        countries.add("Port Said");
        countries.add("Suez");
        countries.add("El Mahala El Kubra");
        countries.add("Luxor");
        countries.add("Mansoura");
        countries.add("Tanta");
        countries.add("Assiut");
        countries.add("Ismailia");
        countries.add("Fayoum");
        countries.add("Zagazig");
        countries.add("Damietta");
        countries.add("Aswan");
        countries.add("Minya");
        countries.add("Damanhour");
        countries.add("Beni Suef");
        countries.add("Hurghada");
        countries.add("Qena");
        countries.add("Sohag");
        countries.add("Shibin El Kom");
        countries.add("Banha");
        countries.add("Arish");

    }

    public void save(String key, String value) {

        spf = PreferenceManager.getDefaultSharedPreferences(getActivity());
        edit = spf.edit();
        edit.putString(key, value);
        edit.commit();

    }

}



