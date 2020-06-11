package com.example.wesal;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Blod extends Fragment {


    private ArrayList<String> texts;
    private EditText name;
    private EditText address;
    private EditText phone;
    private EditText note;
    private Spinner bloodSpinner;
    private TextView submit;
    private String ID;

    public Blod() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blod, container, false);

        if (getArguments() != null) {
            ID = getArguments().getString("CharityID");
            Toast.makeText(getContext(), ID, Toast.LENGTH_SHORT).show();

        }

        population(view);


        FirebaseApp.initializeApp(requireContext());

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parseData();
            }
        });

        return view;
    }

    private void parseData() {
        turnTextToString();
        //check Text correction
        if (texts.get(0).isEmpty() || texts.get(2).isEmpty() || texts.get(4).equals("select your blood type")) {
            // create dialog to say enter correct data
            openDialog();
        } else {
            // parse data to firebase
            DatabaseReference donateBloodDatabaseReference = FirebaseDatabase.getInstance().getReference("CharityProfiles").child(ID).child("DonateBlood");
//            DatabaseReference donateBloodDatabaseReference = FirebaseDatabase.getInstance().getReference("DonateBlood").child(ID);
            String key= donateBloodDatabaseReference.push().getKey();
            if (key!=null){
                DatabaseReference databaseReferenceBloodUser = donateBloodDatabaseReference.child(key);
                databaseReferenceBloodUser.child("name").setValue(texts.get(0));
                databaseReferenceBloodUser.child("address").setValue(texts.get(1));
                databaseReferenceBloodUser.child("phone").setValue(texts.get(2));
                databaseReferenceBloodUser.child("note").setValue(texts.get(3));
                databaseReferenceBloodUser.child("bloodSpinner").setValue(texts.get(4));
                emptyEditText();
            }
            Toast.makeText(getContext(), R.string.data_recorded, Toast.LENGTH_SHORT).show();
        }
    }
    private void emptyEditText() {
        name.setText("");
        address.setText("");
        phone.setText("");
        note.setText("");
    }
    private void openDialog() {

        AlertDialog.Builder alert=new AlertDialog.Builder(getContext());
        alert.setTitle(R.string.missed_data);
        alert.setMessage(R.string.enter_correct_data);
        alert.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alert.create().show();
    }
    // turn text in to string and save it in to array
    private void turnTextToString() {
        texts = new ArrayList<>();
        texts.add(name.getText().toString());
        texts.add(address.getText().toString());
        texts.add(phone.getText().toString());
        texts.add(note.getText().toString());
        texts.add(bloodSpinner.getSelectedItem().toString());
    }
    private void population(View view) {
        name = view.findViewById(R.id.editTextNameBlodForm);
        address = view.findViewById(R.id.editTextAddressBlodForm);
        phone = view.findViewById(R.id.editTextPhoneNumberBlodForm);
        note = view.findViewById(R.id.editTextNotesBlodForm);
        bloodSpinner = view.findViewById(R.id.spinnerBloodTypes);
        submit = view.findViewById(R.id.textViewSubmitButtonBlodForm);
    }

}
