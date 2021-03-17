package com.whn.scan.controller;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.gg.reader.api.dal.GClient;
import com.whn.scan.pojo.Log;
import com.whn.scan.pojo.OneObject;
import com.whn.scan.service.LabelsManagerService;

@RestController
public class ReadController {

	private GClient client = OneObject.client;
	@Autowired
	private LabelsManagerService la;

	@RequestMapping("/read")
	public ArrayList<Log> readd() {
		return la.readEpc(client);
	}

}