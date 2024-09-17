package com.example.myhealthdietapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LogIn extends AppCompatActivity {

    private EditText txtUsername, txtPassword;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);

        Button login = findViewById(R.id.btnLogIn);

        login.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                getWindow().setSoftInputMode(
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                );
                LogIn();
            }
        });


        Button btnSignIn =findViewById(R.id.btnSignIn);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogIn.this, SignIn.class);
                startActivity(intent);
            }
        });

    }





    @RequiresApi(api = Build.VERSION_CODES.N)
    public void LogIn() {

        final String strUser = txtUsername.getText().toString();



        if (validUsername() & validPass()) {


            final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

            final DatabaseReference ref = rootRef.child("Users").child(strUser);
            ValueEventListener eventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {

                    if (snapshot.exists()) {

                        txtUsername.setError(null);

                        String strPassword = txtPassword.getText().toString();

                        String dbPassword = snapshot.child("password").getValue(String.class);

                        if (dbPassword.equals(strPassword)) {

                            txtUsername.setError(null);

                            NewUser();

                        } else {
                            txtPassword.setError("Wrong Password Entered");
                            txtPassword.requestFocus();
                        }
                    } else {
                        txtUsername.setError("Username Does Not Exist");
                        txtUsername.requestFocus();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            };

            ref.addListenerForSingleValueEvent(eventListener);

        } else return;
    }




    @RequiresApi(api = Build.VERSION_CODES.N)
    private boolean validUsername() {

        String username = txtUsername.getText().toString().trim();

        if (username.isEmpty()) {
            txtUsername.setError("Field cannot be empty");
            return false;

        } else {
            txtUsername.setError(null);

            return true;
        }


    }




    private boolean validPass() {

        String password = txtPassword.getText().toString().trim();

        if (password.isEmpty()) {
            txtPassword.setError("Field cannot be empty");
            return false;

        } else {
            txtPassword.setError(null);
            return true;
        }


    }


    public void NewUser() {

        final String strUsername = txtUsername.getText().toString();


        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

        final DatabaseReference ref = rootRef.child("Users Details").child(strUsername);

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    getWindow().setSoftInputMode(
                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                    );
                    Intent intent = new Intent(LogIn.this, Home.class);
                    intent.putExtra("Username", strUsername);

                    startActivity(intent);

                } else {



                    Intent intent = new Intent(LogIn.this, DetailsandGoals.class);
                   intent.putExtra("Username", strUsername);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        ref.addListenerForSingleValueEvent(eventListener);
    }




}

