package com.bestotc.domain.request;

import com.bestotc.domain.VcbPayload;
import com.bestotc.utils.ValidatorHelper;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 买币下单V1
 * @author qxx on 2019/1/2.
 */
@Getter
@Setter
public class ApiOtcCreateBuyOrderRequest {
    /**
     * 第三方订单号
     */
    @NotBlank(payload = VcbPayload.MissingParameter.class, message = "outOrderNo")
    private String outOrderNo;

    /**
     * 币种: usdt
     */
    @NotBlank(payload = VcbPayload.MissingParameter.class, message = "variety")
    private String variety;

    /**
     * 发布类型: CNY
     */
    @NotBlank(payload = VcbPayload.MissingParameter.class, message = "currency")
    private String currency;

    /**
     * 购买金额
     */
    @NotNull(payload = VcbPayload.MissingParameter.class, message = "totalAmount")
    @DecimalMin(value = "0", payload = VcbPayload.InvalidParameter.class, inclusive = false,message = "must be grater than zero")
    private String totalAmount;

    /**
     * 付款方式 1 支付宝 3 银行卡
     */
    @NotNull(payload = VcbPayload.MissingParameter.class, message = "paymentType")
    @Pattern(regexp = "[13]", payload = VcbPayload.InvalidParameter.class, message = "value not supported")
    private String paymentType;

    /**
     * 付款用户姓名
     */
    private String name;

    /**
     * 付款用户身份证号
     */
    private String idNumber;

}
