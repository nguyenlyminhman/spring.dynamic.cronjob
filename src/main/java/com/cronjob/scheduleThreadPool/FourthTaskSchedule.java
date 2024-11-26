package com.cronjob.scheduleThreadPool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
public class FourthTaskSchedule extends ThreadPoolTaskScheduler implements ITaskScheduler {

    @Autowired
    public FourthTaskSchedule() {}

    // This method will start a dynamic cron job
    public void scheduleTaskWithCronExpression(String cronExpression) {
        startJob(cronExpression);
    }

    @Override
    public void startJob(String expression) {
        super.initialize();
        super.schedule(new Runnable() {
                           @Override
                           public void run() {
                               performFourthTask();
                           }
                       },
                new Trigger() {
                    @Override
                    public Instant nextExecution(TriggerContext triggerContext) {
                        return new CronTrigger(expression).nextExecution(triggerContext);
                    }
                }
        );
    }

    @Override
    public void stopJob() {
        super.stop();
    }

    @Override
    public boolean isJobRunning() {
        return super.getPoolSize() > 0;
    }

    public void performFourthTask() {
        System.out.println("Fourth Cron is running at: " + new Date());
    }

}
