package com.zw.base.util;

import org.apache.commons.lang.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * 日期相关操作
 * @author gaojy
 *
 */
public class DateUtils {


	private static DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public final static String STYLE_1 = "yyyy-MM-dd HH:mm:ss";

	public final static String STYLE_2 = "yyyy-MM-dd";

	public final static String STYLE_3 = "yyyyMMdd";

	public final static String STYLE_4 = "yyyyMMddhh";

	public final static String STYLE_5 = "yyyyMMddhhmm";

	public final static String STYLE_6 = "yyyy年MM月dd日HH时mm分ss秒";

	public final static String STYLE_7 = "yyyy年MM月dd日HH时mm分";

	public final static String STYLE_8 = "yyyy年MM月dd日";

	public final static String STYLE_9 = "hhmmss";

	private static Map<String, SimpleDateFormat> sdfMap=new HashMap<String, SimpleDateFormat>();

	static{
		sdfMap.put(STYLE_1, new SimpleDateFormat(STYLE_1));
		sdfMap.put(STYLE_2, new SimpleDateFormat(STYLE_2));
		sdfMap.put(STYLE_3, new SimpleDateFormat(STYLE_3));
		sdfMap.put(STYLE_4, new SimpleDateFormat(STYLE_4));
		sdfMap.put(STYLE_5, new SimpleDateFormat(STYLE_5));
		sdfMap.put(STYLE_6, new SimpleDateFormat(STYLE_6));
		sdfMap.put(STYLE_7, new SimpleDateFormat(STYLE_7));
		sdfMap.put(STYLE_8, new SimpleDateFormat(STYLE_8));
		sdfMap.put(STYLE_9, new SimpleDateFormat(STYLE_9));
	}

	public static String getCurrentTime() {
		return sdfMap.get(STYLE_1).format(new Date());
	}
	
	/**
	 * 获取当前时间
	 * 方法描述
	 * @return
	 */
	public static String getNowDate(){
		return sdf.format(new Date());
	}
	
