package com.example.myhealthdietapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DetailsandGoals extends AppCompatActivity {

    private EditText txtHeight, txtWeight, txtAge;

    private RadioGroup groupUnitHeight, groupUnitWeight, groupGender;

    private RadioButton rdBtnUnitHeight, rdBtnUnitWeight, rdBtnGender;

    private Button btnGain, btnMaintain, btnLose, btnSave;

    private FirebaseDatabase rootNode;

    private String strUsername, strHeight, strWeight, strAge, strUnitHeight, strUnitWeight, strGender, strGoal;

    UsersDetailsClass usersDetailsClass;
    ProgressClass progressClass;

    Switch switchHeight, switchWeight, switchAge;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailsand_goals);

        txtHeight = findViewById(R.id.txtHeight1);
        txtWeight = findViewById(R.id.txtWeight);
        txtAge = findViewById(R.id.txtAge);


        btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Save();

            }
        });


        btnGain = findViewById(R.id.btnGainW);

        btnGain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strGoal = "Gain weight";
                Details();

            }
        });


        btnMaintain = findViewById(R.id.btnMaintainW);

        btnMaintain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strGoal = "Maintain weight";
                Details();

            }
        });


        btnLose = findViewById(R.id.btnLoseW);

        btnLose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strGoal = "Lose weight";
                Details();

            }
        });
    }


    public void Save() {
        switchHeight = findViewById(R.id.swHeight);
        switchWeight = findViewById(R.id.swWeight);
        switchAge = findViewById(R.id.swAge);


        if (validHeight() & validWeight() & validAge()) {

            strHeight = txtHeight.getText().toString().trim();
            strWeight = txtWeight.getText().toString().trim();
            strAge = txtAge.getText().toString().trim();


            findUnitsAndGender();

        } else return;

    }


    public void Details() {


        if (validHeight() & validWeight() & validAge()) {

            Intent i = getIntent();

            strUsername = i.getStringExtra("Username");


            rootNode = FirebaseDatabase.getInstance();

            DatabaseReference writetoDb = rootNode.getReference("Users Details");

            usersDetailsClass = new UsersDetailsClass(strHeight, strWeight, strAge, strUnitHeight, strUnitWeight, strGender, strGoal);

            writetoDb.child(strUsername).setValue(usersDetailsClass);
            writetoDb.child(strUsername).child("bloodPressure").child("diastolicPressure").setValue(80);
            writetoDb.child(strUsername).child("bloodPressure").child("systolicPressure").setValue(120);
            writetoDb = rootNode.getReference("Progress");
            progressClass = new ProgressClass(1, Integer.parseInt(strWeight));

            writetoDb.child(strUsername).child("Weight1").setValue(progressClass);

            Toast.makeText(getApplicationContext(), "Your details have been saved", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(DetailsandGoals.this, Home.class);
            startActivity(intent);
        } else return;

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


    private boolean validWeight() {

        String weight = txtWeight.getText().toString().trim();

        if (weight.isEmpty()) {
            txtWeight.setError("Field cannot be empty");
            txtWeight.requestFocus();
            return false;

        } else {
            txtWeight.setError(null);
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


    public void findUnitsAndGender() {


        if (switchHeight.isChecked() == false) {
            strUnitHeight = "Feet";
        } else {
            strUnitHeight = "CM";
        }

        if (switchWeight.isChecked() == false) {
            strUnitWeight = "Lbs";
        } else {
            strUnitWeight = "KG";
        }

        if (switchAge.isChecked() == false) {
            strGender = "Male";
        } else {
            strGender = "Female";
        }

    }

}
