package com.bala.satish.LibraryManagementSystem;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ApplicationListAdapterClass extends ArrayAdapter<BookDetails>{
    private Activity context;
    private List<BookDetails> BooksList;
    int n = 1;

    public ApplicationListAdapterClass(Activity context, List<BookDetails> BooksList){
        super(context, R.layout.appplications_book_list_item,BooksList);
        this.context = context;
        this.BooksList = BooksList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final BookDetails bookDetails = BooksList.get(position);
        LayoutInflater inflater = context.getLayoutInflater();
        View ListViewItem = inflater.inflate(R.layout.appplications_book_list_item,null,true);
        final TextView textViewBookName = (TextView) ListViewItem.findViewById(R.id.tvBName);
        final Button buttonApprove = (Button) ListViewItem.findViewById(R.id.bApprove);
        final Button buttonReject = (Button) ListViewItem.findViewById(R.id.bReject);
        textViewBookName.setText(bookDetails.getBook());
        final String BookName = bookDetails.getBook();
        buttonApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "Approve", Toast.LENGTH_SHORT).show();
                final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Books/"+BookName+"/quantity");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String Quantity = dataSnapshot.getValue().toString().trim();
                        //Toast.makeText(context, ""+Quantity, Toast.LENGTH_SHORT).show();
                        int Q = Integer.parseInt(Quantity);
                        while(n==1){
                            if(Q!=0){
                                Q= Q-1;
                                Toast.makeText(context, ""+Q, Toast.LENGTH_SHORT).show();
                                BookDetails BD = new BookDetails(""+bookDetails.getAuthor(),""+bookDetails.getBook(),""+Q);
                                final String Applicant = PersonsApplicationsActivity.getApplicant();
                                Toast.makeText(context, ""+BD.getQuantity(), Toast.LENGTH_SHORT).show();
                                Toast.makeText(context, ""+Applicant, Toast.LENGTH_SHORT).show();
                                DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference("Applications/"+Applicant+"/"+bookDetails.getBook()+"/");
                                dbReference.removeValue();
                                DatabaseReference db = FirebaseDatabase.getInstance().getReference("Books/"+bookDetails.getBook()+"/");
                                db.setValue(BD).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            DatabaseReference adr = FirebaseDatabase.getInstance().getReference("Approved Books/"+Applicant+"/"+BookName+"/");
                                            Date date = new Date();
                                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                                            String ApprovalDate =sdf.format(date);
                                            ApprovedBookDetails ABD = new ApprovedBookDetails(""+ApprovalDate,""+BookName,""+14);
                                            adr.setValue(ABD).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    Toast.makeText(context, "Approved", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                        else {
                                            Toast.makeText(context, "Error Occurred.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                            n=0;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        buttonReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Applicant = PersonsApplicationsActivity.getApplicant();
                //Toast.makeText(context, ""+Applicant, Toast.LENGTH_SHORT).show();
                DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference("Applications/"+Applicant+"/"+bookDetails.getBook()+"/");
                dbReference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(context, "Rejected", Toast.LENGTH_SHORT).show();
                            context.recreate();
                        }
                        else{
                            Toast.makeText(context, "Error Occurred", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        return ListViewItem;
    }
}
