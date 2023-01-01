package com.nvm.imapp.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.nvm.imapp.fragment.HelpFragment;
import com.nvm.imapp.fragment.HomeFragment;
import com.nvm.imapp.fragment.HuongDanFragment;
import com.nvm.imapp.fragment.ProfileFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new HomeFragment();
            case 1: return new ProfileFragment();
            case 2: return new HuongDanFragment();
            case 3: return new HelpFragment();
            default: return new HomeFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
