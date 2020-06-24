package com.example.wesal;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class Time extends Fragment {
    private List<TimeModel> timeModelList = new ArrayList<>();
    private RecyclerView recyclerView;
    private String ID;

    public Time() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_time, container, false);



        if (getArguments() != null) {
            ID = getArguments().getString("CharityID");
        }
        fitchRecyclerView(view);



        final TextView textView = view.findViewById(R.id.charityNameForID);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("CharityProfiles").child(ID).child("charityName");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String charityName = Objects.requireNonNull(snapshot.getValue()).toString();
                String name = "Enter " + charityName + " ID";
                textView.setText(name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        Button idButton = view.findViewById(R.id.enterIdButton);
        final EditText idEditText = view.findViewById(R.id.charityID);
        idButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (idEditText.getText().toString().equals("0000")) {
                    recyclerView.setVisibility(View.VISIBLE);
                    view.findViewById(R.id.cardID).setVisibility(View.INVISIBLE);
                } else {
                    Toast.makeText(getContext(), R.string.incorrect_id, Toast.LENGTH_SHORT).show();
                }

            }
        });


        return view;
    }

    private void fitchRecyclerView(View view) {

        timeModelList.add(new TimeModel(R.drawable.logo, "wesal", "we are wesal", "12:00"));
        timeModelList.add(new TimeModel(R.drawable.logo, "wesal", "we are wesal", "12:00"));
        timeModelList.add(new TimeModel(R.drawable.logo, "wesal", "we are wesal", "12:00"));
        timeModelList.add(new TimeModel(R.drawable.logo, "wesal", "we are wesal", "12:00"));
        timeModelList.add(new TimeModel(R.drawable.logo, "wesal", "we are wesal", "12:00"));

        recyclerView = view.findViewById(R.id.timeRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        TimeAdabter timeAdabter = new TimeAdabter(getContext(), timeModelList);
        recyclerView.setAdapter(timeAdabter);
    }

}
