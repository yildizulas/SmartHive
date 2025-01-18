package com.example.smarthive;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CreateGroupActivity extends AppCompatActivity {

    private EditText groupNameField, groupDescriptionField;
    private Button createGroupButton;

    private FirebaseFirestore db;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        groupNameField = findViewById(R.id.groupNameField);
        groupDescriptionField = findViewById(R.id.groupDescriptionField);
        createGroupButton = findViewById(R.id.createGroupButton);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        createGroupButton.setOnClickListener(v -> {
            String groupName = groupNameField.getText().toString().trim();
            String groupDescription = groupDescriptionField.getText().toString().trim();

            if (!groupName.isEmpty() && !groupDescription.isEmpty()) {
                createGroup(groupName, groupDescription);
            } else {
                Toast.makeText(CreateGroupActivity.this, "Please fill out all fields.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createGroup(String groupName, String groupDescription) {
        // Kullanıcı oturum kontrolü
        if (auth.getCurrentUser() == null) {
            Toast.makeText(this, "User not logged in!", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = auth.getCurrentUser().getUid();

        // Grup verileri
        Map<String, Object> groupData = new HashMap<>();
        groupData.put("groupName", groupName);
        groupData.put("description", groupDescription);
        groupData.put("createdBy", userId);
        groupData.put("members", Collections.singletonList(userId)); // Kullanıcı ID'sini listeye ekle

        // Firestore'a veri yazma
        db.collection("groups")
                .add(groupData)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(CreateGroupActivity.this, "Group created successfully!", Toast.LENGTH_SHORT).show();
                    Log.d("Firestore", "Document ID: " + documentReference.getId());
                })
                .addOnFailureListener(e -> {
                    Log.e("FirestoreError", "Error creating group: " + e.getMessage());
                    Toast.makeText(CreateGroupActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}