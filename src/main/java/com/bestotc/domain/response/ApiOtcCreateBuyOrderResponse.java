package com.bestotc.domain.response;

import com.alibaba.fastjson.JSON;
import com.bestotc.domain.AdvertPaymentVo;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashMap;

@Getter
@Setter
public class ApiOtcCreateBuyOrderResponse extends ApiBaseResponse {
    /**
     * 币数量
     */
    private String amount;
    /**
     * 法币金额
     */
    private String totalAmount;
    /**
     * 付款方式
     */
    private AdvertPaymentVo payOption;
    /**
     * 商家接单时间
     */
    private Date applyTime;

}
