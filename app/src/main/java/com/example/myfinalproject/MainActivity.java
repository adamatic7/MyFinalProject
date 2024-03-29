package com.example.myfinalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.myfinalproject.db.AppDatabase;
import com.example.myfinalproject.db.Product;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{


    List<String> poNumberList = new ArrayList<String>();
    FragmentManager fm;
    public MyCartFragment fragProducts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        LoadFragments(new MyCartFragment(), R.id.fragment_container, "MyCartFragment");

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new HomeFragment())
                .commit();


    }


    private void LoadFragments(Fragment f, int fID, String tag){
        fm = getSupportFragmentManager();
        fm.beginTransaction()
                .add(fID, f, tag)
                .commit();
    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment = null;

            switch (menuItem.getItemId()){
                case R.id.menu_home:
                    selectedFragment = new HomeFragment();
                    break;
                case R.id.menu_products:
                    selectedFragment = new ProductsFragment();
                    break;
                case R.id.menu_my_cart:
                    selectedFragment = new MyCartFragment();
                    break;
                case R.id.menu_my_account:
                    selectedFragment = new AccountFragment();
                    break;

            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment)
                    .commit();
            return true;
        }
    };


    public Context getContext(){
        Context mContext = new MainActivity();
        return mContext;
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sp = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor spEditor = sp.edit();
        String sessionId = getIntent().getStringExtra("GUID");
        int tt = 0;
        poNumberList.add(sessionId);
        if(poNumberList.size() != 0){
            for (int i=0; i < poNumberList.size();i++){

                Log.d("PO", String.valueOf(poNumberList.size()));

                spEditor.putString("poNumber" + i, poNumberList.get(i));
                tt++;
            }
            int i2 = 0;

            int value = tt;

            spEditor.putInt("poTotal", value);
            spEditor.commit();
        }
    }
}
