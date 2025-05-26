package com.example.app;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class ExportPdfUtil {

    public static void exportToPdf(Context context, List<Expense> expenses) {
        PdfDocument pdfDocument = new PdfDocument();
        Paint paint = new Paint();

        int y = 50;
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(300, 600, 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);
        Canvas canvas = page.getCanvas();

        paint.setTextSize(12f);
        canvas.drawText("Expense Report", 80, y, paint);
        y += 20;

        for (Expense exp : expenses) {
            canvas.drawText("Title: " + exp.title, 10, y, paint); y += 15;
            canvas.drawText("Amount: ₹" + exp.amount, 10, y, paint); y += 15;
            canvas.drawText("Category: " + exp.category, 10, y, paint); y += 15;
            canvas.drawText("Date: " + exp.date, 10, y, paint); y += 25;
        }

        pdfDocument.finishPage(page);

        try {
            File file = new File(context.getExternalFilesDir(null), "ExpenseReport.pdf");
            pdfDocument.writeTo(new FileOutputStream(file));
            Toast.makeText(context, "PDF saved to: " + file.getAbsolutePath(), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Error saving PDF", Toast.LENGTH_SHORT).show();
        }

        pdfDocument.close();
    }
}
