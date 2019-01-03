package com.bestotc.domain.request;

import lombok.Getter;
import lombok.Setter;
import com.bestotc.domain.VcbPayload;

import javax.validation.constraints.NotNull;

/**
 * 获取币种单价
 */
@Getter
@Setter
public class ApiOtcGetPriceRequest{

    /**
     * 币种:btc,usdt...
     */
    @NotNull(payload = VcbPayload.MissingParameter.class, message = "variety")
    private String variety;

    /**
     * 法币类型:CNY
     */
    @NotNull(payload = VcbPayload.MissingParameter.class, message = "currency")
    private String currency;

}
