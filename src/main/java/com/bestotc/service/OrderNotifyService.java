package com.bestotc.service;

import com.alibaba.fastjson.JSON;
import com.bestotc.utils.ApiIdentityUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.Signature;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Scanner;
import com.google.common.base.Function;
import com.bestotc.domain.OrderNotifyMessage;

/**
 * 订单状态改变后的异步通知
 */
@Slf4j
public class OrderNotifyService {
    /**
     * 消息类型:通知消息
     */
    private static final String MESSAGE_TYPE_NOTIFICATION = "Notification";
    /**
     * 确认订阅消息
     */
    private static final String MESSAGE_TYPE_SUBSC = "SubscriptionConfirmation";
    /**
     * 用户取消订阅消息
     */
    private static final String MESSAGE_TYPE_UNSUBSC = "UnsubscribeConfirmation";
    /**
     * 队列签名版本号
     */
    private static final String QUEUE_SIGNATURE_VERSION = "1";

    /**
     * request header 中的消息类型key
     */
    private static final String HEADER_MESSAGE_TYPE = "x-amz-sns-message-type";

    private final String SECRET_KEY ;

    public OrderNotifyService(String secretKey) {
        this.SECRET_KEY = secretKey;
    }

    public void orderStatusNotify(HttpServletRequest request,Function<OrderNotifyMessage,Boolean> function)throws IOException {
        String messageType = request.getHeader(HEADER_MESSAGE_TYPE);
        if (messageType == null) {
            log.info("x-amz-sns-message-type not exsist");
            throw new RuntimeException("Unexpected signature version. Unable to verify signature.");
        }

        //获取消息体
        Scanner scan = new Scanner(request.getInputStream());
        StringBuilder builder = new StringBuilder();
        while (scan.hasNextLine()) {
            builder.append(scan.nextLine());
        }
        Message msg = JSON.parseObject(builder.toString(),Message.class);

        /**
         * 验证通知队列签名
         */
        if (!msg.getSignatureVersion().equals(QUEUE_SIGNATURE_VERSION) || !isMessageSignatureValid(msg)) {
            throw new SecurityException("Signature verification failed.");
        }

        if (messageType.equals(MESSAGE_TYPE_NOTIFICATION)) {
            // 验证业务签名
            if (!ApiIdentityUtil.sign(SECRET_KEY, msg.getMessage()).equals(msg.getSubject())) {
                log.info(">>Business signature verification failed.");
                throw new SecurityException("Unexpected signature version. Unable to verify signature.");
            }
            OrderNotifyMessage orderNotifyMessage = JSON.parseObject(msg.getMessage(), OrderNotifyMessage.class);
            log.info(">>Business msg:{}", orderNotifyMessage);
            function.apply(orderNotifyMessage);
        } else if (messageType.equals(MESSAGE_TYPE_SUBSC)) {
            //确认订阅 请求SubscribeURL即可
            Scanner sc = new Scanner(new URL(msg.getSubscribeURL()).openStream());
            StringBuilder sb = new StringBuilder();
            while (sc.hasNextLine()) {
                sb.append(sc.nextLine());
            }
            log.info(">>Subscription confirmation (" + msg.getSubscribeURL() + ") Return value: " + sb.toString());
        } else if (messageType.equals(MESSAGE_TYPE_UNSUBSC)) {
            //用户取消订阅三天后生效 (取消方法 builder.toString() 中获取UnSubscribeURL 请求即可)
            log.info(">>Unsubscribe confirmation: " + msg.getMessage());
        } else {
            log.info(">>Unknown message type.");
        }
        log.info(">>Done processing message: " + msg.getMessageId());
    }


    /****************以下为亚马逊队列验证验签方法********************/
    private static boolean isMessageSignatureValid(Message msg) {
        try {
            URL url = new URL(msg.getSigningCertURL());
            InputStream inStream = url.openStream();
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            X509Certificate cert = (X509Certificate)cf.generateCertificate(inStream);
            inStream.close();

            Signature sig = Signature.getInstance("SHA1withRSA");
            sig.initVerify(cert.getPublicKey());
            sig.update(getMessageBytesToSign(msg));
            return sig.verify(Base64.decodeBase64(msg.getSignature()));
        }
        catch (Exception e) {
            throw new SecurityException("Verify method failed.", e);
        }
    }
    private static byte [] getMessageBytesToSign (Message msg) {
        byte [] bytesToSign = null;
        if (msg.getType().equals(MESSAGE_TYPE_NOTIFICATION)) {
            bytesToSign = buildNotificationStringToSign(msg).getBytes();
        } else if (msg.getType().equals(MESSAGE_TYPE_SUBSC) || msg.getType().equals(MESSAGE_TYPE_UNSUBSC)) {
            bytesToSign = buildSubscriptionStringToSign(msg).getBytes();
        }
        return bytesToSign;
    }

    private static String buildNotificationStringToSign(Message msg) {
        String stringToSign = null;

        //Build the string to sign from the values in the message.
        //Name and values separated by newline characters
        //The name value pairs are sorted by name
        //in byte sort order.
        stringToSign = "Message\n";
        stringToSign += msg.getMessage() + "\n";
        stringToSign += "MessageId\n";
        stringToSign += msg.getMessageId() + "\n";
        if (msg.getSubject() != null) {
            stringToSign += "Subject\n";
            stringToSign += msg.getSubject() + "\n";
        }
        stringToSign += "Timestamp\n";
        stringToSign += msg.getTimestamp() + "\n";
        stringToSign += "TopicArn\n";
        stringToSign += msg.getTopicArn() + "\n";
        stringToSign += "Type\n";
        stringToSign += msg.getType() + "\n";
        return stringToSign;
    }

    private static String buildSubscriptionStringToSign(Message msg) {
        String stringToSign = null;
        //Build the string to sign from the values in the message.
        //Name and values separated by newline characters
        //The name value pairs are sorted by name
        //in byte sort order.
        stringToSign = "Message\n";
        stringToSign += msg.getMessage() + "\n";
        stringToSign += "MessageId\n";
        stringToSign += msg.getMessageId() + "\n";
        stringToSign += "SubscribeURL\n";
        stringToSign += msg.getSubscribeURL() + "\n";
        stringToSign += "Timestamp\n";
        stringToSign += msg.getTimestamp() + "\n";
        stringToSign += "Token\n";
        stringToSign += msg.getToken() + "\n";
        stringToSign += "TopicArn\n";
        stringToSign += msg.getTopicArn() + "\n";
        stringToSign += "Type\n";
        stringToSign += msg.getType() + "\n";
        return stringToSign;
    }

    /**
     * 消息体
     */
    @Getter
    @Setter
    private static class Message {
        /**
         * 消息签名
         */
        private String subject;
        /**
         * 消息体内容
         */
        private String message;


        /*******************以下为亚马逊通知队列相关参数,非业务相关************************/

        /**
         * 消息类型:Notification(通知消息)  SubscriptionConfirmation(确认订阅消息) UnsubscribeConfirmation(用户取消订阅消息)
         */
        private String type;
        /**
         * messageId
         */
        private String messageId;
        /**
         * 令牌
         */
        private String token;
        /**
         * 主题
         */
        private String topicArn;
        /**
         * 订阅url
         */
        private String subscribeURL;
        /**
         * 时间戳
         */
        private String timestamp;
        /**
         * 签名版本号
         */
        private String signatureVersion;
        /**
         * 亚马逊队列签名字符串
         */
        private String signature;
        /**
         * 验证签名url
         */
        private String signingCertURL;
    }

}