	/**
	 * 字符串类型转化为日期类型
	 * @param str
	 * @return
	 */
	public static Date strConvertToDate(String str){
		if(StringUtils.isNotEmpty(str)){
			try {
				return sdf.parse(str);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 日期类型转化为制定的日期格式
	 * @param date
	 * @return
	 */
	public static Date dateConvertToDate(Date date){
		String str= sdf.format(date);
		try {
			return sdf.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	/**
	 * 比较时间大小
	 * 
	 * @param beginDate 开始时间
	 * @param endDate   结束时间
	 * @return true begin大于end
	 */
	public static boolean datecompare(Date beginDate,Date endDate){
		try{
			if(beginDate==null || endDate==null){
				return false;
			}
			if(beginDate.getTime()>=endDate.getTime()){
				return true;
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 判断number是不是在两者之间
	 * @author wu.yy
	 * 
	 * @param number
	 * @param start
	 * @param end
	 * @return
	 */
	public static boolean during(int number,int start,int end){
		if(number>=start && number<end){
			return true;
		}
		return false;
	}
	/**
	 * 根据不同的类别获取相应的统计格式
	 * @param lb 类别 kk_day 天 kk_month月 kk_jd季度 kk_year年kk_kk 卡口
	 * @param code 类别相应的编码值
	 * @param beginDate 开始时间
	 * @param endDate 结束时间
	 * @return map对象
	 */
	public static Map<String, Object> getTimeByLb(String lb,String code,String beginDate,String endDate){
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isBlank(lb)){
			map.put("code", "to_char(t.pass_time,'yyyy-mm-dd')");
			map.put("beginDate", "trunc(sysdate-7)");
			map.put("endDate", "trunc(sysdate-1)");
		}else if(lb.equalsIgnoreCase("kk_day")){
			map.put("code", "to_char(t.pass_time,'yyyy-mm-dd')");
			if(code.equalsIgnoreCase("0")){
				map.put("beginDate", "trunc(sysdate-7)");
				map.put("endDate", "trunc(sysdate-1)");
			}else if(code.equalsIgnoreCase("1")){
				map.put("beginDate", "trunc(add_months(sysdate,-1))");
				map.put("endDate", "trunc(sysdate-1)");
			}else if(code.equalsIgnoreCase("2")){
				if(StringUtils.isNotBlank(beginDate)){
					map.put("beginDate","to_date('"+beginDate+"','yyyy-mm-dd')");
				}
				if(StringUtils.isNotBlank(endDate)){
					map.put("endDate", "to_date('"+endDate+"','yyyy-mm-dd')");
				}
			}
			return map;
		}else if(lb.equalsIgnoreCase("kk_month")){
			map.put("code", "to_char(t.pass_time,'yyyy-mm')");
			if(code.equalsIgnoreCase("0")){
				map.put("beginDate", "trunc(add_months(sysdate,-3))");
				map.put("endDate", "trunc(sysdate-1)");
			}else if(code.equalsIgnoreCase("1")){
				map.put("beginDate", "trunc(add_months(sysdate,-6))");
				map.put("endDate", "trunc(sysdate-1)");
			}else if(code.equalsIgnoreCase("2")){
				map.put("beginDate", "trunc(add_months(sysdate,-12))");
				map.put("endDate", "trunc(sysdate-1)");
			}else if(code.equalsIgnoreCase("3")){
				if(StringUtils.isNotBlank(beginDate)){
					map.put("beginDate","to_date('"+beginDate+"-01-01','yyyy-mm-dd')");
					map.put("endDate", "to_date('"+beginDate+"-12-31','yyyy-mm-dd')");
				}
			}
			return map;
		}else if(lb.equalsIgnoreCase("kk_jd")){
			map.put("code", "to_char(t.pass_time,'Q')");
			map.put("bswq", beginDate);
			if(code.equalsIgnoreCase("0")){
				if(StringUtils.isNotBlank(beginDate)){
					map.put("beginDate","to_date('"+beginDate+"-01-01','yyyy-mm-dd')");
					map.put("endDate", "to_date('"+beginDate+"-12-31','yyyy-mm-dd')");
				}
			}
			return map;
		}else if(lb.equalsIgnoreCase("kk_year")){
			map.put("code", "to_char(t.pass_time,'yyyy')");
			map.put("bswy", beginDate);
			if(code.equalsIgnoreCase("0")){
				if(StringUtils.isNotBlank(beginDate)){
					map.put("beginDate","to_date('"+beginDate+"-01-01','yyyy-mm-dd')");
				}
				if(StringUtils.isNotBlank(endDate)){
					map.put("endDate", "to_date('"+endDate+"-12-31','yyyy-mm-dd')");
				}
			}
			return map;
		}else if(lb.equalsIgnoreCase("kk_kk")){
			map.put("code", "pass_port_name");
			if(code.equalsIgnoreCase("0")){
				map.put("beginDate", "trunc(sysdate-7)");
				map.put("endDate", "trunc(sysdate-1)");
			}else if(code.equalsIgnoreCase("1")){
				map.put("beginDate", "trunc(add_months(sysdate,-1))");
				map.put("endDate", "trunc(sysdate-1)");
			}else if(code.equalsIgnoreCase("2")){
				map.put("beginDate", "trunc(add_months(sysdate,-3))");
				map.put("endDate", "trunc(sysdate-1)");
			}else if(code.equalsIgnoreCase("3")){
				map.put("beginDate", "trunc(add_months(sysdate,-6))");
				map.put("endDate", "trunc(sysdate-1)");
			}else if(code.equalsIgnoreCase("4")){
				map.put("beginDate", "trunc(add_months(sysdate,-12))");
				map.put("endDate", "trunc(sysdate-1)");
			}else if(code.equalsIgnoreCase("5")){
				if(StringUtils.isNotBlank(beginDate)){
					map.put("beginDate","to_date('"+beginDate+"-01-01','yyyy-mm-dd')");
				}
				if(StringUtils.isNotBlank(endDate)){
					map.put("endDate", "to_date('"+endDate+"-12-31','yyyy-mm-dd')");
				}
			}else if(code.equalsIgnoreCase("6")){
				map.put("beginDate","to_date('"+beginDate+"-"+((Integer.parseInt(endDate)-1)*3+1)+"-01','yyyy-mm-dd')");
				map.put("endDate", "to_date('"+beginDate+"-"+(Integer.parseInt(endDate)*3)+"-"+getDayByMonth(String.valueOf(Integer.parseInt(endDate)*3))+"','yyyy-mm-dd')");
			}else if(code.equalsIgnoreCase("7")){
				map.put("beginDate","to_date('"+beginDate+"-"+endDate+"-01','yyyy-mm-dd')");
				map.put("endDate", "to_date('"+beginDate+"-"+endDate+"-"+getDayByMonth(endDate)+"','yyyy-mm-dd')");
			}else if(code.equalsIgnoreCase("8")){
				if(StringUtils.isNotBlank(beginDate)){
					map.put("beginDate","to_date('"+beginDate+"','yyyy-mm-dd')");
				}
				if(StringUtils.isNotBlank(endDate)){
					map.put("endDate", "to_date('"+endDate+"','yyyy-mm-dd')");
				}
			}
			return map;
		}
		return map;
	}
	/**
	 * 根据当前月份获取该月最后一天日期
	 * @param month 月份
	 * @return 该月最后一天日期
	 */
	private static String getDayByMonth(String month){
		if(month.equalsIgnoreCase("2")){
			return "28";
		}else if(month.equalsIgnoreCase("1") || month.equalsIgnoreCase("3") || month.equalsIgnoreCase("5") ||month.equalsIgnoreCase("7") || month.equalsIgnoreCase("8") || month.equalsIgnoreCase("10") || month.equalsIgnoreCase("12")){
			return "31";
		}else{
			return "30";
		}
	}
	
	/**
	 * 获取当前日期前一天
	 * @return date
	 */
	public static Date getLastDay(){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);    //得到前一天
		Date date = calendar.getTime();
		return date;
	} 
	
	/**
	 * 获取当前日期前两天
	 * @return date
	 */
	public static Date getLastTwoDay(){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -2);    //得到前一天
		Date date = calendar.getTime();
		return date;
	} 
	
	/**
	 * 格式化日期
	 * @param format
	 * @return
	 */
	public static String formatDate(String format){
		SimpleDateFormat sFormat = new SimpleDateFormat(format);
		return sFormat.format(new Date());
	}
	
	/**
	 * 格式化日期
	 * @param format
	 * @return
	 */
	public static String formatDate(Date date,String format){
		SimpleDateFormat sFormat = new SimpleDateFormat(format);
		return sFormat.format(date);
	}
	/**
	 * 时间格式化为：yyyyMMddHHmmss
	 * @param date
	 * @return 返回已格式化的字符串
	 */
	public static String getDateString(Date date){
		SimpleDateFormat s = new SimpleDateFormat("yyyyMMddHHmmss");
		return s.format(date);
	}
	/**
	 * 判断验证码是否失效
	 * @param date1 ： 数据库中的时间
	 * @param code：数据字典中的子健code
	 * @return false 失效  true 未失效
	 * @throws HsException 
	 */
	public static Boolean isDateBig(String date1,String errortime) throws Exception{
		SimpleDateFormat s = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = s.parse(date1);//将已格式化的时间字符串转成时间格式
		long dataBaseTame = date.getTime();//将具体时间转成毫秒数
		//将修改时间加上数据字典中配置的参数
		long secon =  Integer.parseInt(errortime)*1000 + dataBaseTame;
		Date nowDate = new Date();//获取当前时间
		long nowDateTime = nowDate.getTime();//将具体时间转成毫秒数
		if(secon < nowDateTime){ //判断验证码是否已失效
			return false;
		}
		return true;
	}
	/**
	 * 将yyyy年MM月dd天格式的时间字符串转为yyyymmdd格式
	 * @param date
	 * @return 返回已格式化的字符串
	 * @throws HsException 
	 */
	public static String changeDate(String str) throws Exception{
		SimpleDateFormat s = new SimpleDateFormat("yyyy年MM月dd日");
		Date date = s.parse(str);
		return getDateString(date);
	}
	/**
	 * 将yyyy年MM月dd天格式的时间字符串转为yyyymmdd格式
	 * @param date
	 * @return 返回已格式化的字符串
	 * @throws HsException 
	 */
	public static String changeDates(String str) throws Exception{
		SimpleDateFormat s = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat s1 = new SimpleDateFormat("yyyy年MM月dd日 ");
		str = str.substring(0, 8);
		Date date = s.parse(str);
		return s1.format(date);
	}
	/**
	 * 判断用户输入密码错误不能登录时间是否已过
	 * @param date1 ： 数据库中用户密码输入错误时间
	 * @param code：数据字典中的子健code
	 * @return false 锁定时间内 true 锁定时间外
	 * @throws HsException 
	 */
	public static Boolean isErrorTime(String date1,String code,String limtime) throws Exception{
		SimpleDateFormat s = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = s.parse(date1);//将已格式化的时间字符串转成时间格式
			long dataBaseTame = date.getTime();//将具体时间转成毫秒数
			//将修改时间加上数据字典中配置的参数
			long secon =  Integer.parseInt(limtime)*1000 + dataBaseTame;
			Date nowDate = new Date();//获取当前时间
			long nowDateTime = nowDate.getTime();//将具体时间转成毫秒数
			if(secon > nowDateTime){ //判断用户是否在锁定时间内
				return false;
			}
			return true;
	}
	
}
