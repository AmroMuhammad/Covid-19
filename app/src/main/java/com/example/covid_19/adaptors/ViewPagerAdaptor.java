package com.example.covid_19.adaptors;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.ListFragment;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdaptor extends FragmentPagerAdapter {

    private List<Fragment> mfragments;
    private List<String> mfragmentTitles;

    public ViewPagerAdaptor(@NonNull FragmentManager fm, int behavior, List<Fragment> fragments, List<String> fragmentTitles) {
        super(fm, behavior);
        mfragments = new ArrayList<>();
        mfragmentTitles = new ArrayList<>();
        mfragments = fragments;
        mfragmentTitles = fragmentTitles;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mfragments.get(position);
    }

    @Override
    public int getCount() {
        return mfragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mfragmentTitles.get(position);
    }
}
