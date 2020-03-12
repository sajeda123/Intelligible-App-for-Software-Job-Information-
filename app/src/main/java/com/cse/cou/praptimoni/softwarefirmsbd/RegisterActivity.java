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

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    Button register_btn;
    EditText username,email,password,company;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        username=findViewById(R.id.reg_username);
        email=findViewById(R.id.reg_email);
        password=findViewById(R.id.reg_password);
        company=findViewById(R.id.reg_password);
        register_btn=findViewById(R.id.register);
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username_str=username.getText().toString();
                String email_str=email.getText().toString();
                String password_str=password.getText().toString();
                String company_str=company.getText().toString();
                if(username_str.isEmpty()&&email_str.isEmpty()&&password_str.isEmpty()){
                    Toast.makeText(getApplicationContext(),"*** fields are needed",Toast.LENGTH_LONG).show();
                }else {
                    getRegistered(username_str,email_str,password_str,company_str);    
                }
                
            }
        });
    }

    private void getRegistered(String username_str, String email, String password, String company_str) {
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = mAuth.getCurrentUser();

                            startActivity(new Intent(getApplicationContext(),CompanyListActivity.class));

                        } else {
                            Toast.makeText(getApplicationContext(), "Authentication failed."+task.getException(), Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }
}
