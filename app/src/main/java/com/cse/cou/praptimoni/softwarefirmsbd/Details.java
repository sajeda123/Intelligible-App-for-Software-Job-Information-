package com.cse.cou.praptimoni.softwarefirmsbd;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cse.cou.praptimoni.softwarefirmsbd.adapter.CommentListAdapter;
import com.cse.cou.praptimoni.softwarefirmsbd.modal.Comments;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.cse.cou.praptimoni.softwarefirmsbd.Constants.MyPREFERENCES;

public class Details extends AppCompatActivity {
    private FirebaseAuth mAuth;
    TextView show_name, show_size, show_requirement, show_statring_salary, show_language,
            show_hiring_procedure, show_website, show_location, show_comments;
    EditText comment;
    Button save_comment;
    RecyclerView commentListview;
    ArrayList<Comments> commentlist;
    CommentListAdapter adapter;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        SharedPreferences sharedpreferences= getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putString("firm_id", getIntent().getStringExtra("id"));
        editor.apply();

        imageView=findViewById(R.id.showImage);

        Glide.with(this)
                .load(getIntent().getStringExtra("image"))
                .into(imageView);

        commentListview = findViewById(R.id.comment_list);
        commentListview.setLayoutManager(new LinearLayoutManager(this));
        commentListview.setHasFixedSize(true);
        commentlist = new ArrayList<Comments>();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("firms").child(getIntent().getStringExtra("id")).child("comments");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                commentlist.clear();


                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Comments s = new Comments();

                    s.setComment_details(d.child("comment_details").getValue().toString());
                    s.setId(d.getKey());
                    s.setUser_name(d.child("id").getValue().toString());
                    Log.d("dddddd", "onDataChange: " + s.getComment_details());
                    commentlist.add(s);
                }

//                if(commentlist.size()>5){
//                    comment.setVisibility(View.GONE);
//                    save_comment.setVisibility(View.GONE);
//                }

                adapter = new CommentListAdapter(commentlist, Details.this);


                commentListview.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        comment = findViewById(R.id.comment_text);


        save_comment = findViewById(R.id.save_commnet_btn);


        save_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment_str = comment.getText().toString();
                if (comment_str.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Comment box is empty!", Toast.LENGTH_LONG).show();
                } else {
                    saveComment(comment_str);
                    comment.setText("");
                }
            }
        });


        mAuth = FirebaseAuth.getInstance();

        show_name = findViewById(R.id.show_firm_name);
        show_size = findViewById(R.id.show_firm_size);
        show_requirement = findViewById(R.id.show_requirement);
        show_statring_salary = findViewById(R.id.show_starting_salary);
        show_language = findViewById(R.id.show_exam_language);
        show_hiring_procedure = findViewById(R.id.show_hiring_procedure);
        show_website = findViewById(R.id.show_website);
        show_location = findViewById(R.id.show_location);
        show_comments = findViewById(R.id.show_comments);

        if(mAuth.getCurrentUser()==null){
            comment.setVisibility(View.GONE);
            save_comment.setVisibility(View.GONE);
        }

        show_name.setText(getIntent().getStringExtra("name"));
        show_size.setText(getIntent().getStringExtra("size"));
        show_requirement.setText(getIntent().getStringExtra("requirement"));
        show_statring_salary.setText(getIntent().getStringExtra("salary"));
        show_language.setText(getIntent().getStringExtra("language"));
        show_hiring_procedure.setText(getIntent().getStringExtra("hiring_procedure"));
        show_website.setText(getIntent().getStringExtra("website"));
        show_website.setText(Html.fromHtml(
                "Website : "+
                        "<a href=\""+getIntent().getStringExtra("website")+"\">Go to website</a> "));
        show_website.setMovementMethod(LinkMovementMethod.getInstance());
//        show_website.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(Details.this,WebsiteLink.class);
//                intent.putExtra("link",getIntent().getStringExtra("website"));
//                startActivity(intent);
//            }
//        });
        show_location.setText("Location : "+getIntent().getStringExtra("location"));
        show_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Details.this,MapsActivity.class);

                intent.putExtra("lat",getIntent().getFloatExtra("lat",0));
                intent.putExtra("lan",getIntent().getFloatExtra("lan",0));
                intent.putExtra("address",getIntent().getStringExtra("location"));
                startActivity(intent);
            }
        });
        // show_comments.setText(getIntent().getStringExtra("comments"));

    }

    private void saveComment(String comment_str) {

        String id = getIntent().getStringExtra("id");
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("firms").child(id).child("comments");

        Comments comments = new Comments(mAuth.getCurrentUser().getUid(), comment_str, mAuth.getCurrentUser().getEmail());

        databaseReference.push().setValue(comments);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if(mAuth.getCurrentUser()!=null){
            getMenuInflater().inflate(R.menu.menu, menu);

        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout_btn) {
            mAuth.signOut();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }
        return true;
    }
}
