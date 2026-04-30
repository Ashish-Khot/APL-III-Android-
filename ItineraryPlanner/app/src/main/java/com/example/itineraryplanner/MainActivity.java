package com.example.itineraryplanner;

import android.content.ContentValues;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.itineraryplanner.api.ApiClient;
import com.example.itineraryplanner.api.GeminiRequest;
import com.example.itineraryplanner.api.GeminiResponse;
import com.example.itineraryplanner.api.GeminiService;
import com.example.itineraryplanner.api.OpenAiRequest;
import com.example.itineraryplanner.api.OpenAiResponse;
import com.example.itineraryplanner.api.OpenRouterService;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    // IMPORTANT: Check your credits at https://openrouter.ai/keys and https://aistudio.google.com/
    private static final String GEMINI_API_KEY = "AIzaSyCj8F7zx715FtDvmyuVD9-5bAYEjSSrB24";
    private static final String OPENROUTER_API_KEY = "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";

    private TextInputEditText locationEditText;
    private TextInputEditText daysEditText;
    private MaterialButton generateButton;
    private LinearProgressIndicator progressBar;
    private MaterialCardView resultCard;
    private TextView itineraryTitle;
    private TextView itineraryContent;
    private MaterialButton downloadButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        locationEditText = findViewById(R.id.locationEditText);
        daysEditText = findViewById(R.id.daysEditText);
        generateButton = findViewById(R.id.generateButton);
        progressBar = findViewById(R.id.progressBar);
        resultCard = findViewById(R.id.resultCard);
        itineraryTitle = findViewById(R.id.itineraryTitle);
        itineraryContent = findViewById(R.id.itineraryContent);
        downloadButton = findViewById(R.id.downloadButton);

        generateButton.setOnClickListener(v -> generateItinerary());
        downloadButton.setOnClickListener(v -> downloadItinerary());
    }

    private void generateItinerary() {
        String location = locationEditText.getText().toString().trim();
        String daysStr = daysEditText.getText().toString().trim();

        if (location.isEmpty() || daysStr.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        resultCard.setVisibility(View.GONE);
        generateButton.setEnabled(false);

        String prompt = "Create a detailed day-by-day travel itinerary for " + location + " for " + daysStr + " days. " +
                "Include morning, afternoon, and evening activities. Keep it concise but helpful.";

        callGeminiApi(location, prompt, daysStr);
    }

    private void callGeminiApi(String location, String prompt, String daysStr) {
        GeminiService service = ApiClient.getGeminiClient().create(GeminiService.class);
        GeminiRequest request = new GeminiRequest(prompt);
        Call<GeminiResponse> call = service.generateContent(GEMINI_API_KEY, request);

        call.enqueue(new Callback<GeminiResponse>() {
            @Override
            public void onResponse(@NonNull Call<GeminiResponse> call, @NonNull Response<GeminiResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getCandidates() != null && !response.body().getCandidates().isEmpty()) {
                    String result = response.body().getCandidates().get(0).getContent().getParts().get(0).getText();
                    showResult(location, result);
                    progressBar.setVisibility(View.GONE);
                    generateButton.setEnabled(true);
                } else {
                    // Fallback to OpenRouter with a more reliable model slug
                    callOpenRouterApi(location, prompt, daysStr);
                }
            }

            @Override
            public void onFailure(@NonNull Call<GeminiResponse> call, @NonNull Throwable t) {
                callOpenRouterApi(location, prompt, daysStr);
            }
        });
    }

    private void callOpenRouterApi(String location, String prompt, String daysStr) {
        OpenRouterService service = ApiClient.getOpenRouterClient().create(OpenRouterService.class);
        
        List<OpenAiRequest.Message> messages = new ArrayList<>();
        messages.add(new OpenAiRequest.Message("user", prompt));
        
        // Updated model slug for OpenRouter (Llama 3.1 is the new standard)
        OpenAiRequest request = new OpenAiRequest("meta-llama/llama-3.1-8b-instruct:free", messages);

        Call<OpenAiResponse> call = service.getChatCompletion(
                "Bearer " + OPENROUTER_API_KEY,
                "https://itinerary-planner.app", // Referer required by OpenRouter
                "Itinerary Planner",
                request
        );

        call.enqueue(new Callback<OpenAiResponse>() {
            @Override
            public void onResponse(@NonNull Call<OpenAiResponse> call, @NonNull Response<OpenAiResponse> response) {
                progressBar.setVisibility(View.GONE);
                generateButton.setEnabled(true);

                if (response.isSuccessful() && response.body() != null && response.body().getChoices() != null && !response.body().getChoices().isEmpty()) {
                    String result = response.body().getChoices().get(0).getMessage().getContent();
                    showResult(location, result);
                } else {
                    showSampleItinerary(location, Integer.parseInt(daysStr));
                    Toast.makeText(MainActivity.this, "AI Services unavailable, showing sample data.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<OpenAiResponse> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                generateButton.setEnabled(true);
                showSampleItinerary(location, Integer.parseInt(daysStr));
            }
        });
    }

    private void showResult(String location, String content) {
        itineraryTitle.setText("Itinerary for " + location);
        itineraryContent.setText(content);
        resultCard.setVisibility(View.VISIBLE);
    }

    private void showSampleItinerary(String location, int days) {
        StringBuilder sb = new StringBuilder();
        sb.append("(Connection Error - Showing Template)\n\n");
        for (int i = 1; i <= days; i++) {
            sb.append("Day ").append(i).append(":\n");
            sb.append("• Morning: Sightseeing in ").append(location).append("\n");
            sb.append("• Afternoon: Local lunch and market visit\n");
            sb.append("• Evening: Leisure walk in center\n\n");
        }
        showResult(location, sb.toString());
    }

    private void downloadItinerary() {
        String content = itineraryContent.getText().toString();
        String title = itineraryTitle.getText().toString();
        if (content.isEmpty()) return;

        PdfDocument pdfDocument = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(595, 842, 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);
        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();
        
        paint.setTextSize(18);
        paint.setFakeBoldText(true);
        int x = 50, y = 50;
        canvas.drawText(title, x, y, paint);
        y += 40;
        paint.setTextSize(10);
        paint.setFakeBoldText(false);

        for (String line : content.split("\n")) {
            if (y > 800) {
                pdfDocument.finishPage(page);
                page = pdfDocument.startPage(pageInfo);
                canvas = page.getCanvas();
                y = 50;
            }
            if (line.length() > 80) {
                canvas.drawText(line.substring(0, 80), x, y, paint);
                y += 15;
                canvas.drawText(line.substring(80), x, y, paint);
            } else {
                canvas.drawText(line, x, y, paint);
            }
            y += 15;
        }

        pdfDocument.finishPage(page);
        String fileName = "Itinerary_" + System.currentTimeMillis() + ".pdf";
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName);
                values.put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf");
                values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);
                Uri uri = getContentResolver().insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values);
                if (uri != null) {
                    OutputStream os = getContentResolver().openOutputStream(uri);
                    if (os != null) {
                        pdfDocument.writeTo(os);
                        os.close();
                        Toast.makeText(this, "PDF saved to Downloads", Toast.LENGTH_LONG).show();
                    }
                }
            }
        } catch (IOException e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            pdfDocument.close();
        }
    }
}