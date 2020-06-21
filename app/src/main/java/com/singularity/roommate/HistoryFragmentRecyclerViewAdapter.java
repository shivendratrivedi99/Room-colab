package com.singularity.roommate;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class HistoryFragmentRecyclerViewAdapter extends RecyclerView.Adapter<HistoryFragmentRecyclerViewAdapter.ViewHolder>{
    private ArrayList<Transaction> transactionList;

    HistoryFragmentRecyclerViewAdapter(ArrayList<Transaction> transactionList) {
        this.transactionList = transactionList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.list_layout, parent, false);
        final ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Transaction transaction = transactionList.get(position);
        DecimalFormat df = new DecimalFormat("#####.##");
        holder.displayTotal.setText("₹"+df.format(transaction.getTotalAmount()));
        holder.displayEach.setText(df.format(transaction.getEachAmount()));

        ArrayList<String> members= transaction.getMembers();
        try {
            holder.member1.setText(members.get(0));
            holder.member2.setText(members.get(1));
            holder.member3.setText(members.get(2));
            holder.member4.setText(members.get(3));
        }
        catch (Exception e){
            Log.e("fd","d");
        }
//        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(view.getContext(),"click on item: ",Toast.LENGTH_LONG).show();
//            }
//        });
    }


    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView displayTotal, member1, member2, member3, member4, displayEach;
        ConstraintLayout constraintLayout;
        ViewHolder(View itemView) {
            super(itemView);
            this.displayTotal = itemView.findViewById(R.id.display_total);
            this.member1 = itemView.findViewById(R.id.member1);
            this.member2 = itemView.findViewById(R.id.member2);
            this.member3 = itemView.findViewById(R.id.member3);
            this.member4 = itemView.findViewById(R.id.member4);
            this.displayEach = itemView.findViewById(R.id.display_each);
            constraintLayout = itemView.findViewById(R.id.constraint_layout);
        }
    }
}