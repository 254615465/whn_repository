package com.whn.scan.controller;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.gg.reader.api.dal.HandlerTagEpcLog;
import com.gg.reader.api.protocol.gx.LogBaseEpcInfo;
import com.whn.scan.pojo.Log;
import com.whn.scan.pojo.OneObject;

/**
 * 6c标签上传事件实现类 监听状态开始类
 */
public class xxxx implements HandlerTagEpcLog {
	private final static Logger log = LoggerFactory.getLogger(xxxx.class);
	private static Integer count = 0;// 计数
	public static List<LogBaseEpcInfo> logs;
	private LogBaseEpcInfo logBaseEpcInfo;
	private ArrayList<Log> logList = OneObject.logList;

	public static void main(String[] args) {// encodeStringToHex decodeHexToString
		xxxx h = new xxxx();
		h.ches1();
		try { // 使正在执行的线程以指定毫秒数暂停（暂时停止执行）
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("------------------------------------------------------");
		h.ches2();

	}

	public void ches1() {// ArrayList<Log> logList = OneObject.logList;
		System.out.println("logList:::" + logList);
		System.out.println("OneObject.logList:::" + OneObject.logList);
		OneObject.logList = new ArrayList<Log>();
		logList=OneObject.logList;
		System.out.println("logList:::" + logList);
		System.out.println("OneObject.logList:::" + OneObject.logList);
		
		Log log1 = new Log();
		log1.setAntId(1);
		logList.add(log1);
		System.out.println("1logList:::" + logList);
		System.out.println("1OneObject.logList:::" + OneObject.logList);
		Log log2 = new Log();
		log2.setAntId(2);
		logList.add(log2);
		System.out.println("2logList:::" + logList);
		System.out.println("2OneObject.logList:::" + OneObject.logList);
		System.out.println("-*************************************");
		Log log3 = new Log();
		log3.setAntId(3);
		OneObject.logList.add(log3);
		System.out.println("3logList:::" + logList);
		System.out.println("3OneObject.logList:::" + OneObject.logList);
		Log log4 = new Log();
		log4.setAntId(4);
		OneObject.logList.add(log4);
		System.out.println("4logList:::" + logList);
		System.out.println("4OneObject.logList:::" + OneObject.logList);
	}

	public void ches2() {// ArrayList<Log> logList = OneObject.logList;
		System.out.println("logList:::" + logList);
		System.out.println("OneObject.logList:::" + OneObject.logList);
		Log log1 = new Log();
		log1.setAntId(3);
		logList.add(log1);
		System.out.println("logList:::" + logList);
		System.out.println("OneObject.logList:::" + OneObject.logList);
		Log log2 = new Log();
		log2.setAntId(4);
		logList.add(log2);
		System.out.println("logList:::" + logList);
		System.out.println("OneObject.logList:::" + OneObject.logList);
	}

	public xxxx() {

	}

	/**
	 * 标签上报事件 执行体处于监听状态时检测到标签则执行 相当于线程的run方法
	 */
	public void log(String arg0, LogBaseEpcInfo logBaseEpcInfo) {
//		System.out.println("执行了"+count+"次");
		boolean repeat = false;
		if (count == 0) {
			if (logs != null) {
				logs.clear();
			}
			logs = new ArrayList<LogBaseEpcInfo>();
			// start = System.currentTimeMillis();
		}
		if (logBaseEpcInfo != null) {

//			System.out.println("logBaseEpcInfo=="+logBaseEpcInfo);
			if (logs.size() > 0) {
				repeat = isRepeatTid(logBaseEpcInfo);
//				System.out.println("去重=="+repeat);
				if (!repeat) {
					logs.add(logBaseEpcInfo);
				}
			} else {
				logs.add(logBaseEpcInfo);
			}
			log.info("--检测到标签执行了" + count + "次--logs长度:" + logs.size() + "--去重:" + repeat + "--");
		}

//		System.out.println("logs长度=="+logs.size());
//		System.out.println("差值=="+(System.currentTimeMillis() - start));
		count++;
	}

	/**
	 * 去重处理 tid
	 */
	private static boolean isRepeatTid(LogBaseEpcInfo logBaseEpcInfo) {
		for (int i = 0; i < logs.size(); i++) {
			if (logs.get(i).getTid().equals(logBaseEpcInfo.getTid())) {
				return true;
			}
//			}
		}
		return false;
	}

	public LogBaseEpcInfo getLogBaseEpcInfo() {
		return logBaseEpcInfo;
	}

	public void setLogBaseEpcInfo(LogBaseEpcInfo logBaseEpcInfo) {
		this.logBaseEpcInfo = logBaseEpcInfo;
	}

	public static void setCount(Integer count) {
		xxxx.count = count;
	}

}