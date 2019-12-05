package com.example.myfinalproject;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.DialogFragment;


import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;
import java.net.MalformedURLException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.myfinalproject.db.AppDatabase;
import com.example.myfinalproject.db.Product;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductDetailsDialogFragment extends DialogFragment{

    private TextView txtProductTitle, txtProductDescription, txtProductPrice;
    private ImageView imgProduct;
    private Button btnSave;
    private View root;

    Product product;
    private String product_pk;

    List<String> titleList = new ArrayList<String>();
    List<String> qtyList = new ArrayList<String>();
    List<String> priceList = new ArrayList<String>();

    int total;
    String sName;
    String sQty;
    String sPrice;

    //arrays


    public ProductDetailsDialogFragment() {
        // Required empty public constructor

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_product_details_dialog, container, false);
        txtProductTitle = (TextView)root.findViewById(R.id.txtProductName_details);
        txtProductDescription = (TextView)root.findViewById(R.id.txtProductDescription_details);
        txtProductPrice = (TextView)root.findViewById(R.id.txtProductPrice_details);
        btnSave = (Button)root.findViewById(R.id.btnAddToCart);
        final EditText editQuantity = (EditText)root.findViewById(R.id.editQuantity);

        Bundle bundle = this.getArguments();

        if(bundle != null){
            product_pk = bundle.getString("product_pk");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    product = AppDatabase.getInstance(getContext())
                            .productDAO()
                            .getByTitle(product_pk);

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {



                            txtProductTitle.setText(product.getTitle());
                            txtProductDescription.setText(product.getDescription());
                            txtProductPrice.setText(product.getPrice().toString());

                        }
                    });
                    final ImageView imgProductTwo = (ImageView)root.findViewById(R.id.imgProduct_details);
                    String url = product.getImage_url();
                    try {
                        final Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(url).getContent());
                        imgProductTwo.post(new Runnable() {
                            @Override
                            public void run() {
                                if(bitmap != null){
                                    imgProductTwo.setImageBitmap(bitmap);
                                }
                            }
                        });
                    }catch (Exception e){
                        e.printStackTrace();
                    }


/*
                    ImageView imgProductTwo = (ImageView)root.findViewById(R.id.imgProduct_details);
                    Glide.with(getContext())
                            .load(product.getImage_url())
                            .asBitmap()
                            .into(imgProductTwo);

                 Log.d("ImageURL: ", product.getImage_url());
                    try{
                        ImageView imgProductTwo = (ImageView)root.findViewById(R.id.imgProduct_details);
                        URL url = new  URL(product.getImage_url());
                        InputStream content = (InputStream)url.getContent();
                        Drawable d = Drawable.createFromStream(content, "src");
                        imgProductTwo.setImageDrawable(d);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

*/



                }
            }).start();

        }

        Toolbar toolbar = (Toolbar) root.findViewById(R.id.toolbar_details);
        toolbar.setTitle("Product Details");

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();

        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setHomeAsUpIndicator(android.R.drawable.ic_menu_close_clear_cancel);
            Log.d("action Bar: ", "in loop");
        }
        setHasOptionsMenu(true);

        txtProductTitle = (TextView)root.findViewById(R.id.txtProductName_details);
        txtProductDescription = (TextView)root.findViewById(R.id.txtProductDescription_details) ;
        txtProductPrice = (TextView)root.findViewById(R.id.txtProductPrice_details) ;


        editQuantity.setInputType(InputType.TYPE_CLASS_TEXT);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sp = getActivity().getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor spEditor = sp.edit();

                addItemsToList(txtProductTitle.getText().toString(), editQuantity.getText().toString(), txtProductPrice.getText().toString());



                int tt = 0;
                if(titleList.size() != 0){
                    for (int i=0; i < titleList.size();i++){


                        spEditor.putString("Name" + i, titleList.get(i));
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
                dismiss();
            }
        });

        return root;
    }




    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        getActivity().getMenuInflater().inflate(R.menu.menu_product_details, menu);
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case android.R.id.home:
                dismiss();
                Log.d("clicked cancel", "youre in cancel");

            default:
                return super.onOptionsItemSelected(item);

        }

    }


    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences sp = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor spEditor = sp.edit();
        titleList.clear();
        qtyList.clear();
        priceList.clear();

        int tt = 0;
        if(titleList.size() != 0){
            for (int i=0; i < titleList.size();i++){


                spEditor.putString("Name" + i, titleList.get(i));
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

    }


    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences sp = getActivity().getPreferences(Context.MODE_PRIVATE);
        int t = sp.getInt("total", total);

        titleList.clear();
        qtyList.clear();
        priceList.clear();

        for (int i = 0; i < t; i++) {
            String name = sp.getString("Name" + (i), sName);
            String QtyTwo = sp.getString("Qty" + (i), sQty);
            titleList.add(sp.getString("Name" + i, sName));

            qtyList.add(sp.getString("Qty" + i, sQty));
            priceList.add(sp.getString("Price" + i, sPrice));

        }
    }

    public void addItemsToList(String name, String value, String price){

        titleList.add(name);
        qtyList.add(value);
        priceList.add(price);
    }


}
