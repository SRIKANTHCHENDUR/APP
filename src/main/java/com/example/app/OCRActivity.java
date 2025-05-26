package com.example.app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import java.io.IOException;

public class OCRActivity extends AppCompatActivity {
    ImageView imageView;
    TextView textResult;
    Button btnPickImage;

    ActivityResultLauncher<String> imagePickerLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocr);

        imageView = findViewById(R.id.imageView);
        textResult = findViewById(R.id.textResult);
        btnPickImage = findViewById(R.id.btnPickImage);

        imagePickerLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(),
                uri -> {
                    if (uri != null) {
                        try {
                            Bitmap bitmap = getBitmapFromUri(uri);
                            imageView.setImageBitmap(bitmap);
                            runTextRecognition(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

        btnPickImage.setOnClickListener(v -> imagePickerLauncher.launch("image/*"));
    }
    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
            return ImageDecoder.decodeBitmap(ImageDecoder.createSource(getContentResolver(), uri));
        } else {
            return MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
        }
    }



    private void runTextRecognition(Bitmap bitmap) {
        InputImage image = InputImage.fromBitmap(bitmap, 0);
        TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);

        recognizer.process(image)
                .addOnSuccessListener(result -> {
                    textResult.setText(result.getText());
                })
                .addOnFailureListener(e -> {
                    textResult.setText("Failed to read text");
                });
    }
}
