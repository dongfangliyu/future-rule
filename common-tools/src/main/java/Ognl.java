import com.zw.base.util.BeanUtil;

public class Ognl {

    public static boolean isEmpty(Object o) throws IllegalArgumentException {
        return BeanUtil.isEmpty(o);
    }

    public static boolean isNotEmpty(Object o) {
        return !isEmpty(o);
    }


    public static boolean isNotEmpty(Long o) {
        return !isEmpty(o);
    }


    public static boolean isNumber(Object o) {
        return BeanUtil.isNumber(o);
    }

}
