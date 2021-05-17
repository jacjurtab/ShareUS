package com.example.shareus.ui.misviajes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.shareus.R;
import com.example.shareus.ui.misviajes.tabs.ViajesConductorFragment;
import com.example.shareus.ui.misviajes.tabs.ViajesPasadosFragment;
import com.example.shareus.ui.misviajes.tabs.ViajesPasajeroFragment;
import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

public class MisViajesFragment extends Fragment {

    private ViewPager viewPager;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_misviajes, container, false);

        TabLayout tabLayout = root.findViewById(R.id.menutabs);
        viewPager = root.findViewById(R.id.pagetabs);

        FragmentManager cfManager = getChildFragmentManager();

        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_menu_lupa).setText("Conductor"));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_menu_calendario).setText("Pasajero"));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_menu_coche).setText("Anteriores"));

        PagerAdapter pagerAdapter = new PagerAdapter(cfManager, tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        return root;
    }


    public class PagerAdapter extends FragmentPagerAdapter {
        int numerodetabs;
        public PagerAdapter(FragmentManager fm, int numerodetabs) {
            super(fm);
            this.numerodetabs = numerodetabs;
        }

        @NotNull
        @Override
        public Fragment getItem(int i) {
            Fragment fragment = null;
            switch (i){
                case 0:
                    fragment = new ViajesConductorFragment();
                    break;
                case 1:
                    fragment = new ViajesPasajeroFragment();
                    break;
                case 2:
                    fragment = new ViajesPasadosFragment();
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return numerodetabs;
        }
    }
}

