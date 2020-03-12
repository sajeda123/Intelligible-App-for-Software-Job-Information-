package com.cse.cou.praptimoni.softwarefirmsbd;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    Button login,register;
    EditText username, passwrod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.user_name);
        passwrod = findViewById(R.id.password);
        login = findViewById(R.id.login_btn);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username_str = username.getText().toString();
                String passwrod_str = passwrod.getText().toString();
                if (username_str.isEmpty() && passwrod_str.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter username and password", Toast.LENGTH_LONG).show();
                } else {
                    userLogin(username_str, passwrod_str);
                }
            }
        });
        register=findViewById(R.id.register_btn);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
            }
        });
    }

    private void userLogin(String email, String password) {
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            startActivity(new Intent(getApplicationContext(),CompanyListActivity.class));

                        } else {
                            // If sign in fails, display a message to the user.Log.w(TAG, "signInWithEmail:failure", task.getException());Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
                                 Toast.makeText(getApplicationContext(),"Invalid username and password",Toast.LENGTH_LONG).show();

                        }

                    }
                });
    }
}
