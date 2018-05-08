package com.example.android.weeklygroceryapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class listAddItem extends AppCompatActivity {
    private String listName;
    private JSONArray itemsArr;
    private EditText name,amount,price;
    private Button addItem;
    private DBHelper dbHelper = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_add_item);
        setTitle("Add New Item");
        name = findViewById(R.id.new_item_name);
        amount = findViewById(R.id.new_item_amount);
        price = findViewById(R.id.new_item_price);
        addItem = findViewById(R.id.btn_add_item);

        Intent addItem = getIntent();
        listName = addItem.getStringExtra("listName");
        try {
            itemsArr = new JSONArray(addItem.getStringExtra("itemsArr"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public void AddItem(View view) {
        if (TextUtils.isEmpty(name.getText())){
            name.setError("Name Required!");
        } else {
            if (TextUtils.isEmpty(amount.getText())){
                amount.setText("0");
            }
            if (TextUtils.isEmpty(price.getText())){
                price.setText("0.0");
            }
            RecyclerList currList = dbHelper.getList(listName);
            Log.i("test",currList.getList());
            itemsArr = currList.getListItemsArr();

            JSONObject newitem =new JSONObject();
            try {
                newitem.put("Name", name.getText().toString());
                newitem.put("Price", Float.parseFloat(price.getText().toString()));
                newitem.put("Amount", Integer.parseInt(amount.getText().toString()));
                if (itemsArr==null){
                    itemsArr = new JSONArray();
                }
                itemsArr.put(newitem);
            } catch (JSONException e){
                e.printStackTrace();
            }
            dbHelper.addItemToList(currList.getList(), itemsArr);
            finish();
        }
    }
}
