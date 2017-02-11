package ieee.donn.NeedBlood;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import ieee.donn.R;

import ieee.donn.Fragments.LoginFragment;
import ieee.donn.Fragments.RegisterFragment;

/**
 * Created by rashad on 5/31/16.
 */
public class RegisterWelcomeFragment extends Fragment {

    TextView register;
    Button login;
    FragmentTransaction fragmentTransaction;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.registerwelocme, container, false);

        register = (TextView) root.findViewById(R.id.reg);
        login = (Button) root.findViewById(R.id.login);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setCurrentFragment(new RegisterFragment(), "Sign Up");

            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setCurrentFragment(new LoginFragment(), "Login");

            }
        });


        register.setPaintFlags(login.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        register.setText("Don't Have an account? Click to sign up");

        return root;
    }

    public void setCurrentFragment(Fragment newFragment, String title) {

        fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        fragmentTransaction.replace(R.id.mainFrame, newFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();


    }
}