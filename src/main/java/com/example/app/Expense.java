package com.example.app;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Expense {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String title;
    public double amount;
    public String date;
    public String category;
}
