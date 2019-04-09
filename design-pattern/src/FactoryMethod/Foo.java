package FactoryMethod;

import util.P;

public class Foo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//不改变原有的工厂，而是再建一个工厂来生产新的产品，新工厂仍然调用getProduct()得到产品
		P.p(new FactoryImp1().getProduct().getName());
		P.p(new FactoryImp2().getProduct().getName());

	}

}
