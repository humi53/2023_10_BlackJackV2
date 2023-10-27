package com.yopheu.aenean.models.money;

public class DealerMoney extends Money{
	private static DealerMoney instance = new DealerMoney();
	private DealerMoney() {}
	public static DealerMoney getInstance() {
		return instance;
	}
	// 딜러는 -되도 괜찮음.
	@Override
	public boolean sub(int money) {
		super.money -= money;
		if(super.money < 0) return false;	// 딜러 잔고가 -인지 확인
		return true;
	}
	
	
}
