package com.whn.scan.tool;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import com.gg.reader.api.dal.GClient;
import com.gg.reader.api.dal.HandlerTagEpcLog;
import com.gg.reader.api.dal.HandlerTagEpcOver;
import com.gg.reader.api.protocol.gx.EnumG;
import com.gg.reader.api.protocol.gx.LogBaseEpcInfo;
import com.gg.reader.api.protocol.gx.LogBaseEpcOver;
import com.gg.reader.api.protocol.gx.MsgBaseInventoryEpc;
import com.gg.reader.api.protocol.gx.MsgBaseSetPower;
import com.gg.reader.api.protocol.gx.MsgBaseStop;
import com.gg.reader.api.protocol.gx.ParamEpcReadTid;
import com.whn.scan.pojo.Log;
import com.whn.scan.pojo.OneObject;

public class Encapsulation implements HandlerTagEpcLog, HandlerTagEpcOver {

	private GClient client = OneObject.client;
	private ArrayList<Log> logList = OneObject.logList;

	/**
	 * 标签上报事件 执行体处于监听状态时检测到标签则执行 相当于线程的run方法
	 */
	public void log(String s, LogBaseEpcInfo logbaseepcinfo) {
		if (null != logbaseepcinfo && 0 == logbaseepcinfo.getResult()) {
			setLog(logbaseepcinfo);
		}
	}

	/**
	 * 标签上报结束事件
	 */
	public void log(String s, LogBaseEpcOver logbaseepcover) {
		if (null != logbaseepcover) {
			System.out.println("读取结束." + "_____________________________________");
		}
	}

	/**
	 * 读取后封装对象，用来回显数据
	 */
	public void setLog(LogBaseEpcInfo logBaseEpcInfo) {
		TurnTools tools = new TurnTools();
		Log log = new Log();
		log.setAntId(logBaseEpcInfo.getAntId());
		log.setTid(logBaseEpcInfo.getTid());
		log.setEpcdata(tools._16ToStr(logBaseEpcInfo.getEpc()));
		log.setEpc(logBaseEpcInfo.getEpc());
		log.setUserdata(logBaseEpcInfo.getUserdata());
		log.setMode("  ？");
		log.setDate(new SimpleDateFormat("yyyy年MM月dd日 HH小时mm分钟ss秒").format(new Date()));
		if (!isCheck(log)) {// 不重复--增加
			logList.add(log);
			System.out.println("set方法中的logBaseEpcInfo     " + logBaseEpcInfo);
			System.out.println("set方法中的log     " + log);
			System.out.println("set方法中的logList    长度： " + logList.size() + "------" + logList);
		}
	}

	/**
	 * 查重 -重true -不重复false
	 */
	public boolean isCheck(Log log) {
		for (int i = 0; i < logList.size(); i++) {
			if (log.getTid().equals(logList.get(i).getTid())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 停止指令，使读写器停止RFID操作，进入空闲状态
	 */
	public boolean baseStop() {
		MsgBaseStop msgBaseStop = new MsgBaseStop();
		client.sendSynMsg(msgBaseStop);
		if (0 == msgBaseStop.getRtCode()) {
			System.out.println("空闲状态-----成功");
			return true;
		} else {
			System.out.println("空闲状态-----失败");
			return false;
		}
	}

	/**
	 * 功率配置, 将4个天线功率都设置为30dBm.
	 */
	public void setPower() {
		MsgBaseSetPower msgBaseSetPower = new MsgBaseSetPower();
		Hashtable<Integer, Integer> hashtable = new Hashtable<Integer, Integer>();
		hashtable.put(1, 30);
		hashtable.put(2, 30);
		hashtable.put(3, 30);
		hashtable.put(4, 30);
		msgBaseSetPower.setDicPower(hashtable);
		client.sendSynMsg(msgBaseSetPower);
		if (0 == msgBaseSetPower.getRtCode()) {
			System.out.println("功率 设置 成功 ");
		} else {
			System.out.println("功率 设置 失败 ");
		}
	}

	/**
	 * 4个天线读卡, 读取EPC数据区以及TID数据区
	 */
	public void baseInventoryEpc() {
		MsgBaseInventoryEpc msgBaseInventoryEpc = new MsgBaseInventoryEpc();
		msgBaseInventoryEpc
				.setAntennaEnable(EnumG.AntennaNo_1 | EnumG.AntennaNo_2 | EnumG.AntennaNo_3 | EnumG.AntennaNo_4);
		msgBaseInventoryEpc.setInventoryMode(EnumG.InventoryMode_Inventory);// 0为单次，1为连续
		ParamEpcReadTid tid = new ParamEpcReadTid();
		tid.setMode(EnumG.ParamTidMode_Auto);
		tid.setLen(6);
		msgBaseInventoryEpc.setReadTid(tid);
		client.sendSynMsg(msgBaseInventoryEpc);
		if (0 == msgBaseInventoryEpc.getRtCode()) {
			System.out.println("读取成功");
		} else {
			System.out.println("读取失败");
		}
	}

}