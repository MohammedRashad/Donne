package bloodbank.ieee.com.bloodbank.Fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bloodbank.ieee.com.bloodbank.R;


public class HomeFragment extends Fragment {


    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.home, container, false);

        return root;
    }


}
