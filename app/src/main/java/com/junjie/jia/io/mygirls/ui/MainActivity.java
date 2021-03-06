package com.junjie.jia.io.mygirls.ui;

import android.os.Bundle;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.junjie.jia.io.mygirls.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    private static String[] tabs;
    private static final List<PageFragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initTabResource();
        findView();

        for (String tab : tabs) {
            fragmentList.add(PageFragment.newInstance(tab));
        }

        viewPager.setAdapter(new GankPageAdapter(getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(tabs.length);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void findView() {
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
    }

    private void initTabResource() {
        tabs = getResources().getStringArray(R.array.tabs);
    }

    private static final class GankPageAdapter extends FragmentPagerAdapter {
        GankPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return tabs.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position];
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        }
    }
}
