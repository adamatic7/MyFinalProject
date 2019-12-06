package com.example.myfinalproject;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {


    View root;

    int poTotal;
    String poNumber;
    List<String> poNumberList = new ArrayList<String>();

    TableLayout tl;
    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_account, container, false);

        tl = (TableLayout)root.findViewById(R.id.mainTable);


        return root;
    }

    @Override
    public void onResume() {
        super.onResume();


        tl.removeAllViews();
        SharedPreferences sp = getActivity().getPreferences(Context.MODE_PRIVATE);
        int t = sp.getInt("poTotal", poTotal);

        Log.d("Account", String.valueOf(t));
        poNumberList.clear();


        for (int i = 0; i < t; i++){
            String name = sp.getString("poNumber" + (i), poNumber);

            poNumberList.add(sp.getString("poNumber" + i, poNumber));
        }

        tl.removeAllViews();

        TextView[] textArray = new TextView[poNumberList.size()];
        TableRow[] tr_head = new TableRow[poNumberList.size()];

        if(poNumberList.size() > 0){

            for(int i = 0; i < poNumberList.size(); i++){
                String name = poNumberList.get(i);

                tr_head[i] = new TableRow(getContext());
                tr_head[i].setId(i+1);
                tr_head[i].setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT
                ));

                textArray[i] = new TextView(getContext());
                textArray[i].setId(i+111);
                textArray[i].setText("Purchase Order: " + name);
                textArray[i].setTextSize(20);
                textArray[i].setPadding(20,5,5,5);




                tr_head[i].addView(textArray[i]);


                tl.addView(tr_head[i], new TableLayout.LayoutParams(
                        TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT
                ));

                final int index = i;


            }
        }
    }
}
