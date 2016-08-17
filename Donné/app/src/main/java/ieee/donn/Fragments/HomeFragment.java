package bloodbank.ieee.com.bloodbank.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ieee.donne.R;

import bloodbank.ieee.com.bloodbank.NeedBlood.GetBloodFragment;
import bloodbank.ieee.com.bloodbank.NeedBlood.ProfileFragment;
import bloodbank.ieee.com.bloodbank.NeedBlood.RegisterWelcomeFragment;
import bloodbank.ieee.com.bloodbank.QuizFragments.TakeQuizFragment;


public class HomeFragment extends Fragment {

    CardView join,check,get;
    TextView joinText;
    FragmentTransaction fragmentTransaction;
    Toolbar toolbar;
    String logged;
    SharedPreferences sse;

    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.home, container, false);

        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("NeedBlood");

        join = (CardView) root.findViewById(R.id.join);
        check = (CardView) root.findViewById(R.id.check);
        get = (CardView) root.findViewById(R.id.get);

        joinText = (TextView) root.findViewById(R.id.text);

        loadPreferences();

        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setCurrentFragment(new GetBloodFragment(), "Search for donors");

            }
        });

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setCurrentFragment(new TakeQuizFragment(), "Check Eligibility");
            }
        });

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (logged.equals("1")) {

                    setCurrentFragment(new ProfileFragment(), "NeedBlood Profile");

                } else {

                    setCurrentFragment(new RegisterWelcomeFragment(), "Register/Login");

                }

            }
        });

        return root;
    }


    public void setCurrentFragment(Fragment newFragment, String title) {

        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);

        fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
         fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        fragmentTransaction.replace(R.id.mainFrame, newFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        toolbar.setTitle(title);


    }



    public void loadPreferences() {

        sse = PreferenceManager.getDefaultSharedPreferences(getActivity());
        logged = sse.getString("code", "0");

        if (logged.equals("1")) {

            joinText.setText("NeedBlood Profile");

        } else {

            joinText.setText("Join NeedBlood");

        }

    }


}
