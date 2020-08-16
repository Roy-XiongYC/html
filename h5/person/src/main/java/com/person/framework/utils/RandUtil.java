package com.person.framework.utils;

import java.util.Random;

/**
 * 产出随机字符串
 * 
 * @author kaifa7
 *
 */
public class RandUtil {

	private static Random rand = new Random();
	private static String[] letters = new String[] { "a", "b", "c", "d", "e",
			"f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r",
			"s", "t", "u", "v", "w", "x", "y", "z" };

	/**
	 * 返回纯数字的字符串
	 * 
	 * @param size
	 *            指定随机数的长度
	 * @return
	 */
	public static String randMath(int size) {
		if (size < 1) {
			return null;
		}
		StringBuffer buffer = new StringBuffer("");
		for (int i = 0; i < size; i++) {
			buffer.append(rand.nextInt(10));
		}
		return buffer.toString();
	}

	/**
	 * 返回纯字母的字符串
	 * 
	 * @param size
	 *            指定随机字母串的长度
	 * @return
	 */
	public static String randLetter(int size) {
		if (size < 1) {
			return null;
		}
		StringBuffer buffer = new StringBuffer("");
		for (int i = 0; i < size; i++) {
			buffer.append(letters[rand.nextInt(26)]);
		}
		return buffer.toString();
	}

	/**
	 * 产生数字与字母相混合的字符串
	 * 
	 * @param size 指定随机字符串的长度
	 * @return
	 */
	public static String randMathAndLetter(int size) {
		if (size < 1) {
			return null;
		}
		StringBuffer buffer = new StringBuffer("");
		for (int i = 0; i < size; i++) {
			int rand =Integer.valueOf(randMath(1))% 2;
			if (rand == 0) {
				buffer.append(randMath(1));
			} else {
				buffer.append(randLetter(1));
			}
		}
		return buffer.toString();
	}

}
