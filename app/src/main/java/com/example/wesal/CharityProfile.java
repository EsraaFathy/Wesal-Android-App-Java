package com.example.wesal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.example.wesal.databinding.ActivityCharityProfileBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class CharityProfile extends AppCompatActivity {
    ActivityCharityProfileBinding binding;
    private String id;
    private ArrayList<String> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_charity_profile);
        Intent intent = getIntent();
        id = intent.getStringExtra("CharityId");
        retrieveCharitiesProfiles();

    }

    private void parseData() {
        binding.Name.setText(data.get(0));
        binding.about.setText(data.get(1));
        binding.address.setText(data.get(2));
        binding.phoneNumber.setText(data.get(3));
        Picasso.with(CharityProfile.this)
                .load(data.get(4))
                .placeholder(R.drawable.loading)
                .into(binding.imageViewEnterCharity);
    }

    private void retrieveCharitiesProfiles() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("CharityProfiles").child(id);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                data.add((String) dataSnapshot.child("charityName").getValue());//0
                data.add((String) dataSnapshot.child("about").getValue());//1
                data.add((String) dataSnapshot.child("address").getValue());//2
                data.add((String) dataSnapshot.child("Phone").getValue());//3
                data.add((String) dataSnapshot.child("imageNameURL").getValue());//4
                parseData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(CharityProfile.this, R.string.some_error_accurate, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
