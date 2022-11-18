package com.example.eventsrus.Adapter;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {


     private final List<Fragment> fragmentList=new ArrayList<>();
     private final List<String> fragmentTitle=new ArrayList<>();


    public ViewPagerAdapter(FragmentManager fm){
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    public void addFragment(Fragment fragment,String title){
         fragmentList.add(fragment);
         fragmentTitle.add(title);
    }


    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position){
        return fragmentTitle.get(position);
    }
}
