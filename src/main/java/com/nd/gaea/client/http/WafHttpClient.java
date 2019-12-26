/*
package com.nd.gaea.client.http;


import com.nd.gaea.WafProperties;
import com.nd.gaea.client.support.CConfKeys;
import com.nd.gaea.client.support.WafConstants;
import com.nd.gaea.client.support.WafContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

*/
/**
 * http请求工具类
 *
 * @author vime
 * @since 0.9.5
 *//*

@Slf4j
public class WafHttpClient {
    private static final boolean TRANSMIT = WafProperties.getPropertyForBoolean(CConfKeys.WAF_TRANSMIT_HEADER);
    private static final boolean TRANSMIT_SDP_APP_ID = WafProperties.getPropertyForBoolean(CConfKeys.WAF_TRANSMIT_HEADER_SDP_APP_ID);
    private static final boolean TRANSMIT_SDP_MIGRATED = WafProperties.getPropertyForBoolean(CConfKeys.WAF_TRANSMIT_HEADER_SDP_MIGRATED);

    private static final boolean ADD_UA_APPLICATION_NAME = WafProperties.getPropertyForBoolean(CConfKeys.WAF_CLIENT_UA_APPLICATION_NAME);

    protected static int WAF_CLIENT_CONNECT_TIMEOUT_INT_VALUE;
    protected static int WAF_CLIENT_SOCKET_TIMEOUT_INT_VALUE;

    protected WafRestTemplate restTemplate;

    static {
        WAF_CLIENT_CONNECT_TIMEOUT_INT_VALUE = WafProperties.getPropertyForInteger
                (CConfKeys.WAF_CLIENT_CONNECT_TIMEOUT);
        WAF_CLIENT_SOCKET_TIMEOUT_INT_VALUE = WafProperties.getPropertyForInteger
                (CConfKeys.WAF_CLIENT_SOCKET_TIMEOUT);
    }

    public WafHttpClient() {
        this(WAF_CLIENT_CONNECT_TIMEOUT_INT_VALUE, WAF_CLIENT_SOCKET_TIMEOUT_INT_VALUE);
    }

    public WafHttpClient(int retryCount) {
        this(WAF_CLIENT_CONNECT_TIMEOUT_INT_VALUE, WAF_CLIENT_SOCKET_TIMEOUT_INT_VALUE, retryCount);
    }

    public WafHttpClient(int retryCount, Collection<Class<? extends IOException>> clazzes) {
        this(WAF_CLIENT_CONNECT_TIMEOUT_INT_VALUE, WAF_CLIENT_SOCKET_TIMEOUT_INT_VALUE, retryCount, clazzes);
    }

    */
/**
     * 初始化WafHttpClient对象
     *
     * @param connectTimeout 连接超时时间（毫秒），默认 5000 ms
     * @param socketTimeout  socket读写数据超时时间（毫秒），默认 10000 ms
     *//*

    public WafHttpClient(int connectTimeout, int socketTimeout) {
        if (connectTimeout < 0) {
            throw new IllegalArgumentException("Connect timeout value is illegal, must be >=0");
        }
        if (socketTimeout < 0) {
            throw new IllegalArgumentException("Socket timeout value is illegal, must be >=0");
        }
        WafRestTemplateBuilder wafRestTemplateBuilder = new WafRestTemplateBuilder();
        restTemplate = wafRestTemplateBuilder.build(connectTimeout, socketTimeout);
    }

    */
/**
     * 根据配置提供拥有重试能力的HttpClient
     *
     * @param connectTimeout 连接超时时间
     * @param socketTimeout  连接空闲时间(无时间传输)
     * @param retryCount     重试次数，0表示不重试
     *//*

    public WafHttpClient(int connectTimeout, int socketTimeout, int retryCount) {
        this(connectTimeout, socketTimeout, retryCount, new ArrayList<Class<? extends IOException>>());
    }

    */
/**
     * 根据配置提供拥有重试能力的HttpClient
     * Create the request retry handler using the specified IOException classes
     *
     * @param connectTimeout 连接超时时间
     * @param socketTimeout  连接空闲时间(无时间传输)
     * @param retryCount     重试次数，0表示不重试
     * @param clazzes        但为这些IOException时不重试
     *//*

    public WafHttpClient(int connectTimeout, int socketTimeout, int retryCount, Collection<Class<? extends IOException>> clazzes) {
        if (connectTimeout < 0) {
            throw new IllegalArgumentException("Connect timeout value is illegal, must be >=0");
        }
        if (socketTimeout < 0) {
            throw new IllegalArgumentException("Socket timeout value is illegal, must be >=0");
        }
        WafRestTemplateBuilder wafRestTemplateBuilder = new WafRestTemplateBuilder();
        restTemplate = wafRestTemplateBuilder.build(connectTimeout, socketTimeout, retryCount, clazzes);
    }

    public WafHttpClient(WafRestTemplate restTemplate) {
        Assert.notNull(restTemplate);
        this.restTemplate = restTemplate;
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    void setRestTemplate(WafRestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    protected HttpHeaders mergerHeaders(URI uri, HttpMethod method, HttpHeaders headers) {
        return headers;
    }

    //region post

    */
