package bloodbank.ieee.com.bloodbank.NeedBlood;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import bloodbank.ieee.com.bloodbank.Fragments.QuizFragment;
import bloodbank.ieee.com.bloodbank.Fragments.RegisterFragment;
import bloodbank.ieee.com.bloodbank.R;

/**
 * Created by rashad on 5/31/16.
 */
public class RegisterWelcomeFragment extends Fragment {

    Button goToQuiz;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.registerwelocme, container, false);

        goToQuiz = (Button) root.findViewById(R.id.reg);

        goToQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment currentFragment = new RegisterFragment();
                fragmentTransaction.replace(R.id.mainFrame, currentFragment);
                fragmentTransaction.commit();

            }
        });
        return root;
    }

}