package com.whn.scan.tool;

import com.gg.reader.api.utils.BitBuffer;
import com.gg.reader.api.utils.HexUtils;

public class TurnTools {

	// 计算pc值
	public static String getPc(int pcLen) {
		int iPc = pcLen << 11;
		BitBuffer buffer = BitBuffer.allocateDynamic();
		buffer.put(iPc);
		buffer.position(16);
		byte[] bTmp = new byte[2];
		buffer.get(bTmp);
		return HexUtils.bytes2HexString(bTmp);
	}

	// 写入数据不足4位后面补'0' AA00
	public static String padLeft(String src, int len, char ch) {// AAA 4 '0'
		int diff = len - src.length();
		if (diff <= 0) {
			return src;
		}

		char[] chars = new char[len];
		System.arraycopy(src.toCharArray(), 0, chars, 0, src.length());
		for (int i = src.length(); i < len; i++) {
			chars[i] = ch;
		}
		return new String(chars);
	}

	public static int getValueLen(String str) {
		str = str.trim();
		return str.length() % 4 == 0 ? str.length() / 4 : (str.length() / 4) + 1;
	}

	/**
	 * 字符串转换成为16进制(无需Unicode编码)
	 */
	public String strTo_16(String str) {
		char[] chars = "0123456789ABCDEF".toCharArray();
		StringBuilder sb = new StringBuilder("");
		byte[] bs = str.getBytes();
		int bit;
		for (int i = 0; i < bs.length; i++) {
			bit = (bs[i] & 0x0f0) >> 4;
			sb.append(chars[bit]);
			bit = bs[i] & 0x0f;
			sb.append(chars[bit]);
		}
		return sb.toString().trim();
	}

	/**
	 * 16进制直接转换成为字符串(无需Unicode解码)
	 */
	public String _16ToStr(String hexStr) {
		String str = "0123456789ABCDEF";
		char[] hexs = hexStr.toCharArray();
		byte[] bytes = new byte[hexStr.length() / 2];
		int n;
		for (int i = 0; i < bytes.length; i++) {
			n = str.indexOf(hexs[2 * i]) * 16;
			n += str.indexOf(hexs[2 * i + 1]);
			bytes[i] = (byte) (n & 0xff);
		}
		return new String(bytes);
	}

}