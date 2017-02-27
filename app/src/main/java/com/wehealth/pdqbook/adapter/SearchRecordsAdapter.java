package com.wehealth.pdqbook.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wehealth.pdqbook.R;
import com.wehealth.pdqbook.getway.datamodel.SearchRecord;
import com.wehealth.pdqbook.view.RecyclerItemClickListener;

import java.util.ArrayList;

/**
 * Created by xiaoyang on 2017/2/16.
 */

public class SearchRecordsAdapter extends RecyclerView.Adapter<SearchRecordsAdapter.SearchRecordViewHolder> {
    private RecyclerItemClickListener mClickListener;
    private ArrayList<SearchRecord> mRecords;

    public SearchRecordsAdapter(ArrayList<SearchRecord> records) {
        this.mRecords = records;
    }

    public void setClickListener(RecyclerItemClickListener listener) {
        this.mClickListener = listener;
    }

    @Override
    public SearchRecordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_records, null);
        return new SearchRecordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchRecordViewHolder holder, int position) {
        holder.content.setText(mRecords.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return mRecords.size();
    }


    class SearchRecordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView content;

        public SearchRecordViewHolder(View itemView) {
            super(itemView);
            content = (TextView) itemView.findViewById(R.id.item_search_records_content);
            if (mClickListener != null) {
                itemView.setOnClickListener(this);
            }
        }

        @Override
        public void onClick(View v) {
            mClickListener.onClick(getAdapterPosition());
        }
    }
}
