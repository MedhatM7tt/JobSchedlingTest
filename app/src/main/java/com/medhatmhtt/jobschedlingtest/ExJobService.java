package com.medhatmhtt.jobschedlingtest;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.nfc.Tag;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

/**
 * Created by SM on 8/17/2018.
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class ExJobService extends JobService {
    private static final String TAG ="ExampleJobServiceClass ";
    private boolean jobCancelled=false;
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.d(TAG,"Job Started !");
        doBackGroundWork(jobParameters);

        return true;
    }

    private void doBackGroundWork(final JobParameters jobParameters){
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<10;i++){
                    if(jobCancelled)
                        return;
                    Log.d(TAG,"run : "+i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Log.d(TAG,"Job Finished");
                jobFinished(jobParameters,false);
            }
        }).start();
    }
    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.d(TAG,"Job Cancelled before completion");
        jobCancelled=true;
        return false;
    }
}
