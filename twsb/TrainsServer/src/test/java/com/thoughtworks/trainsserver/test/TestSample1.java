package com.thoughtworks.trainsserver.test;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.thoughtworks.trainsserver.service.ServiceProvider;
import com.thoughtworks.trainsserver.service.impl.ServiceProviderImpl;

public class TestSample1 {

	private static String fileName = "sample1.txt";
	private static ServiceProvider serviceProvider = new ServiceProviderImpl();

	@BeforeClass
	public static void globalInit() {
		serviceProvider.initGraph(fileName);
	}

	@Before
	public void setUp() {
	}

	@Test
	public void test1() {
		// get the distance through a certain route
		String input = "A-B-C";
		System.out.println("Output #1:" + serviceProvider.getDistance(input));
	}
	
	@Test
	public void test2() {
		// get the distance through a certain route
		String input = "A-D";
		System.out.println("Output #2:" + serviceProvider.getDistance(input));
	}

	@Test
	public void test3() {
		// get the distance through a certain route
		String input = "A-D-C";
		System.out.println("Output #3:" + serviceProvider.getDistance(input));
	}

	@Test
	public void test4() {
		// get the distance through a certain route
		String input = "A-E-B-C-D";
		System.out.println("Output #4:" + serviceProvider.getDistance(input));
	}

	@Test
	public void test5() {
		// get the distance through a certain route
		String input = "A-E-D";
		System.out.println("Output #5:" + serviceProvider.getDistance(input));
	}

	@Test
	public void test6() {
		// The number of trips starting at C and ending at C with a maximum of 3 stops
		System.out.println("Output #6:" + serviceProvider.getNumberWithinMaxStops("C", "C", 3));
	}

	@Test
	public void test7() {
		// The number of trips starting at A and ending at C with exactly 4 stops
		System.out.println("Output #7:" + serviceProvider.getNumberOfExactStops("A", "C", 4));
	}

	@Test
	public void test8() {
		// The length of the shortest route (in terms of distance to travel) from A to C
		System.out.println("Output #8:" + serviceProvider.getShortestLength("A", "C"));
	}

	@Test
	public void test9() {
		// The length of the shortest route (in terms of distance to travel) from B to B
		System.out.println("Output #9:" + serviceProvider.getShortestLength("A", "C"));
	}

	@Test
	public void test10() {
		// The number of different routes from C to C with a distance of less than 30
		System.out.println("Output #10:" + serviceProvider.getNumberWithinMaxDistance("C", "C", 30));
	}


	@After
	public void tearDown() {
	}

	@AfterClass
	public static void globalDestroy() {
	}
}
