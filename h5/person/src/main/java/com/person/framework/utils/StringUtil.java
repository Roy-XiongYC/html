package com.person.framework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String Utility Class including String related manipulation. <br/>
 * And all methods that return a String class comply with the rule that, if
 * parameter String is null, the result will be null.<br/>
 * But if the return type is other than String, the return may be varied.
 * 
 * @author sherlockq
 * 
 */
public final class StringUtil {

	/** log instance. */
	protected static final Logger log = LoggerFactory
			.getLogger(StringUtil.class);

	/**
	 * The separator for split and join.
	 */
	public static final char SEPARATOR = '|';

	/**
	 * Returns a blank string while given string is null.
	 * 
	 * @param value
	 *            string value to be avoid.
	 * @return blank string while null, other original string value.
	 */
	public static String avoidNull(String value) {
		return (value == null) ? "" : value;
	}

	/**
	 * Returns the byte length in given charset of the String.
	 * 
	 * @param value
	 * @param charset
	 *            Charset under which to measure.
	 * @return Returns 0 if String is null, or the charset is not supported.
	 * @see CharsetConstant
	 */
	public static int getByteLength(String value, String charset) {
		if (value == null) {
			return 0;
		} else {
			try {
				return value.getBytes(charset).length;
			} catch (UnsupportedEncodingException e) {
				log.error("Encoding error", e);
				return 0;
			}
		}

	}

	/**
	 * Returns object's toString() result if it's not null. Return an empty
	 * String if it's null.
	 * 
	 * @param obj
	 *            object.
	 * @return string
	 */
	public static String getObjString(Object obj) {
		if (null == obj)
			return "";
		else
			return obj.toString();
	}

	/**
	 * Check whether given string value is null or a blank string (trimmed).
	 * 
	 * @param value
	 *            string value to be checked.
	 * @return true if string value is null or blank, otherwise false.
	 */
	public static boolean isNullOrBlank(String value) {
		return value == null || "".equals(value.trim());
	}

	public static boolean isNullOrBlank(Object value) {
		return value == null || "".equals(((String) value).trim());
	}

