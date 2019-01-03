package com.bestotc.domain.response;

import com.bestotc.domain.AdvertPaymentVo;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * 买币下单V2
 */
@Getter
@Setter
public class ApiOtcCreateBuyOrderResponseV2 extends ApiBaseResponse {
    /**
     * otc订单号
     */
    private String otcOrderSn;
    /**
     * 购买数量
     */
    private String amount;
    /**
     * 单价
     */
    private String price;
    /**
     * 购买金额
     */
    private String totalAmount;
    /**
     * 付款信息列表
     */
    private List<AdvertPaymentVo> payOptions;
    /**
     * 下单时间
     */
    private Date applyTime;
    /**
     * 商家名称
     */
    private String providerName;



}
