package com.wehealth.pdqbook.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wehealth.pdqbook.R;
import com.wehealth.pdqbook.getway.model.SearchResultInfoEntry;

import java.util.ArrayList;

/**
 * @Author yangxiao on 3/23/2017.
 */

public class SearchResultPopWindowListAdapter extends RecyclerView.Adapter<SearchResultPopWindowListAdapter.SearchResultViewHolder> {

    private ArrayList<SearchResultInfoEntry> mList;

    public SearchResultPopWindowListAdapter(ArrayList<SearchResultInfoEntry> list) {
        this.mList = list;
    }

    @Override
    public SearchResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_popwindow_list, parent, false);
        return new SearchResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchResultViewHolder holder, int position) {
        holder.title.setText(mList.get(position).getTitle());
        holder.description.setText(mList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class SearchResultViewHolder extends RecyclerView.ViewHolder {

        TextView title, description;

        public SearchResultViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.item_pop_list_title);
            description = (TextView) itemView.findViewById(R.id.item_pop_list_content);
        }
    }
}
