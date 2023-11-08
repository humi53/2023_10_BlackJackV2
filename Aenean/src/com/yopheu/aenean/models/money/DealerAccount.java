package com.yopheu.aenean.models.money;

public class DealerAccount extends Account{
	private static DealerAccount instance = new DealerAccount();
	private DealerAccount() {}
	public static DealerAccount getInstance() {
		return instance;
	}
	// 딜러는 -되도 괜찮음.
	@Override
	public boolean subMoney(int money) {
		super.money -= money;
		if(super.money < 0) return false;	// 딜러 잔고가 -인지 확인
		return true;
	}
	
	
}
