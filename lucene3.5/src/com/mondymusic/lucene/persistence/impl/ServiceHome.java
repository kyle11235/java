package com.mondymusic.lucene.persistence.impl;

import java.util.ArrayList;
import java.util.List;

import com.mondymusic.lucene.persistence.IServiceHome;
import com.mondymusic.lucene.pojo.service.Service;

public class ServiceHome implements IServiceHome {

	@Override
	public List<Service> getServiceList() {
		List<Service> out = new ArrayList<Service>();
		for (int i = 1; i <= 10; i++) {
			Service service = new Service(new Long(i), "zhang" + i, new Integer(i * 10));
			out.add(service);
		}

		return out;
	}

}
