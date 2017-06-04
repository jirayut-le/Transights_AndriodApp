package com.softspec.transights.transights;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Estimate extends Fragment {

    private TextView time, price;
    private Spinner deptSpn, arriSpn;
    private Button calculateBtn;
    private ArrayList<Station> stations;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_estimate, container, false);

        deptSpn = (Spinner) v.findViewById(R.id.spinner_dept);
        arriSpn = (Spinner) v.findViewById(R.id.spinner_arri);
        time = (TextView) v.findViewById(R.id.time_result);
        price = (TextView) v.findViewById(R.id.price_result);
        calculateBtn = (Button) v.findViewById(R.id.calculateBtn);

        calculateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(deptSpn.getSelectedItem() != null && arriSpn.getSelectedItem() != null){
                    String dept = deptSpn.getSelectedItem().toString();
                    String arri = arriSpn.getSelectedItem().toString();

                    if(!dept.equalsIgnoreCase("Select Depart Station") && !arri.equalsIgnoreCase("Select Arrive Station"))
                        calculate(dept, arri);
                    else
                        Toast.makeText(getContext(), "Please select both depart and arrive station.", Toast.LENGTH_LONG).show();
                }

            }
        });
        return v;
    }

    public void setupSpinner(ArrayList<Station> stations){
        this.stations = stations;
        List<String> list = new ArrayList<>();
        list.add("Select Depart Station");
        list = addStationToList(list);
        deptSpn.setAdapter(getArrayAdapterSpinner(list));

        List<String> list2 = new ArrayList<>();
        list2.add("Select Arrive Station");
        list2 = addStationToList(list2);
        arriSpn.setAdapter(getArrayAdapterSpinner(list2));
    }

    private List<String> addStationToList(List<String> list){
        for(Station s : stations)
            list.add(s.getStationName());
        return list;
    }

    private ArrayAdapter<String> getArrayAdapterSpinner(List<String> list){
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return  dataAdapter;
    }

    public void calculate(String dept, String arri){
        for(Station s : stations)
            if(s.getStationName().equalsIgnoreCase(dept))
                for(Station s2 : s.getArriveStation())
                    if(s2.getStationName().equalsIgnoreCase(arri)){
                        setPriceAndTime(s2.getPrice(), s2.getTime());
                    }
    }

    private void setPriceAndTime(int price, int time){
        this.time.setText(Integer.toString(time));
        this.price.setText(Integer.toString(price));
    }


}
