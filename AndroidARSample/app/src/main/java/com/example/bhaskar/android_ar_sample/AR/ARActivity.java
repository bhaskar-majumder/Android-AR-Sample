package com.example.bhaskar.android_ar_sample.AR;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.bhaskar.android_ar_sample.Logger;
import com.example.bhaskar.android_ar_sample.R;
import com.example.bhaskar.android_ar_sample.preview_model.view.ModelActivity;
import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.HitTestResult;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.RotationController;
import com.google.ar.sceneform.ux.TransformableNode;

import org.andresoviedo.util.android.ContentUtils;

import static java.lang.Thread.sleep;

public class ARActivity extends AppCompatActivity {
    private static final String TAG = ARActivity.class.getSimpleName();
    private static final double MIN_OPENGL_VERSION = 3.0;
    private static final float ROTATION_ANGLE = 5;
    private ArFragment arFragment;
    private ModelRenderable renderable;
    private TransformableNodeEx transformableNode;
    private float angle = 0;
    private Node.OnTouchListener onTransformableNodeTouchListener = new Node.OnTouchListener() {
        @Override
        public boolean onTouch(HitTestResult hitTestResult, MotionEvent motionEvent) {
            Logger.getInstance().log(motionEvent.toString());
            TransformableNodeEx node = (TransformableNodeEx)hitTestResult.getNode();
            if(node != null) {
                transformableNode = node;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        if (!checkIsSupportedDeviceOrFinish(this)) {
            return;
        }

        setContentView(R.layout.ar_activity_ux);
        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ux_fragment);

        Intent intent = getIntent();
        int resourceId = intent.getExtras().getInt("RESOURCE_ID");

        ModelRenderable.builder()
                .setSource(this, resourceId)
                .build()
                .thenAccept(renderable -> this.renderable = renderable)
                .exceptionally(
                        throwable -> {
                            Toast toast =
                                    Toast.makeText(this, "Unable to load renderable", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });

        arFragment.setOnTapArPlaneListener(
                (HitResult hitResult, Plane plane, MotionEvent motionEvent) -> {
                    if (renderable == null) {
                        return;
                    }

                    Anchor anchor = hitResult.createAnchor();
                    AnchorNode anchorNode = new AnchorNode(anchor);
                    anchorNode.setParent(arFragment.getArSceneView().getScene());

                    TransformableNodeEx node = new TransformableNodeEx(arFragment.getTransformationSystem());
                    //node.setLocalRotation(Quaternion.axisAngle(new Vector3(0, 0, 1f), 180));
                    node.setParent(anchorNode);
                    node.setRenderable(renderable);
                    node.select();
                    node.setOnTouchListener(onTransformableNodeTouchListener);
                    transformableNode = node;

                    configureLeftRightDeleteImageButtons();
                });
    }

    private void configureLeftRightDeleteImageButtons(){
        ImageButton leftButton = findViewById(R.id.button_moveLeft);
        leftButton.setVisibility(View.VISIBLE);
        leftButton.setImageResource(R.drawable.left_arrow);
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(transformableNode != null) {
                    transformableNode.rotateHorizontally(ROTATION_ANGLE * -1);
                    Logger.getInstance().log("Left rotation - Current angle = " + Float.toString(transformableNode.getHorizontalRotationAngle()));
                }
            }
        });

        ImageButton rightButton = findViewById(R.id.button_moveRight);
        rightButton.setVisibility(View.VISIBLE);
        rightButton.setImageResource(R.drawable.right_arrow);
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(transformableNode != null) {
                    transformableNode.rotateHorizontally(ROTATION_ANGLE);
                    Logger.getInstance().log("Right rotation - Current angle = " + Float.toString(transformableNode.getHorizontalRotationAngle()));
                }
            }
        });

        ImageButton deleteButton = findViewById(R.id.button_delete);
        deleteButton.setVisibility(View.VISIBLE);
        deleteButton.setImageResource(R.drawable.delete_node);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(transformableNode != null) {
                    transformableNode.getParent().removeChild(transformableNode);
                    transformableNode = null;
                }
            }
        });

//        ImageButton infoButton = findViewById(R.id.button_info);
//        infoButton.setVisibility(View.VISIBLE);
//        infoButton.setImageResource(R.drawable.info);
//        infoButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(transformableNode != null) {
//                    Uri uri;
//                    ContentUtils.provideAssets(ARActivity.this);
//                    uri = Uri.parse("assets://" + getPackageName() + transformableNode.getObjectFilePath());
//                    launchModelRendererActivity(uri);
//                }
//            }
//        });
    }

    private void launchModelRendererActivity(Uri uri) {
        Intent intent = new Intent(getApplicationContext(), ModelActivity.class);
        intent.putExtra("uri", uri.toString());
        intent.putExtra("immersiveMode", "true");

        startActivity(intent);
    }

    private static boolean checkIsSupportedDeviceOrFinish(final Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            Log.e(TAG, "App requires Android N or later");
            Toast.makeText(activity, "App requires Android N or later", Toast.LENGTH_LONG).show();
            activity.finish();
            return false;
        }
        String openGlVersionString =
                ((ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE))
                        .getDeviceConfigurationInfo()
                        .getGlEsVersion();
        if (Double.parseDouble(openGlVersionString) < MIN_OPENGL_VERSION) {
            Log.e(TAG, "App requires OpenGL ES 3.0 later");
            Toast.makeText(activity, "App requires OpenGL ES 3.0 or later", Toast.LENGTH_LONG)
                    .show();
            activity.finish();
            return false;
        }
        return true;
    }
}
