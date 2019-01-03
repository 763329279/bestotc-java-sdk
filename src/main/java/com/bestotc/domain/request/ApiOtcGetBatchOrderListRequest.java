package com.bestotc.domain.request;

import com.bestotc.domain.VcbPayload;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 批量查询订单列表
 */
@Getter
@Setter
public class ApiOtcGetBatchOrderListRequest {

    @NotBlank(payload = VcbPayload.MissingParameter.class, message = "outOrderIds")
    private String outOrderIds;
}
