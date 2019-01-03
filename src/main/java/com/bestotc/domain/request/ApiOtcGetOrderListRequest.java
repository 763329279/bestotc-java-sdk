package com.bestotc.domain.request;

import lombok.Getter;
import lombok.Setter;

/**
 * 获取订单列表
 */
@Getter
@Setter
public class ApiOtcGetOrderListRequest extends QueryBaseRequest {
    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

}
