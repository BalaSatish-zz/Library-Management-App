package com.bala.satish.LibraryManagementSystem;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ApplyBooksActivity extends Activity {
    TextView tvBookName,tvAuthorName;
    String BookName,BookAuthor,BookAvailability;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_books);
        tvBookName=(TextView) findViewById(R.id.tvBookName);
        tvAuthorName=(TextView) findViewById(R.id.tvAuthorName);
        Bundle b = getIntent().getExtras();
        BookName = b.getString("BookName");
        BookAuthor = b.getString("BookAuthor");
        BookAvailability = b.getString("BookAvailability");
        tvBookName.setText(BookName);
        tvAuthorName.setText(BookAuthor);
    }

    public void ApplyBook(View view) {
        BookName = tvBookName.getText().toString().trim();
        BookAuthor = tvAuthorName.getText().toString().trim();
        String Email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        Email = Email.replace("@gmail.com","");
        databaseReference = FirebaseDatabase.getInstance().getReference("Applications/"+Email+"/"+BookName+"/");
        BookDetails BD = new BookDetails(""+BookAuthor,""+BookName,""+BookAvailability);
        databaseReference.setValue(BD).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(ApplyBooksActivity.this, "Applied Successfully ", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
