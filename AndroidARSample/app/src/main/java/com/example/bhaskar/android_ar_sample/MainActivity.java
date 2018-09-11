package com.example.bhaskar.android_ar_sample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.bhaskar.android_ar_sample.AR.ARActivity;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton button = findViewById(R.id.button_topleft);
        button.setImageResource(R.drawable.sample_1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startARActivity(R.raw.andy);
            }
        });

        button = findViewById(R.id.button_topright);
        button.setImageResource(R.drawable.sample_2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startARActivity(0);
                Toast.makeText(MainActivity.this, "Not implemented", Toast.LENGTH_LONG).show();
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

    void startARActivity(int resourceId){
        Intent intent = new Intent(this, ARActivity.class);
        intent.putExtra("RESOURCE_ID", resourceId);
        this.startActivity(intent);
    }
}
