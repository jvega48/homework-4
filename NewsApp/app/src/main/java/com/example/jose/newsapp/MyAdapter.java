package com.example.jose.newsapp;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.example.jose.newsapp.utilities.Contract;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ItemHolder> {

    private Cursor cursor;
    private ItemClickListener listener;
    private Context context;
    public static final String TAG = "my adapter";

    public MyAdapter(Cursor cursor, ItemClickListener listener) {
        this.cursor = cursor;
        this.listener = listener;
    }

    public interface ItemClickListener{
        void onItemClick(Cursor cursor, int listener);
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(R.layout.item, parent, shouldAttachToParentImmediately);
        ItemHolder holder = new  ItemHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView title;
        TextView abstr;
        TextView publishDate;
        ImageView img;
        String due;

        ItemHolder(View view){
            super(view);
            title = (TextView)view.findViewById(R.id.title);
            abstr = (TextView)view.findViewById(R.id.abstr);
            publishDate = (TextView)view.findViewById(R.id.date);
            img = (ImageView)view.findViewById(R.id.img);
            view.setOnClickListener(this);
        }

        public void bind(int pos){
            cursor.moveToPosition(pos);
            title.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_TITLE)));
            abstr.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_ABSTRACT)));
            due = cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_PUBLISHED_DATE));
            publishDate.setText(getFormattedDate(due));
            //publishDate.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_PUBLISHED_DATE)));
            String url = cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_THUMBURL));
            Log.d(TAG, url);
            if(url != null){
                Picasso.with(context).load(url).into(img);
            }
        }

        private String getFormattedDate(String dateString) {
            try {
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                Date date = sdf1.parse(dateString);
                SimpleDateFormat sdf2 = new SimpleDateFormat("EEE, MMM d, yyyy");
                return sdf2.format(date);
            } catch (ParseException e) {
                Log.e(TAG, "Error parsing date string " + dateString, e);
                return dateString;
            }
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            listener.onItemClick(cursor, position);
        }
    }
}
