package com.lenovo.feizai.myjob;

import com.lenovo.feizai.entity.CheckInfo;
import com.lenovo.feizai.service.CheckInfoServiceDao;
import com.lenovo.feizai.util.TimeUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * @author feizai
 * @date 2021/4/26 0026 上午 9:16:18
 * @annotation
 */
public class LeaveJob implements Job {

    @Autowired
    CheckInfoServiceDao checkInfoServiceDao;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

        CheckInfo checkInfo = (CheckInfo) context.getJobDetail().getJobDataMap().get("check");

        System.out.println(TimeUtil.getNowTimestamp() +"-开始执行该任务-"+checkInfo.getOrdernumber());

        checkInfo.setState("已超时");

        checkInfoServiceDao.goout(checkInfo);

        CheckInfo info = new CheckInfo();
        info.setMerchant(checkInfo.getMerchant());
        info.setSerialnumber(checkInfo.getSerialnumber());
        info.setCarlicense(checkInfo.getCarlicense());
        info.setIntime(TimeUtil.getNowTimestamp());
        info.setState("未缴费");
        checkInfoServiceDao.addCheckInfo(info);

        System.out.println(TimeUtil.getNowTimestamp() +"-执行完该任务-"+checkInfo.getOrdernumber());
    }
}
