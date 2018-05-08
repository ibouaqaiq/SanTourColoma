package com.example.issam.santourcoloma.View;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.example.issam.santourcoloma.Fragments.AllSitiosFragment;
import com.example.issam.santourcoloma.Fragments.FavFragment;
import com.example.issam.santourcoloma.Fragments.VisitadosFragment;
import com.example.issam.santourcoloma.R;

public class SitiosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sitios);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Sitios"));
        tabLayout.addTab(tabLayout.newTab().setText("Favoritos"));
        tabLayout.addTab(tabLayout.newTab().setText("Visitados"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        final ViewPager viewPager = findViewById(R.id.viewPager);
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    class PagerAdapter extends FragmentStatePagerAdapter {

        private int numberOfTabs;

        public PagerAdapter(FragmentManager fm, int numberOfTabs) {
            super(fm);
            this.numberOfTabs = numberOfTabs;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position){
                case 0:
                    return new AllSitiosFragment();
                case 1:
                    return new FavFragment();
                case 2:
                    return new VisitadosFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return numberOfTabs;
        }
    }
}
