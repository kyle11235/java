package com.kyle.springmvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kyle.springmvc.mq.producer.ProducerService;

@RequestMapping("/test")
@Controller
public class TestController extends BaseController {

	@Autowired
	private ProducerService producerService;

	@RequestMapping("")
	public String test() {
		return "test/Ok";
	}

	@RequestMapping("/mq1")
	public String mq1() {
		producerService.sendMessage("hello");
		return "test/Ok";
	}

}
