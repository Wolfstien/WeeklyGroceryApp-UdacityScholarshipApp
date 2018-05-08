package com.example.android.weeklygroceryapp;

import android.app.LauncherActivity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>{

    private List<RecyclerList>listItems;
    private Context context;

    public ListAdapter(List<RecyclerList> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View l = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_list, parent, false);
        return new ViewHolder(l);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final RecyclerList currList = listItems.get(position);
        holder.textView_list.setText(currList.getList());
        Integer noItems;
        if (currList.getListItemsArr()==null){
            noItems = 0;
            holder.list_no_items.setBackgroundResource(0);
        } else {
            noItems = currList.getListItemsArr().length();
            holder.list_no_items.setText(noItems.toString());
        }

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,currList.getList(), Toast.LENGTH_SHORT).show();
                Intent listViewIntent = new Intent(context,CurrentlySelectedList.class);
                listViewIntent.putExtra("listName",currList.getList());
                listViewIntent.putExtra("listItems",currList.getListItemsArr().toString());
                context.startActivity(listViewIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textView_list,list_no_items;
        RelativeLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            textView_list = itemView.findViewById(R.id.textView_list);
            list_no_items = itemView.findViewById(R.id.list_no_items);
            parentLayout = itemView.findViewById(R.id.list_onClick);
        }
    }


}
