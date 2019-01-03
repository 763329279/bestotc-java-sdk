package com.bestotc.domain.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiBaseResponse {

    /**
     * 调用状态:success,failure
     */
    private String status;

}
