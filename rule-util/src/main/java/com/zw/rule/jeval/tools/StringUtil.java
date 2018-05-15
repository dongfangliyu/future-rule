package com.zw.rule.jeval.tools;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang.StringUtils;

public class StringUtil {
    public StringUtil() {
    }

    public static boolean isValidStr(String str) {
        return str != null && str.trim().length() > 0;
    }

    public static String convertStrIfNull(String str) {
        return str == null?"":str;
    }

    public static boolean getStrToBoolean(String str) {
        return isValidStr(str)?str.toLowerCase().trim().equals("true"):false;
    }

    public static int getStrToInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException var2) {
            return 0;
        }
    }

    public static int getStrToInt(String str, int defaultValue) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException var3) {
            return defaultValue;
        }
    }

    public static long getStrTolong(String str) {
        long result = 0L;
        if(!isValidStr(str)) {
            return result;
        } else {
            try {
                result = Long.parseLong(str);
            } catch (NumberFormatException var4) {
                var4.printStackTrace();
            }

            return result;
        }
    }

    public static double getStrTodouble(String str) {
        double result = 0.0D;
        if(!isValidStr(str)) {
            return result;
        } else {
            try {
                result = Double.parseDouble(str);
            } catch (NumberFormatException var4) {
                var4.printStackTrace();
            }

            return result;
        }
    }

    public static BigDecimal getStrToBigDecimal(String str) {
        BigDecimal result = new BigDecimal(0);
        if(!isValidStr(str)) {
            return result;
        } else {
            try {
                result = new BigDecimal(str);
            } catch (NumberFormatException var3) {
                var3.printStackTrace();
            }

            return result;
        }
    }

    public static Integer getStrToInteger(String str) {
        Integer result = new Integer(0);
        if(!isValidStr(str)) {
            return result;
        } else {
            try {
                result = Integer.valueOf(str);
            } catch (NumberFormatException var3) {
                var3.printStackTrace();
            }

            return result;
        }
    }

    public static Long getStrToLong(String str) {
        Long result = new Long(0L);
        if(!isValidStr(str)) {
            return result;
        } else {
            try {
                result = Long.valueOf(str.trim());
            } catch (NumberFormatException var3) {
                var3.printStackTrace();
            }

            return result;
        }
    }

    public static Double getStrToDouble(String str) {
        Double result = new Double(0.0D);
        if(!isValidStr(str)) {
            return result;
        } else {
            try {
                result = Double.valueOf(str);
            } catch (NumberFormatException var3) {
                var3.printStackTrace();
            }

            return result;
        }
    }

    public static String getArrToStr(Object[] obj) {
        if(obj == null) {
            return null;
        } else {
            StringBuffer buffer = new StringBuffer();
            if(obj.length > 0) {
                buffer.append(obj[0]);
            }

            for(int m = 1; m < obj.length; ++m) {
                buffer.append(",").append(obj[m]);
            }

            return buffer.toString();
        }
    }

    public static String removeEqualStr(String metadata, String tagStr) {
        if(!isValidStr(metadata)) {
            return "";
        } else {
            HashSet set = new HashSet();
            String[] arr = metadata.split(tagStr);
            String[] var7 = arr;
            int var6 = arr.length;

            for(int returnMetadata = 0; returnMetadata < var6; ++returnMetadata) {
                String it = var7[returnMetadata];
                if(isValidStr(it)) {
                    set.add(it);
                }
            }

            Iterator var8 = set.iterator();
            StringBuffer var9 = new StringBuffer();

            while(var8.hasNext()) {
                var9.append((String)var8.next() + tagStr);
            }

            return var9.toString().substring(0, var9.length() - 1);
        }
    }

    public static boolean hasEqualStr(String strArr, String str, String tagStr) {
        boolean bool = false;
        if(isValidStr(strArr)) {
            String[] arr = strArr.split(tagStr);
            String[] var8 = arr;
            int var7 = arr.length;

            for(int var6 = 0; var6 < var7; ++var6) {
                String temp = var8[var6];
                if(temp.equals(str)) {
                    bool = true;
                    break;
                }
            }
        }

        return bool;
    }

    public static String toUtf8String(String str) {
        StringBuffer sb = new StringBuffer();

        for(int i = 0; i < str.length(); ++i) {
            char c = str.charAt(i);
            if(c >= 0 && c <= 255) {
                sb.append(c);
            } else {
                byte[] b;
                try {
                    b = Character.toString(c).getBytes("utf-8");
                } catch (Exception var7) {
                    b = new byte[0];
                }

                for(int j = 0; j < b.length; ++j) {
                    int k = b[j];
                    if(k < 0) {
                        k += 256;
                    }

                    sb.append("%" + Integer.toHexString(k).toUpperCase());
                }
            }
        }

        return sb.toString();
    }

    public static String formatIntegrity(String formatString, int updateIndex, char updateValue) {
        if(!isValidStr(formatString)) {
            return formatString;
        } else if(updateIndex < 1) {
            return formatString;
        } else if(updateIndex > formatString.length()) {
            return formatString;
        } else {
            char[] formatStringChar = formatString.toCharArray();
            formatStringChar[updateIndex] = updateValue;
            return String.valueOf(formatStringChar);
        }
    }

    public static String converSpecialChar(String str) {
        if(!isValidStr(str)) {
            return str;
        } else {
            str = str.trim();
            if(str.indexOf("\\") >= 0) {
                str = str.replaceAll("\\\\", "\\\\\\\\\\\\\\\\");
            }

            if(str.indexOf("\'") >= 0) {
                str = str.replaceAll("\'", "\\\\\'");
            }

            if(str.indexOf("\"") >= 0) {
                str = str.replaceAll("\"", "\\\\\"");
            }

            if(str.indexOf("%") >= 0) {
                str = str.replaceAll("%", "\\\\%");
            }

            return str;
        }
    }

    public static int getLength(String str) {
        return str.replaceAll("[一-龥　-〿\uff00-\uffef]", "rr").length();
    }

    public static String[] sortArrays(String[] arrays) {
        for(int i = 0; i < arrays.length - 1; ++i) {
            String temp = "";

            for(int j = 0; j < arrays.length - i - 1; ++j) {
                if(getStrTolong(arrays[j]) > getStrTolong(arrays[j + 1])) {
                    temp = arrays[j + 1];
                    arrays[j + 1] = arrays[j];
                    arrays[j] = temp;
                }
            }
        }

        return arrays;
    }

    public static String[] sortArraystoBigDecimal(String[] arrays) {
        for(int i = 0; i < arrays.length - 1; ++i) {
            String temp = "";

            for(int j = 0; j < arrays.length - i - 1; ++j) {
                if(getStrToBigDecimal(arrays[j]).compareTo(getStrToBigDecimal(arrays[j + 1])) == 1) {
                    temp = arrays[j + 1];
                    arrays[j + 1] = arrays[j];
                    arrays[j] = temp;
                }
            }
        }

        return arrays;
    }

    public static boolean isBlank(String str) {
        return str == null || str.equals("");
    }

    public static List<Long> toLongList(String str) {
        ArrayList idList = new ArrayList();
        if(!isBlank(str)) {
            String[] idsArray = str.split(",");

            for(int i = 0; i < idsArray.length; i++) {
                idList.add(Long.valueOf(Long.parseLong(idsArray[i])));
            }
        }

        return idList;
    }

    public static String listToString(List list, char separator) {
        return StringUtils.join(list.toArray(), separator);
    }

    public static void main(String[] args) {
        String[] var10000 = new String[]{"How", "d$o", "you", "do"};
        String[] strArray = new String[]{"5.36", "5.003", "1.36", "9.87", "3.33333379"};
        strArray = sortArraystoBigDecimal(strArray);
        String str = getArrToStr(strArray);
        System.out.println(str);
    }
}

