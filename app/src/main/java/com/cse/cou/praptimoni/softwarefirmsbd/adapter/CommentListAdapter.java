package com.cse.cou.praptimoni.softwarefirmsbd.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cse.cou.praptimoni.softwarefirmsbd.CommentDetails;
import com.cse.cou.praptimoni.softwarefirmsbd.R;
import com.cse.cou.praptimoni.softwarefirmsbd.modal.Comments;

import java.util.List;

public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.MyViewHolder> {
    private List<Comments> mDataset;

    Context context;
    public CommentListAdapter(List<Comments> myDataset, Context context) {
        this.context=context;
        mDataset = myDataset;
    }
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textView;
        public MyViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.single_commnet_item_text);
        }
    }


    @Override
    public CommentListAdapter.MyViewHolder onCreateViewHolder(final ViewGroup parent,
                                                           int viewType) {
        View v= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comments_layout, parent, false);


        MyViewHolder vh = new MyViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentListAdapter.MyViewHolder holder, final int position) {
        holder.textView.setText(mDataset.get(position).getComment_details());
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, CommentDetails.class);
                intent.putExtra("id",mDataset.get(position).getId());
                intent.putExtra("comment",mDataset.get(position).getComment_details());
                intent.putExtra("user",mDataset.get(position).getUser_name());
                context.startActivity(intent);
            }
        });

    }



    // Return the size of your dataset (invoked by the layout manager)

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}