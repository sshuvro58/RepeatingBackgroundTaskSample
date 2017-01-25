package me.shuvro.repeatingbackgroundtasksample;

import android.annotation.TargetApi;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Build;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

/**
 * Created by root on 1/25/17.
 */

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class TestJobService extends JobService {
    private static final String TAG = "SyncService";

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d("JobScheduler","working");

        if(!Utils.isMyServiceRunning(TestService.class,getApplicationContext())){
            startService(new Intent(getApplicationContext(),TestService.class));
            Log.d("JobScheduler","service not running starting Service");
            //jobFinished(params,false);
        }else {
            Log.d("JobScheduler","service running running");
        }

        jobFinished(params,false);


        return true;
    }



    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }

}