package com.example.wesal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import com.example.wesal.databinding.HomeActivityBinding;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class Home_Activity extends AppCompatActivity  {

    HomeActivityBinding homeActivityBinding;
    String ID;
    ArrayList<String> charitiesID = new ArrayList<>();
    ArrayList<String> charitiesNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        homeActivityBinding = DataBindingUtil.setContentView(this, R.layout.home_activity);
        setSupportActionBar(homeActivityBinding.actionPar);
        FirebaseApp.initializeApp(Objects.requireNonNull(this));

        retrieveCharitiesProfiles();


        final Intent intent = new Intent(Home_Activity.this, Charties_Catigory.class);
        homeActivityBinding.donateBloodHomeActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ID!=null){
                    intent.putExtra("NavigationID", "1");
                    intent.putExtra("CharityId", ID);
                    startActivity(intent);
                }else{
                    Toast.makeText(Home_Activity.this, R.string.choose_charity_frist, Toast.LENGTH_SHORT).show();
                }

            }
        });
        homeActivityBinding.donateMaterialHomeActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ID!=null){
                    intent.putExtra("NavigationID", "2");
                    intent.putExtra("CharityId", ID);
                    startActivity(intent);
                }else{
                    Toast.makeText(Home_Activity.this, R.string.choose_charity_frist, Toast.LENGTH_SHORT).show();
                }

            }
        });
        homeActivityBinding.donateMoneyHomeActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ID!=null){
                    intent.putExtra("NavigationID", "3");
                    intent.putExtra("CharityId", ID);
                    startActivity(intent);
                }else{
                    Toast.makeText(Home_Activity.this, R.string.choose_charity_frist, Toast.LENGTH_SHORT).show();
                }

            }
        });
        homeActivityBinding.donateTimeHomeActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ID!=null){
                    intent.putExtra("NavigationID", "4");
                    intent.putExtra("CharityId", ID);
                    startActivity(intent);
                }else{
                    Toast.makeText(Home_Activity.this, R.string.choose_charity_frist, Toast.LENGTH_SHORT).show();
                }

            }
        });
        homeActivityBinding.charityProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ID!=null){
                    Intent intent = new Intent(Home_Activity.this, CharityProfile.class);
                    intent.putExtra("CharityId", ID);
                    startActivity(intent);
                }else{
                    Toast.makeText(Home_Activity.this, R.string.choose_charity_frist, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    //spionner handle
    private void spinnerMethod() {
        if (!isNetworkAvailable()){
            Toast.makeText(Home_Activity.this, R.string.chech_the_connection, Toast.LENGTH_SHORT).show();
        }
        ArrayAdapter<String> spinnerAdapter =new ArrayAdapter(this,android.R.layout.simple_spinner_item,charitiesNames);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        homeActivityBinding.spinnerMainActivity.setAdapter(spinnerAdapter);
        homeActivityBinding.spinnerMainActivity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ID=charitiesID.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    // retrieve data from firebase
    private void retrieveCharitiesProfiles() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("CharityProfiles");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    charitiesNames.add((String) postSnapshot.child("charityName").getValue());
                    charitiesID.add((String) postSnapshot.child("uniqueKey").getValue());
                }
                spinnerMethod();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Home_Activity.this, R.string.some_error_accurate, Toast.LENGTH_SHORT).show();
            }
        });

    }

    //many used dialog
    private void openDialog(String message) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(R.string.missed_data);
        alert.setMessage(message);
        alert.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alert.create().show();
    }

    // check internet connection method
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    //method for handel search side menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);

        return true;
    }
    //method for handel search side menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.searchForBlood) {
            startActivity(new Intent(Home_Activity.this, SearchMap.class));
        }
        return super.onOptionsItemSelected(item);
    }


}
