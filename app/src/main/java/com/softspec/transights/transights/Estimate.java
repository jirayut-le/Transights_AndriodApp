package com.softspec.transights.transights;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Estimate extends Fragment {

    private Spinner deptSpn, arriSpn;
    private ArrayList<Station> stations;

    public Estimate() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_estimate, container, false);

        deptSpn = (Spinner) v.findViewById(R.id.spinner_dept);
        arriSpn = (Spinner) v.findViewById(R.id.spinner_arri);
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

}
