package com.example.myfinalproject.db;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

public interface ProductDAO {

    @Query("select * from Product")
    LiveData<List<Product>> getAll();

    @Insert
    void insert (Product product);

    @Query("Select * from Product where title =:title")
    Product getByTitle(String title);

    @Update
    void update(Product product);

    @Delete
    void delete(Product product);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void InsertProducts(ArrayList<Product> products);

}
