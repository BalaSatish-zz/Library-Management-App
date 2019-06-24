package com.bala.satish.LibraryManagementSystem;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etEmail1, etPassword1;
    Button bLogin;
    TextView tvRegister;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bLogin = (Button) findViewById(R.id.bLogin);
        tvRegister = findViewById(R.id.tvRegister);
        bLogin.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
        firebaseAuth = FirebaseAuth.getInstance();
        etEmail1 = findViewById(R.id.etEmail1);
        etPassword1 = findViewById(R.id.etPassword1);
        progressDialog = new ProgressDialog(this);
    }

    @Override
    public void onClick(View view) {
        if (view == tvRegister) {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        }
        if (view == bLogin) {
            String Email1, P;
            Email1 = etEmail1.getText().toString().trim();
            P = etPassword1.getText().toString().trim();
            progressDialog.setMessage("Login under progress");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            firebaseAuth = FirebaseAuth.getInstance();
            firebaseAuth.signInWithEmailAndPassword(Email1, P).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull final Task<AuthResult> task) {
                    FirebaseUser firebaseUser= firebaseAuth.getCurrentUser();
                    String UserID="";
                    if (firebaseUser != null) {
                        UserID = firebaseUser.getUid();
                    }
                    if(task.isSuccessful()){
                    DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference("Users/");
                    firebaseDatabase.child("" + UserID).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String Access = "" + dataSnapshot.getValue();

                            //Toast.makeText(LoginActivity.this, ""+Access, Toast.LENGTH_SHORT).show();
                            if (Access.contains("false")) {
                                progressDialog.dismiss();
                                startActivity(new Intent(LoginActivity.this,UserActivity.class));
                            } else if (Access.contains("true")) {
                                progressDialog.dismiss();
                                startActivity(new Intent(LoginActivity.this, AdminManagerActivity.class));
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            progressDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "Error Occurred", Toast.LENGTH_SHORT).show();

                        }
                    });
                }else{
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "Error Occurred", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
