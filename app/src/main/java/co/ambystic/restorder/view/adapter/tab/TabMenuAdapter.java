package co.ambystic.restorder.view.adapter.tab;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class TabMenuAdapter extends FragmentStatePagerAdapter {
    private List<String> tabTitleList = new ArrayList<>();
    private List<Fragment> tabFragmentList = new ArrayList<>();

    public TabMenuAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(Fragment fragment, String title){
        tabFragmentList.add(fragment);
        tabTitleList.add(title);
    }

    @Override
    public Fragment getItem(int i) {
       return tabFragmentList.get(i);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
       return tabTitleList.get(position);
    }

    @Override
    public int getCount() {
        return tabFragmentList.size();
    }
}
