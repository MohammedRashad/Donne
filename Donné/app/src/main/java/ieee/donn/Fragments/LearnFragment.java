package ieee.donn.Fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ieee.donn.LearnFragmentFiles.ViewPagerAdapter;
import  ieee.donn.R;



/**
 * Created by rashad on 5/16/16.
 *
 */

public class LearnFragment extends Fragment {

    TabLayout.Tab what,why,types,facts;

    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.learn, container, false);

        TabLayout tabLayout = (TabLayout) root.findViewById(R.id.tabs);
        ViewPager viewPager = (ViewPager) root.findViewById(R.id.viewpager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());

        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);

        what = tabLayout.newTab();
        why = tabLayout.newTab();
        types = tabLayout.newTab();
        facts = tabLayout.newTab();


        tabLayout.addTab(what, 0);
        tabLayout.addTab(why, 1);
        tabLayout.addTab(types, 2);
        tabLayout.addTab(facts , 3);


        what.setText("What is it?");
        why.setText("Why to donate?");
        types.setText("Donation Types");
        facts.setText("Important Facts");


        tabLayout.setTabTextColors(ContextCompat.getColorStateList(getActivity() , R.drawable.tab_selector));
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getActivity() , R.color.indicator));
        what.select();

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        return root;
    }

}