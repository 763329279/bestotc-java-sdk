package com.bestotc.domain.request;

import com.bestotc.domain.VcbPayload;
import com.bestotc.utils.ValidatorHelper;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;


@Getter
@Setter
public class ApiOtcAppealRequest {

    /**
     * 第三方订单号
     */
    @NotBlank(payload = VcbPayload.MissingParameter.class, message = "outOrderNo")
    private String outOrderNo;
    /**
     * 内容
     */
    @NotBlank(payload = VcbPayload.MissingParameter.class, message = "content")
    private String content;
    /**
     * 附件图片url
     */
    private String attach;

    /**
     * 消息发送者类型 0:客服,1:用户,2:承兑商,3:OTC客服
     */
    @Range(payload = VcbPayload.InvalidParameter.class, min = 1, max = 2, message = "must be range between 1 and 2")
    private Integer fromType = 1;

}
