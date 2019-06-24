package com.bala.satish.LibraryManagementSystem;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TakenBooksListAdapterClass extends ArrayAdapter<ApprovedBookDetails>{
    private Activity context;
    private List<ApprovedBookDetails> BooksList;
    int n=1;
    int DaysLeft;
    public TakenBooksListAdapterClass(Activity context, List<ApprovedBookDetails> BooksList){
        super(context, R.layout.book_list_item,BooksList);
        this.context = context;
        this.BooksList = BooksList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ApprovedBookDetails bookDetails = BooksList.get(position);
        LayoutInflater inflater = context.getLayoutInflater();
        View ListViewItem = inflater.inflate(R.layout.taken_books_list_item,null,true);
        final TextView textViewBookName = (TextView) ListViewItem.findViewById(R.id.tvBName);
        final TextView textViewBookApprovalDate = (TextView) ListViewItem.findViewById(R.id.tvBApprovalDate);
        final TextView textViewDaysLeft = (TextView) ListViewItem.findViewById(R.id.tvDaysLeft);
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
                textViewDaysLeft.setText("Pay Fine: ₹"+(-daysLeft)*5);
                textViewDaysLeft.setTextColor(Color.RED);
                textViewDaysLeft.setTextSize((float)26);

            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return ListViewItem;
    }

    public long daysBetween(Date one,Date two){
        long difference = (one.getTime()-two.getTime())/86400000;
        return Math.abs(difference);
    }
}
