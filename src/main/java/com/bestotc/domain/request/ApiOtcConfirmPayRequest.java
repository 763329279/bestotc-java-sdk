package com.bestotc.domain.request;

import com.bestotc.domain.VcbPayload;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

/**
 * 确认付款
 */
@Getter
@Setter
public class ApiOtcConfirmPayRequest {

    /**
     * 第三方订单号
     */
    @NotNull(payload = VcbPayload.MissingParameter.class, message = "outOrderNo")
    private String outOrderNo;

    /**
     * 付款用户姓名
     */
    @NotNull(payload = VcbPayload.MissingParameter.class, message = "name")
    private String name;

    /**
     * 付款用户身份证号
     */
    @NotNull(payload = VcbPayload.MissingParameter.class, message = "idNumber")
    private String idNumber;

    /**
     * 付款用户手机
     */
    private String mobile;


}
