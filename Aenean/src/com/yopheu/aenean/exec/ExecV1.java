package com.yopheu.aenean.exec;

import com.yopheu.aenean.models.CommDataWrapper;
import com.yopheu.aenean.models.DeckDto;
import com.yopheu.aenean.service.GameService;
import com.yopheu.aenean.service.impl.GameServiceImplV1;
import com.yopheu.aenean.service.impl.ViewServiceImplV1;

public class ExecV1 {
	public static void main(String[] args) {
//		System.out.println("콘솔 구동 블랙잭");
//		AeneanGame game = new AeneanGame();
		
		GameService gameService = new GameServiceImplV1();
		gameService.start();
		
	}
}
