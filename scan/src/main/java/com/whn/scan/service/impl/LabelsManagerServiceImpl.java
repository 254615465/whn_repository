package com.whn.scan.service.impl;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.gg.reader.api.dal.GClient;
import com.gg.reader.api.protocol.gx.EnumG;
import com.gg.reader.api.protocol.gx.MsgBaseWriteEpc;
import com.gg.reader.api.utils.HexUtils;
import com.whn.scan.pojo.Log;
import com.whn.scan.pojo.OneObject;
import com.whn.scan.service.LabelsManagerService;
import com.whn.scan.tool.Encapsulation;
import com.whn.scan.tool.TurnTools;

@Service
public class LabelsManagerServiceImpl implements LabelsManagerService {

	@Value("${scan.Serial_RS232}")
	private String serial_RS232;
	private Encapsulation en = new Encapsulation();
	private ArrayList<Log> logList = OneObject.logList;

	/**
	 * 连接
	 */
	public String connect(GClient client) {
		System.out.println("开启连接-----------------------------");
		boolean con = client.openSerial(serial_RS232, 2000);// 连接
		return con ? "连接成功" : "连接失败";
	}

	/**
	 * 停止
	 */
	public String stop() {
		return en.baseStop() ? "停止成功" : "停止失败";// 停止指令，使读写器停止RFID操作，进入空闲状态
	}

	/**
	 * 关闭
	 */
	public String close(GClient client) {
		System.out.println("关闭连接");
		en.baseStop();// 停止指令，使读写器停止RFID操作，进入空闲状态
		return client.close() ? "关闭成功" : "关闭失败";// 关闭连接
	}

	/**
	 * 读数据
	 */
	public ArrayList<Log> readEpc(GClient client) {
		OneObject.logList = new ArrayList<Log>();
		logList = OneObject.logList;
		client.onTagEpcLog = new Encapsulation();// 标签上报事件 执行体处于监听状态时检测到标签则执行 相当于线程的run方法
		client.onTagEpcOver = new Encapsulation();// 标签上报结束事件
		en.baseStop();// 停止指令，使读写器停止RFID操作，进入空闲状态
		en.setPower();// 功率配置, 将4个天线功率都设置为30dBm.
		en.baseInventoryEpc();// 4个天线读卡, 读取EPC以及TID数据区--设置单次读/重复读--csendSynMsg();开始读
		try { // 使正在执行的线程以指定毫秒数暂停（暂时停止执行）
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return logList;
	}

	/**
	 * 写数据
	 */
	public String writeEpc(GClient client, String str) {
		if (OneObject.logList.size() != 1) {
			return "错误---标签数量不是一个";
		}
		System.out.println(OneObject.logList);
		str = new TurnTools().strTo_16(str);
		MsgBaseWriteEpc msg = new MsgBaseWriteEpc();
		msg.setAntennaEnable(EnumG.AntennaNo_1 | EnumG.AntennaNo_2);
		msg.setStart(1);
		msg.setArea(EnumG.WriteArea_Epc);
		int iWordLen = TurnTools.getValueLen(str);
		str = TurnTools.getPc(iWordLen) + TurnTools.padLeft(str.toUpperCase(), 4 * iWordLen, '0'); // PC值+数据内容
		msg.setBwriteData(HexUtils.hexString2Bytes(str));
		client.sendSynMsg(msg);
		if (0 == msg.getRtCode()) {
			System.out.println("写入成功" + str);
			return "写入成功" + str;
		}
		return msg.getRtMsg();
	}

}