package com.example.wesal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Charties_Catigory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charties__catigory);
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener= new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment=null;
            switch (menuItem.getItemId()){
                case R.id.bold_category_icon:
                    selectedFragment=new Blod();
                    break;
                case R.id.matrial_category_icon:
                    selectedFragment=new Matrial();
                    break;
                case R.id.money_category_icon:
                    selectedFragment=new Money();
                    break;
                case R.id.time_category_icon:
                    selectedFragment=new Time();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentBase,selectedFragment).commit();
            return true;
        }
    };

}
