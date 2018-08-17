package com.medhatmhtt.jobschedlingtest;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Switch aSwitch;
    private boolean switchStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        aSwitch = (Switch) findViewById(R.id.statusSwitch);
        switchStatus= aSwitch.isChecked();
        aSwitch.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                if(aSwitch.isChecked()){
                    startJob();
                    Toast.makeText(getApplicationContext(),"Service Started !",Toast.LENGTH_SHORT).show();
                }
                else{
                    cancelJob();
                    Toast.makeText(getApplicationContext(),"Service Cancelled !",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void startJob() {
        ComponentName componentName=new ComponentName(this,ExJobService.class);
        JobInfo jobInfo= new JobInfo.Builder(888,componentName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setRequiresCharging(false).setPersisted(true).setPeriodic(60*1000).build();
        JobScheduler jobScheduler= (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        int result= jobScheduler.schedule(jobInfo);
        if(result==JobScheduler.RESULT_SUCCESS){
            Log.d("MainClass","Success");
        }
        else {
            Log.d("MainClass","Failed");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void cancelJob() {
        JobScheduler jobScheduler=(JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        jobScheduler.cancel(888);
        Log.d("MainClass","Cancelled");
    }
}
