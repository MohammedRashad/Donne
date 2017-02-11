package ieee.donn.Fragments;

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

import ieee.donn.QuizFragments.ResultFragment;
import ieee.donn.R;

/**
 * Created by rashad on 5/16/16.
 */
public class QuizFragment extends Fragment {

    RadioGroup[] question = new RadioGroup[8];
    Button result;
    View root;
    int[] q = new int[8];


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.quiz, container, false);

        result = (Button) root.findViewById(R.id.result);

        question[0] = (RadioGroup) root.findViewById(R.id.radioGroup1);
        question[1] = (RadioGroup) root.findViewById(R.id.radioGroup2);
        question[2] = (RadioGroup) root.findViewById(R.id.radioGroup3);
        question[3] = (RadioGroup) root.findViewById(R.id.radioGroup4);
        question[4] = (RadioGroup) root.findViewById(R.id.radioGroup5);
        question[5] = (RadioGroup) root.findViewById(R.id.radioGroup6);
        question[6] = (RadioGroup) root.findViewById(R.id.radioGroup7);
        question[7] = (RadioGroup) root.findViewById(R.id.radioGroup8);


        question[0].setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radioButton || checkedId == R.id.radioButton3) {

                    q[0] = 0;

                } else if (checkedId == R.id.radioButton2) {

                    q[0] = 1;

                } else {

                    q[0] = -1;

                }

            }
        });

        question[1].setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radioButton4 || checkedId == R.id.radioButton5) {

                    q[1] = 0;

                } else if (checkedId == R.id.radioButton6) {

                    q[1] = 1;

                } else {

                    q[1] = -1;

                }


            }
        });

        question[2].setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radioButton7) {

                    q[2] = 0;

                } else if (checkedId == R.id.radioButton8) {

                    q[2] = 1;

                } else {

                    q[2] = -1;

                }

            }
        });

        question[3].setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radioButton10) {

                    q[3] = 0;

                } else if (checkedId == R.id.radioButton11) {

                    q[3] = 1;

                } else {

                    q[3] = -1;

                }


            }
        });

        question[4].setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {


                if (checkedId == R.id.radioButton13) {

                    q[4] = 0;

                } else if (checkedId == R.id.radioButton14) {

                    q[4] = 1;

                } else {

                    q[4] = -1;

                }


            }
        });


        question[5].setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radioButton16) {

                    q[5] = 0;

                } else if (checkedId == R.id.radioButton17) {

                    q[5] = 1;

                } else {

                    q[5] = -1;
                }


            }
        });


        question[6].setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radioButton18) {

                    q[6] = 1;

                } else if (checkedId == R.id.radioButton21) {

                    q[6] = 0;

                } else {

                    q[6] = -1;
                }


            }
        });



        question[7].setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radioButton20) {

                    q[7] = 1;

                } else if (checkedId == R.id.radioButton19) {

                    q[7] = 0;

                } else {

                    q[7] = -1;
                }


            }
        });



        result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (q[0] == -1 || q[1] == -1 || q[2] == -1 || q[3] == -1 || q[4] == -1 || q[5] == -1 || q[6] == -1 || q[7] == -1) {


                    Snackbar.make(v, "Error :\nA question is not answered, please answer all questions", Snackbar.LENGTH_LONG).show();


                } else {

                    Bundle bundle = new Bundle();

                    bundle.putInt("q1", q[0]);
                    bundle.putInt("q2", q[1]);
                    bundle.putInt("q3", q[2]);
                    bundle.putInt("q4", q[3]);
                    bundle.putInt("q5", q[4]);
                    bundle.putInt("q6", q[5]);
                    bundle.putInt("q7", q[6]);
                    bundle.putInt("q8", q[7]);


                    Fragment currentFragment = new ResultFragment();

                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

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