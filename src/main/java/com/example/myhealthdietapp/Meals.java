package com.example.myhealthdietapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Meals extends AppCompatActivity {


    ImageView imgBreakfast, imgLunch, imgDinner;
    Button btnCamBreakfast, btnCamLunch, btnCamDinner, btnBreak, btnLunch, btnDinner;
    String cam, strUsername;

    MealsClass mealsClass;

    FirebaseStorage storage;
    StorageReference storageReference;



    String currentPhotoPath;

    Uri contentUri;
    File f;

    public static int CAMERA_PERM_CODE = 101;
    public static int CAMERA_REQUEST_CODE = 102;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meals);

        Intent i = getIntent();

        strUsername = i.getStringExtra("Username");

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


        imgBreakfast = findViewById(R.id.imgBreakfast);
        btnCamBreakfast = findViewById(R.id.btnCamBreakfast);

        btnCamBreakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cam = "Breakfast";
                camPermission();

            }
        });


        imgLunch = findViewById(R.id.imgLunch);
        btnCamLunch = findViewById(R.id.btnCamLunch);

        btnCamLunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cam = "Lunch";
                camPermission();

            }
        });


        imgDinner = findViewById(R.id.imgDinner);
        btnCamDinner = findViewById(R.id.btnCamDinner);

        btnCamDinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cam = "Dinner";
                camPermission();

            }
        });


        btnBreak = findViewById(R.id.btnAddBreakfast);
        btnBreak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                breakfast();
            }
        });


        btnLunch = findViewById(R.id.btnAddLunch);
        btnLunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lunch();
            }
        });


        btnDinner = findViewById(R.id.btnAddDinner);
        btnDinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dinner();
            }
        });

    }


    public void camPermission() {
        if (ContextCompat.checkSelfPermission(Meals.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Meals.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERM_CODE);
        } else {
            dispatchTakePictureIntent();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_PERM_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent();
            } else {
                Toast.makeText(this, "require camera permission", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";

        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
            }
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == CAMERA_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {

                f = new File(currentPhotoPath);
                Log.d("tag", "Absolute Url of Image is " + Uri.fromFile(f));

                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                contentUri = Uri.fromFile(f);
                mediaScanIntent.setData(contentUri);
                this.sendBroadcast(mediaScanIntent);

                if (cam == "Breakfast") {


                    imgBreakfast.setImageURI(Uri.fromFile(f));
                    uploadImage(f.getName(), contentUri);

                } else if (cam == "Lunch") {

                    imgLunch.setImageURI(Uri.fromFile(f));
                    uploadImage(f.getName(), contentUri);

                } else if (cam == "Dinner") {

                    imgDinner.setImageURI(Uri.fromFile(f));
                    uploadImage(f.getName(), contentUri);
                }
            }
        }


    }




    public void uploadImage(String name, Uri contentUri) {
        final StorageReference image = storageReference.child("images/" + name);
        image.putFile(contentUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                image.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.d("tag", "onSuccess: Upload Image URL is " + uri.toString());
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Meals.this, "Upload Unsuccessful", Toast.LENGTH_SHORT).show();
            }
        });
    }





    public void breakfast() {
        final EditText txtProtein = findViewById(R.id.txtProtBreakfast);
        final EditText txtCarbs = findViewById(R.id.txtCarbsBreakfast);
        final EditText txtFat = findViewById(R.id.txtFatBreakfast);
        final EditText txtCal = findViewById(R.id.txtCalBreakfast);
        final EditText txtDesc = findViewById(R.id.txtDescBreakfast);

        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

        DatabaseReference ref = rootRef.child("GoalInfo").child(strUsername);

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {


                if (!snapshot.exists()) {

                    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                    DatabaseReference writetoDb = rootRef.child("GoalInfo");

                    String protein = txtProtein.getText().toString();
                    String carbs = txtCarbs.getText().toString();
                    String fat = txtFat.getText().toString();
                    String cal = txtCal.getText().toString();
                    String desc = txtDesc.getText().toString();

                    mealsClass = new MealsClass(cal,protein, carbs, fat, f.getName(), desc);

                    writetoDb.child(strUsername).child("Meals").child("Breakfast").setValue(mealsClass);
                    Toast.makeText(Meals.this, "Save Successful", Toast.LENGTH_SHORT).show();

                } else {

                    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                    DatabaseReference writetoDb = rootRef.child("GoalInfo").child(strUsername);

                    String protein = txtProtein.getText().toString();
                    String carbs = txtCarbs.getText().toString();
                    String fat = txtFat.getText().toString();
                    String cal = txtCal.getText().toString();
                    String desc = txtDesc.getText().toString();

                    mealsClass = new MealsClass(cal,protein, carbs, fat, f.getName(), desc);

                    writetoDb.child("Meals").child("Breakfast").setValue(mealsClass);
                    Toast.makeText(Meals.this, "Save Successful", Toast.LENGTH_SHORT).show();
                }


            }

            public void onCancelled(DatabaseError error) {

            }
        };
        ref.addListenerForSingleValueEvent(eventListener);

    }


    public void lunch() {
        final EditText txtProtein = findViewById(R.id.txtProtLunch);
        final EditText txtCarbs = findViewById(R.id.txtCarbsLunch);
        final EditText txtFat = findViewById(R.id.txtFatLunch);
        final EditText txtCal = findViewById(R.id.txtCalLunch);
        final EditText txtDesc = findViewById(R.id.txtDescLunch);


        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

        DatabaseReference ref = rootRef.child("GoalInfo").child(strUsername);

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {


                if (!snapshot.exists()) {

                    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                    DatabaseReference writetoDb = rootRef.child("GoalInfo");

                    String protein = txtProtein.getText().toString();
                    String carbs = txtCarbs.getText().toString();
                    String fat = txtFat.getText().toString();
                    String cal = txtCal.getText().toString();
                    String desc = txtDesc.getText().toString();

                    mealsClass = new MealsClass(cal,protein, carbs, fat, f.getName(), desc);

                    writetoDb.child(strUsername).child("Meals").child("Lunch").setValue(mealsClass);

                    Toast.makeText(Meals.this, "Save Successful", Toast.LENGTH_SHORT).show();


                } else {
                    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

                    DatabaseReference writetoDb = rootRef.child("GoalInfo").child(strUsername);

                    String protein = txtProtein.getText().toString();
                    String carbs = txtCarbs.getText().toString();
                    String fat = txtFat.getText().toString();
                    String cal = txtCal.getText().toString();
                    String desc = txtDesc.getText().toString();

                    mealsClass = new MealsClass(cal,protein, carbs, fat, f.getName(), desc);

                    writetoDb.child("Meals").child("Lunch").setValue(mealsClass);

                    Toast.makeText(Meals.this, "Save Successful", Toast.LENGTH_SHORT).show();

                }


            }

            public void onCancelled(DatabaseError error) {

            }
        };
        ref.addListenerForSingleValueEvent(eventListener);
    }


    public void dinner() {
        final EditText txtProtein = findViewById(R.id.txtProtDinner);
        final EditText txtCarbs = findViewById(R.id.txtCarbsDinner);
        final EditText txtFat = findViewById(R.id.txtFatDinner);
        final EditText txtCal = findViewById(R.id.txtCalDinner);
        final EditText txtDesc = findViewById(R.id.txtDescDinner);


        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

        DatabaseReference ref = rootRef.child("GoalInfo").child(strUsername);

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {


                if (!snapshot.exists()) {

                    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                    DatabaseReference writetoDb = rootRef.child("GoalInfo");

                    String protein = txtProtein.getText().toString();
                    String carbs = txtCarbs.getText().toString();
                    String fat = txtFat.getText().toString();
                    String cal = txtCal.getText().toString();
                    String desc = txtDesc.getText().toString();

                    mealsClass = new MealsClass(cal,protein, carbs, fat, f.getName(), desc);

                    writetoDb.child(strUsername).child("Meals").child("Dinner").setValue(mealsClass);

                    Toast.makeText(Meals.this, "Save Successful", Toast.LENGTH_SHORT).show();


                } else {

                    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                    DatabaseReference writetoDb = rootRef.child("GoalInfo").child(strUsername);

                    String protein = txtProtein.getText().toString();
                    String carbs = txtCarbs.getText().toString();
                    String fat = txtFat.getText().toString();
                    String cal = txtCal.getText().toString();
                    String desc = txtDesc.getText().toString();

                    mealsClass = new MealsClass(cal,protein, carbs, fat, f.getName(), desc);

                    writetoDb.child("Meals").child("Dinner").setValue(mealsClass);

                    Toast.makeText(Meals.this, "Save Successful", Toast.LENGTH_SHORT).show();

                }


            }

            public void onCancelled(DatabaseError error) {

            }
        };
        ref.addListenerForSingleValueEvent(eventListener);
    }


}
