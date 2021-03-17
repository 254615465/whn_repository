package com.whn.scan.pojo;

import lombok.Data;

@Data
public class Log {

	private Integer antId;// 序号
	private String tid;// tid
	private String epc;// epc
	private String epcdata;// 数据
	private String userdata;// 用户
	private String mode;// 操作方式
	private String date;// 操作时间

	public Log() {
	}

	public Log(Integer antId, String tid, String epc, String epcdata, String userdata, String mode, String date) {
		this.antId = antId;
		this.tid = tid;
		this.epc = epc;
		this.epcdata = epcdata;
		this.userdata = userdata;
		this.mode = mode;
		this.date = date;
	}

	public String toString() {
		return "Log [序号antId=" + antId + ", tid=" + tid + ", epc=" + epc + ",数据 epcdata=" + epcdata + ", 用户userdata="
				+ userdata + ", 操作方式 mode=" + mode + ", 操作时间 date=" + date + "]";
	}
}