/**
     * 发送 post 请求
     *
     * @param url          完整的请求地址
     * @param requestBody  请求内容
     * @param uriVariables 使用地址方式传递的参数
     *//*

    public void post(String url, Object requestBody, Object... uriVariables) {
        HttpEntity<Object> httpEntity = getHttpEntity(requestBody);
        execute(url, HttpMethod.POST, httpEntity, uriVariables);
    }

    */
/**
     * 发送 post 请求
     *
     * @param url          请求完整的url
     * @param requestBody  请求内容
     * @param uriVariables 请求参数
     *//*

    public void post(String url, Object requestBody, Map<String, ?> uriVariables) {
        HttpEntity<Object> httpEntity = getHttpEntity(requestBody);
        execute(url, HttpMethod.POST, httpEntity, uriVariables);
    }
    //endregion

    //region get

    */
/**
     * 发送 get 请求
     *
     * @param url          请求完整的url
     * @param responseType 返回数据类型
     * @param uriVariables 请求参数
     * @return
     *//*

    public <T> T getForObject(String url, Type responseType, Map<String, ?> uriVariables) {
        HttpEntity<Object> httpEntity = getHttpEntity(null);
        return executeForObject(url, HttpMethod.GET, httpEntity, responseType, uriVariables);
    }

    */
/**
     * 发送 get 请求
     *
     * @param url          请求完整的url
     * @param responseType 返回数据类型
     * @param uriVariables 请求参数
     * @return
     *//*

    public <T> ResponseEntity<T> getForEntity(String url, Type responseType, Map<String, ?> uriVariables) {
        HttpEntity<Object> httpEntity = getHttpEntity(null);
        return executeForEntity(url, HttpMethod.GET, httpEntity, responseType, uriVariables);
    }
    //endregion

    //region put

    */
/**
     * 发送 put 请求
     *
     * @param url          请求完整的url
     * @param requestBody  请求内容
     * @param uriVariables 请求参数
     *//*

    public void put(String url, Object requestBody, Object... uriVariables) {
        HttpEntity<Object> httpEntity = getHttpEntity(requestBody);
        execute(url, HttpMethod.PUT, httpEntity, uriVariables);
    }

    */
/**
     * 发送 get 请求
     *
     * @param url          请求完整的url
     * @param responseType 返回数据类型
     * @param uriVariables 请求参数
     * @return
     *//*

    public <T> T getForObject(String url, Type responseType, Object... uriVariables) {
        HttpEntity<Object> httpEntity = getHttpEntity(null);
        return executeForObject(url, HttpMethod.GET, httpEntity, responseType, uriVariables);
    }

    */
/**
     * 发送 get 请求
     *
     * @param url          请求完整的url
     * @param responseType 返回数据类型
     * @param uriVariables 请求参数
     * @return
     *//*

    public <T> ResponseEntity<T> getForEntity(String url, Type responseType, Object... uriVariables) {
        HttpEntity<Object> httpEntity = getHttpEntity(null);
        return executeForEntity(url, HttpMethod.GET, httpEntity, responseType, uriVariables);
    }

    */
/**
     * 发送 put 请求
     *
     * @param url          请求完整的url
     * @param requestBody  请求内容
     * @param responseType 返回数据类型
     * @param uriVariables 请求参数
     * @return
     *//*

    public <T> T putForObject(String url, Object requestBody, Type responseType, Object... uriVariables) {
        HttpEntity<Object> httpEntity = getHttpEntity(requestBody);
        return executeForObject(url, HttpMethod.PUT, httpEntity, responseType, uriVariables);
    }

    */
/**
     * 发送 put 请求
     *
     * @param url          请求完整的url
     * @param requestBody  请求内容
     * @param responseType 返回数据类型
     * @param uriVariables 请求参数
     * @return
     *//*

    public <T> ResponseEntity<T> putForEntity(String url, Object requestBody, Type responseType, Object... uriVariables) {
        HttpEntity<Object> httpEntity = getHttpEntity(requestBody);
        return executeForEntity(url, HttpMethod.PUT, httpEntity, responseType, uriVariables);
    }

    */
/**
     * 发送 put 请求
     *
     * @param url          请求完整的url
     * @param requestBody  请求内容
     * @param uriVariables 请求参数
     *//*

    public void put(String url, Object requestBody, Map<String, ?> uriVariables) {
        HttpEntity<Object> httpEntity = getHttpEntity(requestBody);
        execute(url, HttpMethod.PUT, httpEntity, uriVariables);
    }

    */
