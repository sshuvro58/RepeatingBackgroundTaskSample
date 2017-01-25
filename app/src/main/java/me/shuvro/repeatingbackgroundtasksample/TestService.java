package me.shuvro.repeatingbackgroundtasksample;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by root on 1/25/17.
 */

public class TestService extends IntentService {


    public TestService() {
        super("TestService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {


        File output = new File(Environment.getExternalStorageDirectory(),
                "/TestDir/"+ System.currentTimeMillis()+".jpg" );
//
//
//        InputStream stream = null;
//        FileOutputStream fos = null;
//        try {
//            Log.d("JobScheduler","file download work");
//            URL url = new URL("https://blog.sqlauthority.com/i/b/ilovesamples.jpg");
//            stream = url.openConnection().getInputStream();
//            InputStreamReader reader = new InputStreamReader(stream);
//            fos = new FileOutputStream(output.getPath());
//            int next = -1;
//            while ((next = reader.read()) != -1) {
//                fos.write(next);
//            }
//            // Successful finished
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (stream != null) {
//                try {
//                    stream.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            if (fos != null) {
//                try {
//                    fos.close();
//                } catch (IOException e) {
//                    Log.d("JobScheduler","file download error"+e.getMessage());
//                    e.printStackTrace();
//                }
//            }
//        }

        try {
            Log.d("JobScheduler","file download work");
            URL url = new URL("https://blog.sqlauthority.com/i/b/ilovesamples.jpg");
            URLConnection connection = url.openConnection();
            connection.connect();
            // this will be useful so that you can show a typical 0-100% progress bar
            int fileLength = connection.getContentLength();

            // download the file
            InputStream input = new BufferedInputStream(connection.getInputStream());
            OutputStream outputs = new FileOutputStream(output);

            byte data[] = new byte[1024];
            long total = 0;
            int count;
            while ((count = input.read(data)) != -1) {
                total += count;
                // publishing the progress....
                Bundle resultData = new Bundle();
                resultData.putInt("progress" ,(int) (total * 100 / fileLength));

                outputs.write(data, 0, count);
            }

            outputs.flush();
            outputs.close();
            input.close();
        } catch (IOException e) {
            Log.d("JobScheduler","file download error"+e.getMessage());
            e.printStackTrace();
        }





        Intent newIntent = new Intent("custom-event-name");
        LocalBroadcastManager.getInstance(this).sendBroadcast(newIntent);
    }
}
