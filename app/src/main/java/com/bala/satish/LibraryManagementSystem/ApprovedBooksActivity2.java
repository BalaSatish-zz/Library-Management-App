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

public class ApprovedBooksActivity2 extends Activity {
    ListView lvPersonsApplicationsList;
    static String Applicant;
    DatabaseReference databaseReference;  ListView lvBooksList;
    FirebaseDatabase firebaseDatabase;
    String Book;
    ArrayList<String> BookNames;
    ArrayList<ApprovedBookDetails> BooksList;
    ProgressDialog progressDialog;
    int Count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persons_applications);
        lvPersonsApplicationsList = (ListView) findViewById(R.id.lvPersonsApplcationslist);
        BookNames = new ArrayList<String>();
        BooksList = new ArrayList<ApprovedBookDetails>();
        Bundle b = getIntent().getExtras();
        Applicant = b.getString("Applicant");
        progressDialog = new ProgressDialog(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        progressDialog.setMessage("Fetching Details");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        databaseReference = FirebaseDatabase.getInstance().getReference("Approved Books/"+Applicant+"/");
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
                    DatabaseReference BookReference = FirebaseDatabase.getInstance().getReference("Approved Books/"+Applicant+"/"+x+"/");
                    BookReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Counter();
                            ApprovedBookDetails BD = dataSnapshot.getValue(ApprovedBookDetails.class);
                            if (BD != null) {
                                //Toast.makeText(BooksListActivity.this, ""+BD.getBook(), Toast.LENGTH_SHORT).show();
                                BooksList.add(BD);
                            }
                            if(BooksList.size()==BookNames.size()){
                                progressDialog.dismiss();
                                ApprovedBooksListAdapterClass adapter = new ApprovedBooksListAdapterClass(ApprovedBooksActivity2.this,BooksList);
                                lvPersonsApplicationsList.setAdapter(adapter);
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
            Toast.makeText(ApprovedBooksActivity2.this, "List Updated", Toast.LENGTH_SHORT).show();
            return;
        }

    }

    public static String getApplicant() {
        return Applicant;
    }
}
