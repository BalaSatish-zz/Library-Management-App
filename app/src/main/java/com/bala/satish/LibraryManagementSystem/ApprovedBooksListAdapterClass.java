package com.bala.satish.LibraryManagementSystem;

import android.app.Activity;
import android.graphics.Color;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ApprovedBooksListAdapterClass extends ArrayAdapter<ApprovedBookDetails>{
    private Activity context;
    private List<ApprovedBookDetails> BooksList;
    int n=1;
    int DaysLeft;
    public ApprovedBooksListAdapterClass(Activity context, List<ApprovedBookDetails> BooksList){
        super(context, R.layout.book_list_item,BooksList);
        this.context = context;
        this.BooksList = BooksList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ApprovedBookDetails bookDetails = BooksList.get(position);
        LayoutInflater inflater = context.getLayoutInflater();
        View ListViewItem = inflater.inflate(R.layout.approved_book_list_item,null,true);
        final TextView textViewBookName = (TextView) ListViewItem.findViewById(R.id.tvBName);
        final TextView textViewBookApprovalDate = (TextView) ListViewItem.findViewById(R.id.tvBApprovalDate);
        final TextView textViewDaysLeft = (TextView) ListViewItem.findViewById(R.id.tvDaysLeft);
        final Button buttonReturnBook = (Button) ListViewItem.findViewById(R.id.bReturnBook);
        textViewBookName.setText(bookDetails.getBookName());
        textViewBookApprovalDate.setText("Approved On: "+bookDetails.approvalDate);
        textViewBookApprovalDate.setTextSize((float)22);
        String sdate  = bookDetails.getApprovalDate();
        DateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date oldDate = formater.parse(sdate);
            Date today = new Date();
            long days = daysBetween(oldDate,today);
            int daysLeft = (int) (14-days);
            textViewDaysLeft.setText("Days Left: "+daysLeft);
            textViewDaysLeft.setTextColor(Color.GREEN);
            if(daysLeft<0){
                textViewDaysLeft.setText("Fine: ₹"+(-daysLeft)*5);
                textViewDaysLeft.setTextColor(Color.RED);
                textViewDaysLeft.setTextSize((float)26);

            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


        buttonReturnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Applicant = ApprovedBooksActivity2.getApplicant();
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Approved Books/"+Applicant+"/"+bookDetails.getBookName()+"/");
                databaseReference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Books/"+bookDetails.getBookName()+"/quantity");
                        db.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String Quantity = String.valueOf(dataSnapshot.getValue());
                                while(n==1) {
                                    int Q = Integer.parseInt(Quantity);
                                    Q = Q+1;
                                    DatabaseReference d = FirebaseDatabase.getInstance().getReference("Books/"+bookDetails.getBookName()+"/quantity");
                                    d.setValue(""+Q);
                                    n=0;
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        Toast.makeText(context, "Book Returned", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        return ListViewItem;
    }

    public long daysBetween(Date one,Date two){
        long difference = (one.getTime()-two.getTime())/86400000;
        return Math.abs(difference);
    }
}
