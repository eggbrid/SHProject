package com.shpro.xus.shproject.service;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.shpro.xus.shproject.util.CommentUtil;

/**
 * Created by xus on 2017/5/5.
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class LvService extends JobService {
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.e("wangxu","onStartJob");
        if (!CommentUtil.isServiceWork(this, BackgroundService.class.getName())){
            Intent grayIntent = new Intent(getApplicationContext(), BackgroundService.class);
            startService(grayIntent);
        }
        jobFinished(jobParameters,true);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.e("wangxu","onStopJob");
        return true;
    }
}
