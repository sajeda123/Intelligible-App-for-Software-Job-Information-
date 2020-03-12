package com.cse.cou.praptimoni.softwarefirmsbd;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.cse.cou.praptimoni.softwarefirmsbd.modal.FirmsInfo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;


public class AdminActivity extends AppCompatActivity {
    private StorageReference storageReference;

    Button submit_btn;
    EditText firm_name, firm_size, requirement, salary, exmasLanguage, hiring_procedure, website, location;

    ImageView imageView;

    FirmsInfo firmsInfo;
    Uri filePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        submit_btn = findViewById(R.id.submit_btn);
        firm_name = findViewById(R.id.firm_name);
        firm_size = findViewById(R.id.firm_size);
        requirement = findViewById(R.id.firm_requirement);
        salary = findViewById(R.id.firm_salary);
        exmasLanguage = findViewById(R.id.firm_language);
        hiring_procedure = findViewById(R.id.firm_hiring_procedure);
        website = findViewById(R.id.firm_website);
        location = findViewById(R.id.firm_location);
        imageView=findViewById(R.id.firm_image);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });
        storageReference= FirebaseStorage.getInstance().getReference();

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (
                        !firm_name.getText().toString().isEmpty() &&
                                !firm_size.getText().toString().isEmpty() &&
                                !requirement.getText().toString().isEmpty() &&
                                !salary.getText().toString().isEmpty() &&
                                !hiring_procedure.getText().toString().isEmpty() &&
                                !website.getText().toString().isEmpty()
                        ) {

                    uploadFile();


                    // comments.setText("");

                } else {
                    Toast.makeText(getApplicationContext(), "Fill all the fields", Toast.LENGTH_LONG).show();
                }

            }

        });
    }


    private void uploadFile() {

        if (filePath != null) {

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();

            final StorageReference riversRef = storageReference.child("App/prapti.png");
            riversRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    firmsInfo = new FirmsInfo();
                                    firmsInfo.setFirm_name(firm_name.getText().toString() + "");
                                    firmsInfo.setSize(firm_size.getText().toString() + "");
                                    firmsInfo.setRequirment(requirement.getText().toString() + "");
                                    firmsInfo.setStarting_salary(salary.getText().toString() + "");
                                    firmsInfo.setExamsLanguage(exmasLanguage.getText().toString() + "");
                                    firmsInfo.setHiring_procedure(hiring_procedure.getText().toString() + "");
                                    firmsInfo.setWebsite(website.getText().toString() + "");
                                    firmsInfo.setLocation(location.getText().toString() + "");
                                    firmsInfo.setImage_link(uri.toString());
                                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                                    DatabaseReference reference = databaseReference.child("firms");
                                    reference.push().setValue(firmsInfo);

                                    firm_name.setText("");
                                    firm_size.setText("");
                                    requirement.setText("");
                                    salary.setText("");
                                    exmasLanguage.setText("");
                                    hiring_procedure.setText("");
                                    website.setText("");
                                    location.setText("");
                                    imageView.setImageResource(0);
                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "File Uploaded", Toast.LENGTH_LONG).show();

                                }
                            });
                  }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {

                            progressDialog.dismiss();


                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();


                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    });
        }

        else {

        }
    }


    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 111);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
