package com.bestotc.domain.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 卖币下单
 */
@Getter
@Setter
public class ApiOtcCreateSellOrderResponse extends ApiBaseResponse {

    /**
     * 售卖数量
     */
    private String amount;

    /**
     * 售卖金额
     */
    private String totalAmount;

    /**
     * 单价
     */
    private String price;

    /**
     * 供应商姓名
     */
    private String providerName;

    /**
     * otc订单号
     */
    private String otcOrderSn;

    /**
     * 下单时间
     */
    private Date applyTime;
}
