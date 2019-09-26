package com.mondymusic.lucene.main;

import java.util.List;
import java.util.Locale;

import com.mondymusic.lucene.model.service.IService;
import com.mondymusic.lucene.persistence.IServiceSearchHome;
import com.mondymusic.lucene.persistence.impl.ServiceSearchHome;

public class Main {

	public static IServiceSearchHome serviceSeacherHome = new ServiceSearchHome();

	public static void main(String[] args) {

		

		List<IService> serviceList;
		try {
			print("begin rebuild");
			serviceSeacherHome.rebuildIndex(new Locale("en_GB"));

			print("end rebuild , begin search");
			String queryString = "zhang1";
			serviceList = serviceSeacherHome.search(new Locale("en_GB"), true, queryString, 100);
			for (IService s : serviceList) {
				print(s);
			}
			print("end search");
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public static void print(Object str) {
		System.out.println(str.toString());
	}

}

// alt shift X J
