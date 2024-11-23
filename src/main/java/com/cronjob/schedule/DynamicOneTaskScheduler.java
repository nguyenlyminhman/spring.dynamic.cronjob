package com.cronjob.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.concurrent.ScheduledFuture;

// Notice that this example run only 1 cronjob
@Service
public class DynamicOneTaskScheduler {

    private final TaskScheduler taskScheduler;
    private ScheduledFuture<?> scheduledFuture;

    @Autowired
    public DynamicOneTaskScheduler(TaskScheduler taskScheduler) {
        this.taskScheduler = taskScheduler;
    }

    // This method will start a dynamic cron job
    public void scheduleTaskWithCronExpression(String cronExpression, String code) {
        if (scheduledFuture != null && !scheduledFuture.isCancelled()) {
            scheduledFuture.cancel(false); // Cancel the previous task if it's already scheduled
        }

        // Scheduling a task with the given cron expression
        if("TEST_CODE".equalsIgnoreCase(code))
            scheduledFuture = taskScheduler.schedule(this::performFirstTask, new CronTrigger(cronExpression));
        if("TEST_CODE_2".equalsIgnoreCase(code))
            scheduledFuture = taskScheduler.schedule(this::performSecondTask, new CronTrigger(cronExpression));
    }

    // Task to be performed by the cron job
    public void performFirstTask() {
        System.out.println("Task 1 at " + System.currentTimeMillis());
    }

    public void performSecondTask() {
        System.out.println("Task 2 at " + System.currentTimeMillis());
    }

    // A method to stop the current cron job if needed
    public void stopScheduledTask() {
        if (scheduledFuture != null) {
            scheduledFuture.cancel(false);
        }
    }

}
