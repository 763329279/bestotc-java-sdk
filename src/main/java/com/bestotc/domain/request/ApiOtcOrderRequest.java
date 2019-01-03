package com.bestotc.domain.request;

import lombok.Getter;
import lombok.Setter;
import com.bestotc.domain.VcbPayload;

import javax.validation.constraints.NotNull;

/**
 * 根据第三方订单请求
 */
@Getter
@Setter
public class ApiOtcOrderRequest {

    /**
     * 第三方订单号
     */
    @NotNull(payload = VcbPayload.MissingParameter.class, message = "outOrderNo")
    private String outOrderNo;

}