/**
     * 发送 put 请求
     *
     * @param url          请求完整的url
     * @param requestBody  请求内容
     * @param responseType 返回数据类型
     * @param uriVariables 请求参数
     * @return
     *//*

    public <T> T putForObject(String url, Object requestBody, Type responseType, Map<String, ?> uriVariables) {
        HttpEntity<Object> httpEntity = getHttpEntity(requestBody);
        return executeForObject(url, HttpMethod.PUT, httpEntity, responseType, uriVariables);
    }

    */
/**
     * 发送 post 请求
     *
     * @param url          完整的请求地址
     * @param requestBody  请求内容(body,一般json格式)
     * @param responseType 回应内容类型(一般指定json实体)
     * @param uriVariables 使用地址方式传递的参数
     * @return responseType
     * @since 0.9.5
     *//*

    public <T> T postForObject(String url, Object requestBody, Type responseType, Object... uriVariables) {
        HttpEntity<Object> httpEntity = getHttpEntity(requestBody);
        return executeForObject(url, HttpMethod.POST, httpEntity, responseType, uriVariables);
    }

    */
/**
     * 发送 post 请求
     *
     * @param url          完整的请求地址
     * @param requestBody  请求内容
     * @param responseType 回应内容类型(一般指定json实体)
     * @param uriVariables 使用地址方式传递的参数
     * @return
     *//*

    public <T> T postForObject(String url, Object requestBody, Type responseType, Map<String, ?> uriVariables) {
        HttpEntity<Object> httpEntity = getHttpEntity(requestBody);
        return executeForObject(url, HttpMethod.POST, httpEntity, responseType, uriVariables);
    }

    */
/**
     * 发送 post 请求
     *
     * @param url          完整的请求地址
     * @param requestBody  请求内容(body,一般json格式)
     * @param responseType 回应内容类型(一般指定json实体)
     * @param uriVariables 使用地址方式传递的参数
     * @return ResponseEntity
     *//*

    public <T> ResponseEntity<T> postForEntity(String url, Object requestBody, Type responseType, Map<String, ?> uriVariables) {
        HttpEntity<Object> httpEntity = getHttpEntity(requestBody);
        return executeForEntity(url, HttpMethod.POST, httpEntity, responseType, uriVariables);
    }

    */
/**
     * 发送 put 请求
     *
     * @param url          请求完整的url
     * @param requestBody  请求内容
     * @param responseType 返回数据类型
     * @param uriVariables 请求参数
     * @return
     *//*

    public <T> ResponseEntity<T> putForEntity(String url, Object requestBody, Type responseType, Map<String, ?> uriVariables) {
        HttpEntity<Object> httpEntity = getHttpEntity(requestBody);
        return executeForEntity(url, HttpMethod.PUT, httpEntity, responseType, uriVariables);
    }
    //endregion

    //region delete

    */
/**
     * 发送 delete 请求
     *
     * @param url          请求完整的url
     * @param uriVariables 请求参数
     *//*

    public void delete(String url, Object... uriVariables) {
        HttpEntity<Object> httpEntity = getHttpEntity(null);
        execute(url, HttpMethod.DELETE, httpEntity, uriVariables);
    }

    */
/**
     * 发送 delete 请求
     *
     * @param url          请求完整的url
     * @param uriVariables 请求参数
     *//*

    public void delete(String url, Map<String, ?> uriVariables) {
        HttpEntity<Object> httpEntity = getHttpEntity(null);
        execute(url, HttpMethod.DELETE, httpEntity, uriVariables);
    }


    */
/**
     * 发送 post 请求
     *
     * @param url          完整的请求地址
     * @param requestBody  请求内容(body,一般json格式)
     * @param responseType 回应内容类型(一般指定json实体)
     * @param uriVariables 使用地址方式传递的参数
     * @return ResponseEntity
     *//*

    public <T> ResponseEntity<T> postForEntity(String url, Object requestBody, Type responseType, Object... uriVariables) {
        HttpEntity<Object> httpEntity = getHttpEntity(requestBody);
        return executeForEntity(url, HttpMethod.POST, httpEntity, responseType, uriVariables);
    }

    */
/**
     * 发送 delete 请求
     *
     * @param url          请求完整的url
     * @param responseType 返回数据类型
     * @param uriVariables 请求参数
     * @return
     *//*

    public <T> T deleteForObject(String url, Type responseType, Object... uriVariables) {
        HttpEntity<Object> httpEntity = getHttpEntity(null);
        return executeForObject(url, HttpMethod.DELETE, httpEntity, responseType, uriVariables);
    }

    */
/**
     * 发送 delete 请求
     *
     * @param url          请求完整的url
     * @param responseType 返回数据类型
     * @param uriVariables 请求参数
     * @return
     *//*

    public <T> T deleteForObject(String url, Type responseType, Map<String, ?> uriVariables) {
        HttpEntity<Object> httpEntity = getHttpEntity(null);
        return executeForObject(url, HttpMethod.DELETE, httpEntity, responseType, uriVariables);
    }

    */
