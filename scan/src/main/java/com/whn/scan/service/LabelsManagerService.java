package com.whn.scan.service;

import java.util.ArrayList;
import com.gg.reader.api.dal.GClient;
import com.whn.scan.pojo.Log;

/**
 * 标签管理业务层
 */
public interface LabelsManagerService {

	public String connect(GClient client);

	public String stop();

	public String close(GClient client);

	public ArrayList<Log> readEpc(GClient client);

	public String writeEpc(GClient client,String str);
}