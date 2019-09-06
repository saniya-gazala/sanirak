package com.example.m.myapplication.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;


import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.m.myapplication.Model.Plants;
import com.example.m.myapplication.R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

public class MyAdapter  extends PagerAdapter {


    Context context;
    List<Plants> PlantsList;
    LayoutInflater inflater;


    public MyAdapter(Context context, List<Plants> plantsList) {
        this.context = context;
       this. PlantsList = plantsList;
       inflater = LayoutInflater.from(context);



    }

    @Override
    public int getCount() {
        return PlantsList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return  view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ((ViewPager)container).removeView((View)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        //Inflate view
        View view = inflater.inflate(R.layout.view_pager_item,container,false);

        //View

        ImageView plant_image  = (ImageView)view.findViewById(R.id.plant_image);
        TextView plant_title =  (TextView)view.findViewById(R.id.plant_title);
        TextView plant_description = (TextView)view.findViewById(R.id.plant_description);


         FloatingActionButton btn_fav= (FloatingActionButton)view.findViewById(R.id.btn_fav);


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Image Clicked", Toast.LENGTH_SHORT).show();
            }
        });


        //Event

        btn_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Liked", Toast.LENGTH_SHORT).show();

            }
        });

        //setData
        Picasso.get()
                .load(PlantsList.get(position).getImage()).into(plant_image);
        plant_title.setText(PlantsList.get(position).getName());
        plant_description.setText(PlantsList.get(position).getDescription());

        container.addView(view);
        return  view;

    }
}
