package com.whn.scan.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.ResponseBody;

//@ControllerAdvice
public class MyExceptionHandler {

	//@ExceptionHandler(RuntimeException.class)
	@ResponseBody
	public Map<Object, Object> exceptionHandler() {
		Map<Object, Object> map = new HashMap<>();
		map.put("error", "500");
		map.put("msg", "系统出现错误~");
		return map;
	}
}
