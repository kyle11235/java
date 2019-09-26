package com.mondymusic.lucene.pojo.service;

public class Service {
	private Long id;
	private String name;
	private Integer weight;
	
	public Service(){}
	
	public Service(Long id,String name,Integer weight){
		this.id = id;
		this.name = name;
		this.weight =weight;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	
	

}
