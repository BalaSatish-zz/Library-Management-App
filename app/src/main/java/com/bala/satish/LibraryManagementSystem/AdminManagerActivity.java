package com.bala.satish.LibraryManagementSystem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AdminManagerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_manager);
    }

    public void TakeMeToAddBooks(View view) {
        startActivity(new Intent(this,AddBooksActivity.class));
    }

    public void TakeMeToApproveBooks(View view) {
        startActivity(new Intent(this,ApproveBooksActivity.class));
    }

    public void TakeMeToApprovedBooks(View view) {
        startActivity(new Intent(this,ApprovedBooksActivity1.class));
    }
}
