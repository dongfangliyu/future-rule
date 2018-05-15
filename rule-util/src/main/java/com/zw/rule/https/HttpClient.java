package com.zw.rule.https;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.PartBase;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.protocol.Protocol;



import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by Administrator on 2017/5/15.
 */
public class HttpClient implements Serializable {
    static Logger log = LoggerFactory.getLogger(HttpClient.class);
    private static final long serialVersionUID = -176092625883595547L;
    private static final int OK = 200;
    private static final int NOT_MODIFIED = 304;
    private static final int BAD_REQUEST = 400;
    private static final int NOT_AUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int NOT_ACCEPTABLE = 406;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final boolean DEBUG = true;
    org.apache.commons.httpclient.HttpClient client;
    private MultiThreadedHttpConnectionManager connectionManager;
    private int maxSize;

    public HttpClient() {
        this(150, 30000, 30000, 1048576);
    }

    public HttpClient(int maxConPerHost, int conTimeOutMs, int soTimeOutMs, int maxSize) {
        this.client = null;
        this.connectionManager = new MultiThreadedHttpConnectionManager();
        HttpConnectionManagerParams params = this.connectionManager.getParams();
        params.setDefaultMaxConnectionsPerHost(maxConPerHost);
        params.setConnectionTimeout(conTimeOutMs);
        params.setSoTimeout(soTimeOutMs);
        HttpClientParams clientParams = new HttpClientParams();
        clientParams.setCookiePolicy("ignoreCookies");
        this.client = new org.apache.commons.httpclient.HttpClient(clientParams, this.connectionManager);
        Protocol myhttps = new Protocol("https", new MySSLSocketFactory(), 443);
        Protocol.registerProtocol("https", myhttps);
        this.maxSize = maxSize;
    }

    private static void log(String message) {
        log.debug(message);
    }

    public int getHttps(String url) {
        log.debug(" in getHttps,url=" + url);
        GetMethod method = new GetMethod(url);
        int responseCode = -1;

        try {
            method.getParams().setParameter("http.method.retry-handler", new DefaultHttpMethodRetryHandler(3, false));
            this.client.executeMethod(method);
            responseCode = method.getStatusCode();
            log.info("https StatusCode:" + responseCode);
        } catch (IOException var7) {
            var7.printStackTrace();
        } finally {
            ;
        }

        method.releaseConnection();
        return responseCode;
    }

    public String get(String url) throws Exception {
        return this.get(url, new PostParameter[0]).asString();
    }

    public Response get(String url, PostParameter[] params) throws Exception {
        log("Request:");
        log("GET:" + url);
        if(params != null && params.length > 0) {
            String getmethod = encodeParameters(params);
            if(-1 == url.indexOf("?")) {
                url = url + "?" + getmethod;
            } else {
                url = url + "&" + getmethod;
            }
        }

        GetMethod getmethod1 = new GetMethod(url);
        return this.httpRequest(getmethod1);
    }

    public Response post(String url, PostParameter[] params) throws Exception {
        return this.post(url, params, Boolean.valueOf(true));
    }

    public Response post(String url, PostParameter[] params, Boolean WithTokenHeader) throws HttpsException {
        log("Request:");
        log("POST" + url);
        PostMethod postMethod = new PostMethod(url);

        for(int param = 0; param < params.length; ++param) {
            postMethod.addParameter(params[param].getName(), params[param].getValue());
        }

        HttpMethodParams var6 = postMethod.getParams();
        var6.setContentCharset("UTF-8");
        return WithTokenHeader.booleanValue()?this.httpRequest(postMethod):this.httpRequest(postMethod, WithTokenHeader);
    }

    public String post(String url, Map<String, String> params) throws Exception {
        String strResult = null;
        Response response = this.post(url, params, Boolean.valueOf(true));
        if(response != null) {
            strResult = response.getResponseAsString();
        }

        return strResult;
    }

