package com.example.myfinalproject;
import android.os.AsyncTask;
import android.util.Log;

import android.os.AsyncTask;

import com.example.myfinalproject.db.Product;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class GetAPIProducts extends AsyncTask<String, Integer, String> {

    private String rawJSON;

    private OnProductListComplete mCallBack;
    public interface OnProductListComplete{
        void processProductList(Product[] products);
    }



    public void setOnProductListComplete(OnProductListComplete listener){mCallBack = listener;}

    @Override
    protected String doInBackground(String... strings) {

        try{
            URL url = new URL("https://androidwebappcs3270.azurewebsites.net/api/Products");
            HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int status = connection.getResponseCode();
            switch (status){
                case 200:
                    BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    rawJSON= br.readLine();
                    Log.d("TAG", "doInBackground "+ rawJSON.toString());
            }
        }catch (Exception e){
            Log.d("TAG", "doInBackGround: " + e.toString());
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {


        Product[] products;
        try{
            products = parseJson(result);
            mCallBack.processProductList(products);
        }catch (Exception e){
            Log.d("Test", "OnPostExecute "+ e.toString());
        }

        super.onPostExecute(result);
    }

    private Product[] parseJson(String result) {
        GsonBuilder gsonb = new GsonBuilder();
        Gson gson = gsonb.create();

        Product[] products = null;
        try{
            products = gson.fromJson(rawJSON, Product[].class);
        }catch (Exception e){
            Log.d("Test", "parseJson "+ e.toString());
        }
        return products;
    }
}
