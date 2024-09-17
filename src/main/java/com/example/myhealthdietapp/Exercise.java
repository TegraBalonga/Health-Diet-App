package com.example.myhealthdietapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Exercise extends AppCompatActivity {

    Button btnLay1, btnLay2,btnLay3;

    String strUsername;
    ExerciseClass exerciseClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);


        Intent i = getIntent();

        strUsername = i.getStringExtra("Username");



        btnLay1 = findViewById(R.id.btnEx1);
        btnLay1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Lay1();
            }
        });

        btnLay2 = findViewById(R.id.btnEx2);
        btnLay2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Lay2();
            }
        });

        btnLay3 = findViewById(R.id.btnEx3);
        btnLay3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Lay3();
            }
        });
    }




    public void Lay1(){
        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

        DatabaseReference ref = rootRef.child("GoalInfo").child(strUsername);

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                TextView txtEx1 = findViewById(R.id.txtEx1Lay1);
                TextView txtEx2 = findViewById(R.id.txtEx2Lay1);
                TextView txtEx3 = findViewById(R.id.txtEx3Lay1);
                TextView txtEx4 = findViewById(R.id.txtEx4Lay1);
                if (!snapshot.exists()) {


                    DatabaseReference writetoDb = rootRef.child("GoalInfo");


                    exerciseClass = new ExerciseClass(txtEx1.getText().toString(), txtEx2.getText().toString(),
                            txtEx3.getText().toString(), txtEx4.getText().toString());

                    writetoDb.child(strUsername).child("exercises").setValue(exerciseClass);


                    Intent intent = new Intent(Exercise.this, Home.class);
                    intent.putExtra("Username", strUsername);
                    startActivity(intent);

                }else{

                    DatabaseReference writetoDb = rootRef.child("GoalInfo").child(strUsername);


                    exerciseClass = new ExerciseClass(txtEx1.getText().toString(), txtEx2.getText().toString(),
                            txtEx3.getText().toString(), txtEx4.getText().toString());

                    writetoDb.child("exercises").setValue(exerciseClass);


                    Intent intent = new Intent(Exercise.this, Home.class);
                    intent.putExtra("Username", strUsername);
                    startActivity(intent);

                }


            }

            public void onCancelled(DatabaseError error) {

            }
        };
        ref.addListenerForSingleValueEvent(eventListener);
    }







    public void Lay2(){
        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

        DatabaseReference ref = rootRef.child("GoalInfo").child(strUsername);

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                TextView txtEx1 = findViewById(R.id.txtEx1Lay2);
                TextView txtEx2 = findViewById(R.id.txtEx2Lay2);
                TextView txtEx3 = findViewById(R.id.txtEx3Lay2);
                TextView txtEx4 = findViewById(R.id.txtEx4Lay2);

                if (!snapshot.exists()) {



                    DatabaseReference writetoDb = rootRef.child("GoalInfo");


                    exerciseClass = new ExerciseClass(txtEx1.getText().toString(), txtEx2.getText().toString(),
                            txtEx3.getText().toString(), txtEx4.getText().toString());

                    writetoDb.child(strUsername).child("exercises").setValue(exerciseClass);


                    Intent intent = new Intent(Exercise.this, Home.class);
                    intent.putExtra("Username", strUsername);
                    startActivity(intent);

                }else{

                    DatabaseReference writetoDb = rootRef.child("GoalInfo").child(strUsername);


                    exerciseClass = new ExerciseClass(txtEx1.getText().toString(), txtEx2.getText().toString(),
                            txtEx3.getText().toString(), txtEx4.getText().toString());

                    writetoDb.child("exercises").setValue(exerciseClass);


                    Intent intent = new Intent(Exercise.this, Home.class);
                    intent.putExtra("Username", strUsername);
                    startActivity(intent);

                }


            }

            public void onCancelled(DatabaseError error) {

            }
        };
        ref.addListenerForSingleValueEvent(eventListener);
    }







    public void Lay3(){
        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

        DatabaseReference ref = rootRef.child("GoalInfo").child(strUsername);

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                TextView txtEx1 = findViewById(R.id.txtEx1Lay3);
                TextView txtEx2 = findViewById(R.id.txtEx2Lay3);
                TextView txtEx3 = findViewById(R.id.txtEx3Lay3);
                TextView txtEx4 = findViewById(R.id.txtEx4Lay3);

                if (!snapshot.exists()) {



                    DatabaseReference writetoDb = rootRef.child("GoalInfo");


                    exerciseClass = new ExerciseClass(txtEx1.getText().toString(), txtEx2.getText().toString(),
                            txtEx3.getText().toString(), txtEx4.getText().toString());

                    writetoDb.child(strUsername).child("exercises").setValue(exerciseClass);


                    Intent intent = new Intent(Exercise.this, Home.class);
                    intent.putExtra("Username", strUsername);
                    startActivity(intent);

                }else{

                    DatabaseReference writetoDb = rootRef.child("GoalInfo").child(strUsername);


                    exerciseClass = new ExerciseClass(txtEx1.getText().toString(), txtEx2.getText().toString(),
                            txtEx3.getText().toString(), txtEx4.getText().toString());

                    writetoDb.child("exercises").setValue(exerciseClass);


                    Intent intent = new Intent(Exercise.this, Home.class);
                    intent.putExtra("Username", strUsername);
                    startActivity(intent);

                }


            }

            public void onCancelled(DatabaseError error) {

            }
        };
        ref.addListenerForSingleValueEvent(eventListener);
    }






}
