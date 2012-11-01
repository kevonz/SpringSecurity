package com.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import com.utils.ListMap;

/**
 *工具类。
 */
public class Util {

	private static long pkId; // 唯一Long型主码

	/**
	 * 由于业务需要，可能你会把 char（20）类型的字段映射为id，id由assigned产生
	 * (这种ID产生方法不推荐)，当你load一条记录的时候，若Key不到20位，
	 * 你必须把key补满20位（后补空），否则load或get时将找不到相应的纪录。 以上针对Oracle,其他数据库未作过测试！
	 * 
	 * @author sparta 10/5/20
	 * @param key
	 * @param dbSize
	 * @return
	 */
	public static String decorateKey(String key, int dbSize) {
		String keyWithoutBlank = key.trim();
		String keyOf20Length = null;
		if (keyWithoutBlank.length() < dbSize) { // char(20) 不满补空格，否则无法找到数据
			// assigned not recommended
			keyOf20Length = keyWithoutBlank
					+ getBlanks(dbSize - keyWithoutBlank.length());
		} else {
			keyOf20Length = keyWithoutBlank;
		}
		return keyOf20Length;
	}

	public static String getBlanks(int size) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < size; i++) {
			sb.append(" ");
		}
		return sb.toString();
	}

	/**
	 * 得到树型新节点的id(适用于节点拖放后及新增加后的新节点ID生成)
	 * 
	 * 比如， 0001_010_003，返回的新值为 0001_010_004。 又如， 0001_010_009,返回的新值为
	 * 0001_010_010。 (本id生成策略仅能够满足最高父节点最大值为9999，各子节点最大值为999;
	 * 目前暂不能实现拖动子节点成为最高父节点)
	 * 
	 * @author sparta 2010/5
	 */
	public static String getMaxNode(String id) {
		String oldId = id;
		id = id.substring(id.lastIndexOf("_") + 1, id.length());

		id = oldId.substring(0, oldId.length() - id.length()) + getIdNumber(id);

		return id;
	}

	/**
	 * 节点值加1 进入001，出来002
	 * 
	 * @author sparta 2010/5
	 */
	public static String getIdNumber(String id) {
		// 取最后的值加1
		String str = (Integer.parseInt(id) + 1) + "";
		// 补齐3位， 若不足三位用0补齐(未考虑id为999的情况，因为根据目前的需求，
		// 子节点id的个数是不会超过999的)
		switch (str.length()) {
		case 1:
			str = "00" + str;
			break;
		case 2:
			str = "0" + str;
			break;
		}

		return str;

	}

	/**
	 * 当进入0001，出来0002
	 * 
	 * @param id
	 * @author sparta 2010/5
	 */
	public static String getIncrementStr(String id) {
		// 取最后的值加1
		String str = (Integer.parseInt(id) + 1) + "";
		// 前边补齐3位， 若不足三位用0补齐(未考虑id为999的情况，因为根据目前的需求，
		// 子节点id的个数是不会超过999的)
		switch (str.length()) {
		case 1:
			str = "000" + str;
			break;
		case 2:
			str = "00" + str;
			break;
		case 3:
			str = "0" + str;
			break;
		}

		return str;
	}

	/**
	 * 判断输入的整数是否是奇偶数
	 * 
	 * @param int
	 * @return boolean true为偶数，false为奇数
	 * @author sparta 2010/5
	 */
	public static boolean isEven(int num) {
		if (num % 2 == 0)
			return true;
		else
			return false;
	}

	/**
	 * 返回子节点串的父节点串。 如子节点串为0000_001，则返回父节点串为0000。 系统默认超级父节点为1，默认超级子节点为0，因此，
	 * 若子节点串为0000，则返回父节点串为0，若子节点串为0，则返回父节点串为1
	 * 
	 * @see #avatar
	 * @param 子节点串
	 * @author sparta 2010/5
	 */
	public static String getParentId(String childId) {
		if (childId.length() == 4)
			return "0";
		if (childId.indexOf("000") == 0)
			return childId.substring(0, childId.length() - 4);
		else if (childId.equals("0"))
			return "1";

		return "0";

	}

	/**
	 * 根据新id长度取得单位级别 仅限于单位id为"10011001.."的形式(目前本系统树型列表均使用的该种id形式,4位为一个级别)。
	 * 
	 * @author sparta 2010/06
	 */
	public static int getDwjb(int len) {
		if (len < 4)
			return 1;
		return len / 4;
	}

	/**
	 * 得到数值型的年月日。 返回 20100705
	 * 
	 * @author sparta 10/07/05
	 */
	public static int getYYMMDD() {
		Calendar cl = Calendar.getInstance();
		String y = cl.get(Calendar.YEAR) + "";
		String m = ((cl.get(Calendar.MONTH) + 1) < 10) ? "0"
				+ (cl.get(Calendar.MONTH) + 1) : (cl.get(Calendar.MONTH) + 1)
				+ "";
		String d = (cl.get(Calendar.DAY_OF_MONTH) < 10) ? "0"
				+ cl.get(Calendar.DAY_OF_MONTH) : cl.get(Calendar.DAY_OF_MONTH)
				+ "";
		return Integer.parseInt(y + m + d);
	}

	/**
	 * 返回节点+1的值 比如： 输入10011001，返回10011002。
	 * 输入：100110011001100110011001100110011001100110011001
	 * 返回：100110011001100110011001100110011001100110011002
	 * 
	 * @author sparta 2010/6
	 */
	public static String getNewNodeId(String id) {
		BigInteger bi = new BigInteger(id);
		bi = bi.add(new BigInteger("1"));
		return bi + "";
	}

	/**
	 * 利用java反射技术实现记录各对象相关的参数
	 * 
	 * @author sparta 10/07/22 9:25
	 * @param obj
	 *            , logger, optType: 运行时对象, 日志组件对象, 操作类型(增删改查)
	 * @return void
	 */
	public static void log4Drgs(Object obj, Logger logger, String optType)
			throws SecurityException, NoSuchMethodException,
			IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {

		// 得到运行时对象
		Class clazz = obj.getClass();

		// 得到运行时对象的所有方法
		Method[] methods = clazz.getMethods();
		String methodName = "";

		// 将以get开头的方法得到的参数进行记录
		for (Method method : methods) {
			methodName = ((Member) method).getName();

			// 过滤掉除以get开头的方法，并且过滤掉getClass方法。
			if (methodName.indexOf("get", 0) < 0
					|| methodName.indexOf("getCla", 0) >= 0)
				continue;

			// 提取方法以备执行
			Method getMethod = clazz.getMethod(methodName, null);

			// 日志记录(将执行后方法的值记录下来)
			logger.info("::::::::::" + methodName + "="
					+ getMethod.invoke(obj, null) + "::::::::::");
		}

	}

	/**
	 *将以逗号分隔的字串解析成为数组 如："Str1,Str2,Str3"字串， 解析为 arrStr[0]= str1,arrStr[1]=
	 * str2,arrStr[2]= str3。
	 * 
	 * @author sparta 2010-7-23 上午08:16:20
	 *@param str
	 *@return String[]
	 */
	public static String[] getArr(String str) {
		String[] strArray = { "" };

		// 若字串未包含逗号,则直接返回本字串
		if (str.indexOf(",") < 0) {
			strArray[0] = str;
			return strArray;
		}

		// 解析
		StringTokenizer st = new StringTokenizer(str, ",");
		strArray = new String[st.countTokens()];
		int strLeng = st.countTokens();

		for (int i = 0; i < strLeng; i++) {
			strArray[i] = st.nextToken();
		}

		// 返回
		return strArray;

	}

	/**
	 * 将未排序的HashMap转化为排序的ListMap 升序
	 * 
	 * @param inMap
	 * @return
	 */
	public static ListMap HashMapToListMap(HashMap inMap) {
		ListMap outMap = new ListMap();
		if (inMap == null)
			return outMap;

		Iterator iterator = inMap.keySet().iterator();
		String[] key = new String[inMap.size()];
		int p = 0;
		while (iterator.hasNext()) {
			String keys = (String) iterator.next();
			key[p] = keys;
			p++;
		}
		for (int i = 0; i < key.length; i++) {
			for (int j = i + 1; j < key.length; j++) {
				if (stringToLong(key[i]) > stringToLong(key[j])) {
					String t = key[i];
					key[i] = key[j];
					key[j] = t;
				}
			}
		}
		for (int k = 0; k < key.length; k++) {
			if (stringToLong(key[k]) > 0) {
				outMap.put(key[k] + "", (String) inMap.get(key[k] + ""));
			}
		}
		return outMap;
	}

	/**
	 * 将String转成Long型。
	 * 
	 * @author sparta 2011-4-9 下午05:05:05
	 *@param longStr
	 *@return
	 */
	public static long stringToLong(String longStr) {
		if (longStr == null)
			longStr = "";
		long mm = 0;
		try {
			mm = Long.parseLong(longStr);
		} catch (Exception ex) {
		}
		return mm;
	}

	public static synchronized long getPkId() {
		long lTmp = System.currentTimeMillis();
		if (pkId < lTmp) {
			pkId = lTmp;
		} else {
			pkId++;
		}
		return pkId;
	}

	/*
	 * 程序入口：测试一下本类的各个方法是否成功
	 */
	public static void main(String[] args) {

		System.out.println("@@@@@@2  getPkId() = " + getPkId());
	}

}
