package com.cse.cou.praptimoni.softwarefirmsbd;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;

    LinearLayout layout;
    Button admin_btn,user_btn;
    ImageView splash ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout=findViewById(R.id.layout);
        layout.setVisibility(View.GONE);
        splash=findViewById(R.id.splash_img);
        admin_btn=findViewById(R.id.admin_activity_btn);
        admin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,AdminLogin.class);
                startActivity(intent);
                MainActivity.this.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });
        user_btn=findViewById(R.id.user_btn);
        user_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth = FirebaseAuth.getInstance();

                FirebaseUser currentUser = mAuth.getCurrentUser();
                if(currentUser!=null){
                    Intent intent=new Intent(MainActivity.this,CompanyListActivity.class);
                    startActivity(intent);
                    MainActivity.this.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

                }else {
                    Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(intent);
                    MainActivity.this.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

                }

            }
        });



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel("my_notification","my_notification", NotificationManager.IMPORTANCE_DEFAULT);

                NotificationManager manager=getSystemService(NotificationManager.class);
                manager.createNotificationChannel(channel);
        }
        FirebaseMessaging.getInstance().subscribeToTopic("user")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Notification successfull";
                        if (!task.isSuccessful()) {
                            msg = "Notification failed";
                        }
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {

               String t= instanceIdResult.getToken();
                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("idToken");
                Map<String,String> map=new HashMap<>();
                map.put("key","value");
                databaseReference.child(t).setValue(map);
                Log.d("pppppp", "onSuccess: "+t);
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Initialize Firebase Auth
                    layout.setVisibility(View.VISIBLE);
                    splash.setVisibility(View.GONE);


                }
            },2000);
        }


}
