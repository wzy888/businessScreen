package com.zhumei.baselib.base;



import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

public class BaseFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList;
    private final List<String> items;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mCurTransaction;

    public BaseFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<String> tabs) {
        super(fm);
        mFragmentManager = fm;
        this.fragmentList = fragments;
        items = tabs;
    }


    @Override
    public Fragment getItem(int position) {
        if (fragmentList == null || fragmentList.size() < position) {
            return null;
        } else {
            return fragmentList.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        // 获取当前数据的hashCode
        int hashCode = fragmentList.get(position).hashCode();
        return hashCode;
    }
    @Override
    public int getCount() {
        return fragmentList == null ? 0 : fragmentList.size();
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return items.get(position);
    }


}
