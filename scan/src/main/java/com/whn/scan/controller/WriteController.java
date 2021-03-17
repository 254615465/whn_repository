package com.whn.scan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.gg.reader.api.dal.GClient;
import com.whn.scan.pojo.OneObject;
import com.whn.scan.service.LabelsManagerService;

@RestController
public class WriteController {

	private GClient client = OneObject.client;
	@Autowired
	private LabelsManagerService la;

	@RequestMapping("/write")
	public String writee(String str) {
		return la.writeEpc(client, str);
	}
}
