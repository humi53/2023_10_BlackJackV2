package com.yopheu.aenean.exec;

import com.yopheu.aenean.game.AeneanGame;
import com.yopheu.aenean.models.Suit;
import com.yopheu.aenean.service.ViewService;
import com.yopheu.aenean.service.impl.ViewServiceImplV1;
import com.yopheu.aenean.service.modules.impl.CommDataModuleImplV1;

public class ExecV1 {
	public static void main(String[] args) {
//		System.out.println("콘솔 구동 블랙잭");
//		AeneanGame game = new AeneanGame();
		
		ViewServiceImplV1 viewService = new ViewServiceImplV1(new CommDataModuleImplV1());
		System.out.println("출력 테스트");
		viewService.sample();
		
		
	}
}
