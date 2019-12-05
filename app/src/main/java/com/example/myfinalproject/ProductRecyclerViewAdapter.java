package com.example.myfinalproject;



import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.InputStream;
import java.net.URL;
import java.net.MalformedURLException;
import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.myfinalproject.db.Product;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductRecyclerViewAdapter extends RecyclerView.Adapter<ProductRecyclerViewAdapter.ViewHolder> {

    public final List<Product> products;

    Context context;

    public ImageView i;
    public ProductRecyclerViewAdapter(List<Product> products, Context context_) {
        this.products = products;
        this.context = context_;
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
    public class ViewHolder  extends  RecyclerView.ViewHolder{
        public TextView txtLine1, txtLine2;
        public ImageView imgProduct;
        public View root;
        public Product product;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            root = itemView;
            txtLine1 = (TextView) root.findViewById(R.id.txtLine1);
            txtLine2 = (TextView) root.findViewById(R.id.txtLine2);
            //txtLine3 = (TextView) root.findViewById(R.id.txtLine3);
            imgProduct = (ImageView)root.findViewById(R.id.imageProduct);


        }
        public ImageView getImage(){return this.imgProduct;}
    }
    @NonNull
    @Override
    public ProductRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_product, parent, false);

        view.setLayoutParams(new RecyclerView.LayoutParams(1080,800));
        return new  ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductRecyclerViewAdapter.ViewHolder holder, int position) {
        final Product product = products.get(position);
        if(product!= null){
            holder.product = product;
            holder.txtLine1.setText(product.getTitle());
            //holder.txtLine2.setText(product.getDescription());
            holder.txtLine2.setText(product.getPrice().toString());

            Log.d("Product", holder.product.toString());
            //test image

            //Log.d("ImageURL: ", product.getImage_url());
   /*         try {

                Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(product.getImage_url()).getContent());
                holder.imgProduct.setImageBitmap(bitmap);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
*/

            //to here
            Glide.with(this.context)
                    .load(product.getImage_url())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.getImage());

            Bundle bundle = new Bundle();
            bundle.putInt("product_pk", product.getId());

            holder.root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("onClick", "Your in on click");
                    Bundle bundle = new Bundle();
                    bundle.putString("product_pk", product.getTitle());
                    ProductDetailsDialogFragment c_fragment = new ProductDetailsDialogFragment();
                    c_fragment.setArguments(bundle);

                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    activity.getSupportFragmentManager()
                            .beginTransaction()
                            .add(android.R.id.content, c_fragment)
                            .addToBackStack(null)
                            .commit();

                }
            });



        }

    }

    @Override
    public int getItemCount() {
        return products.size();
    }



}