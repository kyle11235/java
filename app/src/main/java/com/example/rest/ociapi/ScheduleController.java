/* Copyright © 2016 Oracle and/or its affiliates. All rights reserved. */

package com.example.rest.ociapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/db/schedule")
public class ScheduleController {
	
	@Autowired
	private MyScheduler myScheduler;

	@RequestMapping(method = RequestMethod.GET, value = "/{dbID:.+}/{action:.+}")
	public String schedule(@PathVariable String dbID, @PathVariable String action, @RequestParam(value = "cron", required = false, defaultValue = "") String cron) {
		System.out.println("begin scheduling... ");
		System.out.println("dbID=" + dbID + ",cron=" + cron + ",action=" + action.toUpperCase());
		myScheduler.schedule(dbID, cron, action.toUpperCase());
		System.out.println("end scheduling");
		return "ok";
	}

}
