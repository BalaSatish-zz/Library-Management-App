package com.bala.satish.LibraryManagementSystem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class UserActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user2);
    }

    public void ShowMeBooksList(View view) {
        startActivity(new Intent(this,BooksListActivity.class));
    }

    public void ShowMeBooksTakenByMe(View view) {
        startActivity(new Intent(this,TakenBooksActivity.class));
    }
}
