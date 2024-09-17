package com.example.myhealthdietapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.BreakIterator;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProgressFrag extends Fragment {


    public ProgressFrag() {
        // Required empty public constructor
    }

    View view;
    LineChart lineChart;
    ArrayList<Entry> arrWeight = new ArrayList<>();
    ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
    String strUsername;
    int num = 1;
    Button btnAddWeight;
    EditText txtWeight;

    ProgressClass progressClass;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_progress, container, false);

        lineChart = view.findViewById(R.id.LineChart);

        Intent i = getActivity().getIntent();
        strUsername = i.getStringExtra("Username");

        showCharInfo();

        btnAddWeight = view.findViewById(R.id.btnAddWeight);
        btnAddWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData();
            }
        });


        return view;
    }


    public void addData() {


        txtWeight = view.findViewById(R.id.txtWeight);


        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

        DatabaseReference ref = rootRef.child("Progress").child(strUsername);//.child("Weight" + num)

        progressClass = new ProgressClass(num, Integer.parseInt(txtWeight.getText().toString()));

        ref.child("Weight" + num).setValue(progressClass);

        DatabaseReference UserRef = rootRef.child("Users Details").child(strUsername);
        UserRef.child("weight").setValue(txtWeight.getText().toString());

        showCharInfo();
        showCharInfo();
        showCharInfo();
        showCharInfo();

    }


    public void showCharInfo() {

        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

        lineChart.clear();
        arrWeight.clear();
        iLineDataSets.clear();
        lineChart.invalidate();
        lineChart.clear();
        num = 1;

        for (int l = 1; l <= 100; l++) {


            DatabaseReference ref = rootRef.child("Progress").child(strUsername).child("Weight" + l); //


            ValueEventListener eventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {


                    if (snapshot.exists()) {

                        progressClass = new ProgressClass();
                        progressClass.setNum(Integer.parseInt(snapshot.child("num").getValue().toString()));
                        progressClass.setWeight(Integer.parseInt(snapshot.child("weight").getValue().toString()));


                        arrWeight.add(new Entry(progressClass.getNum(), progressClass.getWeight()));

                        LineDataSet lineDataSet = new LineDataSet(null, null);

                        lineDataSet.setValues(arrWeight);
                        lineDataSet.setLabel(snapshot.toString());

                        iLineDataSets.clear();
                        iLineDataSets.add(lineDataSet);


                        lineDataSet.setValueTextSize(16f);

                        LineData lineData = new LineData(iLineDataSets);

                        lineChart.clear();


                        lineChart.setData(lineData);
                        lineChart.invalidate();

                        num++;

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };

            ref.addListenerForSingleValueEvent(eventListener);

        }


    }


}
