package com.cse.cou.praptimoni.softwarefirmsbd;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.cse.cou.praptimoni.softwarefirmsbd.adapter.RecyclerAdapter;
import com.cse.cou.praptimoni.softwarefirmsbd.modal.FirmsInfo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CompanyListActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    RecyclerView myrecyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerAdapter mAdapter;
    List<FirmsInfo> firmsInfoList;
    ImageButton search_btn;
    EditText search_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_list);
        //Initialize Firebase Auth


        mAuth = FirebaseAuth.getInstance();
        setTitle("Software firms list");
        myrecyclerView = findViewById(R.id.my_recycler_view);
        layoutManager = new LinearLayoutManager(this);
        myrecyclerView.setLayoutManager(layoutManager);

        firmsInfoList = new ArrayList<>();

        showDataInRecycler();

        search_text=findViewById(R.id.search_text);
        search_btn=findViewById(R.id.search_btn);
        search_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(s.toString().isEmpty()){
                    showDataInRecycler();
                }
            }
        });
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text=search_text.getText().toString();
                if(text.isEmpty()){
                    showDataInRecycler();
                    Toast.makeText(getApplicationContext(),"Write a firm name",Toast.LENGTH_LONG).show();
                }else {
                    searchAction(text);
                }
            }
        });

    }

   private void showDataInRecycler(){
       final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("firms");
       databaseReference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               firmsInfoList.clear();
               for (DataSnapshot d : dataSnapshot.getChildren()) {
                   FirmsInfo firmsInfo = new FirmsInfo();
                   firmsInfo.setId(d.getKey());
                   firmsInfo.setFirm_name(d.child("firm_name").getValue().toString());
                   firmsInfo.setSize(d.child("size").getValue().toString());
                   firmsInfo.setLocation(d.child("location").getValue().toString());
                   firmsInfo.setWebsite(d.child("website").getValue().toString());
                   firmsInfo.setRequirment(d.child("requirment").getValue().toString());
                   firmsInfo.setExamsLanguage(d.child("examsLanguage").getValue().toString());
                   firmsInfo.setImage_link(d.child("image_link").getValue().toString());
                   firmsInfo.setHiring_procedure(d.child("hiring_procedure").getValue().toString());
                   firmsInfo.setStarting_salary(d.child("starting_salary").getValue().toString());
                   firmsInfoList.add(firmsInfo);
               }
               mAdapter = new RecyclerAdapter(firmsInfoList, CompanyListActivity.this);
               myrecyclerView.setAdapter(mAdapter);

           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });

   }
    private void searchAction(String text) {
        List<FirmsInfo> list=new ArrayList<>();
        for (FirmsInfo f:firmsInfoList
             ) {
            if(f.getFirm_name().toLowerCase().contains(text.toLowerCase())){
                list.add(f);
            }
        }
        mAdapter = new RecyclerAdapter(list, CompanyListActivity.this);
        myrecyclerView.setAdapter(mAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if(mAuth.getCurrentUser()!=null){
            getMenuInflater().inflate(R.menu.menu, menu);

        }        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout_btn) {
            Log.d("pppp", "onOptionsItemSelected: "+ mAuth.getCurrentUser().getUid());
            mAuth.signOut();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }
        return true;
    }
}
