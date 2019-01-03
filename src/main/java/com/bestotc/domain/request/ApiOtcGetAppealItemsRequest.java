package com.bestotc.domain.request;

import com.bestotc.domain.VcbPayload;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ApiOtcGetAppealItemsRequest {

    @NotBlank(payload = VcbPayload.MissingParameter.class, message = "outOrderNo")
    private String outOrderNo;
    private String lastKey;

}
