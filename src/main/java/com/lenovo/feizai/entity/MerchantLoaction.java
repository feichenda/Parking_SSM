package com.lenovo.feizai.entity;

import lombok.Data;

/**
 * @author feizai
 * @date 2021/4/2 0002 下午 8:47:53
 * @annotation
 */
@Data
public class MerchantLoaction {
    int id;
    String username;
    String merchantname;
    String merchantaddress;
    String merchantimage;
    String businesslicense;
    String phone;
    String linkman;
    String QQ;
    double longitude;
    double latitude;
    String city;
    String operatingstate;
    String auditstate;
    String remark;
}
