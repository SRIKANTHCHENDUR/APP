package com.example.app;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class ExportExcelUtil {

    public static void exportToExcel(Context context, List<Expense> expenses) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Expenses");

        // Header row
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Title");
        header.createCell(1).setCellValue("Amount");
        header.createCell(2).setCellValue("Category");
        header.createCell(3).setCellValue("Date");

        // Data rows
        for (int i = 0; i < expenses.size(); i++) {
            Expense e = expenses.get(i);
            Row row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(e.title);
            row.createCell(1).setCellValue(e.amount);
            row.createCell(2).setCellValue(e.category);
            row.createCell(3).setCellValue(e.date);
        }

        // Write to file
        try {
            File file = new File(context.getExternalFilesDir(null), "ExpenseData.xlsx");
            FileOutputStream out = new FileOutputStream(file);
            workbook.write(out);
            out.close();
            workbook.close();
            Toast.makeText(context, "Excel saved: " + file.getAbsolutePath(), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Error saving Excel", Toast.LENGTH_SHORT).show();
        }
    }
}

