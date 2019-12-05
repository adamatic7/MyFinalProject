package com.example.myfinalproject;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myfinalproject.db.AppDatabase;
import com.example.myfinalproject.db.Product;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductsFragment extends Fragment{

    private GetAPIProducts task;
    private RecyclerView recyclerView;
    private ProductRecyclerViewAdapter adapter;
    private int columnCount = 2;


    View root;
    public ProductsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root =  inflater.inflate(R.layout.fragment_products, container, false);

        task = new GetAPIProducts();
        task.setOnProductListComplete(new GetAPIProducts.OnProductListComplete() {
            @Override
            public void processProductList(Product[] products) {
                final ArrayList<Product> productList = new ArrayList<>();
                Log.d("Array size: ", String.valueOf(productList.size()));
                for(Product product: products){
                    productList.add(product);
                    Log.d("Product is:", product.title);
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        AppDatabase.getInstance(getContext())
                                .productDAO().InsertProducts(productList);
                    }
                }).start();
            }
        });
        task.execute("");

        recyclerView = (RecyclerView)root.findViewById(R.id.recyclerView);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

        Context context = getContext();


        adapter = new ProductRecyclerViewAdapter(new ArrayList<Product>(), context);
        if (columnCount <= 1){
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        }else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, columnCount));
        }
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(false);

        Log.d("OnRESUME","your in Products Fragment");
        ViewModelProviders.of(this)
                .get(AllProductsViewModel.class)
                .getProductList(context)
                .observe(this, new Observer<List<Product>>() {
                    @Override
                    public void onChanged(List<Product> products) {
                        if (products != null){
                            adapter.addItems(products);
                        }
                    }
                });
    }




}
