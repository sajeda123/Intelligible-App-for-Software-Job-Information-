package com.cse.cou.praptimoni.softwarefirmsbd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminLogin extends AppCompatActivity {

    EditText username,password;
    Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);


        username=findViewById(R.id.admin_user_name);
        password=findViewById(R.id.admin_user_password);
        login=findViewById(R.id.admin_login_btn);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user=username.getText().toString();
                String pass=password.getText().toString();
                if(user.equals("prapti")&&pass.equals("123456")){
                    startActivity(new Intent(AdminLogin.this,Main2Activity.class));
                }else {
                    Toast.makeText(getApplicationContext(),"Invalid user and password",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
