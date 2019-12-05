package com.example.myfinalproject;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.ParcelUuid;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.myfinalproject.db.AppDatabase;
import com.example.myfinalproject.db.Product;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyCartFragment extends Fragment {

    View root;

    TableLayout tl;
    String sTitle;

    String sName;
    String sQty;
    String sPrice;
    List<String> nameList = new ArrayList<String>();
    List<String> qtyList = new ArrayList<String>();
    List<String> priceList = new ArrayList<String>();
    int total;
    private Button btnCheckOut;
    private GetAPIProducts task;
    public Double price;
    public int listTotal;

    public SendTotal listener;

    interface SendTotal{
        void sendTotalToPayPal(Double total);

    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        Log.d("Created", "Listner");
    }

    public MyCartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_my_cart, container, false);

        tl = (TableLayout) root.findViewById(R.id.cartTable);

        nameList.clear();
        qtyList.clear();
        priceList.clear();
        price = 0.0;
        int i = 0;
        listTotal = 0;
        for (final String m:nameList) {

            Log.d("onCreate", priceList.get(i));
            Log.d("onCreate", qtyList.get(i));
            Double itemPrice = Double.valueOf(priceList.get(i));
            int itemQuantity = Integer.valueOf(qtyList.get(i));
            price += itemPrice * itemQuantity;
            i++;
        }
        Log.d("Total price", String.valueOf(price));
        btnCheckOut = (Button)root.findViewById(R.id.btnCheckout);
        btnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent i = new Intent(getActivity().getBaseContext(), PaypalActivity.class);
                i.putExtra("Total_Price", String.valueOf(price));
                getActivity().startActivity(i);


            }
        });


        return root;
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences sp = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor spEditor = sp.edit();

        //nameList.clear();
        //qtyList.clear();
        //priceList.clear();
        int tt = 0;
        if(nameList.size() >= 0){
            for (int i=0; i < nameList.size();i++){


                spEditor.putString("Name" + i, nameList.get(i));
                tt++;
            }
            int i2 = 0;
            for (int i = 0; i <qtyList.size(); i++){
                i2++;

                spEditor.putString("Qty" + i, qtyList.get(i));
            }
            for (int i = 0; i <priceList.size(); i++){
                i2++;

                spEditor.putString("Price" + i, priceList.get(i));
            }
            int value = tt;

            spEditor.putInt("total", value);
            spEditor.commit();

        }

        Log.d(" On Pause Name Size: ", String.valueOf(nameList.size()));
        //Log.d(" On Pause Name Size: ", String.valueOf(nameList.get(0)));
        Log.d(" On Pause qty Size: ", String.valueOf(qtyList.size()));
        Log.d(" On Pause price Size: ", String.valueOf(priceList.size()));
    }

    @Override
    public void onResume() {
        super.onResume();
        tl.removeAllViews();

        nameList.clear();
        qtyList.clear();
        priceList.clear();
        SharedPreferences sp = getActivity().getPreferences(Context.MODE_PRIVATE);
        int t = sp.getInt("total", total);


        for (int i = 0; i < t; i++){
            String name = sp.getString("Name" + (i), sName);
            String QtyTwo = sp.getString("Qty" + (i), sQty);
            nameList.add(sp.getString("Name" + i, sName));

            qtyList.add(sp.getString("Qty" + i, sQty));
            priceList.add(sp.getString("Price"+ i, sPrice));


        }


        Log.d(" On Resume Name Size: ", String.valueOf(nameList.size()));

        tl.removeAllViews();

        TextView[] textArray = new TextView[nameList.size()];
        TextView[] qtyArray = new TextView[qtyList.size()];
        TextView[] priceArray = new TextView[priceList.size()];
        TableRow[] tr_head = new TableRow[nameList.size()];

        if(nameList.size() > 0){

            for(int i = 0; i < nameList.size(); i++){
                String name = nameList.get(i);
                String qty = qtyList.get(i);
                String price = priceList.get(i);

                tr_head[i] = new TableRow(getContext());
                tr_head[i].setId(i+1);
                tr_head[i].setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT
                ));

                textArray[i] = new TextView(getContext());
                textArray[i].setId(i+111);
                textArray[i].setText("Name: " + name);
                textArray[i].setTextSize(10);
                textArray[i].setPadding(20,5,5,5);


                qtyArray[i] = new TextView(getContext());
                qtyArray[i].setId(i+222);
                qtyArray[i].setTextSize(10);
                qtyArray[i].setText("Quantity: " + qty);
                qtyArray[i].setPadding(20,5,20,5);

                priceArray[i] = new TextView(getContext());
                priceArray[i].setId(i+333);
                priceArray[i].setTextSize(10);
                priceArray[i].setText("Price: " + price);
                priceArray[i].setPadding(20,5,20,5);

                Button[] buttonArray  = new Button[nameList.size()];

                buttonArray[i] = new Button(getContext());
                buttonArray[i].setId(i+333);
                buttonArray[i].setText("Delete");
                buttonArray[i].setPadding(5,5,5,5);



                tr_head[i].addView(textArray[i]);
                tr_head[i].addView(qtyArray[i]);
                tr_head[i].addView(priceArray[i]);
                tr_head[i].addView(buttonArray[i]);

                tl.addView(tr_head[i], new TableLayout.LayoutParams(
                        TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT
                ));

                final int index = i;

                buttonArray[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        nameList.remove(index);
                        qtyList.remove(index);
                        priceList.remove(index);
                        tl.removeView(tl.getChildAt(index));
                        reinitializeTable();

                    }
                });
            }
        }

        price = 0.0;
        int i = 0;
        listTotal = 0;

        for (final String m:nameList) {

            Log.d("onCreate", priceList.get(i));
            Log.d("onCreate", qtyList.get(i));
            Double itemPrice = Double.valueOf(priceList.get(i));
            int itemQuantity = Integer.valueOf(qtyList.get(i));
            price += itemPrice * itemQuantity;
            i++;
        }
        Log.d("Total price", String.valueOf(price));
        

        //listener.sendTotalToPayPal(price);
    }

    public void reinitializeTable() {
        tl.removeAllViews();

        TextView[] textArray = new TextView[nameList.size()];
        TextView[] qtyArray = new TextView[qtyList.size()];
        TextView[] priceArray = new TextView[priceList.size()];
        TableRow[] tr_head = new TableRow[nameList.size()];

        if (nameList.size() >= 0) {

            for (int i = 0; i < nameList.size(); i++) {
                String name = nameList.get(i);
                String qty = qtyList.get(i);
                String fPrice = priceList.get(i);

                tr_head[i] = new TableRow(getContext());
                tr_head[i].setId(i + 1);
                tr_head[i].setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT
                ));

                textArray[i] = new TextView(getContext());
                textArray[i].setId(i + 111);
                textArray[i].setText("Name: " + name);
                //Change Text Size
                textArray[i].setTextSize(10);
                textArray[i].setPadding(20, 5, 5, 5);


                qtyArray[i] = new TextView(getContext());
                qtyArray[i].setId(i + 222);
                qtyArray[i].setTextSize(10);
                qtyArray[i].setText("Quantity: " + qty);
                qtyArray[i].setPadding(20, 5, 20, 5);

                priceArray[i] = new TextView(getContext());
                priceArray[i].setId(i+333);
                priceArray[i].setTextSize(10);
                priceArray[i].setText("Price: " + fPrice);
                priceArray[i].setPadding(20,5,20,5);

                Button[] buttonArray = new Button[nameList.size()];

                buttonArray[i] = new Button(getContext());
                buttonArray[i].setId(i + 333);
                buttonArray[i].setText("Delete");
                buttonArray[i].setPadding(5, 5, 5, 5);


                tr_head[i].addView(textArray[i]);
                tr_head[i].addView(qtyArray[i]);
                tr_head[i].addView(priceArray[i]);
                tr_head[i].addView(buttonArray[i]);

                tl.addView(tr_head[i], new TableLayout.LayoutParams(
                        TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT
                ));

                final int index = i;

                buttonArray[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        nameList.remove(index);
                        qtyList.remove(index);
                        priceList.remove(index);
                        tl.removeView(tl.getChildAt(index));
                        reinitializeTable();

                    }
                });
            }
        }


    }


        public void addItemsToList(String name, String value, String price){

            nameList.add(name);
            qtyList.add(value);
            priceList.add(price);
        }
}
