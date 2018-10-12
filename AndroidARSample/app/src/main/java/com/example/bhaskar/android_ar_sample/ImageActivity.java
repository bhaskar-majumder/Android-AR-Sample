package com.example.bhaskar.android_ar_sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;

public class ImageActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_display);

        int imageResourceId = -1;
        Bundle b = getIntent().getExtras();
        if (b != null) {
            imageResourceId = b.getInt("imageResourceId");
        }

        if(imageResourceId != -1) {
            ImageView imageView = findViewById(R.id.displayImage);
            imageView.setImageResource(imageResourceId);
        }
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Intent data = new Intent();
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            Intent data = new Intent();
            setResult(RESULT_OK, data);
            finish();
            return true;
        }else{
            return super.onOptionsItemSelected(item);
        }
    }
}
