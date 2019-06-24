package com.bala.satish.LibraryManagementSystem;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class EmailsListAdapterClass extends ArrayAdapter<String>{
    private Activity context;
    private List<String> EmailsList;

    public EmailsListAdapterClass(Activity context, List<String> EmailsList){
        super(context, R.layout.emails_list_item,EmailsList);
        this.context = context;
        this.EmailsList = EmailsList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final String Email = EmailsList.get(position);
        LayoutInflater inflater = context.getLayoutInflater();
        View ListViewItem = inflater.inflate(R.layout.emails_list_item,null,true);
        final TextView textViewEmailName = (TextView) ListViewItem.findViewById(R.id.tvEmailName);
        textViewEmailName.setText(Email);
        textViewEmailName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context,PersonsApplicationsActivity.class);
                i.putExtra("Applicant",""+Email);
                context.finish();
                context.startActivity(i);
            }
        });
        return ListViewItem;
    }
}
