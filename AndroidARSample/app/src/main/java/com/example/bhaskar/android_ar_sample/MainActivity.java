package com.example.bhaskar.android_ar_sample;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.bhaskar.android_ar_sample.AR.ARActivity;
import com.example.bhaskar.android_ar_sample.preview_model.view.ModelActivity;

import org.andresoviedo.util.android.ContentUtils;

import java.io.IOException;
import java.io.Serializable;

public class MainActivity extends AppCompatActivity {

    private final int PERMISSION_WRITE_EXTERNAL_STORAGE = 101;

    private boolean permissionsGranted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_WRITE_EXTERNAL_STORAGE);
        }else{
            permissionsGranted = true;
        }

        ImageButton button = findViewById(R.id.button_topleft);
        button.setImageResource(R.drawable.sample_1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startARActivity(R.raw.andy, "/models/andy.obj");
            }
        });

        button = findViewById(R.id.button_topright);
        button.setImageResource(R.drawable.sample_2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startARActivity(R.raw.model, "/models/model.obj");

//                Toast.makeText(MainActivity.this, "Not implemented", Toast.LENGTH_LONG).show();
            }
        });

//        GridView gridview = findViewById(R.id.gridview);
//        gridview.setAdapter(new ImageAdapter(this));
//
//        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, View v,
//                                    int position, long id) {
//            }
//        });
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();

        Logger.getInstance().close();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if(requestCode == PERMISSION_WRITE_EXTERNAL_STORAGE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                permissionsGranted = true;
            }else{
                permissionsGranted = false;
            }
        }
    }

    void startARActivity(int resourceId, String objectFilePath){
        if(permissionsGranted) {
            Intent intent = new Intent(this, ARActivity.class);
            intent.putExtra("RESOURCE_ID", resourceId);
            intent.putExtra("OBJECT_FILE_PATH", objectFilePath);
            this.startActivity(intent);
        }else{
            Toast.makeText(MainActivity.this, "Go to application setting and give the permissions required.", Toast.LENGTH_LONG).show();
        }
    }
}
