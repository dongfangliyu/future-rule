package com.zw.rule.https;

import java.io.File;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2017/5/15.
 */
public class PostParameter implements Serializable {
    String name;
    String value;
    private File file = null;
    private static final long serialVersionUID = -8708108746980739212L;
    private static final String JPEG = "image/jpeg";
    private static final String GIF = "image/gif";
    private static final String PNG = "image/png";
    private static final String OCTET = "application/octet-stream";

    public PostParameter(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public PostParameter(String name, double value) {
        this.name = name;
        this.value = String.valueOf(value);
    }

    public PostParameter(String name, int value) {
        this.name = name;
        this.value = String.valueOf(value);
    }

    public PostParameter(String name, File file) {
        this.name = name;
        this.file = file;
    }

    public String getName() {
        return this.name;
    }

    public String getValue() {
        return this.value;
    }

    public File getFile() {
        return this.file;
    }

    public boolean isFile() {
        return this.file != null;
    }

    public String getContentType() {
        if(!this.isFile()) {
            throw new IllegalStateException("not a file");
        } else {
            String extensions = this.file.getName();
            int index = extensions.lastIndexOf(".");
            String contentType;
            if(-1 == index) {
                contentType = "application/octet-stream";
            } else {
                extensions = extensions.substring(extensions.lastIndexOf(".") + 1).toLowerCase();
                if(extensions.length() == 3) {
                    if("gif".equals(extensions)) {
                        contentType = "image/gif";
                    } else if("png".equals(extensions)) {
                        contentType = "image/png";
                    } else if("jpg".equals(extensions)) {
                        contentType = "image/jpeg";
                    } else {
                        contentType = "application/octet-stream";
                    }
                } else if(extensions.length() == 4) {
                    if("jpeg".equals(extensions)) {
                        contentType = "image/jpeg";
                    } else {
                        contentType = "application/octet-stream";
                    }
                } else {
                    contentType = "application/octet-stream";
                }
            }

            return contentType;
        }
    }

    public static boolean containsFile(PostParameter[] params) {
        boolean containsFile = false;
        if(params == null) {
            return false;
        } else {
            PostParameter[] var5 = params;
            int var4 = params.length;

            for(int var3 = 0; var3 < var4; ++var3) {
                PostParameter param = var5[var3];
                if(param.isFile()) {
                    containsFile = true;
                    break;
                }
            }

            return containsFile;
        }
    }

    static boolean containsFile(List<PostParameter> params) {
        boolean containsFile = false;
        Iterator var3 = params.iterator();

        while(var3.hasNext()) {
            PostParameter param = (PostParameter)var3.next();
            if(param.isFile()) {
                containsFile = true;
                break;
            }
        }

        return containsFile;
    }

    public static PostParameter[] getParameterArray(String name, String value) {
        return new PostParameter[]{new PostParameter(name, value)};
    }

    public static PostParameter[] getParameterArray(String name, int value) {
        return getParameterArray(name, String.valueOf(value));
    }

    public static PostParameter[] getParameterArray(String name1, String value1, String name2, String value2) {
        return new PostParameter[]{new PostParameter(name1, value1), new PostParameter(name2, value2)};
    }

    public static PostParameter[] getParameterArray(String name1, int value1, String name2, int value2) {
        return getParameterArray(name1, String.valueOf(value1), name2, String.valueOf(value2));
    }

    public int hashCode() {
        int result = this.name.hashCode();
        result = 31 * result + this.value.hashCode();
        result = 31 * result + (this.file != null?this.file.hashCode():0);
        return result;
    }

    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        } else if(this == obj) {
            return true;
        } else if(!(obj instanceof PostParameter)) {
            return false;
        } else {
            PostParameter that = (PostParameter)obj;
            if(this.file != null) {
                if(this.file.equals(that.file)) {
                    return this.name.equals(that.name) && this.value.equals(that.value);
                }
            } else if(that.file == null) {
                return this.name.equals(that.name) && this.value.equals(that.value);
            }

            return false;
        }
    }

    public String toString() {
        return "PostParameter{name=\'" + this.name + '\'' + ", value=\'" + this.value + '\'' + ", file=" + this.file + '}';
    }

    public int compareTo(Object o) {
        PostParameter that = (PostParameter)o;
        int compared = this.name.compareTo(that.name);
        if(compared == 0) {
            compared = this.value.compareTo(that.value);
        }

        return compared;
    }

    public static String encodeParameters(PostParameter[] httpParams) {
        if(httpParams == null) {
            return "";
        } else {
            StringBuffer buf = new StringBuffer();

            for(int j = 0; j < httpParams.length; ++j) {
                if(httpParams[j].isFile()) {
                    throw new IllegalArgumentException("parameter [" + httpParams[j].name + "]should be text");
                }

                if(j != 0) {
                    buf.append("&");
                }

                try {
                    buf.append(URLEncoder.encode(httpParams[j].name, "UTF-8")).append("=").append(URLEncoder.encode(httpParams[j].value, "UTF-8"));
                } catch (UnsupportedEncodingException var4) {
                    ;
                }
            }

            return buf.toString();
        }
    }
}
