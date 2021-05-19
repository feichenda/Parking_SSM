package com.lenovo.feizai.myjob;

import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author feizai
 * @date 2021/5/19 0019 下午 3:14:53
 * @annotation
 */
public class ShutDownQuartz implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

    }
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        try {
            Scheduler defaultScheduler = StdSchedulerFactory.getDefaultScheduler();
            defaultScheduler.shutdown(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