	/**
	 * 判断是否为数字
	 * a||1.1||""||null  false
	 *      1  true
	 * @param value
	 * @return
	 */
	public static boolean IsNumber(Object value) {
		if (value == null || value.toString().equals("")) {
			return false;
		}
		try {
			Integer.parseInt(value.toString());
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	/**
	 * 判断是否为浮点型
	 * @param value
	 * @return
	 */
	public static boolean IsDecimal(Object value){
		if(value==null || value.toString().equals("")){
			return false;
		}
		try {
			 new BigDecimal(value.toString());
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * Join collection's toString() into one String split with "\|". <br/>
	 * A null element of the collection will leave a empty string in the result. <br/>
	 * Process has been taken to be willk to separator included String: If there
	 * is \ in the String, it'll be replaced with double \.
	 * 
	 * @param objects
	 * @return If collection has no elements return null. The separator should
	 *         appear the times same to collection's count.
	 * @see URLUtil#split(String)
	 */
	public static String join(Object[] objects) {

		if (objects == null || objects.length == 0) {
			return null;
		}

		StringBuffer sb = new StringBuffer();
		for (Object object : objects) {
			if (object != null) {

				// 首先对所有\进行规避处理，即修改为两个\
				String objString = object.toString();
				if (objString != null) {
					objString = objString.replaceAll("\\\\", // 即\号
							Matcher.quoteReplacement("\\\\"));
					objString = objString.replaceAll("\\|",
							Matcher.quoteReplacement("\\|"));
					sb.append(objString);
				}
			}

			// 之后用\和分隔符作为分割
			sb.append(SEPARATOR);
		}

		// 去除最后一个多余的分隔符，不用考虑空的情况
		return sb.toString().substring(0, sb.length() - 1);
	}

	/**
	 * Left the string by given byte-length in specified charset, the
	 * byte-length of result string should not exceed parameter
	 * <code>bytelen</code> and meanwhile should be closest to it.<br/>
	 * For example, generally Chinese character in UTF-8 occupies 3 bytes, so <br/>
	 * <br/>
	 * <code>
	 * leftByCharsetByte("中文","UTF-8",3) = "中" <br/>
	 * leftByCharsetByte("中文","UTF-8",4) = "中" <br/>
	 * leftByCharsetByte("中文","UTF-8",6) = "中文"<br/>
	 * </code><br/>
	 * If not even a character can be returned, the result will be "" as <br/>
	 * <br/>
	 * <code>
	 * leftByCharsetByte("中文","UTF-8",1) = ""
	 * </code>
	 * 
	 * @param value
	 *            original String to left
	 * @param charset
	 *            Charset Name
	 * @param bytelen
	 *            the max length of byte to return in specified charset
	 * @return If value is null, return null, otherwise return not null String.
	 */
	public static String leftByCharsetByte(String value, String charset,
			int bytelen) {

		// 如果为NULL，则直接返回
		if (null == value) {
			return null;
		}

		try {

			Charset cs = Charset.forName(charset);
			if (cs.encode(value).limit() <= bytelen) { // 直接返回
				return value;
			}

			int byteCount = 0, stringIndex = 0;
			while (true) { // 不用考虑value总长度
				int currentByteLen = cs.encode(
						value.substring(stringIndex, stringIndex + 1)).limit();
				if ((byteCount + currentByteLen) > bytelen) {
					break; // 达到最大字节数，中断

				} else {
					byteCount += currentByteLen; // 继续
					stringIndex++;

				}
			}

			return value.substring(0, stringIndex);

		} catch (UnsupportedCharsetException e) {
			log.error("Encoding error", e);
			return null;
		}

	}

	private static final Pattern pattern = Pattern
			.compile("(?<!\\\\)(\\\\\\\\)*\\Q" + SEPARATOR + "\\E"); // 匹配前方有偶数个或0个\的分隔符

	/**
	 * Split a joined String which separated by specified separator back to a
	 * String array. Should be used with StringUitl.join because there is some
	 * mechanism to avoid special character.
	 * 
	 * @param ori
	 * @return
	 * @see URLUtil#join(Collection)
	 */
	public static String[] split(String ori) {
		if (ori == null) {
			return null;
		} else {

			List<String> strings = new ArrayList<String>();
			// 自己写解析器

			Matcher matcher = pattern.matcher(ori);
			int currentStart = 0;
			while (matcher.find()) {
				// 匹配返回的end即分割符后位置
				int pos = matcher.end();
				strings.add(ori.substring(currentStart, pos - 1)); // 截取本次开始到分隔符前一个位置(去掉\号)
				currentStart = pos;
			}
			// 最后必然还有剩余，就算最后一个字符是分隔符
			strings.add(ori.substring(currentStart, ori.length()));

			String[] strs = strings.toArray(new String[0]);
			// 依次将规避的普通字符恢复
			for (int i = 0; i < strs.length; i++) {
				strs[i] = strs[i].replaceAll("\\Q\\\\\\E",
						Matcher.quoteReplacement("\\"));
				strs[i] = strs[i].replaceAll("\\Q\\|\\E",
						Matcher.quoteReplacement("|"));
			}
			return strs;
		}
	}

	public static void main(String[] args) {
		// String[] strs = new String[] { "\\|", "\\", "\\" };
		// String str = join(strs);
		// System.out.println(str);
		// System.out.println(Arrays.toString(split(str)));

	}

	private static String SUMMARY_SUFFIX = "...";

	/**
	 * use '...' to be as summary suffix if necessary and keep result no more
	 * than totalLen.
	 * 
	 * @param ori
	 * @param totalLen
	 *            should be more than 3
	 * @return
	 */
	public static String summary(String ori, int totalLen) {
		if (ori == null)
			return null;
		if (ori.length() <= totalLen)
			return ori;
		return ori.substring(0, totalLen - 3) + SUMMARY_SUFFIX;
	}

	public static Integer parserInteger(String aNum) {
		if (isNullOrBlank(aNum))
			return null;
		try {
			return Integer.valueOf(aNum);
		} catch (Exception e) {
			return null;
		}
	}

	public static String nullToEmpty(String str) {
		if (isNullOrBlank(str))
			return "";
		return str;
	}

	public static String replaceAllEM(String source, String key) {
		String ret = source.replaceAll(key, "<em>" + key + "</em>");
		return ret;
	}

	/**
	 * 过滤输入的HTML字符 moodboy 2014.12
	 */
	public static String FilerHtml(String source) {
		if (isNullOrBlank(source)) {
			return source;
		}
		source = source.replaceAll("<", "&lt;").replaceAll(">", "&gt;")
				.replaceAll("\"", "&quot;").replaceAll("\'", "&acute;");
		return source;
	}

	/**
	 * 获得字符串"."之后的字符串，适用于获取文件后缀名
	 * 
	 * @param str
	 * @return
	 */
	public static String getPointAfter(String str) {
		return str.substring(str.indexOf(".") + 1);
	}

	/**
	 * 全角转半角
	 * 
	 * @param input
	 *            String.
	 * @return 半角字符串
	 */
	public static String ToDBC(String input) {
		if (!isNullOrBlank(input)) {
			return input;
		}
		char c[] = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == '\u3000') {
				c[i] = ' ';
			} else if (c[i] > '\uFF00' && c[i] < '\uFF5F') {
				c[i] = (char) (c[i] - 65248);
			}
		}
		String returnString = new String(c);
		return returnString;
	}
	
	/**
	 * 输入为表情时，转化为""
	 * 
	 * @param 
	 * @return 
	 */
	public static String LookToNull(String input) {
		if (isNullOrBlank(input)) {
			return input;
		}
		byte[] conbyte = input.getBytes();
		for (int i = 0; i < conbyte.length; i++) {
			if ((conbyte[i] & 0xF8) == 0xF0) {
				return "";
			}
		}
		return input;
	}

	/**
	 * 检查指定的字符串列表是否不为空。
	 */
	public static boolean areNotEmpty(String... values) {
		boolean result = true;
		if (values == null || values.length == 0) {
			result = false;
		} else {
			for (String value : values) {
				result &= !isEmpty(value);
			}
		}
		return result;
	}

	public static boolean isEmpty(String value) {
		int strLen;
		if (value == null || (strLen = value.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if ((Character.isWhitespace(value.charAt(i)) == false)) {
				return false;
			}
		}
		return true;
	}
}
