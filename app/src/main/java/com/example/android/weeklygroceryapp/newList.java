package com.example.android.weeklygroceryapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class newList extends AppCompatActivity {

    private EditText listName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_list);
        listName = findViewById(R.id.newListName);
    }

    public void createNewList(View view) {
        if (TextUtils.isEmpty(listName.getText())) {
            listName.setError("Name Required");
        } else {
            DBHelper dbHelper = new DBHelper(this);
            String name = listName.getText().toString();
            if (dbHelper.createList(name)==false){
                Toast.makeText(this,"List already exists, choose a different name",Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this,"List Created!",Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }
}
