package com.bestotc.domain.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.bestotc.domain.VcbPayload;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@Getter
@Setter
public class ApiOtcAppealDealRequest {

    /**
     * 第三方订单号
     */
    @NotBlank(payload = VcbPayload.MissingParameter.class, message = "outOrderNo")
    private String outOrderNo;

    /**
     * 处理结果:0继续交易 1 取消订单
     */
    @NotNull(payload = VcbPayload.MissingParameter.class, message = "dealType")
    @Range(payload = VcbPayload.InvalidParameter.class, min = 0, max = 1, message = "must be range between 1 and 2")
    private Integer dealType;

    /**
     * 备注
     */
    @NotBlank(payload = VcbPayload.MissingParameter.class, message = "remark")
    private String remark;
}
