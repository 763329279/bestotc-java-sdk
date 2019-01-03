package com.bestotc.domain.request;

import com.bestotc.domain.VcbPayload;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ApiOtcSendAppealMessageRequest {
    /**
     * 第三方订单号
     */
    @NotBlank(payload = VcbPayload.MissingParameter.class, message = "outOrderNo")
    private String outOrderNo;

    /**
     * 消息发送者类型 0:客服,1:用户
     */
    @NotBlank(payload = VcbPayload.MissingParameter.class, message = "fromType")
    @Range(payload = VcbPayload.InvalidParameter.class, min = 0, max = 1, message = "must be range between 1 and 2")
    private Integer fromType;

    /**
     * 内容
     */
    private String content;
    /**
     * 附件图片url
     */
    private String attach;

}
