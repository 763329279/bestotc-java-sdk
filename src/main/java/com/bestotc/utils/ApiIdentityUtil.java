package com.bestotc.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ApiIdentityUtil {

    private static final Logger log = LoggerFactory.getLogger(ApiIdentityUtil.class);

    public static class ApiIdentityParams {

        private static final String ACCESS_KEY_ID = "AccessKeyId";
        private static final String SIGNATURE_VERSION = "SignatureVersion";
        private static final String SIGNATURE_METHOD = "SignatureMethod";
        private static final String TIMESTAMP = "Timestamp";
        private static final String SIGNATURE = "Signature";

    }

    public static Object sign(String accessKeyId, String accessKeySecret, String signatureVersion, String signatureMethod, String timestamp, String method, String host, String uri, Map<String, String> params) {
        params.remove(ApiIdentityParams.SIGNATURE);
        params.put(ApiIdentityParams.ACCESS_KEY_ID, accessKeyId);
        params.put(ApiIdentityParams.SIGNATURE_VERSION, signatureVersion);
        params.put(ApiIdentityParams.SIGNATURE_METHOD, signatureMethod);
        params.put(ApiIdentityParams.TIMESTAMP, timestamp);
        List<String> strings = params.keySet().stream().sorted().map(k -> k + "=" + urlEncode(params.get(k))).collect(Collectors.toList());//changed!!
        String strToSign = String.join("\n", method.toUpperCase(), host.toLowerCase(), uri, String.join("&", strings));
        params.put(ApiIdentityParams.SIGNATURE, sign(accessKeySecret, strToSign));
        return params.get(ApiIdentityParams.SIGNATURE);
    }

    public static String sign(String accessKeySecret, String strToSign) {
        return Base64.encodeBase64String(new HmacUtils(HmacAlgorithms.HMAC_SHA_256, accessKeySecret).hmac(strToSign));
    }

    public static String gmtNow() {
        return Instant.ofEpochSecond(Instant.now().getEpochSecond()).atZone(ZoneId.of("Z")).format(DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss"));
    }

    public static String urlEncode(String s) {
        try {
            return URLEncoder.encode(s, StandardCharsets.UTF_8.name()).replaceAll("\\+", "%20");
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage(), e);
            throw new IllegalArgumentException("UTF-8 encoding not supported!");
        }
    }

    /**
     * Converts a JavaBean to a map.
     *
     * @param bean JavaBean to convert
     * @return map converted
     */
    public static final Map<String, String> toMap(Object bean) {
        Map<String, String> returnMap = new HashMap<>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (int i = 0; i< propertyDescriptors.length; i++) {
                PropertyDescriptor descriptor = propertyDescriptors[i];
                String propertyName = descriptor.getName();
                if (!propertyName.equals("class")) {
                    Method readMethod = descriptor.getReadMethod();
                    Object result = readMethod.invoke(bean, new Object[0]);
                    if (result != null) {
                        returnMap.put(propertyName, result.toString());
                    }
                }
            }
        } catch (InvocationTargetException e) {
            e.printStackTrace();    // failed to call setters
        } catch (IntrospectionException e) {
            e.printStackTrace();    // failed to get class fields
        } catch (IllegalAccessException e) {
            e.printStackTrace();    // failed to instant JavaBean
        }

        return returnMap;
    }

}
