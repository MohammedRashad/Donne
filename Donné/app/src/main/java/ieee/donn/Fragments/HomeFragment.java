package ieee.donn.Fragments;

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

import ieee.donn.NeedBlood.GetBloodFragment;
import ieee.donn.NeedBlood.ProfileFragment;
import ieee.donn.QuizFragments.TakeQuizFragment;
import ieee.donn.R;


public class HomeFragment extends Fragment {

    View root;
    Toolbar toolbar;
    TextView joinText;
    CardView join, check, get;
    FragmentTransaction fragmentTransaction;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.home, container, false);

        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Donné");

        join = (CardView) root.findViewById(R.id.join);
        check = (CardView) root.findViewById(R.id.check);
        get = (CardView) root.findViewById(R.id.get);

        joinText = (TextView) root.findViewById(R.id.text);

        joinText.setText("Donné Profile");


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

                setCurrentFragment(new ProfileFragment(), "Donné Profile");

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


}
