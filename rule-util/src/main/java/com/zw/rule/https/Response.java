package com.zw.rule.https;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.HttpURLConnection;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

/**
 * Created by Administrator on 2017/5/15.
 */
public class Response {
    private static final boolean DEBUG = true;
    static Logger log = Logger.getLogger(Response.class.getName());
    private static ThreadLocal<DocumentBuilder> builders = new ThreadLocal() {
        protected DocumentBuilder initialValue() {
            try {
                return DocumentBuilderFactory.newInstance().newDocumentBuilder();
            } catch (ParserConfigurationException var2) {
                throw new ExceptionInInitializerError(var2);
            }
        }
    };
    private int statusCode;
    private Document responseAsDocument = null;
    private String responseAsString = null;
    private InputStream is;
    private HttpURLConnection con;
    private boolean streamConsumed = false;
    private static Pattern escaped = Pattern.compile("&#([0-9]{3,5});");

    public Response() {
    }

    public Response(HttpURLConnection con) throws IOException {
        this.con = con;
        this.statusCode = con.getResponseCode();
        if((this.is = con.getErrorStream()) == null) {
            this.is = con.getInputStream();
        }

        if(this.is != null && "gzip".equals(con.getContentEncoding())) {
            this.is = new GZIPInputStream(this.is);
        }

    }

    Response(String content) {
        this.responseAsString = content;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public String getResponseHeader(String name) {
        return this.con != null?this.con.getHeaderField(name):null;
    }

    public InputStream asStream() {
        if(this.streamConsumed) {
            throw new IllegalStateException("Stream has already been consumed.");
        } else {
            return this.is;
        }
    }

    public String asString() throws HttpsException {
        if(this.responseAsString == null) {
            try {
                InputStream ioe = this.asStream();
                if(ioe == null) {
                    return null;
                }

                BufferedReader br = new BufferedReader(new InputStreamReader(ioe, "UTF-8"));
                StringBuffer buf = new StringBuffer();

                String line;
                while((line = br.readLine()) != null) {
                    buf.append(line).append("\n");
                }

                this.responseAsString = buf.toString();
                this.log(this.responseAsString);
                ioe.close();
                this.con.disconnect();
                this.streamConsumed = true;
            } catch (NullPointerException var5) {
                throw new HttpsException(var5.getMessage(), var5);
            } catch (IOException var6) {
                throw new HttpsException(var6.getMessage(), var6);
            }
        }

        return this.responseAsString;
    }

    public Document asDocument() throws HttpsException {
        if(this.responseAsDocument == null) {
            try {
                this.responseAsDocument = ((DocumentBuilder)builders.get()).parse(new ByteArrayInputStream(this.asString().getBytes("UTF-8")));
            } catch (SAXException var2) {
                throw new HttpsException("The response body was not well-formed:\n" + this.responseAsString, var2);
            } catch (IOException var3) {
                throw new HttpsException("There\'s something with the connection.", var3);
            }
        }

        return this.responseAsDocument;
    }

    public InputStreamReader asReader() {
        try {
            return new InputStreamReader(this.is, "UTF-8");
        } catch (UnsupportedEncodingException var2) {
            return new InputStreamReader(this.is);
        }
    }

    public void disconnect() {
        this.con.disconnect();
    }

    public static String unescape(String original) {
        Matcher mm = escaped.matcher(original);
        StringBuffer unescaped = new StringBuffer();

        while(mm.find()) {
            mm.appendReplacement(unescaped, Character.toString((char)Integer.parseInt(mm.group(1), 10)));
        }

        mm.appendTail(unescaped);
        return unescaped.toString();
    }

    public String toString() {
        return this.responseAsString != null?this.responseAsString:"Response{statusCode=" + this.statusCode + ", response=" + this.responseAsDocument + ", responseString=\'" + this.responseAsString + '\'' + ", is=" + this.is + ", con=" + this.con + '}';
    }

    private void log(String message) {
        log.debug("[" + new Date() + "]" + message);
    }

    private void log(String message, String message2) {
        this.log(message + message2);
    }

    public String getResponseAsString() {
        return this.responseAsString;
    }

    public void setResponseAsString(String responseAsString) {
        this.responseAsString = responseAsString;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
