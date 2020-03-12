package com.cse.cou.praptimoni.softwarefirmsbd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main2Activity extends AppCompatActivity {

    Button software_firm_list,addfirm_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        addfirm_btn=findViewById(R.id.addfirm_btn);
        software_firm_list=findViewById(R.id.list_btn);
        software_firm_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Main2Activity.this,CompanyListActivity.class);
                startActivity(intent);
                Main2Activity.this.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

        addfirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 startActivity(new Intent( Main2Activity.this,AdminActivity.class));

                    Main2Activity.this.overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);


            }
        });
    }
}
