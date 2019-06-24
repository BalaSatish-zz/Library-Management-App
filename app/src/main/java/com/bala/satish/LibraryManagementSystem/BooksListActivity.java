package com.bala.satish.LibraryManagementSystem;

import android.app.Activity;
import android.app.ProgressDialog;
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

public class BooksListActivity extends Activity {
    ListView lvBooksList;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String Book;
    ArrayList<String> BookNames;
    ArrayList<BookDetails> BooksList;
    ProgressDialog progressDialog;
    int Count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        lvBooksList = (ListView) findViewById(R.id.lvBooksList);
        BookNames = new ArrayList<String>();
        BooksList = new ArrayList<BookDetails>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Books");
        progressDialog = new ProgressDialog(this);
    }
    @Override
    protected void onStart() {
        super.onStart();
        progressDialog.setMessage("Fetching Books");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    BookNames.clear();
                    for(DataSnapshot child:dataSnapshot.getChildren()){
                        BookNames.add(child.getKey());
                        //Toast.makeText(BooksListActivity.this, ""+BD.getBook(), Toast.LENGTH_SHORT).show();
                    }
                    Count = BookNames.size();
                    for(String x: BookNames){
//                        Toast.makeText(BooksListActivity.this, ""+x, Toast.LENGTH_SHORT).show();
                        DatabaseReference BookReference = FirebaseDatabase.getInstance().getReference("Books/"+x);
                        BookReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Counter();
                                BookDetails BD = dataSnapshot.getValue(BookDetails.class);
                                    //Toast.makeText(BooksListActivity.this, ""+BD.getBook(), Toast.LENGTH_SHORT).show();
                                    BooksList.add(BD);
                                if(BookNames.size()==BooksList.size()){
                                    progressDialog.dismiss();
                                    BookListAdapterClass adapter = new BookListAdapterClass(BooksListActivity.this,BooksList);
                                    lvBooksList.setAdapter(adapter);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

      public void  Counter(){
        Count--;
        if(Count==0){

        }
        if(Count<0){
            recreate();
            Toast.makeText(BooksListActivity.this, "Books Updated", Toast.LENGTH_SHORT).show();
            return;
        }
      }
    }
