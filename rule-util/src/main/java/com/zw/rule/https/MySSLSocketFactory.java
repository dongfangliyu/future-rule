package com.zw.rule.https;

import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;

import javax.net.ssl.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Created by Administrator on 2017/5/15.
 */
public class MySSLSocketFactory implements ProtocolSocketFactory {
    private SSLContext sslcontext = null;

    public MySSLSocketFactory() {
    }

    private SSLContext createSSLContext() {
        SSLContext sslcontext = null;

        try {
            sslcontext = SSLContext.getInstance("SSL");
            sslcontext.init((KeyManager[])null, new TrustManager[]{new MySSLSocketFactory.TrustAnyTrustManager((MySSLSocketFactory.TrustAnyTrustManager)null)}, new SecureRandom());
        } catch (NoSuchAlgorithmException var3) {
            var3.printStackTrace();
        } catch (KeyManagementException var4) {
            var4.printStackTrace();
        }

        return sslcontext;
    }

    private SSLContext getSSLContext() {
        if(this.sslcontext == null) {
            this.sslcontext = this.createSSLContext();
        }

        return this.sslcontext;
    }

    public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException, UnknownHostException {
        return this.getSSLContext().getSocketFactory().createSocket(socket, host, port, autoClose);
    }

    public Socket createSocket(String host, int port) throws IOException, UnknownHostException {
        return this.getSSLContext().getSocketFactory().createSocket(host, port);
    }

    public Socket createSocket(String host, int port, InetAddress clientHost, int clientPort) throws IOException, UnknownHostException {
        return this.getSSLContext().getSocketFactory().createSocket(host, port, clientHost, clientPort);
    }

    public Socket createSocket(String host, int port, InetAddress localAddress, int localPort, HttpConnectionParams params) throws IOException, UnknownHostException, ConnectTimeoutException {
        if(params == null) {
            throw new IllegalArgumentException("Parameters may not be null");
        } else {
            int timeout = params.getConnectionTimeout();
            SSLSocketFactory socketfactory = this.getSSLContext().getSocketFactory();
            if(timeout == 0) {
                return socketfactory.createSocket(host, port, localAddress, localPort);
            } else {
                Socket socket = socketfactory.createSocket();
                InetSocketAddress localaddr = new InetSocketAddress(localAddress, localPort);
                InetSocketAddress remoteaddr = new InetSocketAddress(host, port);
                socket.bind(localaddr);
                socket.connect(remoteaddr, timeout);
                return socket;
            }
        }
    }

    private static class TrustAnyTrustManager implements X509TrustManager {
        private TrustAnyTrustManager(TrustAnyTrustManager trustAnyTrustManager) {
        }

        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }
}
