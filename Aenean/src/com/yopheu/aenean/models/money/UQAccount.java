package com.yopheu.aenean.models.money;

public class UQAccount extends Account {

	private static UQAccount instance = new UQAccount();
	private UQAccount() {}
	public static UQAccount getInstance() {
		return instance;
	} 

}
