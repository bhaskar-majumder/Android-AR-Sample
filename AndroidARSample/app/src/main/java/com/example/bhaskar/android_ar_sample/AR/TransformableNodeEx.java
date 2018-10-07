package com.example.bhaskar.android_ar_sample.AR;

import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.ux.TransformableNode;
import com.google.ar.sceneform.ux.TransformationSystem;

public class TransformableNodeEx extends TransformableNode {

    private float horizontalRotationAngle = 0;
    private String objectFilePath;

    public TransformableNodeEx(TransformationSystem transformationSystem) {
        super(transformationSystem);
    }

    public void rotateHorizontally(float angle){
        horizontalRotationAngle += angle;
        setLocalRotation(Quaternion.axisAngle(new Vector3(0, 1f, 0), horizontalRotationAngle));
    }

    public float getHorizontalRotationAngle(){
        return horizontalRotationAngle;
    }

    public void setObjectFilePath(String path){
        objectFilePath = path;
    }

    public String getObjectFilePath(){
        return objectFilePath;
    }
}
