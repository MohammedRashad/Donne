package bloodbank.ieee.com.bloodbank.Fragments;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;

import com.ieee.donne.R;

import bloodbank.ieee.com.bloodbank.QuizFragments.ResultFragment;

/**
 * Created by rashad on 5/16/16.
 */
public class QuizFragment extends Fragment {

    RadioGroup question1, question2, question3, question4, question5, question6;
    Button result;
    View root;

    int q1, q2, q3, q4, q5, q6;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.quiz, container, false);

        q1 = q2 = q3 = q4 = q5 = q6 = -1;

        result = (Button) root.findViewById(R.id.result);

        question1 = (RadioGroup) root.findViewById(R.id.radioGroup1);
        question2 = (RadioGroup) root.findViewById(R.id.radioGroup2);
        question3 = (RadioGroup) root.findViewById(R.id.radioGroup3);
        question4 = (RadioGroup) root.findViewById(R.id.radioGroup4);
        question5 = (RadioGroup) root.findViewById(R.id.radioGroup5);
        question6 = (RadioGroup) root.findViewById(R.id.radioGroup6);


        question1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radioButton || checkedId == R.id.radioButton3) {

                    q1 = 0;

                } else if (checkedId == R.id.radioButton2) {

                    q1 = 1;

                } else {

                    q1 = -1;

                }


            }
        });

        question2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radioButton4 || checkedId == R.id.radioButton5) {

                    q2 = 0;

                } else if (checkedId == R.id.radioButton6) {

                    q2 = 1;

                } else {

                    q2 = -1;

                }


            }
        });

        question3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radioButton7) {

                    q3 = 0;

                } else if (checkedId == R.id.radioButton8) {

                    q3 = 1;

                } else {

                    q3 = -1;


                }


            }
        });

        question4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radioButton10) {

                    q4 = 0;

                } else if (checkedId == R.id.radioButton11) {

                    q4 = 1;

                } else {

                    q4 = -1;

                }


            }
        });

        question5.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {


                if (checkedId == R.id.radioButton13) {

                    q5 = 0;

                } else if (checkedId == R.id.radioButton14) {

                    q5 = 1;

                } else {

                    q6 = -1;

                }


            }
        });


        question6.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radioButton16) {

                    q6 = 0;

                } else if (checkedId == R.id.radioButton17) {

                    q6 = 1;

                } else {

                    q6 = -1;
                }


            }
        });


        result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (q1 == -1 || q2 == -1 || q3 == -1 || q4 == -1 || q5 == -1 || q6 == -1) {


                    Snackbar.make(v, "Error :\nA question is not answered, please answer all questions", Snackbar.LENGTH_LONG).show();


                } else {

                    Bundle bundle = new Bundle();

                    bundle.putInt("q1", q1);
                    bundle.putInt("q2", q2);
                    bundle.putInt("q3", q3);
                    bundle.putInt("q4", q4);
                    bundle.putInt("q5", q5);
                    bundle.putInt("q6", q6);

                    Fragment currentFragment = new ResultFragment();

                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);

                    currentFragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.mainFrame, currentFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                }
            }
        });


        return root;
    }

}