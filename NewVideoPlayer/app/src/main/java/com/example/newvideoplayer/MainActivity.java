package com.example.newvideoplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.RecursiveAction;

public class MainActivity extends AppCompatActivity implements SelectListener{
    RecyclerView recyclerView;

/*
    File path = new File(Objects.requireNonNull(System.getenv("EXTERNAl_STORAGE")));
*/
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        askPermission();
    }

    private void askPermission() {
        Dexter.withContext(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        displayFiles();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        Toast.makeText(MainActivity.this,"Storage permission required ;)",Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    private void displayFiles() {

        final List<File> fileList = findVideos(Environment.getExternalStorageDirectory());
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        /*fileList = new ArrayList<>();
        fileList.addAll(findVideos(path));*/
        customAdapter = new CustomAdapter(this,fileList,this);
        customAdapter.setHasStableIds(true);
        recyclerView.setAdapter(customAdapter);
    }

    private ArrayList<File> findVideos(File file){
        ArrayList<File> myVideos = new ArrayList<>();
        File[] allFiles = file.listFiles();
        if (allFiles == null){//!!!!
            return  myVideos;
        }
        for (File singleFile : allFiles){
            if (singleFile.isDirectory() && !singleFile.isHidden()){
                myVideos.addAll(findVideos(singleFile));
            }
            else if(singleFile.getName().toLowerCase().endsWith(".mp4")){
                myVideos.add(singleFile);
            }
        }
        return myVideos;
    }

    @Override
    public void onFileClicked(File file) {
        startActivity(new Intent(MainActivity.this,PlayerActivity.class)
        .putExtra("videoPath",file.getAbsolutePath())
        .putExtra("videoName",file.getName()));
    }
}