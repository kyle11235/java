/* Copyright © 2016 Oracle and/or its affiliates. All rights reserved. */

package com.example.rest;

public class Customer {

	private final String key;
	private final String name;
	private final String address;
	private final String city;
	private final String nation;
	private final String region;
	private final String phone;
	private final String mktsegment;

	

	public Customer(String key, String name, String address, String city, String nation, String region, String phone, String mktsegment) {
		this.key = key;
		this.name = name;
		this.address = address;
		this.city = city;
		this.nation = nation;
		this.region = region;
		this.phone = phone;
		this.mktsegment = mktsegment;
	}

	public String getKey() {
		return key;
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public String getCity() {
		return city;
	}

	public String getNation() {
		return nation;
	}

	public String getRegion() {
		return region;
	}

	public String getPhone() {
		return phone;
	}

	public String getMktsegment() {
		return mktsegment;
	}

	

}
