package com.bestotc.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 订单详情
 */
@Getter
@Setter
public class OtcApiOrderVO {

    /**
     * 第三方订单编号
     */
    private String outOrderNo;
    /**
     * OTC订单编号
     */
    private String otcOrderNo;
    /**
     * 币种:usdt
     */
    private String variety;
    /**
     * 法币:CNY
     */
    private String currency;
    /**
     * 卖币订单,卖币人姓名
     */
    private String buyRealName;
    /**
     * 币数量
     */
    private BigDecimal amount;
    /**
     * 单价
     */
    private BigDecimal unitPrice;
    /**
     * 法币总额
     */
    private BigDecimal totalAmount;
    /**
     * 订单状态1已创建 2待付款 3已付款 4已完成 5取消(已关闭)
     */
    private Integer status;

    /**
     * 订单创建时间
     */
    private Date createTime;
    /**
     * 买家支付时间
     */
    private Date payTime;
    /**
     * 卖家确认收款时间
     */
    private Date confirmTime;
    /**
     * 订单取消时
     */
    private Date cancelTime;
    /**
     * 付款截止时间倒计时(毫秒)
     */
    private Long endPayTime;
    /**
     * 卖币订单,确认付款截止时间
     */
    private Date endConfirmTime;
    /**
     * 订单申诉时间
     */
    private Date appealTime;
    /**
     * 商家接单时间
     */
    private Date applyTime;
    /**
     * 申诉状态1无申诉，2申诉中，3处理完成
     */
    private Integer appealStatus;
    /**
     * 订单类型：2卖币，3买币
     */
    private Integer type;

    /**
     * 付款方式(买币下单V1版本)
     */
    private Integer paymentType;

    /**
     * 付款信息(买币下单V1版本)
     */
    private AdvertPaymentVo payOption;

    /**
     * 付款信息列表(买币下单V2版本)
     */
    private List<AdvertPaymentVo> payOptions;

}
