package com.example.squad.myapplication;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<String> values;
    private List<String> commentCount;
    private List<String> commentCoun;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView Name;
        public TextView commentNo;
        public View layout;
        public TextView comments;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            Name = v.findViewById(R.id.Name);
            commentNo = v.findViewById(R.id.CommentNO);
            comments = v.findViewById(R.id.comments);
        }
    }

    public void add(int position, String item) {
        values.set(position, item);
        notifyItemInserted(position);
    }


    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }

    public MyAdapter() {

    }

    MyAdapter(List<String> myDataset, List<String> count, List<String> coun) {
        values = myDataset;
        commentCount = count;
        commentCoun = coun;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View v = inflater.inflate(R.layout.row_layout, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.Name.setText(values.get(position));
        holder.commentNo.setText("Comment :" + commentCount.get(position));
        holder.comments.setText(commentCoun.get(position));

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return values.size();
    }
}
