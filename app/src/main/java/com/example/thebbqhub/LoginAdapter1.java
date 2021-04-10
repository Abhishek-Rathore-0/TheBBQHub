package com.example.thebbqhub;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class LoginAdapter1 extends FragmentPagerAdapter {

    private final List<Fragment> fragmentName = new ArrayList<>();
    private final List<String> fragmentTitle = new ArrayList<>();

    public LoginAdapter1(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentName.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitle.get(position);
    }

    @Override
    public int getCount() {
        return fragmentTitle.size();
    }

    public void AddFragment(Fragment fragment,String title){
        fragmentName.add(fragment);
        fragmentTitle.add(title);
    }
}
