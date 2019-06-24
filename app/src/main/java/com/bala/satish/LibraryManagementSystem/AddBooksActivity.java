package com.bala.satish.LibraryManagementSystem;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddBooksActivity extends Activity implements View.OnClickListener {
    private Button badd;
    private DatabaseReference mDatabase;
    private String value;
    EditText etBookName,etBookAuthor,etBookQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

         badd= (Button)findViewById(R.id.bAdd);
         badd.setOnClickListener(this);
         etBookName = (EditText) findViewById(R.id.etBookName);
         etBookAuthor = (EditText) findViewById(R.id.etBookAuthor);
         etBookQuantity = (EditText) findViewById(R.id.etBookQuantity);
    }

    @Override
    public void onClick(View v) {
        if (v==badd){
            String BookName,BookAuthor,BookQuantity;
            BookName = etBookName.getText().toString().trim();
            BookAuthor = etBookAuthor.getText().toString().trim();
            BookQuantity = etBookQuantity.getText().toString().trim();
            if(BookName.isEmpty()|| BookAuthor.isEmpty() || BookQuantity.isEmpty()){
                Toast.makeText(this, "Fields Can't be Empty...!", Toast.LENGTH_SHORT).show();
                return;
            }
            BookDetails newadmin1 = new BookDetails(""+BookAuthor,""+BookName,""+BookQuantity );
            mDatabase = FirebaseDatabase.getInstance().getReference("Books/"+BookName);
            mDatabase.setValue(newadmin1);
        }
    }
}
