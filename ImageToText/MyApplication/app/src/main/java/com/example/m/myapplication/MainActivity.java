package com.example.m.myapplication;

import android.app.AlertDialog;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.Toast;

import com.example.m.myapplication.Adapter.MyAdapter;
import com.example.m.myapplication.Interface.IFireStoreLoadDone;
import com.example.m.myapplication.Model.Plants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class MainActivity extends AppCompatActivity implements IFireStoreLoadDone {

    ViewPager viewPager;
    MyAdapter myAdapter;

 IFireStoreLoadDone iFireStoreLoadDone;

 CollectionReference Plants;

AlertDialog Dailog;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Init

        iFireStoreLoadDone = this;
        Plants = FirebaseFirestore.getInstance().collection("Plants");

        Dailog = new SpotsDialog.Builder().setContext(this).build();


        //view
        viewPager = (ViewPager)findViewById(R.id.view_pager);


        getData();



    }

    private void getData() {

        Dailog.dismiss();

        Plants.get()
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        iFireStoreLoadDone.onFireStoreLoadFailed(e.getMessage());
                    }
                }).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){

                    List<Plants> plants = new ArrayList<>();
                    for(QueryDocumentSnapshot PlantsSnapShot : task.getResult()){
                        Plants plant = PlantsSnapShot.toObject(Plants.class);
                        plants.add(plant);

                    }
                    iFireStoreLoadDone.onFireStoreLoadSuccess(plants);
                }
            }
        });
    }

    @Override
    public void onFireStoreLoadSuccess(List<com.example.m.myapplication.Model.Plants> plants) {
        myAdapter = new MyAdapter(this,plants);
        viewPager.setAdapter(myAdapter);

        Dailog.dismiss();
    }

    @Override
    public void onFireStoreLoadFailed(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        Dailog.dismiss();
    }



}
