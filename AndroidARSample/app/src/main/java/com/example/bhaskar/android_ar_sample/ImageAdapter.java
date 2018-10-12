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
    public enum ListItemType{
        None,
        Image,
        ImageAndText
    }

    private Context context;
    private ArrayList<IListItem> iListItems = new ArrayList<>();
    private LayoutInflater inflater = null;
    private ListItemType listItemType = ListItemType.None;

    public ImageAdapter(Context context, ArrayList<IListItem> list, ListItemType listItemType) {
        this.context = context;
        this.iListItems = list;
        this.inflater = LayoutInflater.from(context);
        this.listItemType = listItemType;
    }

    public int getCount() {
        return iListItems.size();
    }

    public Object getItem(int position) {
        return iListItems.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if(this.listItemType == ListItemType.ImageAndText) {
            if (convertView == null) {
                vi = inflater.inflate(R.layout.list_view_entry, null);
            }

            TextView title = vi.findViewById(R.id.modelText);
            ImageView imageView = vi.findViewById(R.id.modelImage);

            IListItem item = iListItems.get(position);
            title.setText(item.getModelName());
            imageView.setImageResource(item.getModelImageResourceId());
        }else if(this.listItemType == ListItemType.Image){
            if (convertView == null) {
                vi = inflater.inflate(R.layout.list_view_entry_only_image, null);
            }

            ImageView imageView = vi.findViewById(R.id.modelOnlyImage);
            IListItem item = iListItems.get(position);
            imageView.setImageResource(item.getModelImageResourceId());
        }

        return vi;
    }
}
