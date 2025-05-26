package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;

public class MainActivity extends AppCompatActivity {

    private BarChart barChart;
    private Button addExpenseBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize BarChart
        barChart = findViewById(R.id.barChart);

        // Initialize Add Expense Button
        addExpenseBtn = findViewById(R.id.addExpenseBtn);

        // OnClick opens AddExpenseActivity
        addExpenseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddExpenseActivity.class);
                startActivity(intent);
            }
        });

    }
}
