package com.example.vd.floatydemorecyclerview;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bezyapps.floatieslibrary.Floaty;

/**
 * Created by Eric Bhatti on 6/26/2016.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private String[] mDataset;
    private MainActivity mainActivity;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;

        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.textViewContent);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(MainActivity activity, String[] myDataset) {
        mDataset = myDataset;
        mainActivity = activity;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_view, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTextView.setText(mDataset[position]);
        holder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Floaty floaty = Floaty.getInstance();
//                floaty.stopService();
                View body = floaty.getBody();
                TextView textView = (TextView) body.findViewById(R.id.textViewContent);
                textView.setTextColor(Color.RED);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
                textView.setText(mDataset[position]);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    mainActivity.startFloatyForAboveAndroidL();
                } else {
                    floaty.startService();

                }
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}



