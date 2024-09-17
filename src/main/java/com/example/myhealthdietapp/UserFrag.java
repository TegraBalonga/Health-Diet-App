package com.example.myhealthdietapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserFrag extends Fragment {

    public UserFrag() {
        // Required empty public constructor
    }

    TextView txtFullName;
    EditText txtName, txtEmail, txtNumber, txtAge, txtHeight, txtWeight;
    Button btnUpdate;


    String strUsername, strUnitHeight, strUnitWeight;

    View view;

    Switch switchHeight, switchWeight;


    FirebaseDatabase rootNode;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user, container, false);

        switchHeight = view.findViewById(R.id.switchHeight);
        switchWeight = view.findViewById(R.id.switchWeight);

        Intent i = getActivity().getIntent();
        strUsername = i.getStringExtra("Username");

        showInfo();

        switchHeight.setChecked(true);


        txtFullName = view.findViewById(R.id.txtVName);
        txtName = view.findViewById(R.id.txtName);
        txtEmail = view.findViewById(R.id.txtEmail);
        txtNumber = view.findViewById(R.id.txtNumber);
        txtAge = view.findViewById(R.id.txtAge2);
        txtHeight = view.findViewById(R.id.txtHeight2);
        txtWeight = view.findViewById(R.id.txtWeight2);


        switchHeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switchHeight.isChecked() == true) {
                    double height = Double.parseDouble(txtHeight.getText().toString());
                    height = height * 30.48;

                    txtHeight.setText(String.format("%.2f", height));

                    strUnitHeight = "CM";
                } else {


                    double height = Double.parseDouble(txtHeight.getText().toString());
                    height = height / 30.48;

                    txtHeight.setText(String.format("%.2f", height));
                    strUnitHeight = "Feet";
                }
            }
        });




        switchWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switchWeight.isChecked() == true) {
                    double weight = Double.parseDouble(txtWeight.getText().toString());
                    weight = weight / 2.205;
                    //String.format("%.2f", weight);
                    txtWeight.setText(String.format("%.2f", weight));
                    strUnitWeight = "KG";

                } else {


                    double weight = Double.parseDouble(txtWeight.getText().toString());
                    weight = weight * 2.205;
                    // String.format("%.2f", weight);
                    txtWeight.setText(String.format("%.2f", weight));
                    strUnitWeight = "Lbs";
                }
            }
        });


        btnUpdate = view.findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });


        return view;
    }


    public void update() {

        if (validName() & validHeight() & validWeight() & validEmail() & validNumber() & validAge()) {



            String strName = txtName.getText().toString().trim();
            String strHeight = txtHeight.getText().toString().trim();
            String strAge = txtAge.getText().toString().trim();
            String strEmail = txtEmail.getText().toString().trim();
            String strNumber = txtNumber.getText().toString().trim();
            String strWeight = txtWeight.getText().toString().trim();


            rootNode = FirebaseDatabase.getInstance();

            DatabaseReference writetoDb = rootNode.getReference("Users");

            writetoDb.child(strUsername).child("fullName").setValue(strName);
            writetoDb.child(strUsername).child("phone").setValue(strNumber);
            writetoDb.child(strUsername).child("email").setValue(strEmail);


            writetoDb = rootNode.getReference("Users Details");

            writetoDb.child(strUsername).child("height").setValue(strHeight);
            writetoDb.child(strUsername).child("weight").setValue(strWeight);
            writetoDb.child(strUsername).child("age").setValue(strAge);
            writetoDb.child(strUsername).child("unitHeight").setValue(strUnitHeight);
            writetoDb.child(strUsername).child("unitWeight").setValue(strUnitWeight);

            Toast.makeText(getActivity(), "Your details have been updated", Toast.LENGTH_SHORT).show();

            txtFullName.setText(strName);
            showInfo();


        } else return;
    }


    public boolean validName() {

        String name = txtName.getText().toString().trim();

        if (name.isEmpty()) {
            txtName.setError("Field cannot be empty");
            return false;

        } else {
            txtName.setError(null);
            return true;
        }
    }


    public boolean validEmail() {

        String email = txtEmail.getText().toString().trim();

        if (email.isEmpty()) {
            txtEmail.setError("Field cannot be empty");
            return false;

        } else {
            txtEmail.setError(null);
            return true;
        }
    }


    public boolean validHeight() {

        String height = txtHeight.getText().toString().trim();

        if (height.isEmpty()) {
            txtHeight.setError("Field cannot be empty");
            return false;

        } else {
            txtHeight.setError(null);
            return true;
        }
    }


    public boolean validWeight() {

        String weight = txtWeight.getText().toString().trim();

        if (weight.isEmpty()) {
            txtWeight.setError("Field cannot be empty");
            return false;

        } else {
            txtWeight.setError(null);
            return true;
        }
    }


    private boolean validNumber() {

        String number = txtNumber.getText().toString().trim();

        if (number.isEmpty()) {
            txtNumber.setError("Field cannot be empty");
            txtNumber.requestFocus();
            return false;

        } else {
            txtNumber.setError(null);
            return true;
        }

    }


    private boolean validAge() {

        String age = txtAge.getText().toString().trim();

        if (age.isEmpty()) {
            txtAge.setError("Field cannot be empty");
            txtAge.requestFocus();
            return false;

        } else {
            txtAge.setError(null);
            return true;
        }


    }


    public void showInfo() {
        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

        DatabaseReference infoRef = rootRef;

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                if (snapshot.exists()) {


                    //  String name = snapshot.child("fullName").getValue().toString();

                    txtFullName.setText(snapshot.child("Users").child(strUsername).child("fullName").getValue().toString());
                    txtName.setText(snapshot.child("Users").child(strUsername).child("fullName").getValue().toString());
                    txtEmail.setText(snapshot.child("Users").child(strUsername).child("email").getValue().toString());
                    txtNumber.setText(snapshot.child("Users").child(strUsername).child("phone").getValue().toString());

                    txtAge.setText(snapshot.child("Users Details").child(strUsername).child("age").getValue().toString());
                    txtHeight.setText(snapshot.child("Users Details").child(strUsername).child("height").getValue().toString());
                    txtWeight.setText(snapshot.child("Users Details").child(strUsername).child("weight").getValue().toString());

                    strUnitHeight = snapshot.child("Users Details").child(strUsername).child("unitHeight").getValue().toString();

                    strUnitWeight = snapshot.child("Users Details").child(strUsername).child("unitWeight").getValue().toString();


                    String cm = "CM";
                    if (strUnitHeight.equals(cm)) {

                        switchHeight.setChecked(true);

                    } else {

                        switchHeight.setChecked(false);

                    }


                    String kg = "KG";
                    if (strUnitWeight.equals(kg)) {

                        switchWeight.setChecked(true);

                    } else {

                        switchWeight.setChecked(false);

                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        infoRef.addListenerForSingleValueEvent(eventListener);


    }


}
