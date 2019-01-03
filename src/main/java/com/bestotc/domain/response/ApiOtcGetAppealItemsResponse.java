package com.bestotc.domain.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * 申诉消息列表
 */
@Getter
@Setter
public class ApiOtcGetAppealItemsResponse {

    /**
     * 取消息列表标识
     */
    private String lastKey;
    /**
     * 申诉消息列表
     */
    private List<Item> items;

    /**
     * 申诉消息消息内容
     */
    @Getter
    @Setter
    public static class Item {
        /**
         * 内容
         */
        private String content;
        /**
         * 附件图片url
         */
        private String attach;
        /**
         * 消息发送者类型 0:客服,1:用户,2:承兑商,3:OTC客服
         */
        private Integer fromType;
        /**
         * 消息发送时间
         */
        private Date createTime;

    }
}
