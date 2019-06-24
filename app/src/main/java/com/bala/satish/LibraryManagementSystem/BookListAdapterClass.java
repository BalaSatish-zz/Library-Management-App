package com.bala.satish.LibraryManagementSystem;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class BookListAdapterClass extends ArrayAdapter<BookDetails>{
    private Activity context;
    private List<BookDetails> BooksList;

    public BookListAdapterClass(Activity context,List<BookDetails> BooksList){
        super(context, R.layout.book_list_item,BooksList);
        this.context = context;
        this.BooksList = BooksList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final BookDetails bookDetails = BooksList.get(position);
        LayoutInflater inflater = context.getLayoutInflater();
        View ListViewItem = inflater.inflate(R.layout.book_list_item,null,true);
        final TextView textViewBookName = (TextView) ListViewItem.findViewById(R.id.tvBName);
        final TextView textViewBookAuthor = (TextView) ListViewItem.findViewById(R.id.tvBAuthor);
        final TextView textViewBookAvailability = (TextView) ListViewItem.findViewById(R.id.tvBAvailability);
        textViewBookName.setText(bookDetails.getBook());
        textViewBookAuthor.setText(bookDetails.getAuthor());
        if(Integer.parseInt(String.valueOf(bookDetails.getQuantity()))!=0){
            textViewBookAvailability.setText("Available");
            textViewBookAvailability.setTextColor(Color.GREEN);
            textViewBookName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context,ApplyBooksActivity.class);
                    i.putExtra("BookName",textViewBookName.getText().toString().trim());
                    i.putExtra("BookAuthor",textViewBookAuthor.getText().toString().trim());
                    i.putExtra("BookAvailability",""+bookDetails.getQuantity());
                    context.finish();
                    context.startActivity(i);
                }
            });
        }
        else{
            textViewBookAvailability.setText("***Currently Unavailable***");
            textViewBookAvailability.setTextColor(Color.RED);
        }
        return ListViewItem;
    }
}
