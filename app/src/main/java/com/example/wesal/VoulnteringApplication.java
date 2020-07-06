package com.example.wesal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.example.wesal.databinding.ActivityVoulnteringApplicationBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class VoulnteringApplication extends AppCompatActivity {

    ActivityVoulnteringApplicationBinding binding;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_voulntering_application);
        setSupportActionBar(binding.actionParActivityID);
        if (getSupportActionBar()!= null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        binding.actionParActivityID.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        Intent intent = getIntent();
        id = intent.getStringExtra("CharityId");
        
        binding.enterIdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parseData();
            }
        });

        binding.enterIDHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    private void parseData() {
        String key;
        GetIdModel idModel = getData();
        if (binding.nameEditID.toString().isEmpty() ||
                binding.phoneEditID.getText().toString().isEmpty() ||
                binding.mailEditID.getText().toString().isEmpty()) {
            openDialog();
        } else {
            DatabaseReference idAskForVolunteerDatabaseReference = FirebaseDatabase.getInstance()
                    .getReference("CharityProfiles").child(id).child("AskForVolunteer");
            key = idAskForVolunteerDatabaseReference.push().getKey();
            if (key != null) {
                DatabaseReference idAskForVolunteerDatabaseReference1 = idAskForVolunteerDatabaseReference.child(key);
                idAskForVolunteerDatabaseReference1.setValue(idModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(VoulnteringApplication.this, R.string.success, Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(VoulnteringApplication.this, R.string.some_error_acurate_try_again, Toast.LENGTH_SHORT).show();
                    }
                });

            }


        }

    }

    private void openDialog() {

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(R.string.missed_data);
        alert.setMessage(R.string.enter_correct_data);
        alert.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alert.create().show();
    }

    private GetIdModel getData() {
        String message;
        if (binding.messageEditID.getText().toString().isEmpty()) {
            message = "no message";
        } else {
            message = binding.messageEditID.getText().toString();
        }
        return new GetIdModel(binding.nameEditID.getText().toString(),
                binding.phoneEditID.getText().toString(),
                binding.mailEditID.getText().toString(),
                message);
    }

}