/**
     * 发送 delete 请求
     *
     * @param url          请求完整的url
     * @param responseType 返回数据类型
     * @param uriVariables 请求参数
     * @return
     *//*

    public <T> ResponseEntity<T> deleteForEntity(String url, Type responseType, Object... uriVariables) {
        HttpEntity<Object> httpEntity = getHttpEntity(null);
        return executeForEntity(url, HttpMethod.DELETE, httpEntity, responseType, uriVariables);
    }

    */
/**
     * 发送 delete 请求
     *
     * @param url          请求完整的url
     * @param responseType 返回数据类型
     * @param uriVariables 请求参数
     * @return
     *//*

    public <T> ResponseEntity<T> deleteForEntity(String url, Type responseType, Map<String, ?> uriVariables) {
        HttpEntity<Object> httpEntity = getHttpEntity(null);
        return executeForEntity(url, HttpMethod.DELETE, httpEntity, responseType, uriVariables);
    }
    //endregion

    //region execute

    */
/**
     * 根据 method 执行rest请求，返回对象
     *
     * @param url           请求完整的url
     * @param method        请求方法
     * @param requestEntity 请求内容(head/body)
     * @param responseType  返回内容的类型，一般是ClassName.class
     * @param uriVariables  参数变量
     * @return 请求成功返回指定类型的对象
     *//*

    public <T> T executeForObject(String url, HttpMethod method, HttpEntity<?> requestEntity, Type responseType, Object... uriVariables) {
        URI uri = new UriTemplate(url).expand(uriVariables);
        HttpEntity<?> mergeRequestEntity = merge(uri, method, requestEntity);
        RequestCallback requestCallback = restTemplate.httpEntityCallback(mergeRequestEntity, responseType);
        ResponseExtractor<T> responseExtractor = restTemplate.httpMessageConverterExtractor(responseType);
        return doExecute(uri, method, requestCallback, responseExtractor);
    }

    */
/**
     * 根据 method 执行 rest 请求，返回对象
     *
     * @param url           请求完整的url
     * @param method        请求方法
     * @param requestEntity 请求内容(head/body)
     * @param responseType  返回内容的类型，一般是ClassName.class
     * @param uriVariables  参数变量
     * @return 请求成功返回指定类型的对象
     *//*

    public <T> T executeForObject(String url, HttpMethod method, HttpEntity<?> requestEntity, Type responseType, Map<String, ?> uriVariables) {
        URI uri = new UriTemplate(url).expand(uriVariables);
        HttpEntity<?> mergeRequestEntity = merge(uri, method, requestEntity);
        RequestCallback requestCallback = restTemplate.httpEntityCallback(mergeRequestEntity, responseType);
        ResponseExtractor<T> responseExtractor = restTemplate.httpMessageConverterExtractor(responseType);
        return doExecute(uri, method, requestCallback, responseExtractor);
    }

    */
/**
     * 根据 method 发送 rest 请求，返回带头信息的对象
     *
     * @param url           请求完整的url
     * @param method        请求方法
     * @param requestEntity 请求内容(head/body)
     * @param responseType  返回内容的类型，一般是ClassName.class
     * @param uriVariables  参数变量
     * @return 请求成功返回指定类型的对象
     *//*

    public <T> ResponseEntity<T> executeForEntity(String url, HttpMethod method, HttpEntity<?> requestEntity, Type responseType, Object... uriVariables) {
        URI uri = new UriTemplate(url).expand(uriVariables);
        HttpEntity<?> mergeRequestEntity = merge(uri, method, requestEntity);
        RequestCallback requestCallback = restTemplate.httpEntityCallback(mergeRequestEntity, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);

        return doExecute(uri, method, requestCallback, responseExtractor);
    }

    */
/**
     * 根据 method 发送 rest 请求，返回 {@link org.springframework.http.ResponseEntity} 对象
     *
     * @param url           请求完整的url
     * @param method        请求方法
     * @param requestEntity 请求内容(head/body)
     * @param responseType  返回内容的类型，一般是ClassName.class
     * @param uriVariables  参数变量
     * @return 请求成功返回指定类型的对象
     *//*

    public <T> ResponseEntity<T> executeForEntity(String url, HttpMethod method, HttpEntity<?> requestEntity, Type responseType, Map<String, ?> uriVariables) {
        URI uri = new UriTemplate(url).expand(uriVariables);
        HttpEntity<?> mergeRequestEntity = merge(uri, method, requestEntity);
        RequestCallback requestCallback = restTemplate.httpEntityCallback(mergeRequestEntity, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);

        return doExecute(uri, method, requestCallback, responseExtractor);
    }

    */
