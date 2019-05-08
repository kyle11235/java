/* Copyright © 2016 Oracle and/or its affiliates. All rights reserved. */

package com.example.rest;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

	private CustomerDAO edao = new CustomerDatabaseDAOImpl();

	@RequestMapping(method = RequestMethod.GET)
	public Customer[] get(@RequestParam(value = "num", required = false, defaultValue = "10") Integer num) {
		return edao.get(num).toArray(new Customer[0]);
	}

}
