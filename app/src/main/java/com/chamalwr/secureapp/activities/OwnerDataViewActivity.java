package com.chamalwr.secureapp.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.chamalwr.secureapp.R;
import com.chamalwr.secureapp.controller.OwnerController;
import com.chamalwr.secureapp.model.Owner;
import com.chamalwr.secureapp.model.OwnersResponse;

import java.util.List;

public class OwnerDataViewActivity extends AppCompatActivity {
    private OwnersResponse ownersResponse;
    private List<Owner> ownerListResponse;

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
        OwnerController ownerController = new OwnerController();
        ownerController.getOwners(1, 30).thenAccept(response -> {
            runOnUiThread(() -> {
                List<Owner> ownerListResponse = response.getOwners();
                ListView ownerDataListView = findViewById(R.id.ownersListView);
                ArrayAdapter<Owner> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ownerListResponse);
                ownerDataListView.setAdapter(adapter);
            });
        });
    }

}