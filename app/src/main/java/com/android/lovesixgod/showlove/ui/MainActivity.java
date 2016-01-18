package com.android.lovesixgod.showlove.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.lovesixgod.showlove.R;
import com.android.lovesixgod.showlove.adapter.MyViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private MyViewPagerAdapter myViewPagerAdapter;
    private String[] titles;
    private List<Fragment> fragments;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private FloatingActionButton floatingActionButton;
    private Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        initData();

        configViews();
    }

    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        navigationView = (NavigationView) findViewById(R.id.navigation);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floating_button);
    }

    private void initData() {
        titles = getResources().getStringArray(R.array.tab_titles);
        fragments = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            Bundle mBundle = new Bundle();
            mBundle.putInt("flag", i);
            MyFragment fragment = new MyFragment();
            fragment.setArguments(mBundle);
            fragments.add(i, fragment);
        }
    }

    private void configViews() {
        setSupportActionBar(toolbar);

        // ViewPager绑定数据
        myViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager(), titles, fragments);
        viewPager.setAdapter(myViewPagerAdapter);

        // TabLayout设置内容
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabsFromPagerAdapter(myViewPagerAdapter);

        // 跳转到Menu的Toggle
        ActionBarDrawerToggle mActionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_open);
        mActionBarDrawerToggle.syncState();
        drawerLayout.setDrawerListener(mActionBarDrawerToggle);

        // 抽屉菜单填充内容
        navigationView.inflateHeaderView(R.layout.header_navigation);
        navigationView.inflateMenu(R.menu.menu_navigation);

        floatingActionButton.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        snackbar = Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_SHORT)
                .setAction("Action", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        snackbar.dismiss();
                    }
                });
        snackbar.show();
    }
}
