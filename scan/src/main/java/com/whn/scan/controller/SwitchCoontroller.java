package com.whn.scan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.gg.reader.api.dal.GClient;
import com.whn.scan.pojo.OneObject;
import com.whn.scan.service.LabelsManagerService;

@RestController
public class SwitchCoontroller {

	private GClient client = OneObject.client;
	@Autowired
	private LabelsManagerService la;

	/**
	 * 连接
	 */
	@RequestMapping("/connect")
	public String connect() {
		return la.connect(client);
	}

	/**
	 * 停止
	 */
	@RequestMapping("/stop")
	public String stop() {
		return la.stop();
	}

	/**
	 * 关闭
	 */
	@RequestMapping("/close")
	public String close() {
		return la.close(client);
	}

}
