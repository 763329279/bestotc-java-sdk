package com.bestotc.domain.request;

import com.bestotc.domain.VcbPayload;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;


/**
 * 买币下单V2
 */
@Getter
@Setter
public class ApiOtcCreateBuyOrderRequestV2 {
    /**
     * 第三方订单号
     */
    @NotNull(payload = VcbPayload.MissingParameter.class, message = "outOrderNo")
    private String outOrderNo;

    /**
     * 币种: usdt
     */
    @NotNull(payload = VcbPayload.MissingParameter.class, message = "variety")
    private String variety;

    /**
     * 法币类型: CNY
     */
    @NotNull(payload = VcbPayload.MissingParameter.class, message = "currency")
    private String currency;

    /**
     * 购买金额
     */
    @NotNull(payload = VcbPayload.MissingParameter.class, message = "totalAmount")
    @DecimalMin(value = "0", payload = VcbPayload.InvalidParameter.class, inclusive = false,message = "must be grater than zero")
    @DecimalMax(value = "50000", payload = VcbPayload.InvalidParameter.class,message = "must be less than 50000")
    private String totalAmount;

    /**
     * 付款方式,分隔 1 支付宝 2微信 3 银行卡  默认:1,2,3
     */
    private String paymentType;

    /**
     * 付款用户姓名
     */
    @NotBlank(payload = VcbPayload.MissingParameter.class, message = "currency")
    private String name;

    /**
     * 付款用户身份证号
     */
    @NotBlank(payload = VcbPayload.MissingParameter.class, message = "currency")
    private String idNumber;

}
