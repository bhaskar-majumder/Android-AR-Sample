package com.example.bhaskar.android_ar_sample;

public class ImageModelListItem implements IListItem {
    private int modelImageResource;
    private String modelName;

    public ImageModelListItem(int imageResourceId, String modelName){
        this.modelImageResource = imageResourceId;
        this.modelName = modelName;
    }

    @Override
    public int getModelImageResourceId() {
        return modelImageResource;
    }

    @Override
    public String getModelName() {
        return modelName;
    }
}
