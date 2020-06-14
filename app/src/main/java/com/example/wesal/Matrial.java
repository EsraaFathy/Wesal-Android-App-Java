package com.example.wesal;


import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class Matrial extends Fragment {
    private EditText name, address, phone;
    private Spinner spinnerMartial;
    private Button submit;
    private ImageView imageView;
    private String ID;
    private ArrayList<String> texts;
    private final static int PICK_UP_IMAGE_REQUEST = 2;
    private Uri imageUri;
    private DatabaseReference ob;
    private StorageTask uploadTask;
    private String imageNameURL;
    private String key;

    public Matrial() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_matrial, container, false);

        if (getArguments() != null) {
            ID = getArguments().getString("CharityID");
//            Toast.makeText(getContext(), ID, Toast.LENGTH_SHORT).show();
        }

        population(view);
        FirebaseApp.initializeApp(requireContext());

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isNetworkAvailable()) {
                    Toast.makeText(getContext(), R.string.chech_the_connection, Toast.LENGTH_SHORT).show();
                } else {
                    if (uploadTask != null && uploadTask.isInProgress()) {
                        openDialog();
                    } else {
                        if (uploadImage())
                        parseData();
                    }
                }
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
        return view;
    }

    // open file to choose image from mobile storage
    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_UP_IMAGE_REQUEST);
    }

    // this method will return the extension of file we pick
    private String getFileExtension(Uri imageUri) {
        ContentResolver contentResolver = getContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(imageUri));
    }

    private boolean uploadImage() {
        StorageReference storageReferenceCharityProfile = FirebaseStorage.getInstance().getReference("Martial");
        if (imageUri != null) {
            // image name in storage
            final StorageReference fileReference = storageReferenceCharityProfile.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));

            uploadTask = fileReference.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            passUrl(fileReference);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), "Fail", Toast.LENGTH_SHORT).show();
                        }
                    });
            return true;
        } else {
            openDialog();
            return false;
        }

    }


    private void passUrl(StorageReference fileReference) {
        ob = FirebaseDatabase.getInstance().getReference("CharityProfiles").child(ID).child("Martial").child(key);

        fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                imageNameURL = uri.toString();
                ob.child("imageNameURL").setValue(imageNameURL);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    //    //to receive the choosed image and view it in to image view
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_UP_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            Picasso.with(getContext()).load(imageUri).into(imageView);
        }
    }

    private void parseData() {
        turnTextToString();
        //check Text correction
        if (texts.get(0).isEmpty() || texts.get(1).isEmpty() || texts.get(2).isEmpty()) {
            // create dialog to say enter correct data
            openDialog();
        } else {
            // parse data to firebase
            DatabaseReference donateBloodDatabaseReference = FirebaseDatabase.getInstance().getReference("CharityProfiles").child(ID).child("Martial");
             key = donateBloodDatabaseReference.push().getKey();
            if (key != null) {
                DatabaseReference databaseReferenceBloodUser = donateBloodDatabaseReference.child(key);
                databaseReferenceBloodUser.child("name").setValue(texts.get(0));
                databaseReferenceBloodUser.child("address").setValue(texts.get(1));
                databaseReferenceBloodUser.child("phone").setValue(texts.get(2));
                databaseReferenceBloodUser.child("type").setValue(texts.get(3));
                emptyEditText();
            }
            Toast.makeText(getContext(), R.string.data_recorded, Toast.LENGTH_SHORT).show();
        }
    }

    private void emptyEditText() {
        name.setText("");
        address.setText("");
        phone.setText("");
    }

    private void openDialog() {

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
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
        texts.add(spinnerMartial.getSelectedItem().toString());
    }

    private void population(View view) {
        name = view.findViewById(R.id.editTextNameMatrialForm);
        address = view.findViewById(R.id.editTextAddressMatrialForm);
        phone = view.findViewById(R.id.editTextPhoneNumberMatrialForm);
        spinnerMartial = view.findViewById(R.id.chooseMatrialTypeSpinner);
        imageView = view.findViewById(R.id.addImageMatialform1);
        submit = view.findViewById(R.id.submitButtonMartialForm);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}
