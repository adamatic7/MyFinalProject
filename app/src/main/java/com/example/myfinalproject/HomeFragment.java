package com.example.myfinalproject;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myfinalproject.db.AppDatabase;
import com.example.myfinalproject.db.Product;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private GetAPIProducts task;
    Product product;
    private String product_pk;
    public TextView txtProductTitle, txtProductPrice;
    //public ImageView imgProduct;

    View root;
    public HomeFragment() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                            @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        root =  inflater.inflate(R.layout.fragment_home, container, false);
        ImageView imageView = (ImageView)root.findViewById(R.id.logoImage);

        txtProductTitle = (TextView) root.findViewById(R.id.txtLine1);
        txtProductPrice = (TextView) root.findViewById(R.id.txtLine2);
        //imgProduct = (ImageView)root.findViewById(R.id.imageProduct);
        // int imageResource = getResources().getIdentifier("@drawable/logo", null, this.getPackageName());
        // imageView.setImageResource(imageResource);
        imageView.setImageResource(R.drawable.logo);



        //Bundle bundle = this.getArguments();

 //       Log.d("Bundle", bundle.toString());

/*
        //Comment out to the start
        new Thread(new Runnable() {
            @Override
            public void run() {
                product = AppDatabase.getInstance(getContext())
                        .productDAO()
                        .getByTitle("PlayStation 4");
                Log.d("Get id 3333333", product.getTitle());
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txtProductTitle.setText(product.getTitle());
                        txtProductPrice.setText(product.getPrice().toString());
                    }
                });
                final ImageView imgProduct = (ImageView)root.findViewById(R.id.imageProduct);
                String url = product.getImage_url();
                try {
                    final Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(url).getContent());
                    imgProduct.post(new Runnable() {
                        @Override
                        public void run() {
                            if(bitmap != null){
                                imgProduct.setImageBitmap(bitmap);
                            }
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        }).start();

//           product_pk = bundle.getString("PlayStation 4");
            //Log.d("Home", product_pk);
        new Thread(new Runnable() {
                    @Override
                    public void run() {

                        Log.d("IN Thread", "IN");
                        product = AppDatabase.getInstance(getContext())
                                .productDAO()
                                .getById(1);

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {


                                txtProductTitle.setText(product.getTitle());
                                Log.d("HOME", product.getTitle());
                                txtProductPrice.setText(product.getPrice().toString());

                            }
                        });
                       // Log.d("HOME", product.title);
                        //final ImageView imgProductTwo = (ImageView) root.findViewById(R.id.imageProduct);
                        String url = product.getImage_url();
                        try {
                            final Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(url).getContent());
                            imgProductTwo.post(new Runnable() {
                                @Override
                                public void run() {
                                    if (bitmap != null) {
                                        imgProductTwo.setImageBitmap(bitmap);
                                    }
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();

                        }

                    }


                }).start();

         */

        //Comment out the next two lines
        //txtProductTitle = (TextView)root.findViewById(R.id.txtLine1);
        //txtProductPrice = (TextView)root.findViewById(R.id.txtLine2) ;


        return root;
    }

}