/**
     * 根据 method 执行 rest 请求，不返回数据
     *
     * @param url           请求完整的url
     * @param method        请求方法
     * @param requestEntity 请求内容
     * @param uriVariables  请求参数
     *//*

    public void execute(String url, HttpMethod method, HttpEntity<?> requestEntity, Object... uriVariables) {
        URI uri = new UriTemplate(url).expand(uriVariables);
        HttpEntity<?> mergeRequestEntity = merge(uri, method, requestEntity);
        RequestCallback requestCallback = restTemplate.httpEntityCallback(mergeRequestEntity);
        doExecute(uri, method, requestCallback, null);
    }

    */
/**
     * 根据 method 执行 rest 请求，不返回数据
     *
     * @param url           请求完整的url
     * @param method        请求方法
     * @param requestEntity 请求内容(head/body)
     * @param uriVariables  参数变量
     *//*

    public void execute(String url, HttpMethod method, HttpEntity<?> requestEntity, Map<String, ?> uriVariables) {
        URI uri = new UriTemplate(url).expand(uriVariables);
        HttpEntity<?> mergeRequestEntity = merge(uri, method, requestEntity);
        RequestCallback requestCallback = restTemplate.httpEntityCallback(mergeRequestEntity);
        doExecute(uri, method, requestCallback, null);
    }
    //endregion

    protected HttpEntity<Object> getHttpEntity(Object requestBody) {
        return new HttpEntity<>(requestBody);
    }

    private HttpEntity<?> merge(URI uri, HttpMethod method, HttpEntity<?> httpEntity) {
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpHeaders tempHeaders = httpEntity.getHeaders();
        if (null != tempHeaders) {
            httpHeaders.putAll(tempHeaders);
        }

        if (TRANSMIT) {
            //透传HEADER
            if(TRANSMIT_SDP_APP_ID){
                addHeader(httpHeaders, WafConstants.SDP_APP_ID, WafContext.getSdpAppId());
            }
            if(TRANSMIT_SDP_MIGRATED && WafContext.getCommonHeader().getSdpMigrated()){
                addHeader(httpHeaders, WafConstants.HEADER_SDP_MIGRATED, String.valueOf(WafContext.getCommonHeader().getSdpMigrated()));
            }
        }
        if (ADD_UA_APPLICATION_NAME) {
            //在UA增加应用名称信息
            String applicationName = WafContext.getApplicationName();
            if (StringUtils.isNotEmpty(applicationName)) {
                httpHeaders.set(org.apache.http.HttpHeaders.USER_AGENT, applicationName);
            }
        }
        httpHeaders = mergerHeaders(uri, method, httpHeaders);

        return new HttpEntity<>(httpEntity.getBody(), httpHeaders);
    }

    private void addHeader(HttpHeaders httpHeaders, String key, String value){
        if(httpHeaders.get(key) == null && value != null){
            httpHeaders.set(key, value);
        }
    }

    */
/**
     * 基于此方法真正发送rest请求
     *
     * @param url               请求完整的地址
     * @param method            请求方法
     * @param requestCallback   请求回调函数
     * @param responseExtractor 回应执行器
     * @return
     *//*

    protected <T> T doExecute(URI url, HttpMethod method, RequestCallback requestCallback, ResponseExtractor<T> responseExtractor) {
        System.out.println("你好世界");
        log.info("Http request begin. " + method + ": " + url);
        StopWatch sw = new StopWatch();
        sw.start();
        T result = restTemplate.execute(url, method, requestCallback, responseExtractor);
        sw.stop();
        log.info("Http request end. Total millis: " + sw.getTotalTimeMillis() + " " + method + ": " + url);

        return result;
    }

    */
/**
     * 发送 post 请求
     *
     * @param url          请求完整的url
     * @param responseType 返回数据类型
     * @param uriVariables 请求参数
     * @return
     * @deprecated 请使用 {@link #postForObject(String, Object, Type, Object...)}
     *//*

    @Deprecated
    public <T> T post(String url, Type responseType, Object... uriVariables) {
        return this.postForObject(url, null, responseType, uriVariables);
    }

    */
/**
     * 发送 post 请求
     *
     * @param url          请求完整的url
     * @param requestBody  请求内容
     * @param responseType 返回数据类型
     * @param uriVariables 请求参数
     * @return
     * @deprecated 请使用 {@link #postForObject(String, Object, Type, Object...)}
     *//*

    @Deprecated
    public <T> T post(String url, Object requestBody, Type responseType, Object... uriVariables) {
        return this.postForObject(url, requestBody, responseType, uriVariables);
    }

    */
/**
     * 重载post方法
     *
     * @param url          请求完整的url
     * @param responseType 返回数据类型
     * @param uriVariables 请求参数
     * @return
     * @deprecated 请使用 {@link #postForObject(java.lang.String, java.lang.Object, Type, Map)}
     *//*

    @Deprecated
    public <T> T post(String url, Type responseType, Map<String, ?> uriVariables) {
        return this.postForObject(url, null, responseType, uriVariables);
    }

    */
