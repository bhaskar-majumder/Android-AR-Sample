package com.example.bhaskar.android_ar_sample;

public class ThreeDModelListItem implements IListItem{
    private int modelImageResource;
    private String modelName;
    private String modelResourcePath;

    public ThreeDModelListItem(int imageResourceId, String modelName, String modelResourcePath){
        this.modelImageResource = imageResourceId;
        this.modelName = modelName;
        this.modelResourcePath = modelResourcePath;
    }

    @Override
    public int getModelImageResourceId() {
        return modelImageResource;
    }

    @Override
    public String getModelName() {
        return modelName;
    }

    public String getmodelResourcePath(){
        return modelResourcePath;
    }
}
