package com.example.android.weeklygroceryapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RecyclerList {

    public String List;
    public JSONArray ListItemsArr;

    public RecyclerList() {
    }
    public RecyclerList(String list) {
        List = list;
    }
    public RecyclerList(String list, String itemName, float Price, int Amount, Boolean State) {
        List = list;
        JSONObject listItem = new JSONObject();
        try {
            State=false;
            listItem.put("Name", itemName);
            listItem.put("Price", Price);
            listItem.put("Amount", Amount);
            listItem.put("State", State);
            if (ListItemsArr==null){
                ListItemsArr = new JSONArray();
            }
            ListItemsArr.put(listItem);
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    public String getList() {
        return List;
    }

    public JSONArray getListItemsArr() {
        return ListItemsArr;
    }

    public void setList(String list) {
        List = list;
    }

    public void setListItemsArr(JSONArray listItemsArr) {
        ListItemsArr = listItemsArr;
    }
}