/**
     * 重载post方法，带body参数
     *
     * @param url          请求完整的url
     * @param requestBody  请求内容
     * @param responseType 返回数据类型
     * @param uriVariables 请求参数
     * @return
     * @deprecated 请使用 {@link #postForObject(java.lang.String, java.lang.Object, Type, Map)}
     *//*

    @Deprecated
    public <T> T post(String url, Object requestBody, Type responseType, Map<String, ?> uriVariables) {
        return this.postForObject(url, requestBody, responseType, uriVariables);
    }

    */
/**
     * 没有参数的 post 请求方法
     *
     * @param url          请求完整的url
     * @param responseType 返回数据类型
     * @return
     * @deprecated 请使用 {@link #postForObject(java.lang.String, java.lang.Object, Type, Object...)}
     *//*

    @Deprecated
    public <T> T post(String url, Type responseType) {
        return this.postForObject(url, null, responseType);
    }

    */
/**
     * 重载post方法，带body参数
     *
     * @param url          请求完整的url
     * @param requestBody  请求内容
     * @param responseType 返回数据类型
     * @return
     * @deprecated 请使用 {@link #postForObject(java.lang.String, java.lang.Object, Type, Object...)}
     *//*

    @Deprecated
    public <T> T post(String url, Object requestBody, Type responseType) {
        return this.postForObject(url, requestBody, responseType);
    }

    */
/**
     * get方法发送请求
     *
     * @param url          请求完整的url
     * @param responseType 返回数据类型
     * @param uriVariables 请求参数
     * @return
     * @deprecated 请使用 {@link #getForObject(java.lang.String, Type, Object...)}
     *//*

    @Deprecated
    public <T> T get(String url, Type responseType, Object... uriVariables) {
        return this.getForObject(url, responseType, uriVariables);
    }

    */
/**
     * get方法请求，带body参数
     *
     * @param url          请求完整的url
     * @param requestBody  请求内容
     * @param responseType 返回数据类型
     * @param uriVariables 请求参数
     * @return
     * @deprecated 请使用 {@link #getForObject(java.lang.String, Type, Object...)}
     *//*

    @Deprecated
    public <T> T get(String url, Object requestBody, Type responseType, Object... uriVariables) {
        return this.getForObject(url, responseType, uriVariables);
    }

    */
/**
     * 重载get方法，参数形式改为map
     *
     * @param url          请求完整的url
     * @param responseType 返回数据类型
     * @param uriVariables 请求参数
     * @return
     * @deprecated 请使用 {@link #getForObject(java.lang.String, Type, Map)}
     *//*

    @Deprecated
    public <T> T get(String url, Type responseType, Map<String, ?> uriVariables) {
        return this.getForObject(url, responseType, uriVariables);
    }

    */
/**
     * 重载get方法，带body参数
     *
     * @param url          请求完整的url
     * @param requestBody  请求内容
     * @param responseType 返回数据类型
     * @param uriVariables 请求参数
     * @return
     * @deprecated 请使用 {@link #getForObject(java.lang.String, Type, java.util.Map)}
     *//*

    @Deprecated
    public <T> T get(String url, Object requestBody, Type responseType, Map<String, ?> uriVariables) {
        return this.getForObject(url, responseType, uriVariables);
    }

    */
/**
     * 没有参数的get请求方法
     *
     * @param url          请求完整的url
     * @param responseType 返回数据类型
     * @return
     * @deprecated 请使用 {@link #getForObject(java.lang.String, Type, Object...)}
     *//*

    @Deprecated
    public <T> T get(String url, Type responseType) {
        return this.getForObject(url, responseType);
    }

    */
/**
     * 带body参数的get请求方法
     *
     * @param url          请求完整的url
     * @param requestBody  请求内容
     * @param responseType 返回数据类型
     * @return
     * @deprecated 请使用 {@link #getForObject(java.lang.String, Type, Object...)}
     *//*

    @Deprecated
    public <T> T get(String url, Object requestBody, Type responseType) {
        return this.getForObject(url, responseType);
    }

    */
/**
     * 发送 delete 请求
     *
     * @param url          请求完整的url
     * @param responseType 返回数据类型
     * @param uriVariables 请求参数
     * @return
     * @deprecated 请使用 {@link #deleteForObject(String, Type, Object...)}
     *//*

    @Deprecated
    public <T> T delete(String url, Type responseType, Object... uriVariables) {
        return this.deleteForObject(url, responseType, uriVariables);
    }

    */
/**
     * 发送 delete 请求, 带body参数
     *
     * @param url          请求完整的url
     * @param requestBody  请求内容
     * @param responseType 返回数据类型
     * @param uriVariables 请求参数
     * @return
     * @deprecated 请使用 {@link #deleteForObject(String, Type, Object...)}
     *//*

    @Deprecated
    public <T> T delete(String url, Object requestBody, Type responseType, Object... uriVariables) {
        return this.deleteForObject(url, responseType, uriVariables);
    }

    */
