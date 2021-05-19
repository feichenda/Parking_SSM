package com.lenovo.feizai.myjob;

import com.lenovo.feizai.entity.CheckInfo;
import com.lenovo.feizai.entity.Order;
import com.lenovo.feizai.util.TimeUtil;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

import static org.quartz.DateBuilder.futureDate;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @author feizai
 * @date 2021/4/25 0025 下午 5:10:34
 * @annotation
 */
@Component("myQuartzManager")
public class QuartzManager {
    private static SchedulerFactory schedulerFactory = new StdSchedulerFactory();

    /**
     * @Description: 添加一个定时任务
     *
     * @param jobName 任务名
     * @param jobGroupName  任务组名
     * @param triggerName 触发器名
     * @param triggerGroupName 触发器组名
     * @param jobClass  任务
     * @param order   订单
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void addSubsrcibeJob(String jobName, String jobGroupName,
                                String triggerName, String triggerGroupName, Class jobClass, Order order) {
        try {
            Scheduler sched = schedulerFactory.getScheduler();
            // 任务名，任务组，任务执行类
            JobDetail jobDetail= JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName).build();

            //传输到具体Job类中
            jobDetail.getJobDataMap().put("order", order);

            // 触发器
//            TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();

            // 触发器名,触发器组
//            triggerBuilder.withIdentity(triggerName, triggerGroupName);

            // 触发器时间设定几分钟后启动
//            triggerBuilder.startAt(futureDate(order.getDuration(), DateBuilder.IntervalUnit.MINUTE));

            // 创建Trigger对象
//            SimpleTrigger trigger = (SimpleTrigger) triggerBuilder.build();

            //创建一个Trigger
            SimpleTrigger trigger = (SimpleTrigger) newTrigger()
                    .withIdentity(triggerName, triggerGroupName)
                    .startAt(futureDate(TimeUtil.getTimeNowTo(order.getEndDate()), DateBuilder.IntervalUnit.SECOND)) // use DateBuilder to create a date in the future
                    .forJob(jobName, jobGroupName) // identify job with its JobKey
                    .build();

            // 调度容器设置JobDetail和Trigger
            sched.scheduleJob(jobDetail, trigger);

            // 启动
            if (!sched.isShutdown()) {
                sched.start();
            }
            System.out.println(TimeUtil.getNowTimestamp()+"任务添加成功:"+jobName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void addLeaveJob(String jobName, String jobGroupName,
                            String triggerName, String triggerGroupName, Class jobClass, CheckInfo checkInfo) {
        try {
            Scheduler sched = schedulerFactory.getScheduler();
            // 任务名，任务组，任务执行类
            JobDetail jobDetail= JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName).build();

            //传输到具体Job类中
            jobDetail.getJobDataMap().put("check", checkInfo);

            // 触发器
//            TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();

            // 触发器名,触发器组
//            triggerBuilder.withIdentity(triggerName, triggerGroupName);

            // 触发器时间设定几分钟后启动
//            triggerBuilder.startAt(futureDate(order.getDuration(), DateBuilder.IntervalUnit.MINUTE));

            // 创建Trigger对象
//            SimpleTrigger trigger = (SimpleTrigger) triggerBuilder.build();

            //创建一个Trigger
            SimpleTrigger trigger = (SimpleTrigger) newTrigger()
                    .withIdentity(triggerName, triggerGroupName)
                    .startAt(futureDate(5, DateBuilder.IntervalUnit.MINUTE)) // use DateBuilder to create a date in the future
                    .forJob(jobName, jobGroupName) // identify job with its JobKey
                    .build();

            // 调度容器设置JobDetail和Trigger
            sched.scheduleJob(jobDetail, trigger);

            // 启动
            if (!sched.isShutdown()) {
                sched.start();
            }
            System.out.println(TimeUtil.getNowTimestamp()+"任务添加成功:"+jobName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description: 修改一个任务的触发时间
     *
     * @param jobName
     * @param jobGroupName
     * @param triggerName 触发器名
     * @param triggerGroupName 触发器组名
     * @param order
     */
    public void modifySubsrcibeJobTime(String jobName,
                                     String jobGroupName, String triggerName, String triggerGroupName, Order order) {
        try {
            Scheduler sched = schedulerFactory.getScheduler();
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
//            CronTrigger trigger = (CronTrigger) sched.getTrigger(triggerKey);
            SimpleTrigger trigger = (SimpleTrigger) sched.getTrigger(triggerKey);
            if (trigger == null) {
                return;
            }

            Date oldEndTime = trigger.getNextFireTime();
            Date newEndTime = new Date(order.getEndDate().getTime());
            if (oldEndTime.before(newEndTime)) {
                /** 方式一 ：调用 rescheduleJob 开始 */
                // 触发器
//                TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
                // 触发器名,触发器组
//                triggerBuilder.withIdentity(triggerName, triggerGroupName);
//                triggerBuilder.startNow();
                // 触发器时间设定
//                triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
                // 创建Trigger对象
//                trigger = (CronTrigger) triggerBuilder.build();
                // 方式一 ：修改一个任务的触发时间
//                sched.rescheduleJob(triggerKey, trigger);
                /** 方式一 ：调用 rescheduleJob 结束 */

                /** 方式二：先删除，然后在创建一个新的Job  */
                JobDetail jobDetail = sched.getJobDetail(JobKey.jobKey(jobName, jobGroupName));
                Class<? extends Job> jobClass = jobDetail.getJobClass();
                removeJob(jobName, jobGroupName, triggerName, triggerGroupName);
                addSubsrcibeJob(jobName, jobGroupName, triggerName, triggerGroupName, jobClass, order);
                /** 方式二 ：先删除，然后在创建一个新的Job */
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description: 移除一个任务
     *
     * @param jobName
     * @param jobGroupName
     * @param triggerName
     * @param triggerGroupName
     */
    public void removeJob(String jobName, String jobGroupName,
                                 String triggerName, String triggerGroupName) {
        try {
            Scheduler sched = schedulerFactory.getScheduler();

            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);

            sched.pauseTrigger(triggerKey);// 停止触发器
            sched.unscheduleJob(triggerKey);// 移除触发器
            sched.deleteJob(JobKey.jobKey(jobName, jobGroupName));// 删除任务
            System.out.println(TimeUtil.getNowTimestamp()+"任务删除成功:"+jobName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description:启动所有定时任务
     */
    public void startJobs() {
        try {
            Scheduler sched = schedulerFactory.getScheduler();
            sched.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description:关闭所有定时任务
     */
    public void shutdownJobs() {
        try {
            Scheduler sched = schedulerFactory.getScheduler();
            if (!sched.isShutdown()) {
                sched.shutdown();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
