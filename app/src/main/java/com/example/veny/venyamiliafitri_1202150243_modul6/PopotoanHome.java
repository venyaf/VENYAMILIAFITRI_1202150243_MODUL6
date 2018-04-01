package com.example.veny.venyamiliafitri_1202150243_modul6;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class PopotoanHome extends AppCompatActivity {
    FloatingActionButton fab;
    ViewPager vp;
    TabLayout tab;
    FirebaseAuth auth;
    AppBarLayout abl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popotoan_home);

        vp = (ViewPager)findViewById(R.id.vp);
        tab = (TabLayout) findViewById(R.id.tabs);
        fab = (FloatingActionButton)findViewById(R.id.fab);
        abl = (AppBarLayout)findViewById(R.id.appbar);
        auth = FirebaseAuth.getInstance();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent dari PopotoanHome ke PostPotoActivity
                Intent intent = new Intent(PopotoanHome.this,PostPotoActivity.class);
                startActivity(intent);
            }
        });

        //SetupPager ViewPager
        setupPager(vp);

        //setupWithViewPager si ViewPager pada tab
        tab.setupWithViewPager(vp);
        //set tulisan pada tab
        tab.getTabAt(0).setText("TERBARU");
        tab.getTabAt(1).setText("POTO SAYA");
        tab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //getPosition()==0 untuk tab terbaru lalu set warna background
                if(tab.getPosition()==0){
                    abl.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                }else{
                    abl.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                }
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
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    //Method ketika menu logout dipilih
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //getItemId() dari item dengan action_settings pada Menu
        if(item.getItemId()==R.id.action_settings) {
            //FirebaseAuth.getInstance() untuk signOut()
            auth.signOut();
            //Intent dari PopotoanHome ke MenuLogin
            startActivity(new Intent(PopotoanHome.this, MenuLogin.class));
            //finish
            finish();
        }
        //mengembalikan nilai boolean true
        return true;
    }

    //Menetapkan adapter untuk viewpager
    public void setupPager(ViewPager v){
        VPAdapter adapter = new VPAdapter(getSupportFragmentManager());
        //menambah fragment pada adapter
        adapter.addFragment(new PopotoanHomeTerbaru(), "TERBARU");
        adapter.addFragment(new PopotoanHomeUser(),"SAYA");

        //setAdapter ViewPager pada adapter
        v.setAdapter(adapter);
    }

    //Subclass sebagai adapter untuk Viewpager dengan fragmentnya
    class VPAdapter extends FragmentPagerAdapter {
        //deklarasi nama variable ArrayList<> baru dengan fragment
        private final List<Fragment> listfragment = new ArrayList<>();
        //deklarasi nama variable ArrayList<> baru dengan String
        private final List<String> listfragmenttitle = new ArrayList<>();
        public VPAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            //mengembalikan get(position) pada listfragment
            return listfragment.get(position);
        }

        public void addFragment(Fragment f, String title){
            //Menambahkan fragment dan title
            listfragment.add(f);
            listfragmenttitle.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }

        @Override
        public int getCount() {
            //mengembalikan ukuran listfragment
            return listfragment.size();
        }
    }
}
