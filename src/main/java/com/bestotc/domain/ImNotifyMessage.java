package com.bestotc.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * IM 异步通知消息
 * @author qxx on 2019/1/2.
 */
@Getter
@Setter
public class ImNotifyMessage {
    /**
     * 第三方订单号
     */
    private String outOrderNo;

    /**
     *时间戳|2018-12-18T11:42:10.257Z 0时区时间
     */
    private String timestamp;
    /**
     * 消息类型 0:普通消息 1:发起申诉消息  2:结束申诉消息
     */
    private Integer messageType;
    /**
     * 消息发送者类型 0:客服,1:用户,2:承兑商,3:OTC客服
     */
    private Integer fromType;

    /**
     * 内容
     */
    private String content;

    /**
     * 附件(图片地址)
     */
    private String attach;
}
