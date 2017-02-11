package ieee.donn.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ieee.donn.R;


/**
 * Created by rashad on 5/23/16.
 *
 */
public class SpreadFragment extends Fragment {

    Button shareGithub, shareApp, awarenessShare;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.spread, container, false);

        shareApp = (Button) root.findViewById(R.id.button2);
        shareGithub = (Button) root.findViewById(R.id.button4);
        awarenessShare = (Button) root.findViewById(R.id.button3);

        shareGithub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO Add the test
                share("#Donné is an open-source blood donation network, you can view the source code here : https://github.com/MohammedRashad/Donne");

            }
        });

        shareApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO Add the test

                share("#Donné is an open-source blood donation network, you can get it here : https://play.google.com/store/apps/details?id=bloodbank.ieee.com.bloodbank");


            }
        });

        awarenessShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                share("Everyday, All around the world, thousands of people are on the verge of dying due to their need to blood.\n\nYou can change this by giving away your blood..\n\nBecome a hero and save someone's live by giving them the gift of live!\n\n#Donné");

            }
        });


        return root;
    }


    public void share(String message){

        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");

        share.putExtra(android.content.Intent.EXTRA_TEXT, message);
        startActivity(Intent.createChooser(share, "Share it"));


    }

}
