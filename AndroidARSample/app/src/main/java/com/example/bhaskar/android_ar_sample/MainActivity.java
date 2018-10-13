package com.example.bhaskar.android_ar_sample;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import com.example.bhaskar.android_ar_sample.AR.ARActivity;
import com.example.bhaskar.android_ar_sample.preview_model.view.ModelActivity;

import org.andresoviedo.util.android.ContentUtils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final int PERMISSION_WRITE_EXTERNAL_STORAGE = 101;

    private final int AR_ACTIVITY_CODE = 1;
    private final int THREE_D_MODEL_ACTIVITY_CODE = 2;
    private final int IMAGE_VIEWER_ACTIVITY_CODE = 3;

    private boolean permissionsGranted = false;
    ArrayList<IListItem> listOfARObjects = new ArrayList<>();
    ArrayList<IListItem> listOf3DObjects = new ArrayList<>();
    ArrayList<IListItem> listOfModelImages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_WRITE_EXTERNAL_STORAGE);
        }else{
            permissionsGranted = true;
        }

        TabHost host = (TabHost)findViewById(R.id.tabHost);
        host.setup();
        configureARTab(host);
        configure3DPreviewTab(host);
        configureImageTab(host);
    }

    private void configureARTab(TabHost host){
        listOfARObjects.add(new ARModelListItem(R.drawable.sample_1, "Andy", R.raw.andy));
        listOfARObjects.add(new ARModelListItem(R.drawable.sample_2, "Chair", R.raw.chair));
        listOfARObjects.add(new ARModelListItem(R.drawable.sample_3, "Spider", R.raw.spider));

        TabHost.TabSpec spec = host.newTabSpec("Tab One");
        spec.setContent(R.id.tab1);
        spec.setIndicator("AR Models");
        host.addTab(spec);
        ListView listView = findViewById(R.id.ARList);
        ImageAdapter adapter = new ImageAdapter(this, listOfARObjects, ImageAdapter.ListItemType.ImageAndText);
        listView.setAdapter(adapter);
        AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView parent, View view, int position, long id){
                ARModelListItem item = (ARModelListItem) listOfARObjects.get(position);
                startARActivity(item.getModelResourceId());
            }
        };
        listView.setOnItemClickListener(listener);
    }

    private void configure3DPreviewTab(TabHost host){
        listOf3DObjects.add(new ThreeDModelListItem(R.drawable.sample_1, "Andy", "/models/andy/andy.obj"));
        listOf3DObjects.add(new ThreeDModelListItem(R.drawable.sample_2, "Chair", "/models/chair/model.obj"));
        listOf3DObjects.add(new ThreeDModelListItem(R.drawable.sample_3, "Spider", "/models/spider/model.obj"));

        TabHost.TabSpec spec = host.newTabSpec("Tab Two");
        spec.setContent(R.id.tab2);
        spec.setIndicator("3D Models Preview");
        host.addTab(spec);
        ListView listView = findViewById(R.id.ThreeDList);
        ImageAdapter adapter = new ImageAdapter(this, listOf3DObjects, ImageAdapter.ListItemType.ImageAndText);
        listView.setAdapter(adapter);
        AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView parent, View view, int position, long id){
                ThreeDModelListItem item = (ThreeDModelListItem) listOf3DObjects.get(position);
                Uri uri;
                ContentUtils.provideAssets(MainActivity.this);
                uri = Uri.parse("assets://" + getPackageName() + item.getmodelResourcePath());
                launchModelRendererActivity(uri);
            }
        };
        listView.setOnItemClickListener(listener);
    }

    private void configureImageTab(TabHost host){
        listOfModelImages.add(new ImageModelListItem(R.drawable.sample_1, "Andy"));
        listOfModelImages.add(new ImageModelListItem(R.drawable.sample_2, "Chair"));
        listOfModelImages.add(new ImageModelListItem(R.drawable.sample_3, "Spider"));
        listOfModelImages.add(new ImageModelListItem(R.drawable.sample_1, "Andy"));
        listOfModelImages.add(new ImageModelListItem(R.drawable.sample_2, "Chair"));
        listOfModelImages.add(new ImageModelListItem(R.drawable.sample_3, "Spider"));

        TabHost.TabSpec spec = host.newTabSpec("Tab Three");
        spec.setContent(R.id.tab3);
        spec.setIndicator("Model Images");
        host.addTab(spec);
        ListView listView = findViewById(R.id.ImageList);
        ImageAdapter adapter = new ImageAdapter(this, listOfModelImages, ImageAdapter.ListItemType.ImageAndText);
        listView.setAdapter(adapter);
        AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView parent, View view, int position, long id){
                ImageModelListItem item = (ImageModelListItem) listOfModelImages.get(position);
                launchImageViewerActivity(item.getModelImageResourceId());
            }
        };
        listView.setOnItemClickListener(listener);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(IMAGE_VIEWER_ACTIVITY_CODE == requestCode){
            TabHost host = findViewById(R.id.tabHost);
            host.setCurrentTab(2);
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    private void startARActivity(int resourceId){
        if(permissionsGranted) {
            Intent intent = new Intent(this, ARActivity.class);
            intent.putExtra("RESOURCE_ID", resourceId);
            this.startActivity(intent);
        }else{
            Toast.makeText(MainActivity.this, "Go to application setting and give the permissions required.", Toast.LENGTH_LONG).show();
        }
    }

    private void launchImageViewerActivity(int imageResourceId){
        Intent intent = new Intent(getApplicationContext(), ImageActivity.class);
        intent.putExtra("imageResourceId", imageResourceId);
        this.startActivityForResult(intent, IMAGE_VIEWER_ACTIVITY_CODE);
    }

    private void launchModelRendererActivity(Uri uri) {
        Intent intent = new Intent(getApplicationContext(), ModelActivity.class);
        intent.putExtra("uri", uri.toString());
        intent.putExtra("immersiveMode", "true");
        startActivity(intent);
    }
}
