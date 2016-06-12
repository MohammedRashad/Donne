package bloodbank.ieee.com.bloodbank.QuizFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import bloodbank.ieee.com.bloodbank.Fragments.QuizFragment;
import bloodbank.ieee.com.bloodbank.R;

/**
 * Created by rashad on 5/26/16.
 */
public class TakeQuizFragment extends Fragment {

    Button goToQuiz;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.quiz_welcome, container, false);

        goToQuiz = (Button) root.findViewById(R.id.button5);

        goToQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO Add the test
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment currentFragment = new QuizFragment();
                fragmentTransaction.replace(R.id.mainFrame, currentFragment);
                fragmentTransaction.commit();

            }
        });
        return root;
    }

}