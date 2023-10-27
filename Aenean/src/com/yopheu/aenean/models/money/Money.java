package com.yopheu.aenean.models.money;

public class Money {

	protected int money = 0;	
	
	public boolean add(int money) {
		this.money += money;
		return true;
	}

	public boolean sub(int money) {
		int temp = this.money - money;
		if(temp < 0) return false;
		this.money -= money;
		return true;		
	}

	public boolean totalSet(int money) {
		if(money <= 0) return false;
		this.money = money;
		return true;
	}

	public int get() {
		return this.money;
	}

}
