package com.example.app;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.app.R;


import androidx.appcompat.app.AppCompatActivity;

public class AddExpenseActivity extends AppCompatActivity {

    private EditText etTitle, etAmount, etCategory, etDate;
    private Button btnSave;
    private ExpenseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        // Initialize views
        etTitle = findViewById(R.id.etTitle);
        etAmount = findViewById(R.id.etAmount);
        etCategory = findViewById(R.id.etCategory);
        etDate = findViewById(R.id.etDate);
        btnSave = findViewById(R.id.btnSave);

        // Initialize database instance (make sure ExpenseDatabase is implemented)
        db = ExpenseDatabase.getInstance(this);

        btnSave.setOnClickListener(v -> {
            String title = etTitle.getText().toString().trim();
            String amountStr = etAmount.getText().toString().trim();
            String category = etCategory.getText().toString().trim();
            String date = etDate.getText().toString().trim();

            if (title.isEmpty() || amountStr.isEmpty() || category.isEmpty() || date.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            double amount;
            try {
                amount = Double.parseDouble(amountStr);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid amount format", Toast.LENGTH_SHORT).show();
                return;
            }

            // Create Expense object and set data
            Expense expense = new Expense();
            expense.title = title;
            expense.amount = amount;
            expense.category = category;
            expense.date = date;

            // Insert expense in background thread
            new Thread(() -> {
                db.expenseDao().insert(expense);
                runOnUiThread(() -> {
                    Toast.makeText(this, "Expense saved successfully", Toast.LENGTH_SHORT).show();
                    finish(); // close activity
                });
            }).start();
        });
    }
}
