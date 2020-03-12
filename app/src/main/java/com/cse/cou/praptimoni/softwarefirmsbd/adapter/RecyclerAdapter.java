package com.cse.cou.praptimoni.softwarefirmsbd.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cse.cou.praptimoni.softwarefirmsbd.Details;
import com.cse.cou.praptimoni.softwarefirmsbd.R;
import com.cse.cou.praptimoni.softwarefirmsbd.modal.FirmsInfo;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    private List<FirmsInfo> mDataset;

    Context context;
    public RecyclerAdapter(List<FirmsInfo> myDataset,Context context) {
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
            textView = v.findViewById(R.id.single_item_text);
        }
    }


    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(final ViewGroup parent,
                                                           int viewType) {
        View v= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);


        MyViewHolder vh = new MyViewHolder(v);

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.textView.setText(mDataset.get(position).getFirm_name());
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent  intent=new Intent(context,Details.class);
                intent.putExtra("id",mDataset.get(position).getId());
                intent.putExtra("name","Firm Name : "+mDataset.get(position).getFirm_name());
                intent.putExtra("size","Firm Size : "+mDataset.get(position).getSize());
                intent.putExtra("salary","Fresher`s Salary : "+mDataset.get(position).getStarting_salary());
                intent.putExtra("website",mDataset.get(position).getWebsite());
                intent.putExtra("location",mDataset.get(position).getLocation());
                intent.putExtra("image",mDataset.get(position).getImage_link());
                intent.putExtra("language","Language Knowledge : "+mDataset.get(position).getExamsLanguage());
                intent.putExtra("hiring_procedure","Hiring Procedure : "+mDataset.get(position).getHiring_procedure());
                intent.putExtra("requirement","Requirement : "+mDataset.get(position).getRequirment());
               // intent.putExtra("comments","Comments : "+mDataset.get(position).getComments());
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