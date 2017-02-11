package ieee.donn.QuizFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import ieee.donn.R;

import ieee.donn.Fragments.LearnFragment;
import ieee.donn.Fragments.RegisterFragment;
import ieee.donn.NeedBlood.ScheduleFragment;

/**
 * .
 * Created by rashad on 5/26/16.
 * .
 */
public class ResultFragment extends Fragment {

    int flag = 0;

    View root;
    TextView resultt, data;
    Button learn, sched;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.result, container, false);

        resultt = (TextView) root.findViewById(R.id.textRes);
        data = (TextView) root.findViewById(R.id.data);

        learn = (Button) root.findViewById(R.id.learn);
         sched = (Button) root.findViewById(R.id.sched);


        Bundle bundle = getArguments();

        int q1 = bundle.getInt("q1");
        int q2 = bundle.getInt("q2");
        int q3 = bundle.getInt("q3");
        int q4 = bundle.getInt("q4");
        int q5 = bundle.getInt("q5");
        int q6 = bundle.getInt("q6");
        int q7 = bundle.getInt("q7");
        int q8 = bundle.getInt("q8");


        if (q1 == 0 || q2 == 0 || q3 == 0 || q4 == 0 || q5 == 0 || q6 == 0|| q7 == 0 || q8 == 0) {


            sched.setVisibility(View.INVISIBLE);

            resultt.setText("Sorry, You did not pass the test");

            data.setText("\nYou seem to not meet one of the criteria of being a blood donor," +
                    "this means that your blood isn't safe for transfusion or that blood donation may affect your health or live." +
                    "\n\nYou can check the 'Learn' Section to know what you are missing.");

        } else {

            learn.setVisibility(View.INVISIBLE);

            resultt.setText("Congrats, You passed the test!");

            data.setText("\nYou met the criteria, and now you can donate your blood to any one who need need it, this is your first step towards change. \n\n" +
                    "You can go to the nearest hospital and donate your blood, make your own blood donation schedule " +
                    "or register in the blood donatiob service to make people know that another generous donor is available.");


        }


        learn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment currentFragment = new LearnFragment();

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
                fragmentTransaction.replace(R.id.mainFrame, currentFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });




        sched.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment currentFragment = new ScheduleFragment();

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
                fragmentTransaction.replace(R.id.mainFrame, currentFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });


        return root;
    }


}