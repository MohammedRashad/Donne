package bloodbank.ieee.com.bloodbank.LearnFragmentFiles;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import bloodbank.ieee.com.bloodbank.Fragments.QuizFragment;
import bloodbank.ieee.com.bloodbank.R;

/**
 * Created by rashad on 5/23/16.
 * ==
 */
public class WhatFragment extends Fragment {

    Button button;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.what_fragment, container, false);

        button = (Button) root.findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO Add the test
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment currentFragment = new QuizFragment();
                fragmentTransaction.replace(R.id.mainFrame, currentFragment);
                fragmentTransaction.commit();

                Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
                toolbar.setTitle("Check Eligibility");

            }
        });

        return root;

    }

}
