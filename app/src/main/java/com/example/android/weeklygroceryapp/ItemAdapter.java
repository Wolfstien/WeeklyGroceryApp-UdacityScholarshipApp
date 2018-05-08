package com.example.android.weeklygroceryapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private String currList;
    private JSONArray itemsArr;
    private Context context;
    private DBHelper dbHelper;
    public SharedPreferences sp;

    public ItemAdapter(Context context, JSONArray itemsArr, String currList){
        this.context = context;
        this.itemsArr = itemsArr;
        this.currList = currList;
        dbHelper = new DBHelper(context);
        sp = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View i = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_items, parent, false);
        return new ViewHolder(i);
    }

    @Override
    public void onBindViewHolder(final ItemAdapter.ViewHolder holder, final int position) {
        final JSONObject currItem;
        try {
            currItem = itemsArr.getJSONObject(position);
            holder.itemName.setText(currItem.get("Name").toString());
            holder.Amount.setText(currItem.get("Amount").toString());

            String fPrice = sp.getString("price_metric","$")+" "+ (Float.parseFloat(currItem.get("Price").toString())*Integer.parseInt(currItem.get("Amount").toString()));
            holder.Price.setText(fPrice);
            holder.Check.setChecked(currItem.getBoolean("State"));
            if (currItem.getBoolean("State")){
                holder.parentLayout.setBackgroundColor(ContextCompat.getColor(context,R.color.mintGreen));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        holder.Check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                try {
                    itemsArr.getJSONObject(position).put("State", b);
                    dbHelper.addItemToList(currList, itemsArr);
                    if (b){
                        holder.parentLayout.setBackgroundColor(ContextCompat.getColor(context,R.color.mintGreen));
                    } else {
                        holder.parentLayout.setBackgroundColor(ContextCompat.getColor(context,R.color.white));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemsArr.length();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout parentLayout;
        TextView itemName,Amount,Price;
        CheckBox Check;

        public ViewHolder(View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.item_list_name);
            parentLayout = itemView.findViewById(R.id.item_onClick);
            Amount = itemView.findViewById(R.id.Amount);
            Price = itemView.findViewById(R.id.Price);
            Check = itemView.findViewById(R.id.item_check);
        }
    }
}
