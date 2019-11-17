package com.example.myfinalproject;



import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myfinalproject.db.Product;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductRecyclerViewAdapter extends RecyclerView.Adapter<ProductRecyclerViewAdapter.ViewHolder> {

    public final List<Product> products;

    public ProductRecyclerViewAdapter(List<Product> products) {
        this.products = products;
    }

    public void addItems(List<Product> products) {
        this.products.clear();
        this.products.addAll(products);
        notifyDataSetChanged();
    }

    public void clear(){
        this.products.clear();
        notifyDataSetChanged();
    }
    public class ViewHolder {
        public TextView txtLine1, txtLine2, txtLine3;
        public View root;
        public Product product;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            root = itemView;
            txtLine1 = (TextView) root.findViewById(R.id.txtLine1);
            txtLine2 = (TextView) root.findViewById(R.id.txtLine2);
            txtLine3 = (TextView) root.findViewById(R.id.txtLine3);

        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_product, parent, false);

        return new  ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Product product = products.get(position);
        if(product!= null){
            holder.product = product;
            holder.txtLine1.setText(product.getTitle());
            holder.txtLine2.setText(product.getDescription());
            holder.txtLine3.setText(product.getPrice().toString());

            Bundle bundle = new Bundle();
            bundle.putInt("product_pk", product.getId());



        }

    }

    @Override
    public int getItemCount() {
        return products.size();
    }



}