    public Response post(String url, Map<String, String> params, Boolean WithTokenHeader) throws HttpsException {
        log("Request:");
        log("POST" + url);
        PostMethod postMethod = new PostMethod(url);
        Iterator var6 = params.entrySet().iterator();

        while(var6.hasNext()) {
            Map.Entry param = (Map.Entry)var6.next();
            postMethod.addParameter((String)param.getKey(), (String)param.getValue());
        }

        HttpMethodParams param1 = postMethod.getParams();
        param1.setContentCharset("UTF-8");
        return WithTokenHeader.booleanValue()?this.httpRequest(postMethod):this.httpRequest(postMethod, WithTokenHeader);
    }

    public Response httpRequest(HttpMethod method) throws HttpsException {
        return this.httpRequest(method, Boolean.valueOf(true));
    }

    public Response httpRequest(HttpMethod method, Boolean WithTokenHeader) throws HttpsException {
        byte responseCode = -1;

        Response var12;
        try {
            InetAddress ipaddr = InetAddress.getLocalHost();
            new ArrayList();
            method.getParams().setParameter("http.method.retry-handler", new DefaultHttpMethodRetryHandler(3, false));
            this.client.executeMethod(method);
            Header[] resHeader = method.getResponseHeaders();
            int var19 = method.getStatusCode();
            log("Response:");
            log("https StatusCode:" + String.valueOf(var19));
            Header[] var10 = resHeader;
            int var9 = resHeader.length;

            for(int e = 0; e < var9; ++e) {
                Header response = var10[e];
                log(response.getName() + ":" + response.getValue());
            }

            Response var20 = new Response();
            var20.setResponseAsString(method.getResponseBodyAsString());
            log(var20.toString() + "\n");
            if(var19 != 200) {
                try {
                    throw new HttpsException(getCause(var19));
                } catch (Exception var16) {
                    var16.printStackTrace();
                }
            }

            var12 = var20;
        } catch (IOException var17) {
            throw new HttpsException(var17.getMessage(), var17, responseCode);
        } finally {
            method.releaseConnection();
        }

        return var12;
    }

    public static String encodeParameters(PostParameter[] postParams) {
        StringBuffer buf = new StringBuffer();

        for(int j = 0; j < postParams.length; ++j) {
            if(j != 0) {
                buf.append("&");
            }

            try {
                buf.append(URLEncoder.encode(postParams[j].getName(), "UTF-8")).append("=").append(URLEncoder.encode(postParams[j].getValue(), "UTF-8"));
            } catch (UnsupportedEncodingException var4) {
                ;
            }
        }

        return buf.toString();
    }

    private static String getCause(int statusCode) {
        String cause = null;
        switch(statusCode) {
            case 304:
                break;
            case 400:
                cause = "The request was invalid.  An accompanying error message will explain why. This is the status code will be returned during rate limiting.";
                break;
            case 401:
                cause = "Authentication credentials were missing or incorrect.";
                break;
            case 403:
                cause = "The request is understood, but it has been refused.  An accompanying error message will explain why.";
                break;
            case 404:
                cause = "The URI requested is invalid or the resource requested, such as a user, does not exists.";
                break;
            case 406:
                cause = "Returned by the Search API when an invalid format is specified in the request.";
                break;
            case 500:
                cause = "Something is broken.  Please post to the group so the Weibo team can investigate.";
                break;
            case 502:
                cause = "Weibo is down or being upgraded.";
                break;
            case 503:
                cause = "Service Unavailable: The Weibo servers are up, but overloaded with requests. Try again later. The search and trend methods use this to indicate when you are being rate limited.";
                break;
            default:
                cause = "";
        }

        return statusCode + ":" + cause;
    }

    private static class ByteArrayPart extends PartBase {
        private byte[] mData;
        private String mName;

        public ByteArrayPart(byte[] data, String name, String type) throws IOException {
            super(name, type, "UTF-8", "binary");
            this.mName = name;
            this.mData = data;
        }

        protected void sendData(OutputStream out) throws IOException {
            out.write(this.mData);
        }

        protected long lengthOfData() throws IOException {
            return (long)this.mData.length;
        }

        protected void sendDispositionHeader(OutputStream out) throws IOException {
            super.sendDispositionHeader(out);
            StringBuilder buf = new StringBuilder();
            buf.append("; filename=\"").append(this.mName).append("\"");
            out.write(buf.toString().getBytes());
        }
    }
}
