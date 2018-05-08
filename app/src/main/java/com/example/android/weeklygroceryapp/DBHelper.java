package com.example.android.weeklygroceryapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "groceryAppDB";

    private static final String TABLE_SHOPPINGLIST = "shoppingLists";
    private static final String TABLE_ITEMTEMPLATE = "itemTemplates";

//    S = Shopping lists
    private static final String KEY_S_ID = "sId";
    private static final String KEY_S_NAME = "sName";
    private static final String KEY_S_ITEMSARR = "sItemsArr";

//    T =template of item
    private static final String KEY_T_ID = "tId";
    private static final String KEY_T_NAME = "tName";
    private static final String KEY_T_PRICE = "tPrice";
    private static final String KEY_T_CATEGORY = "tCategory";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_SHOPPINGLIST = "create table "+TABLE_SHOPPINGLIST+" ("
                +KEY_S_ID+" integer primary key autoincrement, "
                +KEY_S_NAME+" text not null, "
                +KEY_S_ITEMSARR+" text not null)";
        String CREATE_TABLE_ITEMTEMPLATE = "create table "+TABLE_ITEMTEMPLATE+" ("
                +KEY_T_ID+" integer primary key autoincrement, "
                +KEY_T_NAME+" text not null, "
                +KEY_T_PRICE+" real not null, "
                +KEY_T_CATEGORY+" text)";

        db.execSQL(CREATE_TABLE_SHOPPINGLIST);
        db.execSQL(CREATE_TABLE_ITEMTEMPLATE);

        Template Template = new Template();
        List<Template.TemplateItem> ItemArr = Template.getTemplate();
        ContentValues values = new ContentValues();
        for(Template.TemplateItem I : ItemArr){
            values.put(KEY_T_NAME, I.getName());
            values.put(KEY_T_PRICE, I.getPrice());
            values.put(KEY_T_CATEGORY, I.getCategory());
            db.insert(TABLE_ITEMTEMPLATE, null, values);
            values.clear();
        };
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists "+TABLE_SHOPPINGLIST);
        db.execSQL("drop table if exists "+TABLE_ITEMTEMPLATE);
    }

    public List<RecyclerList> getAllLists(){
        List<RecyclerList> Lists = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * From "+TABLE_SHOPPINGLIST,null);
        if (cursor.moveToFirst()){
            do {
                try {
                    JSONArray itemsArr = new JSONArray(cursor.getString(2));
                    RecyclerList list = new RecyclerList();
                    list.setList(cursor.getString(1));
                    list.setListItemsArr(itemsArr);
                    Lists.add(list);
                } catch (JSONException e){
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
        }
        db.close();
        return Lists;
    }

    public RecyclerList getList(String listName){
        SQLiteDatabase db = this.getWritableDatabase();
        RecyclerList list = new RecyclerList();
        Cursor cursor = db.rawQuery("Select * From "+TABLE_SHOPPINGLIST+" where "+KEY_S_NAME+"=?",new String[] {listName});
        if (cursor.moveToFirst()){
            try {
                JSONArray itemsArr = new JSONArray(cursor.getString(2));
                list.setList(cursor.getString(1));
                list.setListItemsArr(itemsArr);
            } catch (JSONException e){
                e.printStackTrace();
            }
        }
        db.close();
        return list;
    }

    public boolean createList(String listName){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from "+TABLE_SHOPPINGLIST+" where "+KEY_S_NAME+" ="+ "'"+listName+"'",null);
        if(cursor.getCount() <= 0){
            cursor.close();
            JSONArray emptyArr = new JSONArray();
            ContentValues values = new ContentValues();
            values.put(KEY_S_NAME, listName);
            values.put(KEY_S_ITEMSARR, emptyArr.toString());
            db.insert(TABLE_SHOPPINGLIST, null, values);
            values.clear();
            db.close();
            return true;
        } else {
            db.close();
            return false;
        }
    }

//    public void updateList(String listName, JSONArray updateArray){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(KEY_S_ITEMSARR, updateArray.toString());
//        db.update(TABLE_SHOPPINGLIST,values,KEY_S_NAME+"=?",new String[] {listName});
//        values.clear();
//        db.close();
//    }

    public void deleteList(String listName){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SHOPPINGLIST,KEY_S_NAME+"="+listName, null);
        db.close();
    }

    public void addItemToList(String listName, JSONArray itemsArr){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_S_ITEMSARR, itemsArr.toString());
        db.update(TABLE_SHOPPINGLIST, values,KEY_S_NAME+"=?", new String[] {listName});
        values.clear();
        db.close();
    }

    public void updateItem(){

    }

    public void removeItemFromList(){

    }


















//    public static String strSeparator = "__,__";
//    public static String convertArrayToString(String[] array){
//        String str = "";
//        for (int i = 0;i<array.length; i++) {
//            str = str+array[i];
//            // Do not append comma at the end of last element
//            if(i<array.length-1){
//                str = str+strSeparator;
//            }
//        }
//        return str;
//    }
//
//    public static String[] convertStringToArray(String str){
//        String[] arr = str.split(strSeparator);
//        return arr;
//    }

}

