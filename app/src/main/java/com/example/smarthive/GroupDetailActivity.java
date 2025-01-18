package com.example.smarthive;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class GroupDetailActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private String groupId;

    private RecyclerView recyclerView;
    private ListAdapter listAdapter;
    private List<ListModel> listList;

    private Button createListButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_detail);

        // Initialize Firebase and UI components
        db = FirebaseFirestore.getInstance();
        groupId = getIntent().getStringExtra("GROUP_ID");

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        createListButton = findViewById(R.id.createListButton);

        listList = new ArrayList<>();
        listAdapter = new ListAdapter(listList);
        recyclerView.setAdapter(listAdapter);

        createListButton.setOnClickListener(v -> {
            // Navigate to CreateListActivity
            Intent intent = new Intent(GroupDetailActivity.this, CreateListActivity.class);
            intent.putExtra("GROUP_ID", groupId);
            startActivity(intent);
        });

        fetchLists();
    }

    // Fetch all lists for the current group from Firestore
    private void fetchLists() {
        db.collection("lists")
                .whereEqualTo("groupId", groupId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    listList.clear();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        String id = document.getId();
                        String name = document.getString("name");
                        String description = document.getString("description");

                        // Add list to the local list
                        listList.add(new ListModel(id, name, description));
                    }
                    listAdapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Log.e("FirestoreError", "Error fetching lists: " + e.getMessage());
                    Toast.makeText(GroupDetailActivity.this, "Error fetching lists.", Toast.LENGTH_SHORT).show();
                });
    }
}