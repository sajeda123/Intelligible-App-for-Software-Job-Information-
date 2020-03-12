package com.cse.cou.praptimoni.softwarefirmsbd;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cse.cou.praptimoni.softwarefirmsbd.modal.Comments;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.cse.cou.praptimoni.softwarefirmsbd.Constants.MyPREFERENCES;

public class CommentDetails extends AppCompatActivity {
    SharedPreferences sharedpreferences;
    EditText commnet;
    Button update, delete;
    DatabaseReference reference;

    FirebaseAuth auth;

    LinearLayout linearLayout;
    String query_comment_id;
    String getQuery_comment;
    ProgressDialog progressBar;

    boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_details);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        final String firm_id = sharedpreferences.getString("firm_id", "11");
        final String comment_id = getIntent().getStringExtra("id");
        String user_id = getIntent().getStringExtra("user");
        commnet = findViewById(R.id.edit_comment);
        auth = FirebaseAuth.getInstance();

        reference = FirebaseDatabase.getInstance().getReference().child("firms").child(firm_id).child("comments").child(comment_id);



        commnet.setText(getIntent().getStringExtra("comment"));
        linearLayout = findViewById(R.id.commet_btn_layout);
        if (auth.getCurrentUser().getUid().equals(user_id)) {
            linearLayout.setVisibility(View.VISIBLE);
        } else {
            linearLayout.setVisibility(View.GONE);
        }
        update = findViewById(R.id.save_edit_comment);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (commnet.getText().toString().isEmpty()) {

                    Toast.makeText(getApplicationContext(), "Werite commnet", Toast.LENGTH_LONG).show();
                } else {
                    DatabaseReference databaseReference = reference.child("comment_details");
                    databaseReference.setValue(commnet.getText().toString() + "");
                    commnet.setText("");
                }
            }
        });

        delete = findViewById(R.id.delete_comment);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog alertDialog = new AlertDialog.Builder(CommentDetails.this).create();
                alertDialog.setTitle("Delete");
                alertDialog.setMessage("Are you sure to delete this comment?");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();
                                final ProgressDialog dialog1 = ProgressDialog.show(CommentDetails.this, "",
                                        "Deleting. Please wait...", true);
                                DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().child("firms").child(firm_id).child("comments").child(comment_id);
                                reference1.removeValue();
                                new android.os.Handler().postDelayed(
                                        new Runnable() {
                                            public void run() {

                                                dialog1.dismiss();
                                                Intent intent=new Intent(CommentDetails.this,Details.class);
                                                startActivity(intent);
                                                CommentDetails.this.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);


                                                Log.i("tag", "This'll run 300 milliseconds later");
                                            }
                                        },
                                        3000);
                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });


    }
}
