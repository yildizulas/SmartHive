package com.example.smarthive;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    private List<ListModel> listList;

    public ListAdapter(List<ListModel> listList) {
        this.listList = listList;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        ListModel list = listList.get(position);
        holder.listName.setText(list.getName());
        holder.listDescription.setText(list.getDescription());
    }

    @Override
    public int getItemCount() {
        return listList.size();
    }

    static class ListViewHolder extends RecyclerView.ViewHolder {
        TextView listName, listDescription;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            listName = itemView.findViewById(R.id.listName);
            listDescription = itemView.findViewById(R.id.listDescription);
        }
    }
}