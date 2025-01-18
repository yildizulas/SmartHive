package com.example.smarthive;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText emailField;
    private Button resetPasswordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        // Firebase Auth instance
        mAuth = FirebaseAuth.getInstance();

        emailField = findViewById(R.id.emailField);
        resetPasswordButton = findViewById(R.id.resetPasswordButton);

        resetPasswordButton.setOnClickListener(v -> {
            String email = emailField.getText().toString().trim();

            if (!email.isEmpty()) {
                mAuth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(ForgotPasswordActivity.this, "Password reset email sent!", Toast.LENGTH_SHORT).show();
                                finish(); // Go back to login screen
                            } else {
                                Toast.makeText(ForgotPasswordActivity.this, "Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                Toast.makeText(ForgotPasswordActivity.this, "Please enter your email.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}