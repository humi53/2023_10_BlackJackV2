package com.yopheu.aenean.models.money;

public class Account {

	protected int money = 0;	
	
	public boolean addMoney(int money) {
		this.money += money;
		return true;
	}

	public boolean subMoney(int money) {
		if(this.money < money) return false;
		this.money -= money;
		return true;		
	}

	public boolean setMoney(int money) {
		if(money <= 0) return false;
		this.money = money;
		return true;
	}

	public int getMoney() {
		return this.money;
	}

}
