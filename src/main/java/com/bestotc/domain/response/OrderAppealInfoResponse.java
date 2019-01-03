package com.bestotc.domain.response;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 订单申诉信息
 * @author qxx on 2018/11/1.
 */
@Getter
@Setter
public class OrderAppealInfoResponse {

    /**
     * 是否是用户发起的
     */
    private Boolean isUser;

    /**
     * 申诉状态2-申诉中 3-已处理
     */
    private Integer status;

    /**
     * 处理类型 0 无操作 1 关闭订单
     */
    private Integer dealType;

    /**
     * 处理结果
     */
    private String dealResult;

    private Date createTime;

    /**
     * 申诉项
     */
    private List<AppealItemDTO> appealItems;

    @Getter
    @Setter
    public static class AppealItemDTO{

        /**
         * 内容
         */
        private String content;

        /**
         * 图片地址(多个地址以,分割)
         */
        private String attach;

        /**
         * 是否是平台客服回复
         */
        private Boolean isStaff;

        private Date createTime;
    }
}
