package com.zw.base.util;

import net.sf.cglib.beans.BeanMap;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 与Spring中的BeanUtils功能等同
 *
 * @author eleven
 */
public abstract class BeanUtil {



    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof String) {
            if (((String) obj).trim().length() == 0) {
                return true;
            }
        } else if (obj instanceof Collection) {
            if (((Collection) obj).isEmpty()) {
                return true;
            }
        } else if (obj.getClass().isArray()) {
            if (((Object[]) obj).length == 0) {
                return true;
            }
        } else if (obj instanceof Map) {
            if (((Map) obj).isEmpty()) {
                return true;
            }
        }else {
            return false;
        }
        return false;
    }

    public static boolean isNumber(Object obj) {

        if (obj == null) {
            return false;
        }
        if (obj instanceof Number) {
            return true;
        }
        if (obj instanceof String) {
            try {
                Double.parseDouble((String) obj);
                return true;
            } catch (NumberFormatException ex) {
                return false;
            }
        }
        return false;
    }

    public static boolean isInherit(Class currentClass, Class classParent) {
        return classParent.isAssignableFrom(currentClass);
    }

    private static List<Field> getAllDeclareFields(Class<?> cls) {
        List<Field> list = new ArrayList<>();
        Collections.addAll(list, cls.getDeclaredFields());
        return list;
    }


    static List<String> getAllFieldNames(Class<?> cls) {
        List<String> list = new ArrayList<>();
        List<Field> fields = getAllDeclareFields(cls);
        list.addAll(fields.stream().map(Field::getName).collect(Collectors.toList()));
        return list;
    }

    private static Map getBeanMap(Object object) {
        Class<?> beanClass = ClassUtil.getTargetClass(object);
        BeanMap.Generator gen = new BeanMap.Generator();
        gen.setBean(object);
        gen.setBeanClass(beanClass);
        return gen.create();
    }

    public static Map toMap(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Map) {
            return (Map) value;
        }
        if (value instanceof String) {
            String text = (String) value;
            text = text.trim();
            int suffixIndex = text.length() - 1;
            if (suffixIndex > 0 && text.charAt(0) == '{' && text.charAt(suffixIndex) == '}') {
                return JsonUtil.parseMap(text);
            }
        }
        return BeanUtil.getBeanMap(value);
    }

    public static Map<String, Object> transBean2Map(Object obj) {

        if(obj == null){
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();

                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);

                    map.put(key, value);
                }

            }
        } catch (Exception e) {
            System.out.println("transBean2Map Error " + e);
        }
        return map;
    }


    public static <T> T map2Bean(T t,Map map) throws Exception{
        Class clazz = t.getClass();
        //实例化类
        T entity = (T)clazz.newInstance();
        Set<String> keys = map.keySet();
        //变量map 赋值
        for(String key:keys){
            String fieldName = key;
            //判断是sql 还是hql返回的结果
            if(key.equals(key.toUpperCase())){
                //获取所有域变量
                Field[] fields = clazz.getDeclaredFields();
                for(Field field: fields){
                    if(field.getName().toUpperCase().equals(key)) fieldName=field.getName();
                    break;
                }
            }
            //设置赋值
            try {
                if(existsField(clazz,fieldName)){
                    //参数的类型  clazz.getField(fieldName)
                    Class<?> paramClass = clazz.getDeclaredField(fieldName).getType();
                    //拼装set方法名称
                    String methodName = "set"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
                    //根据名称获取方法
                    Method method = clazz.getMethod(methodName, paramClass);
                    //System.out.println(map.get(key).toString()+";"+key+";"+paramClass+";"+methodName);
                    //调用invoke执行赋值
                    method.invoke(entity, map.get(key));
                }
            } catch (Exception e) {
                throw new Exception();
            }
        }

        return entity;
    }

    private static boolean existsField(Class clz,String fieldName){
        try{
            return clz.getDeclaredField(fieldName)!=null;
        }
        catch(Exception e){
        }
        return false;
    }
}
