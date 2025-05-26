package com.example.app;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ExpenseDao {
    @Insert
    void insert(Expense expense);

    @Query("SELECT * FROM Expense ORDER BY date DESC")
    List<Expense> getAll();

    @Query("SELECT category, SUM(amount) as total FROM Expense GROUP BY category")
    List<CategoryTotal> getTotalByCategory();
}
