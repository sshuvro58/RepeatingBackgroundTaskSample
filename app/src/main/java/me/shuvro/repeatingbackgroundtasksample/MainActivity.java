package me.shuvro.repeatingbackgroundtasksample;

import android.annotation.TargetApi;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;

import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TestJobService testService;
    private static int kJobId = 0;
    Button stopJob;
    Button startJob;
    TextView textView;
    MyReceiver myReceiver;
    IntentFilter intentFilter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startJob = (Button) findViewById(R.id.startJob);
        stopJob = (Button) findViewById(R.id.stopJob);
        startJob.setOnClickListener(this);
        stopJob.setOnClickListener(this);
        textView = (TextView) findViewById(R.id.textView);
        myReceiver = new MyReceiver();
        intentFilter = new IntentFilter("just_installed");
        Intent intnet2 = new Intent("just_installed");
        sendBroadcast(intnet2);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,new IntentFilter("custom-event-name"));
        registerReceiver(myReceiver, intentFilter);
    }



    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
        unregisterReceiver(myReceiver);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.startJob){
            ComponentName mServiceComponent = new ComponentName( getPackageName(), TestJobService.class.getName());
            JobInfo.Builder builder = new JobInfo.Builder(1, mServiceComponent);
//            builder.setOverrideDeadline(10 * 1000); // maximum delay

            builder.setPeriodic(60*1000);
            builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY); // require unmetered network
            builder.setRequiresDeviceIdle(false); // device should be idle
            builder.setRequiresCharging(false); // we don't care if the device is charging or not

            JobScheduler jobScheduler = (JobScheduler) getApplication().getSystemService(Context.JOB_SCHEDULER_SERVICE);

            jobScheduler.schedule(builder.build());

        } else if(view.getId() ==R.id.stopJob){
            JobScheduler tm = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
            tm.cancelAll();
        }
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            textView.setText(System.currentTimeMillis() +"");
        }
    };
}
