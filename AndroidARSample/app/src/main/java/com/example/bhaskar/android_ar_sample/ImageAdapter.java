package com.example.bhaskar.android_ar_sample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<IListItem> listModelListItem = new ArrayList<>();
    private LayoutInflater inflater = null;

    public ImageAdapter(Context context, ArrayList<IListItem> list) {
        this.context = context;
        this.listModelListItem = list;
        this.inflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return listModelListItem.size();
    }

    public Object getItem(int position) {
        return listModelListItem.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if(convertView == null)
            vi = inflater.inflate(R.layout.list_view_entry, null);

        TextView title = (TextView)vi.findViewById(R.id.modelText);
        ImageView imageView = (ImageView)vi.findViewById(R.id.modelImage);

        IListItem item = listModelListItem.get(position);

        // Setting all values in listview
        title.setText(item.getModelName());
        imageView.setImageResource(item.getModelImageResourceId());
        return vi;
    }
}
