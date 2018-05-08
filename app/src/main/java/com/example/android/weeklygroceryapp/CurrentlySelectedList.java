package com.example.android.weeklygroceryapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class CurrentlySelectedList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private String listName;
    private JSONArray itemsArr;
    private  DBHelper dbHelper = new DBHelper(this);
    private RecyclerList currList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currently_selected_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recycler_items);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent listViewIntent = getIntent();
        listName = listViewIntent.getStringExtra("listName");
        try {
            itemsArr = new JSONArray(listViewIntent.getStringExtra("listItems"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        setTitle(listName);
        adapter = new ItemAdapter(this, itemsArr, listName);
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addItem = new Intent(CurrentlySelectedList.this,listAddItem.class);
                addItem.putExtra("listName",listName);
                addItem.putExtra("itemsArr",itemsArr.toString());
                startActivity(addItem);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

//      test
        currList = dbHelper.getList(listName);
        itemsArr = currList.getListItemsArr();
        adapter = new ItemAdapter(this, itemsArr, currList.getList());
        recyclerView.setAdapter(adapter);
    }
}
