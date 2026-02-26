package com.example.lab5_starter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class CityArrayAdapter extends ArrayAdapter<City> {
    private ArrayList<City> cities;
    private Context context;

    public CityArrayAdapter(Context context, ArrayList<City> cities){
        super(context, 0, cities);
        this.cities = cities;
        this.context = context;
    }

    @NonNull
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View view = convertView;
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.layout_city, parent, false);
        }

        City city = cities.get(position);
        TextView cityName = view.findViewById(R.id.textCityName);
        TextView cityYear = view.findViewById(R.id.textCityProvince);
        Button deleteButton = view.findViewById((R.id.buttonDeleteCity));

        cityName.setText(city.getName());
        cityYear.setText(city.getProvince());
        deleteButton.setOnClickListener(v -> {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("cities").document(city.getName())
                    .delete()
                    .addOnSuccessListener(aVoid -> Log.d("Firestore" , "city Successfully deleted"))
                    .addOnFailureListener(e -> Log.e("Firestore" , "Error deleting city ;-; " , e));
        });

        return view;
    }
}
