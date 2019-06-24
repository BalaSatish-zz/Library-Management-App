package com.bala.satish.LibraryManagementSystem;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ApprovedBooksActivity1 extends Activity {
    DatabaseReference databaseReference;
    ArrayList<String> EmailsList;
    ListView lvEmailsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approved_books_1);
        databaseReference = FirebaseDatabase.getInstance().getReference("Approved Books");
        lvEmailsList = (ListView) findViewById(R.id.lvEmailsList);
        EmailsList = new ArrayList<String>();
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                EmailsList.clear();
                for(DataSnapshot child: dataSnapshot.getChildren()){
                    String x = child.getKey();
                    EmailsList.add(x);
                    Toast.makeText(ApprovedBooksActivity1.this, ""+x, Toast.LENGTH_SHORT).show();
                }
                EmailsListAdapterClass2 adapter = new EmailsListAdapterClass2(ApprovedBooksActivity1.this,EmailsList);
                lvEmailsList.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
