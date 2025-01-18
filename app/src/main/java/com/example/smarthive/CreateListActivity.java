package com.example.smarthive;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CreateListActivity extends AppCompatActivity {

    private EditText listNameField, listDescriptionField;
    private Button createListButton;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_list);

        db = FirebaseFirestore.getInstance();

        listNameField = findViewById(R.id.listNameField);
        listDescriptionField = findViewById(R.id.listDescriptionField);
        createListButton = findViewById(R.id.createListButton);


        createListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createList();
            }
        });
    }

    private void createList() {
        String listName = listNameField.getText().toString();
        String listDescription = listDescriptionField.getText().toString();

        if (listName.isEmpty()) {
            Toast.makeText(this, "Lütfen liste ismini girin.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Liste verisi
        Map<String, Object> listData = new HashMap<>();
        listData.put("name", listName);
        listData.put("description", listDescription);
        listData.put("groupId", getIntent().getStringExtra("GROUP_ID"));

        // Firestore’a ekleme
        db.collection("lists")
                .add(listData)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(CreateListActivity.this, "Liste oluşturuldu!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CreateListActivity.this, GroupDetailActivity.class);
                    intent.putExtra("GROUP_ID", getIntent().getStringExtra("GROUP_ID"));
                    startActivity(intent);
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(CreateListActivity.this, "Liste oluşturulamadı: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }


}