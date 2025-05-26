package com.example.app;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChartActivity extends AppCompatActivity {

    private PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        pieChart = findViewById(R.id.pieChart);

        loadExpensesAndShowChart();
    }

    private void loadExpensesAndShowChart() {
        new Thread(() -> {
            // Simulate fetching expenses from database
            List<Expense> expenses = ExpenseDatabase.getInstance(this).expenseDao().getAll();

            // Calculate total amount per category
            Map<String, Double> categoryTotals = new HashMap<>();
            for (Expense e : expenses) {
                double current = categoryTotals.getOrDefault(e.category, 0.0);
                categoryTotals.put(e.category, current + e.amount);
            }

            ArrayList<PieEntry> entries = new ArrayList<>();
            for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
                entries.add(new PieEntry(entry.getValue().floatValue(), entry.getKey()));
            }

            runOnUiThread(() -> {
                PieDataSet dataSet = new PieDataSet(entries, "Expenses by Category");
                dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                PieData data = new PieData(dataSet);
                data.setValueTextSize(14f);

                pieChart.setData(data);
                pieChart.invalidate(); // refresh chart
                pieChart.getDescription().setEnabled(false);
                pieChart.setCenterText("Expenses");
                pieChart.setCenterTextSize(18f);
                pieChart.animateY(1000); // optional animation
            });
        }).start();
    }
}
