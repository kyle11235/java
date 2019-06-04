/* Copyright © 2016 Oracle and/or its affiliates. All rights reserved. */

package com.example.rest.ociapi;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import com.example.util.Config;

@CrossOrigin
@RestController
@RequestMapping("/api/db")
public class OCIController {

	private OCIAPI api = new OCIAPI();

	@RequestMapping(method = RequestMethod.GET)
	public List<DB> list() {
		return api.listAutonomousDatabases(Config.getValue("oci.compartmentID"));
	}

    @CrossOrigin(origins = "http://localhost:5000")
	@RequestMapping(method = RequestMethod.PUT, value = "/{dbID:.+}")
	public String update(@PathVariable String dbID, @RequestBody Integer cpuCount) {
		try {
			System.out.println("dbID=" + dbID);
			System.out.println("cpuCount=" + cpuCount);
			return api.updateAutonomousDatabase(dbID, cpuCount);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

	@CrossOrigin(origins = "http://localhost:5000")
	@RequestMapping(method = RequestMethod.POST, value = "/{dbID:.+}/actions/start")
	public String start(@PathVariable String dbID) {
		try {
			System.out.println("dbID=" + dbID);
			return api.startAutonomousDatabase(dbID);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
	@CrossOrigin(origins = "http://localhost:5000")
	@RequestMapping(method = RequestMethod.POST, value = "/{dbID:.+}/actions/stop")
	public String stop(@PathVariable String dbID) {
		try {
			System.out.println("dbID=" + dbID);
			return api.stopAutonomousDatabase(dbID);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
