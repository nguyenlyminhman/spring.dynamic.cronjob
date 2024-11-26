package com.cronjob.scheduleThreadPool;

public interface ITaskScheduler {
    void startJob(String expression);
    void stopJob();
    boolean isJobRunning();
}