/**
     * 发送 delete 请求
     *
     * @param url          请求完整的url
     * @param responseType 返回数据类型
     * @param uriVariables 请求参数
     * @return
     * @deprecated 请使用 {@link #deleteForObject(String, Type, Map)}
     *//*

    @Deprecated
    public <T> T delete(String url, Type responseType, Map<String, ?> uriVariables) {
        return this.deleteForObject(url, responseType, uriVariables);
    }

    */
/**
     * 发送 delete 请求, 带body参数
     *
     * @param url          请求完整的url
     * @param requestBody  请求内容
     * @param responseType 返回数据类型
     * @param uriVariables 请求参数
     * @return
     * @deprecated 请使用 {@link #deleteForObject(String, Type, Object...)}
     *//*

    @Deprecated
    public <T> T delete(String url, Object requestBody, Type responseType, Map<String, ?> uriVariables) {
        return this.deleteForObject(url, responseType, uriVariables);
    }

    */
/**
     * 无参数的delete请求
     *
     * @param url          请求完整的url
     * @param responseType 返回数据类型
     * @return
     * @deprecated 请使用 {@link #deleteForObject(String, Type, Object...)}
     *//*

    @Deprecated
    public <T> T delete(String url, Type responseType) {
        return this.deleteForObject(url, responseType);
    }

    */
/**
     * 带body参数的delete请求
     *
     * @param url          请求完整的url
     * @param requestBody  请求内容
     * @param responseType 返回数据类型
     * @return
     * @deprecated 请使用 {@link #deleteForObject(String, Type, Object...)}
     *//*

    @Deprecated
    public <T> T delete(String url, Object requestBody, Type responseType) {
        return this.deleteForObject(url, responseType);
    }

    */
/**
     * 发送 put 请求
     *
     * @param url          请求完整的url
     * @param responseType 返回数据类型
     * @param uriVariables 请求参数
     * @return
     * @deprecated 请使用 {@link #putForObject(String, Object, Type, Object...)}
     *//*

    @Deprecated
    public <T> T put(String url, Type responseType, Object... uriVariables) {
        return this.putForObject(url, null, responseType, uriVariables);
    }

    */
/**
     * @param url          请求完整的url
     * @param requestBody  请求内容
     * @param responseType 返回数据类型
     * @param uriVariables 请求参数
     * @return
     * @deprecated 请使用 {@link #putForObject(String, Object, Type, Object...)}
     *//*

    @Deprecated
    public <T> T put(String url, Object requestBody, Type responseType, Object... uriVariables) {
        return this.putForObject(url, requestBody, responseType, uriVariables);
    }

    */
/**
     * 发送 put 请求
     *
     * @param url          请求完整的url
     * @param responseType 返回数据类型
     * @param uriVariables 请求参数
     * @return
     * @deprecated 请使用 {@link #putForObject(String, Object, Type, Map)}
     *//*

    @Deprecated
    public <T> T put(String url, Type responseType, Map<String, ?> uriVariables) {
        return this.putForObject(url, null, responseType, uriVariables);
    }

    */
/**
     * 发送 put 请求, 带body参数
     *
     * @param url          请求完整的url
     * @param requestBody  请求内容
     * @param responseType 返回数据类型
     * @param uriVariables 请求参数
     * @return
     * @deprecated 请使用 {@link #putForObject(String, Object, Type, Map)}
     *//*

    @Deprecated
    public <T> T put(String url, Object requestBody, Type responseType, Map<String, ?> uriVariables) {
        return this.putForObject(url, requestBody, responseType, uriVariables);
    }

    */
/**
     * 无参数的put的请求
     *
     * @param url          请求完整的url
     * @param responseType 返回数据类型
     * @return
     * @deprecated 请使用 {@link #putForObject(String, Object, Type, Object...)}
     *//*

    @Deprecated
    public <T> T put(String url, Type responseType) {
        return this.putForObject(url, null, responseType);
    }

    */
/**
     * 带body参数的put请求
     *
     * @param url          请求完整的url
     * @param requestBody  请求内容
     * @param responseType 返回数据类型
     * @return
     * @deprecated 请使用 {@link #putForObject(String, Object, Type, Object...)}
     *//*

    @Deprecated
    public <T> T put(String url, Object requestBody, Type responseType) {
        return this.putForObject(url, requestBody, responseType);
    }

    */
/**
     * 根据传入的方法名，发送相对应的请求
     *
     * @param url          请求完整的url
     * @param responseType 返回数据类型
     * @param uriVariables 请求参数
     * @return
     * @deprecated 请使用 {@link #executeForObject(String, HttpMethod, HttpEntity, Type, Map)}
     *//*

    @Deprecated
    public <T> T exchange(String url, HttpMethod method, Type responseType, Map<String, ?> uriVariables) {
        HttpEntity<Object> httpEntity = getHttpEntity(null);
        return this.executeForObject(url, method, httpEntity, responseType, uriVariables);
    }

    */
