package com.example.myhealthdietapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFrag extends Fragment {

    public HomeFrag() {
        // Required empty public constructor
    }

    private TextView txtVQuote;
    private EditText txtHours;
    private View view;
    private String strUsername;


    PieChart pieChart;
    Button btnActivity, btnMeals, btnPlus, btnMinus;

    public ArrayList<PieEntry> userInfo = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

      //  getActivity().getWindow().setSoftInputMode(
          //      WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
       // );

        Intent i = getActivity().getIntent();
        strUsername = i.getStringExtra("Username");

        pieChart = view.findViewById(R.id.UserPieChart);

        Created();
        pie();







        btnActivity = view.findViewById(R.id.btnActivity);
        btnActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity();
            }
        });


        btnMeals = view.findViewById(R.id.btnMeals);
        btnMeals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Meals();
            }
        });


        btnPlus = view.findViewById(R.id.btnPlus);
        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean increased = true;
                Sleep(increased);
            }
        });


        btnMinus = view.findViewById(R.id.btnMinus);
        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean increased = false;
                Sleep(increased);
            }
        });

        return view;


    }




    /////////////////////////////////////////////////////////CREATED METHOD////////////////////////////////////////////////////////////////////////////
    public void Created() {


        txtVQuote = view.findViewById(R.id.txtVQuote);

        String strDay = "";

        Calendar calendar = Calendar.getInstance();

        int day = calendar.get(Calendar.DAY_OF_WEEK);

        switch (day) {

            case Calendar.MONDAY:

                strDay = "Monday";
                getQuote(strDay);
                break;

            case Calendar.TUESDAY:

                strDay = "Tuesday";
                getQuote(strDay);
                break;

            case Calendar.WEDNESDAY:

                strDay = "Wednesday";
                getQuote(strDay);
                break;

            case Calendar.THURSDAY:

                strDay = "Thursday";
                getQuote(strDay);
                break;

            case Calendar.FRIDAY:

                strDay = "Friday";
                getQuote(strDay);
                break;

            case Calendar.SATURDAY:

                strDay = "Saturday";
                getQuote(strDay);
                break;

            case Calendar.SUNDAY:

                strDay = "Sunday";
                getQuote(strDay);
                break;
        }


    }





    ///////////////////////////////////////////////////////GETQUOTE METHOD//////////////////////////////////////////////////////////////////////////////
    public void getQuote(final String day) {

        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

        DatabaseReference quoteRef = rootRef.child("Quotes").child(day);

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {


                if (snapshot.exists()) {


                    txtVQuote.setText(snapshot.getValue().toString());


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        quoteRef.addListenerForSingleValueEvent(eventListener);
    }





    ////////////////////////////////////////////////////////PIE METHOD/////////////////////////////////////////////////////////////////////////////

    public void pie() {


        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

        DatabaseReference ref = rootRef.child("Users Details").child(strUsername);

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {


                if (snapshot.exists()) {


                    String weight = snapshot.child("weight").getValue().toString();

                    String sysPressure = snapshot.child("bloodPressure").child("systolicPressure").getValue().toString();

                    String diaPressure = snapshot.child("bloodPressure").child("diastolicPressure").getValue().toString();

                    String bRate = snapshot.child("blood rate").getValue().toString();



                    userInfo.add(new PieEntry(Float.parseFloat(weight), "Weight"));
                    userInfo.add(new PieEntry(Float.parseFloat(sysPressure), "Systolic pressure"));
                    userInfo.add(new PieEntry(Float.parseFloat(diaPressure), "Diastolic pressure"));
                    userInfo.add(new PieEntry(Float.parseFloat(bRate), "Blood rate"));


                    PieDataSet pieDataSet = new PieDataSet(userInfo, "");
                    pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                    pieDataSet.setValueTextColor(Color.BLACK);

                    pieDataSet.setValueTextSize(16f);


                    PieData pieData = new PieData(pieDataSet);


                    Legend legend = pieChart.getLegend();
                    legend.setTextColor(Color.BLACK);
                    legend.setTextSize(11f);


                    pieChart.setData(pieData);
                    pieChart.getDescription().setEnabled(false);
                    pieChart.setCenterText("User's info");
                    pieChart.setCenterTextColor(Color.BLACK);
                    pieChart.setCenterTextSize(16f);
                    pieChart.setEntryLabelColor(Color.BLACK);
                    pieChart.animate();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        ref.addListenerForSingleValueEvent(eventListener);

    }


    ////////////////////////////////////////////////////////SLEEP METHOD/////////////////////////////////////////////////////////////////////////////


    public void Sleep(boolean increased) {

        txtHours = view.findViewById(R.id.txtHours);

        int value = Integer.parseInt(txtHours.getText().toString());

        if (increased) {
            value++;


        } else {

            value--;

        }

        txtHours.setText(Integer.toString(value));
       // Toast.makeText(getActivity(), txtHours.getText().toString(), Toast.LENGTH_SHORT).show();
        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

        DatabaseReference ref = rootRef.child("GoalInfo").child(strUsername);

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {


                if (!snapshot.exists()) {


                    DatabaseReference writetoDb = rootRef.child("GoalInfo");

                    writetoDb.child(strUsername).child("sleeping hours").setValue(txtHours.getText().toString());


                } else {

                    DatabaseReference writetoDb = rootRef.child("GoalInfo").child(strUsername);

                    writetoDb.child("sleeping hours").setValue(txtHours.getText().toString());

                }


            }

            public void onCancelled(DatabaseError error) {

            }
        };
        ref.addListenerForSingleValueEvent(eventListener);

    }


    ////////////////////////////////////////////////////////ACTIVITY METHOD/////////////////////////////////////////////////////////////////////////////


    public void Activity() {

        Intent intent = new Intent(getActivity(), Exercise.class);
        intent.putExtra("Username", strUsername);
        startActivity(intent);

    }


    ////////////////////////////////////////////////////////MEALS METHOD/////////////////////////////////////////////////////////////////////////////


    public void Meals() {

        Intent intent = new Intent(getActivity(), Meals.class);
        intent.putExtra("Username", strUsername);
        startActivity(intent);

    }


}
