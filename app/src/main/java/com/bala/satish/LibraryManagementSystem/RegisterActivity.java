package com.bala.satish.LibraryManagementSystem;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends Activity {
    EditText etFName,etLName,etEmailId,etPassword,etPassword2;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etFName=findViewById(R.id.etFName);
        etLName=findViewById(R.id.etLName);
        etEmailId=findViewById(R.id.etEmail);
        etPassword=findViewById(R.id.etPassword);
        etPassword2=findViewById(R.id.etPassword2);
        databaseReference = FirebaseDatabase.getInstance().getReference("Users/");
        progressDialog = new ProgressDialog(this);
    }

    public void RegisterAccount(View view) {
        String FName,LName,Email,P1,P2,Name;
        FName=etFName.getText().toString().trim();
        LName=etLName.getText().toString().trim();
        Email=etEmailId.getText().toString().trim();
        P1=etPassword.getText().toString().trim();
        P2=etPassword2.getText().toString().trim();
        if(!P1.contains(P2)){
            Toast.makeText(this, "Password Mismatch", Toast.LENGTH_SHORT).show();
            return;
        }else {
            progressDialog.setMessage("Registering with us.");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            firebaseAuth = FirebaseAuth.getInstance();
            firebaseAuth.createUserWithEmailAndPassword(Email,P1).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        String uid = firebaseUser.getUid().toString();
                        databaseReference.child(uid).setValue("false").addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                progressDialog.dismiss();
                                Toast.makeText(RegisterActivity.this, "Insertion Success", Toast.LENGTH_SHORT).show();
                            }
                        });
                        Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, "Error Occurred", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }



    }
}
