package org.jcl.life.time;

import org.jcl.life.string.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.*;

final public class TimeUtils {
	private static Logger logger =
			LoggerFactory.getLogger(TimeUtils.class);
	public final static String defaultOneDateFmt = "yyyy/MM/dd";
	public final static String defaultOneDateFmtTime = "yyy/MM/dd HH:mm:ss";
	public static final String[] zodiacArr = { "猴", "鸡", "狗", "猪", "鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊" };
	public static final String[] constellationArr = { "水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "魔羯座" };
	public static final int[] constellationEdgeDay = { 20, 19, 21, 21, 21, 22, 23, 23, 23, 23, 22, 22 };
	/**
	 * 根据format取得今天的日期
	 * @param format
	 * @return
	 */
	public static String getTodayTime(final String format) {
		logger.debug("getTodayTime  begin");
		logger.info("getTodayTime  begin");
		SimpleDateFormat sdf =
				new SimpleDateFormat(StringUtils.isEmpty(format) ? defaultOneDateFmt : format);
		logger.info("sdf=[{}]",sdf);
		return sdf.format(new Date());
	}
	/**
	 * 根据format取得今天的日期及时间
	 * @return
	 */
	public static String getTodayTimeDate() {
		SimpleDateFormat sdf = new SimpleDateFormat(defaultOneDateFmtTime);
		return sdf.format(new Date());
	}

	/**
	 * 取得今天的日期
	 * @return
	 */
	public static String getTodayTime() {
		SimpleDateFormat sdf = new SimpleDateFormat(defaultOneDateFmt);
		return sdf.format(new Date());
	}
	/**
	 * 根据日期(年份)获取生肖
	 * @param date
	 * @return
	 */
	public static String getZodica(Date date) {
		date = StringUtils.isEmpty(date) ? new Date() : date;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
//		System.out.println("year:"+cal.get(Calendar.YEAR));
//		System.out.println("yearyueshu:"+cal.get(Calendar.YEAR) % 12);
		return zodiacArr[cal.get(Calendar.YEAR) % 12];
	}

	/**
	 * 根据日期获取星座
	 * @return
	 */
	public static String getConstellation(Date date) {
		date = StringUtils.isEmpty(date) ? new Date() : date;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		if (day < constellationEdgeDay[month]) {
			month = month - 1;
		}
		if (month >= 0) {
			return constellationArr[month];
		}
		// default to return 魔羯
		return constellationArr[11];
	}

	/**
	 * 获取某年某月有多少天
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getMonthWithDay(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, 1);
		int day = calendar.getActualMaximum(5);
		return day;
	}
	/**
	 * 获取某年某月集合
	 * @param date
	 * @return
	 */
	public static List<Integer> getYearAndMonth(Date date) {
		date = StringUtils.isEmpty(date) ? new Date() : date;
		List<Integer> list = new ArrayList<Integer>();
		Calendar cale = null;
		cale = Calendar.getInstance();
		cale.setTime(date);
		int year = cale.get(Calendar.YEAR);
		int month = cale.get(Calendar.MONTH )+1;
		list.add(year);
		list.add(month);
		return list;
	}
	/**
	 *
	 * 获取实时天气1
	 * 方 法 名： getTodayWeather1
	 *
	 * @param CityName  城市名
	 *注：参数字符串，如果拼接在请求链接之后，需要对中文进行 URLEncode   字符集 UTF-8，转化方式在下面，直接传过去就好了
	 */
	/*
	public static Map<String, Object> getTodayWeather1(String CityName)
			throws IOException, NullPointerException {
		// 连接和风天气的API
		String url1= "https://free-api.heweather.net/s6/weather/now?location="+CityName+"&key=3c3fa198cacc4152b94b20def11b2455";

		URL url = new URL(url1);
		URLConnection connectionData = url.openConnection();
		connectionData.setConnectTimeout(1000);
		Map<String, Object> map = new HashMap<String, Object>();
		BufferedReader br = null;
		try {
					br = new BufferedReader(new InputStreamReader(
					connectionData.getInputStream(), "UTF-8"));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = br.readLine()) != null)
				sb.append(line);
			String datas = sb.toString();
			//截取[]转化为json格式
			datas = datas.replace(datas.substring(datas.indexOf(":")+1,datas.indexOf(":")+2),"");
			datas = datas.replace(datas.substring(datas.length()-2,datas.length()-1),"");
			JSONObject jsonData = JSONObject.fromObject(datas);
			JSONObject info = jsonData.getJSONObject("HeWeather6");
			JSONObject jsonData1 = JSONObject.fromObject(info.getString("basic").toString());
			//JSONObject jsonData2 = JSONObject.fromObject(info.getString("update").toString());
			JSONObject jsonData3 = JSONObject.fromObject(info.getString("now").toString());
			map.put("location",jsonData1.getString("location").toString());
			map.put("parent_city",jsonData1.getString("parent_city").toString());
			map.put("admin_area",jsonData1.getString("admin_area").toString());
			map.put("cnty",jsonData1.getString("cnty").toString());
			map.put("date_time",getTodayTimeDate());
			map.put("wind_dir",jsonData3.getString("wind_dir").toString());
			map.put("cond_txt",jsonData3.getString("cond_txt").toString());
			map.put("cond_code",jsonData3.getString("cond_code").toString());
		} catch (SocketTimeoutException e) {
			System.out.println("连接超时");
		} catch (FileNotFoundException e) {
			System.out.println("加载文件出错");
		} finally {
			//关闭流
			try {
				if(br!=null){
					br.close();
				}

			} catch ( Exception  e) {
				e.printStackTrace();
			}
		}

		return map;
	}

	/**
	 *
	 * 获取实时天气2<br>
	 * 方 法 名： getTodayWeather <br>
	 *
	 * @param Cityid
	 *            城市编码
	 */
	/*
	public static Map<String, Object> getTodayWeather2(String Cityid)
			throws IOException, NullPointerException {
		// 连接中央气象台的API
		URL url = new URL("http://www.weather.com.cn/data/cityinfo/" + Cityid
				+ ".html");
		URLConnection connectionData = url.openConnection();
		connectionData.setConnectTimeout(1000);
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					connectionData.getInputStream(), "UTF-8"));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = br.readLine()) != null)
				sb.append(line);
			String datas = sb.toString();
			System.out.println(datas);
			JSONObject jsonData = JSONObject.fromObject(datas);
			JSONObject info = jsonData.getJSONObject("weatherinfo");
			map.put("city", info.getString("city").toString());// 城市
			map.put("temp1", info.getString("temp1").toString());// 最高温度
			map.put("temp2", info.getString("temp2").toString());// 最低温度
			map.put("weather", info.getString("weather").toString());//天气
			map.put("ptime", info.getString("ptime").toString());// 发布时间

		} catch (SocketTimeoutException e) {
			System.out.println("连接超时");
		} catch (FileNotFoundException e) {
			System.out.println("加载文件出错");
		}
		return map;
	}*/

}
