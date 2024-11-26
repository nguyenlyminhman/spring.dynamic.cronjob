package com.cronjob.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.concurrent.ScheduledFuture;

// Notice that this class allow to run only 1 cronjob
@Service
public class SecondTaskSchedule {

    private final TaskScheduler taskScheduler;
    private ScheduledFuture<?> scheduledFuture;

    @Autowired
    public SecondTaskSchedule(TaskScheduler taskScheduler) {
        this.taskScheduler = taskScheduler;
    }

    // This method will start a dynamic cron job
    public void scheduleTaskWithCronExpression(String cronExpression) {
        if (scheduledFuture != null && !scheduledFuture.isCancelled()) {
            // Cancel the previous task if it's already scheduled
            scheduledFuture.cancel(false);
        }

        // Scheduling a task with the given cron expression
        scheduledFuture = taskScheduler.schedule(this::performSecondTask, new CronTrigger(cronExpression));
    }

    // Task to be performed by the cron job
    public void performSecondTask() {
        System.out.println("Second Cron is running at: " + System.currentTimeMillis());
    }

    // A method to stop the current cron job if needed
    public void stopScheduledTask() {
        if (scheduledFuture != null) {
            scheduledFuture.cancel(false);
        }
    }

}
