package com.wehealth.pdqbook.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wehealth.pdqbook.R;
import com.wehealth.pdqbook.getway.model.SearchResultListEntry;
import com.wehealth.pdqbook.listener.OnItemClickListenerWithViewCallBack;

import java.util.ArrayList;

/**
 * @Author yangxiao on 3/17/2017.
 */

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ViewHolder> {

    private ArrayList<SearchResultListEntry> mList;
    private OnItemClickListenerWithViewCallBack mClickListener;

    public SearchResultAdapter(ArrayList<SearchResultListEntry> list) {
        this.mList = list;
    }

    public void setItemClickListener(OnItemClickListenerWithViewCallBack listener) {
        this.mClickListener = listener;
    }

    public void setList(ArrayList<SearchResultListEntry> list) {
        this.mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_result, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String classic = mList.get(position).getCategoryName();
        if (classic.equalsIgnoreCase("PDQ")) {
            holder.classic.setText(mList.get(position).getList().get(0).getTitle());
            holder.lay.setVisibility(View.GONE);
            holder.description.setText(mList.get(position).getList().get(0).getDescList().get(0).getDesc());
        } else {
            holder.classic.setText(classic);
            holder.lay.setVisibility(View.VISIBLE);
            holder.description.setText(mList.get(position).getList().get(0).getDescription());
        }
        holder.title.setText(mList.get(position).getList().get(0).getTitle());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView classic;
        private TextView title;
        private TextView description;
        private LinearLayout lay;

        public ViewHolder(View itemView) {
            super(itemView);
            classic = (TextView) itemView.findViewById(R.id.item_search_result_classic);
            title = (TextView) itemView.findViewById(R.id.item_search_result_title);
            description = (TextView) itemView.findViewById(R.id.item_search_result_content);
            lay = (LinearLayout) itemView.findViewById(R.id.item_search_result_title_lay);
            if (mClickListener != null) {
                itemView.setOnClickListener(this);
            }
        }

        @Override
        public void onClick(View v) {
            mClickListener.onClick(getAdapterPosition(), v);
        }
    }
}
