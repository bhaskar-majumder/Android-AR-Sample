package com.example.bhaskar.android_ar_sample;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class Logger {

    private static Logger instance = null;
    BufferedWriter bufferedWriter = null;

    private Logger(){
        createFile();
    }

    public static Logger getInstance(){
        if(instance == null){
            instance = new Logger();
        }

        return instance;
    }

    private void createFile(){
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File file = new File(dir, "ARSample.txt");

        //Write to file
        try{
            bufferedWriter = new BufferedWriter(new FileWriter(file, true));
        } catch (Exception e) {
            Log.d("Bhaskar", e.getMessage());
        }
    }

    public void log(String log){
        try {
            if(bufferedWriter == null) {
                createFile();
            }
            bufferedWriter.write(Long.toString(Calendar.getInstance().getTimeInMillis()) + " - " + log);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (Exception e){
            Log.d("Bhaskar", e.getMessage());
        }
    }

    public void close(){
        try {
            bufferedWriter.close();
            bufferedWriter = null;
        } catch (Exception e) {
            Log.d("Bhaskar", e.getMessage());
        }
    }
}
