package com.example.medairecruit.ui.symptom_checker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.medairecruit.R;

import java.util.List;

public class SymptomCheckerSearchedAdapter extends RecyclerView.Adapter<SymptomCheckerSearchedAdapter.ViewHolder> {

    private List<String> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    int selectedPosition = -1;

    // data is passed into the constructor
    SymptomCheckerSearchedAdapter(Context context, List<String> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    public SymptomCheckerSearchedAdapter() {

    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_suggested_layout, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String text = mData.get(position);
        holder.myTextView.setText(text);

//        if (selectedPosition == position) {
//            holder.myTextView.setBackground(ContextCompat.getDrawable(holder.myItemSlot.getContext(), R.drawable.bg_option_selected));
//        } else {
//            holder.myTextView.setBackground(ContextCompat.getDrawable(holder.myItemSlot.getContext(), R.drawable.bg_option_unselected));
//        }
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;
        View myItemSlot;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.titleTV);
            myItemSlot = itemView.findViewById(R.id.itemSlot);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null){
                mClickListener.onItemClick(view, getAdapterPosition());
                selectedPosition = getAdapterPosition();
                notifyDataSetChanged();
            }
        }
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}