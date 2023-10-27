package com.yopheu.aenean.models.money;

public class UQMoney extends Money {

	private static UQMoney instance = new UQMoney();
	private UQMoney() {}
	public static UQMoney getInstance() {
		return instance;
	} 

}
