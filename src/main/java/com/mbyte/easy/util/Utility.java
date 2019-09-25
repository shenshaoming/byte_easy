package com.mbyte.easy.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.lang.reflect.Field;
import java.security.Principal;
import java.util.Random;

/**
 * 
 * @Title: Utility
 * @Description:公用工具类
 * @Author:杨凯旋
 * @Since:2017年7月20日
 * @Version:1.1.0
 */
public class Utility {

	/**
	 * 密码加密
	 * 
	 * @param password
	 *            未加密文本
	 * @return 加密后的文本
	 */
	public static String QuickPassword(String password) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.encode(password);
	}

	/**
	 * 校验密码正确错误
	 * 
	 * @param rawPassword
	 *            未加密文本
	 * @param encodedPassword
	 *            加密文本
	 * @return true:密码正确 ，false密码错误
	 */
	public static Boolean checkPassword(String rawPassword, String encodedPassword) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.matches(rawPassword, encodedPassword);
	}

	/**
	 * 
	 * security 查询登陆用户
	 * 
	 * @return 登录者用户名
	 */
	public static String getCurrentUsername() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			return ((UserDetails) principal).getUsername();
		}
		if (principal instanceof Principal) {
			return ((Principal) principal).getName();
		}
		return String.valueOf(principal);
	}

	/**
	 * 
	 * security 查询登陆用户
	 * 
	 * @return 登录者用户
	 */
	public static UserDetails getCurrentUser() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userDetails;
	}

	public static String CHAR_STR = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	/**
	 * @Title: getRandomStrByNum
	 * @Description:  获取不同位数的随机字符串
	 * @Author: lxt
	 * @param: factor
	 * @Date: 2019-02-13 15:25
	 * @return: java.lang.String
	 * @throws:
	 */
	public static String getRandomStrByNum(int factor) {
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < factor; i++) {
			sb.append(CHAR_STR.charAt(random.nextInt(36)));
		}
		return sb.toString();
	}
	/**
	 * @Title: getRandomByNum
	 * @Description: 获取指定位数的随机数字
	 * @Author: lxt 
	 * @param: factor 位数
	 * @Date: 2019-02-18 14:38 
	 * @return: int
	 * @throws: 
	 */
	public static int getRandomByNum(int factor){
		int min=1,max = 1;
		for (int i = 0; i < factor; i++) {
			if(i < factor-1 ){
				min *= 10;
			}
			max *= 10;
		}
		return getRandomIntInRange(min,max-1);
	}
	/**
	 * @Title: getRandomIntInRange
	 * @Description:  获取指定范围的随机数
	 * @Author: lxt
	 * @param: min 最小值
	 * @param: max 最大值
	 * @Date: 2019-02-18 14:38
	 * @return: int
	 * @throws:
	 */
	public static int getRandomIntInRange(int min, int max) {
		return new Random().ints(min, (max + 1)).limit(1).findFirst().getAsInt();
	}

	/**
	 * @Description : 为object中的所有String属性去除空格字符
	 *
	 * @param object 待处理的实体对象
	 * @return : java.lang.Object
	 * @author : 申劭明
	 * @date : 2019/7/21 11:23
	 */
	public static Object replaceBlankSpace(Object object){
		//获取该类中所有的域(属性)
		Field[] fields = object.getClass().getDeclaredFields();

		for(Field field : fields){
			//对所有的属性判断是否为String类型
			if(field.getType().equals(String.class)){
				//将私有属性设置为可访问状态
				field.setAccessible(true);
				try {
					String string = (String)field.get(object);
					//该属性为空则跳过该属性
					if(string == null){
						continue;
					}
					//将所有的空格字符用""替换
					string = string.replaceAll(" ","");
					//相当于调用了set方法设置属性
					field.set(object,string);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		return object;
	}
}
