package com.example.smarthive;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class GroupListActivity extends AppCompatActivity {

    private FirebaseFirestore db; // Firestore instance
    private FirebaseAuth auth;   // FirebaseAuth instance

    private RecyclerView recyclerView;
    private GroupAdapter groupAdapter;
    private List<Group> groupList;
    private Button createGroupButton; // Button for creating a new group

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_list);

        // Initialize Firebase instances
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        // Setup RecyclerView for displaying groups
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize group list
        groupList = new ArrayList<>();

        // Initialize the adapter and handle item click events
        groupAdapter = new GroupAdapter(groupList, group -> {
            // Navigate to GroupDetailActivity and pass group ID
            Intent intent = new Intent(GroupListActivity.this, GroupDetailActivity.class);
            intent.putExtra("GROUP_ID", group.getId());
            startActivity(intent);
        });

        recyclerView.setAdapter(groupAdapter); // Attach adapter to RecyclerView

        // Setup "Create Group" button
        createGroupButton = findViewById(R.id.createGroupButton);
        createGroupButton.setOnClickListener(v -> {
            // Navigate to CreateGroupActivity
            Intent intent = new Intent(GroupListActivity.this, CreateGroupActivity.class);
            startActivity(intent);
        });

        // Fetch user's groups from Firestore
        fetchGroups();
    }

    // Fetches the groups from Firestore where the user is a member
    private void fetchGroups() {
        String userId = auth.getCurrentUser().getUid();

        db.collection("groups")
                .whereArrayContains("members", userId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    groupList.clear(); // Clear the list before adding new data
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        String id = document.getId();
                        String name = document.getString("groupName");
                        String description = document.getString("description");

                        // Add group to the list
                        groupList.add(new Group(id, name, description));
                    }
                    groupAdapter.notifyDataSetChanged(); // Notify adapter to refresh data
                })
                .addOnFailureListener(e -> {
                    Log.e("FirestoreError", "Error fetching groups: " + e.getMessage());
                    Toast.makeText(GroupListActivity.this, "Error fetching groups.", Toast.LENGTH_SHORT).show();
                });
    }
}