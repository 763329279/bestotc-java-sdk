package com.bestotc.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 订单状态 或申诉状态 变化异步通知的消息
 * @author qxx on 2019/1/2.
 */
@Getter
@Setter
public class OrderNotifyMessage {
    /**
     * 第三方订单号
     */
    private String outOrderNo;

    /**
     *时间戳|2018-12-18T11:42:10.257Z 0时区时间
     */
    private String timestamp;
    /**
     * 通知类型 OrderStatusChangeMessage(订单状态变)   OrderAppealStatusChangeMessage(申诉状态变化)
     */
    private String type;
    /**
     * 订单状态 1已创建,2待付款,3已付款,4已完成,5取消(已关闭)
     */
    private Integer status;

    /**
     * 币数量
     */
    private BigDecimal amount;

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 法币总额
     */
    private BigDecimal total;

    /**
     * 申诉状态 1 无申诉 2申诉中 3申诉完成
     */
    private Integer appealStatus;

    /**
     * 取消类型 1 系统取消 2用户取消 3商家取消
     */
    private Integer cancelType;

    /**
     * 申诉id
     */
    private Long advertOrderAppealId;

    /**
     * 是否订单重开
     */
    private Boolean isReopen;

}
