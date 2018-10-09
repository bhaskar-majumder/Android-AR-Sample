package com.example.bhaskar.android_ar_sample;

import android.media.Image;

public class ARModelListItem implements IListItem {
    private int modelImageResource;
    private String modelName;
    private int modelResourceId;

    public ARModelListItem(int imageResourceId, String modelName, int modelResourceId){
        this.modelImageResource = imageResourceId;
        this.modelName = modelName;
        this.modelResourceId = modelResourceId;
    }

    @Override
    public int getModelImageResourceId() {
        return modelImageResource;
    }

    @Override
    public String getModelName() {
        return modelName;
    }

    public int getModelResourceId(){
        return modelResourceId;
    }
}
