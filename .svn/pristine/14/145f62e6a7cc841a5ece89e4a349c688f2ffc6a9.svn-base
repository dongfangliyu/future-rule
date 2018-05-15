package com.zw.base.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

public class Map2Bean {
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
