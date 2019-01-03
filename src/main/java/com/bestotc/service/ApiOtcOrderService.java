package com.bestotc.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.bestotc.domain.ApiProtocol;
import com.bestotc.domain.OtcApiOrderVO;
import com.bestotc.domain.QueryResponse;
import com.bestotc.domain.request.*;
import com.bestotc.domain.response.*;
import com.bestotc.utils.ApiIdentityUtil;
import com.bestotc.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import com.bestotc.exception.VcbException;
import com.bestotc.utils.ValidatorHelper;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class ApiOtcOrderService {

    /**
     * 秘匙
     */
    private final String ACCESS_KEY_ID ;

    /**
     * 秘钥
     */
    private final String ACCESS_KEY_SECRET;

    /**
     * 签名版本号
     */
    private final String SIGNATURE_VERSION = "1";

    private final String SIGNATURE_METHOD = HmacAlgorithms.HMAC_SHA_256.getName();

    /**
     * 服务器地址
     */
    private final String HOST;
    /**
     * 协议+ HOST
     */
    private  final String DOMAIN;

    private final HttpClient httpClient;

    public ApiOtcOrderService(String accessKeyId, String accessKeySecret, String host, ApiProtocol apiProtocol) {
        this.ACCESS_KEY_ID = accessKeyId;
        this.ACCESS_KEY_SECRET = accessKeySecret;
        this.httpClient = HttpUtil.getHttpClient();
        this.HOST = host;
        this.DOMAIN = apiProtocol.getValue() + this.HOST;
    }

    public ApiOtcOrderService(String accessKeyId, String accessKeySecret, String host, ApiProtocol apiProtocol, HttpClient httpClient) {
        this.ACCESS_KEY_ID = accessKeyId;
        this.ACCESS_KEY_SECRET = accessKeySecret;
        this.httpClient = httpClient;
        this.HOST = host;
        this.DOMAIN = apiProtocol.getValue() + this.HOST;
    }

    /**
     * 买币下单V1
     * @param request
     * @return
     * @throws IOException
     */
    public ApiOtcCreateBuyOrderResponse createBuyOrder(ApiOtcCreateBuyOrderRequest request){
        ValidatorHelper.validator(request);
        Map<String, String> params = ApiIdentityUtil.toMap(request);
        String uri = "/v1/api/openotc/order/buy";
        return call(uri, params, ApiOtcCreateBuyOrderResponse.class);
    }

    /**
     * 买币下单V2
     *
     * @param request
     * @return
     * @throws IOException
     */
    public ApiOtcCreateBuyOrderResponseV2 createBuyOrderV2(ApiOtcCreateBuyOrderRequestV2 request){
        ValidatorHelper.validator(request);
        Map<String, String> params = ApiIdentityUtil.toMap(request);
        String uri = "/v2/api/openotc/order/buy";
        return call(uri, params, ApiOtcCreateBuyOrderResponseV2.class);
    }

    /**
     * 卖币下单
     * @param request
     * @return
     */
    public ApiOtcCreateSellOrderResponse createSellOrder(ApiOtcCreateSellOrderRequest request){
        ValidatorHelper.validator(request);
        Map<String, String> params = ApiIdentityUtil.toMap(request);
        String uri = "/v1/api/openotc/order/sell";
        return call(uri, params, ApiOtcCreateSellOrderResponse.class);
    }

    /**
     * 取消订单
     * @param request
     * @return
     * @throws Exception
     */
    public ApiBaseResponse cancelOrder(ApiOtcOrderRequest request){
        Map<String, String> params = ApiIdentityUtil.toMap(request);
        String uri = "/v1/api/openotc/order/cancel";
        return call(uri, params, ApiBaseResponse.class);
    }

    /**
     * 确认付款
     * @param request
     * @return
     */
    public ApiBaseResponse confirmPay(ApiOtcOrderRequest request){
        Map<String, String> params = ApiIdentityUtil.toMap(request);
        String uri = "/v1/api/openotc/order/buy/confirmPay";
        return call(uri, params, ApiBaseResponse.class);
    }

    /**
     * 确认收款
     * @param request
     * @return
     */
    public ApiBaseResponse confirmReceive(ApiOtcConfirmPayRequest request){
        ValidatorHelper.validator(request);
        Map<String, String> params = ApiIdentityUtil.toMap(request);
        String uri = "/v1/api/openotc/order/sell/confirmReceive";
        return call(uri, params, ApiBaseResponse.class);
    }

    /**
     * 获取账号信息
     * @return
     */
    public ApiOtcAccountResponse getAccountInfo(){
        Map<String, String> params = new HashMap<>();
        String uri = "/v1/api/openotc/account";
        return call(uri, params, ApiOtcAccountResponse.class);
    }

    /**
     * 获取订单列表
     * @param request
     * @return
     */
    public QueryResponse<OtcApiOrderVO> getOrderList(ApiOtcGetOrderListRequest request){
        ValidatorHelper.validator(request);
        Map<String, String> params = ApiIdentityUtil.toMap(request);
        String uri = "/v1/api/openotc/order/list";
        return call(uri, params, new TypeReference<QueryResponse<OtcApiOrderVO>>(){});
    }

    /**
     * 获取币种单价
     * @param request
     * @return
     */
    public ApiOtcGetPriceResponse getPrice(ApiOtcGetPriceRequest request){
        ValidatorHelper.validator(request);
        Map<String, String> params = ApiIdentityUtil.toMap(request);
        String uri = "/v1/api/openotc/price";
        return call(uri, params, ApiOtcGetPriceResponse.class);
    }

    /**
     * 获取订单详情
     * @param request
     * @return
     */
    public OtcApiOrderVO getOrderDetail(ApiOtcOrderRequest request){
        ValidatorHelper.validator(request);
        Map<String, String> params = ApiIdentityUtil.toMap(request);
        String uri = "/v1/api/openotc/order/detail";
        return call(uri, params, OtcApiOrderVO.class);
    }

    /**
     * 批量查询订单列表
     * @param request
     * @return
     */
    public List<OtcApiOrderVO> getOrderListDetail(ApiOtcGetBatchOrderListRequest request){
        ValidatorHelper.validator(request);
        Map<String, String> params = ApiIdentityUtil.toMap(request);
        String uri = "/v1/api/openotc/order/list/batch";
        return call2(uri, params, OtcApiOrderVO.class);
    }

    /**
     * 申诉
     * @param request
     * @return
     */
    public ApiBaseResponse appealSubmit(ApiOtcAppealRequest request){
        ValidatorHelper.validator(request);
        Map<String, String> params = ApiIdentityUtil.toMap(request);
        String uri = "/v1/api/openotc/order/appeal/submit";
        return call(uri, params, ApiBaseResponse.class);
    }

    /**
     * 发送消息
     * @param request
     * @return
     */
    public ApiBaseResponse sendAppealMessage(ApiOtcSendAppealMessageRequest request){
        ValidatorHelper.validator(request);
        Map<String, String> params = ApiIdentityUtil.toMap(request);
        String uri = "/v1/api/openotc/order/appeal/message/send";
        return call(uri, params, ApiBaseResponse.class);
    }

    /**
     * 获取申诉消息列表
     * @param request
     * @return
     */
    public ApiOtcGetAppealItemsResponse getAppealMessageList(ApiOtcGetAppealItemsRequest request){
        ValidatorHelper.validator(request);
        Map<String, String> params = ApiIdentityUtil.toMap(request);
        String uri = "/v1/api/openotc/order/appeal/message/list";
        return call(uri, params, ApiOtcGetAppealItemsResponse.class);
    }

    /**
     * 申诉详情
     * @param request
     * @return
     */
    public OrderAppealInfoResponse getAppealInfo(ApiOtcOrderRequest request){
        ValidatorHelper.validator(request);
        Map<String, String> params = ApiIdentityUtil.toMap(request);
        String uri = "/v1/api/openotc/order/appealInfo";
        return call(uri, params, OrderAppealInfoResponse.class);
    }

    /**
     * 申诉处理
     * @param request
     * @return
     */
    public ApiBaseResponse appealDeal(ApiOtcAppealDealRequest request){
        ValidatorHelper.validator(request);
        Map<String, String> params = ApiIdentityUtil.toMap(request);
        String uri = "/v1/api/openotc/order/appealDeal";
        return call(uri, params, ApiBaseResponse.class);
    }

    private <T> T call(String uri, Map<String, String> params, Class<T> clazz) {
        return JSON.parseObject(call(uri,params)).getObject("body", clazz);
    }
    private <T> List<T> call2(String uri, Map<String, String> params, Class<T> clazz) {
        return JSON.parseObject(call(uri,params)).getJSONArray("body").toJavaList(clazz);
    }
    private <T> T call(String uri, Map<String, String> params, TypeReference<T> typeReference) {
        return JSON.parseObject(JSON.parseObject(call(uri,params)).getString("body"),typeReference);
    }
    private String call(String uri, Map<String, String> params) {
        ApiIdentityUtil.sign(ACCESS_KEY_ID, ACCESS_KEY_SECRET, SIGNATURE_VERSION, SIGNATURE_METHOD, ApiIdentityUtil.gmtNow(), HttpPost.METHOD_NAME, HOST, uri, params);
        String json = JSON.toJSONString(params);
        log.info("json={}", json);
        HttpPost httpPost = new HttpPost(DOMAIN + uri);
        httpPost.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));
        String responseData ;
        try {
            HttpResponse httpResponse = httpClient.execute(httpPost);
            InputStream inputStream = httpResponse.getEntity().getContent();
            responseData = IOUtils.toString(inputStream);
            inputStream.close();
        } catch (IOException e) {
            throw new VcbException("HttpClient IOException");
        }
        log.info("responseData={}", responseData);
        return responseData;
    }


}
