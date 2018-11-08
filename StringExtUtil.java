package com.tritonsfs.cac.depository.util.core;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @description 字符串工具类
 * @author leieb
 * @time 2017年9月6日 下午2:16:48
 *
 */
public class StringExtUtil {

	/**
	 * 首字母转小写
	 * 
	 * @author leieb
	 * @param s
	 * @return
	 */
	public static String toLowerCaseFirstOne(String s) {
		if (Character.isLowerCase(s.charAt(0)))
			return s;
		else
			return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
	}

	/**
	 * 首字母转大写
	 * 
	 * @author leieb
	 * @param s
	 * @return
	 */
	public static String toUpperCaseFirstOne(String s) {
		if (Character.isUpperCase(s.charAt(0)))
			return s;
		else
			return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
	}

	/**
	 * 去掉金额字段无效小数部分。比如：<br/>
	 * 100.00 to 100<br/>
	 * 100.0100 to 100.01
	 * 
	 * @param amount
	 *            金额
	 * @return
	 * @since:2017年11月5日
	 * @author:liuxc
	 */
	public static String keepInteger(String amount) {
		if (amount.indexOf(".") != -1) {
			amount = amount.replaceAll("0+?$", "");// 去掉多余的0
			amount = amount.replaceAll("[.]$", "");// 如最后一位是.则去掉
		}
		return amount;
	}

	/**
	 * 处理利率字段：保留两位小数。注：数据库查询出来是四位小数
	 * 
	 * @param rate
	 * @return
	 * @since:2017年11月5日
	 * @author:liuxc
	 */
	public static String keepTwoDecimal(String rate) {
		if (StringUtils.isBlank(rate))
			return "";
		return rate.substring(0, rate.length() - 2);
	}

	/**
	 * 处理利率字段：将keepTwoDecimal(String rate)处理结果恢复原值:如果数据库扩大100倍取值，则此处缩小100倍处理。
	 * 
	 * @param keepTwoDecimalRate
	 * @return
	 * @since:2017年11月14日
	 * @author:liuxc
	 */
	public static String recoverRate(String keepTwoDecimalRate) {
		if (StringUtils.isBlank(keepTwoDecimalRate))
			return "";
		BigDecimal src = null;
		try {
			src = new BigDecimal(keepTwoDecimalRate);
		} catch (Exception e) {
			return "";
		}
		return src.divide(new BigDecimal(100), 4, BigDecimal.ROUND_DOWN).toString();
	}

	/**
	 * 给手机号码加密，格式为185****5698
	 *
	 * @param phoneNo
	 * @return
	 */
	public static String encryptionPhoneNo(String phoneNo) {
		if (!StringUtils.isEmpty(phoneNo) && phoneNo.length() == 11) {
			phoneNo = phoneNo.substring(0, 3) + "****" + phoneNo.substring(7, 11);
		}
		return phoneNo;
	}

	/**
	 * 给银行卡加密（前四后四中间*）
	 * 
	 * @param bankCardNo
	 * @return
	 */
	public static String encryptionBankCardNo(String bankCardNo) {
		if (StringUtils.isNotEmpty(bankCardNo) && bankCardNo.length() >= 8) {
			return bankCardNo.substring(0, 4) + " **** **** "
					+ bankCardNo.substring(bankCardNo.length() - 4, bankCardNo.length());
		}
		return null;
	}

	/**
	 * 身份证加* 格式为 431***************1234
	 *
	 * @param idCardNo
	 * @return
	 */
	public static String idCardNoStar(String idCardNo) {
		if (StringUtils.isNotEmpty(idCardNo) && idCardNo.length() > 5) {
			idCardNo = idCardNo.substring(0, 2) + "************"
					+ idCardNo.substring(idCardNo.length() - 4, idCardNo.length());
		}
		return idCardNo;
	}

	/**
	 * 给真实姓名加密
	 * 
	 * @param realName
	 **/
	public static String encryptionRealName(String realName) {
		if (StringUtils.isBlank(realName)) {
			return "";
		}
		char[] chars = realName.toCharArray();
		if (chars.length > 2) {
			for (int i = 0; i < chars.length; i++) {
				if (i != 0 && i != chars.length - 1) {
					chars[i] = '*';
				}
			}
			StringBuilder builder = new StringBuilder();
			builder.append(chars);
			return builder.toString();
		} else if (chars.length == 2) {
			return chars[0] + "*";
		} else if (chars.length == 1) {
			return realName;
		} else {
			return "";
		}
	}

	/**
	 * 英文逗号分隔
	 * 
	 * @param deviceTokens
	 * @return
	 */
	public static String getArrayStr(String[] deviceTokens) {
		if (null == deviceTokens || 0 == deviceTokens.length) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < deviceTokens.length - 1; i++) {
			sb.append(deviceTokens[i]).append(",");
		}
		sb.append(deviceTokens[deviceTokens.length - 1]);
		return sb.toString();
	}

	/**
	 * 密码邮箱
	 * 
	 * @param emailAddress
	 *            邮箱地址
	 * @return String (1*********6@163.com)
	 **/
	public static String encryptionEmailAddress(String emailAddress) {
		if (StringUtils.isBlank(emailAddress)) {
			return "";
		}
		if (!emailAddress.contains("@")) {
			return emailAddress;
		} else {
			String email = emailAddress.substring(0, emailAddress.indexOf("@"));
			if (email.length() > 2) {
				StringBuilder builder = new StringBuilder(email.substring(0, 1));
				char[] array = email.toCharArray();
				for (int i = 1; i < array.length - 1; i++) {
					builder.append("*");
				}
				builder.append(email.substring(email.length() - 1));
				builder.append(emailAddress.substring(emailAddress.indexOf("@")));
				return builder.toString();
			} else {
				return emailAddress;
			}
		}
	}
}
