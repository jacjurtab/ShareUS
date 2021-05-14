package com.example.shareus.ui.misviajes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.example.shareus.R;
import com.example.shareus.ui.misviajes.tabview.ViajesConductorFragment;
import com.example.shareus.ui.misviajes.tabview.ViajesPasadosFragment;
import com.example.shareus.ui.misviajes.tabview.ViajesPasajeroFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

public class MisViajesFragment extends Fragment {

    private MisViajesViewModel misViajesViewModel;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private PagerAdapter pagerAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        misViajesViewModel = new ViewModelProvider(this).get(MisViajesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_misviajes, container, false);

        tabLayout = root.findViewById(R.id.menutabs);
        viewPager = root.findViewById(R.id.pagetabs);

        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_menu_lupa).setText("Conductor"));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_menu_calendario).setText("Pasajero"));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_menu_coche).setText("Anteriores"));

        pagerAdapter = new PagerAdapter(getActivity().getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        return root;
    }


    public  class PagerAdapter extends FragmentPagerAdapter {

        int numerodetabs;
        public PagerAdapter(FragmentManager fm, int numerodetabs) {
            super(fm);
            this.numerodetabs = numerodetabs;
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment = null;
            switch (i){
                case 0: fragment = new ViajesConductorFragment(); break;
                case 1: fragment = new ViajesPasajeroFragment(); break;
                case 2: fragment = new ViajesPasadosFragment(); break;
            }
            return fragment;
        }

        @Override
        public int getCount()
        {
            return numerodetabs;
        }
    }
}

