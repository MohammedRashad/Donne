package bloodbank.ieee.com.bloodbank.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ieee.donne.R;


/**
 * Created by rashad on 5/16/16.
 */

public class AboutFragment extends Fragment {


    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.about, container, false);

        return root;
    }


}
