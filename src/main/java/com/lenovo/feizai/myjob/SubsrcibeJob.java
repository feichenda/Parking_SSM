package com.lenovo.feizai.myjob;

import com.lenovo.feizai.entity.Order;
import com.lenovo.feizai.entity.ParkingNumber;
import com.lenovo.feizai.entity.ParkingSpace;
import com.lenovo.feizai.service.ParkingNumberServerDao;
import com.lenovo.feizai.service.ParkingSpaceServiceDao;
import com.lenovo.feizai.service.SubscribeServiceDao;
import com.lenovo.feizai.util.TimeUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * @author feizai
 * @date 2021/4/25 0025 下午 3:05:06
 * @annotation
 */
public class SubsrcibeJob implements Job {

    @Autowired
    SubscribeServiceDao subscribeServiceDao;

    @Autowired
    ParkingSpaceServiceDao parkingSpaceServiceDao;

    @Autowired
    ParkingNumberServerDao parkingNumberServerDao;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

        Order order = (Order) context.getJobDetail().getJobDataMap().get("order");

        System.out.println(TimeUtil.getNowTimestamp() +"-开始执行该任务-"+order.getOrderNumber());

        subscribeServiceDao.updateOrderState("已超时", order.getOrderNumber());

        ParkingSpace space = new ParkingSpace();
        space.setMerchantname(order.getMerchantName());
        space.setSerialnumber(order.getSpace());
        space.setParkingstate("未使用");
        space.setRemark(null);
        parkingSpaceServiceDao.updateParkingSpace(space);

        ParkingNumber number = new ParkingNumber();
        number.setMerchantname(order.getMerchantName());
        number.setAllnumber(0);
        number.setSubscribenumber(-1);
        number.setUnusednumber(0);
        number.setUsednumber(0);
        parkingNumberServerDao.updateNumber(number);

        System.out.println(TimeUtil.getNowTimestamp() +"-执行完该任务-"+order.getOrderNumber());
    }
}
