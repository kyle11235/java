package com.mondymusic.lucene.model.service.impl;

import com.mondymusic.lucene.model.service.IService;

public class ServiceImpl implements IService {
	private Long id;	
	private String name;
	private Integer weight;
	
	public ServiceImpl(Long id , String name, Integer weight){
		this.id = id;
		this.name = name;
		this.weight = weight;
		
	}
	
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Integer getWeight() {
		return weight;
	}

}