/**
     * 根据传入的方法名，发送相对应的请求
     *
     * @param url          请求完整的url
     * @param responseType 返回数据类型
     * @param uriVariables 请求参数
     * @return
     * @deprecated 请使用 {@link #executeForObject(String, HttpMethod, HttpEntity, Type, Object...)}
     *//*

    @Deprecated
    public <T> T exchange(String url, HttpMethod method, Type responseType, Object... uriVariables) {
        HttpEntity<Object> httpEntity = getHttpEntity(null);
        return this.executeForObject(url, method, httpEntity, responseType, uriVariables);
    }

    */
/**
     * 根据传入的方法名，发送相对应的请求
     *
     * @param url          请求完整的url
     * @param method       请求方法
     * @param responseType 返回数据类型
     * @return
     * @deprecated 请使用 {@link #executeForObject(String, HttpMethod, HttpEntity, Type, Object...)}
     *//*

    @Deprecated
    public <T> T exchange(String url, HttpMethod method, Type responseType) {
        HttpEntity<Object> httpEntity = getHttpEntity(null);
        return this.executeForObject(url, method, httpEntity, responseType);
    }

    */
/**
     * 根据传入的方法名，发送相对应的请求
     *
     * @param url          请求完整的url
     * @param requestBody  请求内容
     * @param responseType 返回数据类型
     * @param uriVariables 请求参数
     * @return
     * @deprecated 请使用 {@link #executeForObject(String, HttpMethod, HttpEntity, Type, Map)}
     *//*

    @Deprecated
    public <T> T exchange(String url, HttpMethod method, Type responseType, Object requestBody, HttpHeaders requestHeaders, Map<String, ?> uriVariables) {
        HttpEntity<Object> requestEntity = new HttpEntity<>(requestBody, requestHeaders);
        return this.executeForObject(url, method, requestEntity, responseType, uriVariables);
    }

    */
/**
     * 根据传入的方法名，发送相对应的请求
     *
     * @param url
     * @param method
     * @param responseType
     * @param requestBody
     * @param requestHeaders
     * @param uriVariables
     * @return
     * @deprecated 请使用 {@link #executeForObject(String, HttpMethod, HttpEntity, Type, Object...)}
     *//*

    @Deprecated
    public <T> T exchange(String url, HttpMethod method, Type responseType, Object requestBody, HttpHeaders requestHeaders, Object... uriVariables) {
        HttpEntity<Object> requestEntity = new HttpEntity<>(requestBody, requestHeaders);
        return this.executeForObject(url, method, requestEntity, responseType, uriVariables);
    }

    */
/**
     * 根据传入的方法名，发送相对应的请求
     *
     * @param url
     * @param method
     * @param requestBody
     * @param responseType
     * @param requestHeaders
     * @return
     * @deprecated 请使用 {@link #executeForObject(String, HttpMethod, HttpEntity, Type, Object...)}
     *//*

    @Deprecated
    public <T> T exchange(String url, HttpMethod method, Object requestBody, Type responseType, HttpHeaders requestHeaders) {
        HttpEntity<Object> requestEntity = new HttpEntity<>(requestBody, requestHeaders);
        return this.executeForObject(url, method, requestEntity, responseType);
    }

    */
/**
     * 根据传入的方法名，发送相对应的请求
     *
     * @param url
     * @param method
     * @param responseType
     * @param requestBody
     * @param uriVariables
     * @return
     * @deprecated 请使用 {@link #executeForObject(String, HttpMethod, HttpEntity, Type, Map)}
     *//*

    @Deprecated
    public <T> T exchange(String url, HttpMethod method, Type responseType, Object requestBody, Map<String, ?> uriVariables) {
        HttpEntity<Object> requestEntity = new HttpEntity<>(requestBody, null);
        return this.executeForObject(url, method, requestEntity, responseType, uriVariables);
    }

    */
/**
     * 根据传入的方法名，发送相对应的请求
     *
     * @param url
     * @param method
     * @param responseType
     * @param requestBody
     * @param uriVariables
     * @return
     * @deprecated 请使用 {@link #executeForObject(String, HttpMethod, HttpEntity, Type, Object...)}
     *//*

    @Deprecated
    public <T> T exchange(String url, HttpMethod method, Type responseType, Object requestBody, Object... uriVariables) {
        HttpEntity<Object> requestEntity = new HttpEntity<>(requestBody, null);
        return this.executeForObject(url, method, requestEntity, responseType, uriVariables);
    }

    */
/**
     * 根据传入的方法名，发送相对应的请求
     *
     * @param url
     * @param method
     * @param requestBody
     * @param responseType
     * @return
     * @deprecated 请使用 {@link #executeForObject(String, HttpMethod, HttpEntity, Type, Object...)}
     *//*

    @Deprecated
    public <T> T exchange(String url, HttpMethod method, Object requestBody, Type responseType) {
        HttpEntity<Object> requestEntity = new HttpEntity<>(requestBody, null);
        return this.executeForObject(url, method, requestEntity, responseType);
    }

}*/
