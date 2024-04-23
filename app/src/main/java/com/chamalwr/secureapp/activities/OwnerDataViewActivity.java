package com.chamalwr.secureapp.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.chamalwr.secureapp.R;
import com.chamalwr.secureapp.controller.OwnerController;
import com.chamalwr.secureapp.model.Owner;

import java.util.List;

public class OwnerDataViewActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_owner_data_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        progressBar = findViewById(R.id.progressBar);
        OwnerController ownerController = new OwnerController();
        progressBar.setVisibility(View.VISIBLE);
        ownerController.getOwners(1, 120).thenAccept(response -> runOnUiThread(() -> {
            List<Owner> ownerListResponse = response.getOwners();
            ListView ownerDataListView = findViewById(R.id.ownersListView);
            ArrayAdapter<Owner> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ownerListResponse);
            ownerDataListView.setAdapter(adapter);
            progressBar.setVisibility(View.GONE);
        })).exceptionally(e -> {
            Toast.makeText(this, "Failed to fetch owners", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            return null;
        });
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}