package com.example.wesal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.wesal.databinding.ActivityChartiesCatigoryBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Charties_Catigory extends AppCompatActivity {
    private ActivityChartiesCatigoryBinding binding;
    private Fragment selectedFragment = null;
    String CharityId;
    public String NavigationID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_charties__catigory);
        Intent intent = getIntent();
        CharityId = intent.getStringExtra("CharityId");
        NavigationID = intent.getStringExtra("NavigationID");



        binding.bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        assert NavigationID != null;
        switchFragments(NavigationID);
    }

    private void switchFragments(String id) {

        Bundle bundle = new Bundle();
        bundle.putString("CharityID", CharityId);


        switch (id) {
            case "1":

                selectedFragment = new Blod();
                selectedFragment.setArguments(bundle);
                binding.bottomNavigationView.setSelectedItemId(R.id.bold_category_icon);
                break;
            case "2":
                selectedFragment = new Matrial();
                selectedFragment.setArguments(bundle);
                binding.bottomNavigationView.setSelectedItemId(R.id.matrial_category_icon);
                break;
            case "3":
                selectedFragment = new Money();
                selectedFragment.setArguments(bundle);
                binding.bottomNavigationView.setSelectedItemId(R.id.money_category_icon);
                break;
            case "4":
                selectedFragment = new Time();
                selectedFragment.setArguments(bundle);
                binding.bottomNavigationView.setSelectedItemId(R.id.time_category_icon);
                break;
        }
        if (selectedFragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentBase, selectedFragment).commit();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            Bundle bundle = new Bundle();
            bundle.putString("CharityID", CharityId);

            switch (menuItem.getItemId()) {
                case R.id.bold_category_icon:
                    selectedFragment = new Blod();
                    selectedFragment.setArguments(bundle);
                    break;

                case R.id.matrial_category_icon:
                    selectedFragment = new Matrial();
                    selectedFragment.setArguments(bundle);
                    break;

                case R.id.money_category_icon:
                    selectedFragment = new Money();
                    selectedFragment.setArguments(bundle);
                    break;

                case R.id.time_category_icon:
                    selectedFragment = new Time();
                    selectedFragment.setArguments(bundle);
                    break;
            }
            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentBase, selectedFragment).commit();
            }
            return true;
        }
    };

}
