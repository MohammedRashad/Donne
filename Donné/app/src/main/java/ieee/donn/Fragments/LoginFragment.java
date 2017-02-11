package ieee.donn.Fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import cn.pedant.SweetAlert.SweetAlertDialog;
import dmax.dialog.SpotsDialog;
import ieee.donn.Main.MainActivity;
import ieee.donn.R;

/**
 * Created by rashad on 7/15/16.
 * .
 */


public class LoginFragment extends Fragment {


    View root;
    Button login;
    SharedPreferences spf;
    FirebaseAuth mFirebaseAuth;
    SharedPreferences.Editor edit;
    EditText emailLogin, passLogin;
    String emailLoginStr, passLoginStr;
    FragmentTransaction fragmentTransaction;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.login, container, false);

        login = (Button) root.findViewById(R.id.reg1);
        emailLogin = (EditText) root.findViewById(R.id.editText21);
        passLogin = (EditText) root.findViewById(R.id.editText6);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog dialog = new SpotsDialog(getActivity(), "Signing in..");

                emailLoginStr = emailLogin.getText().toString();
                passLoginStr = passLogin.getText().toString();

                dialog.show();

                /**
                 *
                 * Here is where magic happens
                 *
                 */
                if (emailLoginStr.isEmpty() || passLoginStr.isEmpty()) {

                    Toast.makeText(getActivity(), "Fill all data please..", Toast.LENGTH_LONG).show();

                } else {

                    /**
                     *
                     * Start Signing in,
                     * if failed display an alert for this
                     * if succeeded  go to application itself
                     *
                     */
                    mFirebaseAuth = FirebaseAuth.getInstance();

                    mFirebaseAuth
                            .signInWithEmailAndPassword(emailLoginStr, passLoginStr)
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {

                                        dialog.dismiss();
                                        startActivity(new Intent(getActivity(), MainActivity.class));
                                        getActivity().finish();

                                    } else {

                                        dialog.dismiss();

                                        /**
                                         *
                                         * Displays alert dialog with the error
                                         * missing stuff, incorrect stuff
                                         *
                                         */
                                        new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                                                .setTitleText("Error")
                                                .setContentText(task.getException().getMessage())
                                                .setConfirmText("Retry")
                                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                    @Override
                                                    public void onClick(SweetAlertDialog sDialog) {

                                                        sDialog.dismissWithAnimation();

                                                    }
                                                }).show();

                                    }
                                }
                            });

                }
            }
        });


        return root;

    }


}