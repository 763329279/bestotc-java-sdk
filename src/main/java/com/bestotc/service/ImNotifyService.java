package com.bestotc.service;

import com.alibaba.fastjson.JSON;
import com.bestotc.domain.ImNotifyMessage;
import com.bestotc.domain.OrderNotifyMessage;
import com.bestotc.utils.ApiIdentityUtil;
import com.google.common.base.Function;
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

/**
 * im异步通知
 */
@Slf4j
public class ImNotifyService {

    public void orderStatusNotify(HttpServletRequest request, Function<ImNotifyMessage, Boolean> function) throws IOException {
        //获取消息体
        Scanner scan = new Scanner(request.getInputStream());
        StringBuilder builder = new StringBuilder();
        while (scan.hasNextLine()) {
            builder.append(scan.nextLine());
        }
        ImNotifyMessage msg = JSON.parseObject(builder.toString(), ImNotifyMessage.class);
        function.apply(msg);
    }

}
